package id.co.bni.calculator.controllers;

import id.co.bni.calculator.configs.ApiOrder;
import id.co.bni.calculator.models.ApiResponse;
import id.co.bni.calculator.models.CalculatorRequest;
import id.co.bni.calculator.services.CalculatorService;
import id.co.bni.calculator.utils.ResponseCodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    private final CalculatorService calculatorService;
    private final ResponseCodeHelper rcHelper;

    public CalculatorController(CalculatorService calculatorService, ResponseCodeHelper rcHelper) {
        this.calculatorService = calculatorService;
        this.rcHelper = rcHelper;
    }

    private String traceId() {
        String t = MDC.get("traceId");
        return t != null ? t : "NO-TRACE";
    }

    // =========================================================================
    // ADD
    // =========================================================================

    @PostMapping("/add")
    @ApiOrder("01")
    public ResponseEntity<ApiResponse<Double>> add(@RequestBody CalculatorRequest request) {
        log.info("[{}] ADD via Body | input: a={}, b={}", traceId(), request.getA(), request.getB());
        double result = calculatorService.add(request.getA(), request.getB());
        log.info("[{}] ADD via Body | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("01"), result));
    }

    @GetMapping("/add")
    @ApiOrder("01")
    public ResponseEntity<ApiResponse<Double>> addByQueryParam(
            @RequestParam double a,
            @RequestParam double b) {
        log.info("[{}] ADD via QueryParam | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.add(a, b);
        log.info("[{}] ADD via QueryParam | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("01"), result));
    }

    @GetMapping("/add/{a}/{b}")
    @ApiOrder("01")
    public ResponseEntity<ApiResponse<Double>> addByPathVariable(
            @PathVariable double a,
            @PathVariable double b) {
        log.info("[{}] ADD via PathVariable | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.add(a, b);
        log.info("[{}] ADD via PathVariable | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("01"), result));
    }

    // =========================================================================
    // SUBTRACT
    // =========================================================================

    @PostMapping("/subtract")
    @ApiOrder("02")
    public ResponseEntity<ApiResponse<Double>> subtract(@RequestBody CalculatorRequest request) {
        log.info("[{}] SUBTRACT via Body | input: a={}, b={}", traceId(), request.getA(), request.getB());
        double result = calculatorService.subtract(request.getA(), request.getB());
        log.info("[{}] SUBTRACT via Body | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("02"), result));
    }

    @GetMapping("/subtract")
    @ApiOrder("02")
    public ResponseEntity<ApiResponse<Double>> subtractByQueryParam(
            @RequestParam double a,
            @RequestParam double b) {
        log.info("[{}] SUBTRACT via QueryParam | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.subtract(a, b);
        log.info("[{}] SUBTRACT via QueryParam | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("02"), result));
    }

    @GetMapping("/subtract/{a}/{b}")
    @ApiOrder("02")
    public ResponseEntity<ApiResponse<Double>> subtractByPathVariable(
            @PathVariable double a,
            @PathVariable double b) {
        log.info("[{}] SUBTRACT via PathVariable | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.subtract(a, b);
        log.info("[{}] SUBTRACT via PathVariable | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("02"), result));
    }

    // =========================================================================
    // MULTIPLY
    // =========================================================================

    @PostMapping("/multiply")
    @ApiOrder("03")
    public ResponseEntity<ApiResponse<Double>> multiply(@RequestBody CalculatorRequest request) {
        log.info("[{}] MULTIPLY via Body | input: a={}, b={}", traceId(), request.getA(), request.getB());
        double result = calculatorService.multiply(request.getA(), request.getB());
        log.info("[{}] MULTIPLY via Body | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("03"), result));
    }

    @GetMapping("/multiply")
    @ApiOrder("03")
    public ResponseEntity<ApiResponse<Double>> multiplyByQueryParam(
            @RequestParam double a,
            @RequestParam double b) {
        log.info("[{}] MULTIPLY via QueryParam | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.multiply(a, b);
        log.info("[{}] MULTIPLY via QueryParam | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("03"), result));
    }

    @GetMapping("/multiply/{a}/{b}")
    @ApiOrder("03")
    public ResponseEntity<ApiResponse<Double>> multiplyByPathVariable(
            @PathVariable double a,
            @PathVariable double b) {
        log.info("[{}] MULTIPLY via PathVariable | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.multiply(a, b);
        log.info("[{}] MULTIPLY via PathVariable | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("03"), result));
    }

    // =========================================================================
    // DIVIDE
    // =========================================================================

    @PostMapping("/divide")
    @ApiOrder("04")
    public ResponseEntity<ApiResponse<Double>> divide(@RequestBody CalculatorRequest request) {
        log.info("[{}] DIVIDE via Body | input: a={}, b={}", traceId(), request.getA(), request.getB());
        double result = calculatorService.divide(request.getA(), request.getB());
        log.info("[{}] DIVIDE via Body | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("04"), result));
    }

    @GetMapping("/divide")
    @ApiOrder("04")
    public ResponseEntity<ApiResponse<Double>> divideByQueryParam(
            @RequestParam double a,
            @RequestParam double b) {
        log.info("[{}] DIVIDE via QueryParam | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.divide(a, b);
        log.info("[{}] DIVIDE via QueryParam | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("04"), result));
    }

    @GetMapping("/divide/{a}/{b}")
    @ApiOrder("04")
    public ResponseEntity<ApiResponse<Double>> divideByPathVariable(
            @PathVariable double a,
            @PathVariable double b) {
        log.info("[{}] DIVIDE via PathVariable | input: a={}, b={}", traceId(), a, b);
        double result = calculatorService.divide(a, b);
        log.info("[{}] DIVIDE via PathVariable | hasil: {}", traceId(), result);
        return ResponseEntity.ok(ApiResponse.ok(rcHelper.ok("04"), result));
    }
}
