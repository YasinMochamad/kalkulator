import json
import re
import argparse
from datetime import datetime

def parse_args():
    p = argparse.ArgumentParser()
    p.add_argument('--newman-json', required=True)
    p.add_argument('--app-log',     required=True)
    p.add_argument('--output',      required=True)
    return p.parse_args()

def separator(char='=', width=70):
    return char * width

def section(title):
    return f"\n{separator()}\n  {title}\n{separator()}\n"

def sub_section(title):
    return f"\n{'='*70}\n  {title}\n{'='*70}"

# ─────────────────────────────────────────────────────────────────────────────
# Parse app.log jadi dict: { trace_id: [lines...] }
# ─────────────────────────────────────────────────────────────────────────────
def parse_app_log(app_log_path):
    """
    Baca app.log dan kelompokkan per Trace ID.
    Format blok di app.log:
    ----------------------------------------------------------
      TraceID   : 7672B284
      Method    : POST
      URL       : /api/calculator/multiply
      ...
    ----------------------------------------------------------
    """
    ansi_escape = re.compile(r'\x1b\[[0-9;]*m')

    try:
        with open(app_log_path, 'r', encoding='utf-8', errors='replace') as f:
            raw_lines = f.readlines()
    except Exception:
        return {}

    log_by_trace = {}  # { "ABCD1234": ["line1", "line2", ...] }

    current_trace = None
    buffer = []

    for raw in raw_lines:
        line = ansi_escape.sub('', raw).rstrip()

        # Deteksi Trace ID dari baris log
        # Format app.log asli: "  TraceID   : 7672B284"
        trace_match = re.search(r'TraceID\s*:\s*([A-F0-9]{6,})', line, re.IGNORECASE)
        if trace_match:
            tid = trace_match.group(1).upper()
            # Simpan buffer trace sebelumnya jika ada
            if current_trace and current_trace != tid:
                if current_trace not in log_by_trace:
                    log_by_trace[current_trace] = []
                log_by_trace[current_trace].extend(buffer)
                buffer = []
            current_trace = tid

        if current_trace:
            buffer.append(line)

        # Deteksi akhir blok: baris separator penutup "---...---"
        # Hanya simpan jika buffer sudah mengandung TraceID (bukan separator pembuka)
        if current_trace and re.search(r'^-{20,}', line.strip()):
            if any(re.search(r'TraceID\s*:', l, re.IGNORECASE) for l in buffer):
                if current_trace not in log_by_trace:
                    log_by_trace[current_trace] = []
                log_by_trace[current_trace].extend(buffer)
                buffer = []
                current_trace = None

    # Sisa buffer yang belum tersimpan
    if current_trace and buffer:
        if current_trace not in log_by_trace:
            log_by_trace[current_trace] = []
        log_by_trace[current_trace].extend(buffer)

    return log_by_trace


