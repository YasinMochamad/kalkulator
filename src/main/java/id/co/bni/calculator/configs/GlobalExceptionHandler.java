package id.co.bni.calculator.configs;

import id.co.bni.calculator.models.ApiResponse;
import id.co.bni.calculator.models.AppException;
import id.co.bni.calculator.utils.ApiOrderContext;
import id.co.bni.calculator.utils.ResponseCodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ResponseCodeHelper rcHelper;

    public GlobalExceptionHandler(ResponseCodeHelper rcHelper) {
        this.rcHelper = rcHelper;
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex) {
        String traceId = MDC.get("traceId");
        String apiOrder = ex.getApiOrder();
        HttpStatus status = ex.getHttpStatus();
        String responseCode = rcHelper.build(String.valueOf(status.value()), apiOrder);

        log.warn("[EXCEPTION] AppException | traceId={} | status={} | apiOrder={} | pesan={}",
                traceId, status.value(), apiOrder, ex.getMessage());

        return ResponseEntity.status(status).body(ApiResponse.error(responseCode, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        String traceId = MDC.get("traceId");
        String apiOrder = ApiOrderContext.getOrDefault("00");

        log.warn("[EXCEPTION] IllegalArgumentException | traceId={} | apiOrder={} | pesan={}",
                traceId, apiOrder, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(rcHelper.badRequest(apiOrder), ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoResourceFoundException ex) {
        String traceId = MDC.get("traceId");
        String apiOrder = ApiOrderContext.getOrDefault("00");

        log.warn("[EXCEPTION] 404 Not Found | traceId={} | apiOrder={} | pesan={}",
                traceId, apiOrder, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(rcHelper.notFound(apiOrder),
                        "Resource tidak ditemukan: " + ex.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointer(NullPointerException ex) {
        String traceId = MDC.get("traceId");
        String apiOrder = ApiOrderContext.getOrDefault("00");

        log.error("[EXCEPTION] NullPointerException | traceId={} | apiOrder={} | pesan={}",
                traceId, apiOrder, ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(rcHelper.internalError(apiOrder),
                        "NullPointerException: " + (ex.getMessage() != null ? ex.getMessage() : "null reference")));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        String traceId = MDC.get("traceId");
        String apiOrder = ApiOrderContext.getOrDefault("00");

        log.error("[EXCEPTION] {} | traceId={} | apiOrder={} | pesan={}",
                ex.getClass().getSimpleName(), traceId, apiOrder, ex.getMessage(), ex);

        String errorMsg = ex.getMessage() != null
                ? ex.getClass().getSimpleName() + ": " + ex.getMessage()
                : ex.getClass().getSimpleName();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(rcHelper.internalError(apiOrder), errorMsg));
    }
}
