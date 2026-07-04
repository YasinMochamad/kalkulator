package id.co.bni.calculator.services;

import id.co.bni.calculator.models.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private static final Logger log = LoggerFactory.getLogger(CalculatorService.class);

    private String traceId() {
        String t = MDC.get("traceId");
        return t != null ? t : "NO-TRACE";
    }

    public double add(double a, double b) {
        log.info("[{}] ADD | input: a={}, b={}", traceId(), a, b);
        log.debug("[{}] ADD | logic: {} + {}", traceId(), a, b);
        double result = a + b;
        log.info("[{}] ADD | hasil: {} + {} = {}", traceId(), a, b, result);
        return result;
    }

    public double subtract(double a, double b) {
        log.info("[{}] SUBTRACT | input: a={}, b={}", traceId(), a, b);
        log.debug("[{}] SUBTRACT | logic: {} - {}", traceId(), a, b);
        double result = a - b;
        log.info("[{}] SUBTRACT | hasil: {} - {} = {}", traceId(), a, b, result);
        return result;
    }

    public double multiply(double a, double b) {
        log.info("[{}] MULTIPLY | input: a={}, b={}", traceId(), a, b);
        log.debug("[{}] MULTIPLY | logic: {} x {}", traceId(), a, b);
        double result = a * b;
        log.info("[{}] MULTIPLY | hasil: {} x {} = {}", traceId(), a, b, result);
        return result;
    }

    public double divide(double a, double b) {
        log.info("[{}] DIVIDE | input: a={}, b={}", traceId(), a, b);
        log.debug("[{}] DIVIDE | logic: {} / {}", traceId(), a, b);
        if (b == 0) {
            log.warn("[{}] DIVIDE | GAGAL - pembagi adalah nol, a={}", traceId(), a);
            throw AppException.badRequest("04", "Pembagian dengan nol tidak diperbolehkan");
        }
        double result = a / b;
        log.info("[{}] DIVIDE | hasil: {} / {} = {}", traceId(), a, b, result);
        return result;
    }
}