# ─────────────────────────────────────────────────────────────────────────────
# Build report
# ─────────────────────────────────────────────────────────────────────────────
def build_report(newman_json_path, app_log_path, output_path):
    with open(newman_json_path, 'r', encoding='utf-8') as f:
        data = json.load(f)

    log_by_trace = parse_app_log(app_log_path)

    lines = []
    now = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    lines.append(separator())
    lines.append("  CALCULATOR NEWMAN TEST REPORT")
    lines.append(f"  Generated : {now}")
    lines.append(separator())

    # ── SUMMARY ──────────────────────────────────────────────────────────────
    stats   = data.get('run', {}).get('stats', {})
    timings = data.get('run', {}).get('timings', {})
    lines.append(section("SUMMARY"))
    lines.append(f"  Iterations  : {stats.get('iterations',{}).get('total','-')}")
    lines.append(f"  Requests    : {stats.get('requests',{}).get('total','-')}")
    lines.append(f"  Test Scripts: {stats.get('tests',{}).get('total','-')}  "
                 f"(failed: {stats.get('tests',{}).get('failed','-')})")
    lines.append(f"  Assertions  : {stats.get('assertions',{}).get('total','-')}  "
                 f"(failed: {stats.get('assertions',{}).get('failed','-')})")
    total_ms = timings.get('completed', 0) - timings.get('started', 0)
    lines.append(f"  Duration    : {total_ms} ms")

    # ── FAILURES ─────────────────────────────────────────────────────────────
    failures = data.get('run', {}).get('failures', [])
    lines.append(section("FAILURES"))
    if failures:
        for i, fail in enumerate(failures, 1):
            err = fail.get('error', {})
            src = fail.get('source', {})
            lines.append(f"  [{i}] {err.get('name','?')}: {err.get('message','?')}")
            lines.append(f"       at: {src.get('name','?')}")
    else:
        lines.append("  Tidak ada failure. Semua assertion PASS ✅")

    # ── DETAIL PER REQUEST ────────────────────────────────────────────────────
    lines.append(section("DETAIL PER REQUEST"))

    executions = data.get('run', {}).get('executions', [])
    for exec_item in executions:
        item       = exec_item.get('item', {})
        req_name   = item.get('name', '-')
        request    = exec_item.get('request', {})
        response   = exec_item.get('response', {})
        assertions = exec_item.get('assertions', [])

        lines.append(sub_section(req_name))

        # ── URL & Method ──────────────────────────────────────────────────
        url_obj = request.get('url', {})
        if isinstance(url_obj, dict):
            protocol    = url_obj.get('protocol', 'http')
            host        = '.'.join(url_obj.get('host', []))
            port        = url_obj.get('port', '')
            path_parts  = '/'.join(url_obj.get('path', []))
            query_parts = url_obj.get('query', [])

            url = f"{protocol}://{host}"
            if port:
                url += f":{port}"
            if path_parts:
                url += f"/{path_parts}"
            if query_parts:
                qs = '&'.join(f"{q.get('key','')}={q.get('value','')}" for q in query_parts)
                url += f"?{qs}"
        else:
            url = str(url_obj) if url_obj else '-'
        lines.append(f"\n  URL    : {url}")
        lines.append(f"  Method : {request.get('method','-')}")

        # ── Request Headers ───────────────────────────────────────────────
        lines.append("\n  >> REQUEST HEADERS:")
        req_headers = request.get('header', [])
        if req_headers:
            for h in req_headers:
                lines.append(f"     {h.get('key',''):<25}: {h.get('value','')}")
        else:
            lines.append("     (tidak ada header)")

        # ── Request Body / Params ─────────────────────────────────────────
        body = request.get('body')
        if body and body.get('mode') == 'raw':
            lines.append("\n  >> REQUEST BODY:")
            raw_body = body.get('raw', '').strip()
            try:
                pretty = json.dumps(json.loads(raw_body), indent=5, ensure_ascii=False)
                for bl in pretty.splitlines():
                    lines.append(f"     {bl}")
            except Exception:
                lines.append(f"     {raw_body}")
        elif request.get('method', 'GET') == 'GET':
            params = url_obj.get('query', []) if isinstance(url_obj, dict) else []
            if params:
                lines.append("\n  >> REQUEST PARAMS:")
                for p in params:
                    lines.append(f"     {p.get('key',''):<20}: {p.get('value','')}")

        # ── Response Status ───────────────────────────────────────────────
        lines.append(f"\n  >> RESPONSE STATUS : {response.get('code','-')} {response.get('status','-')}")
        lines.append(f"     Response Time   : {response.get('responseTime','-')} ms")
        lines.append(f"     Response Size   : {response.get('responseSize','-')} bytes")

        # ── Response Headers & Trace ID ───────────────────────────────────
        trace_id = None
        lines.append("\n  >> RESPONSE HEADERS:")
        resp_headers = response.get('header', [])
        if resp_headers:
            for h in resp_headers:
                lines.append(f"     {h.get('key',''):<30}: {h.get('value','')}")
                if h.get('key','').lower() == 'x-trace-id':
                    trace_id = h.get('value','').upper()
        else:
            lines.append("     (tidak ada header)")

        # ── Response Body ─────────────────────────────────────────────────
        lines.append("\n  >> RESPONSE BODY:")
        resp_stream = response.get('stream', {})
        if resp_stream:
            body_bytes = resp_stream.get('data', [])
            try:
                body_str = bytes(body_bytes).decode('utf-8')
                pretty = json.dumps(json.loads(body_str), indent=5, ensure_ascii=False)
                for bl in pretty.splitlines():
                    lines.append(f"     {bl}")
            except Exception:
                lines.append("     (binary/tidak bisa di-decode)")
        else:
            lines.append("     (kosong)")

        # ── Assertions ────────────────────────────────────────────────────
        lines.append("\n  >> ASSERTIONS:")
        if assertions:
            for a in assertions:
                err    = a.get('error')
                status = "✅ PASS" if not err else f"❌ FAIL: {err.get('message','')}"
                lines.append(f"     [{status}] {a.get('assertion','?')}")
        else:
            lines.append("     (tidak ada assertion)")

        # ── App / Service Log (match by Trace ID) ─────────────────────────
        lines.append("\n  >> SERVICE LOG (Spring Boot):")
        if trace_id and trace_id in log_by_trace:
            svc_lines = log_by_trace[trace_id]
            if svc_lines:
                for sl in svc_lines:
                    lines.append(f"     {sl}")
            else:
                lines.append(f"     (log kosong untuk Trace ID: {trace_id})")
        elif trace_id:
            lines.append(f"     (tidak ditemukan log untuk Trace ID: {trace_id})")
        else:
            lines.append("     (Trace ID tidak ditemukan di response header)")

        lines.append("")

    # ── PENUTUP ───────────────────────────────────────────────────────────────
    lines.append(f"\n{separator()}")
    lines.append("  END OF REPORT")
    lines.append(separator())

    with open(output_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines))

    print(f"Report berhasil ditulis: {output_path}")
    print(f"Total baris: {len(lines)}")
    print(f"Trace IDs ditemukan di log: {list(log_by_trace.keys())}")


if __name__ == '__main__':
    args = parse_args()
    build_report(args.newman_json, args.app_log, args.output)
