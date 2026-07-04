@echo off
echo ============================================================
echo   DATA-DRIVEN TESTING - Calculator API
echo   Menggunakan file CSV: data-driven-all-operations.csv
echo ============================================================
cd /d "%~dp0"

echo.
echo [1/2] Pastikan aplikasi Spring Boot sudah running di port 8080...
echo       Jika belum, tekan CTRL+C dan jalankan dulu aplikasinya!
echo.
timeout /T 3

echo [2/2] Menjalankan Newman Data-Driven Testing...
echo.

newman run collections\calculator_data_driven_v1.json ^
  -e collections\calculator_newman_chain_env_v2.json ^
  -d collections\data-driven-all-operations.csv ^
  -r cli,htmlextra ^
  --reporter-htmlextra-export newman-reports\report-data-driven.html ^
  --reporter-htmlextra-title "Calculator Data-Driven Test Report" ^
  --reporter-htmlextra-logs ^
  --reporter-htmlextra-darkTheme

echo.
if %ERRORLEVEL% EQU 0 (
  echo ============================================================
  echo   SEMUA TEST LULUS! Membuka laporan HTML...
  echo ============================================================
  start newman-reports\report-data-driven.html
) else (
  echo ============================================================
  echo   ADA TEST YANG GAGAL! Cek laporan HTML untuk detail.
  echo ============================================================
  start newman-reports\report-data-driven.html
)

pause
