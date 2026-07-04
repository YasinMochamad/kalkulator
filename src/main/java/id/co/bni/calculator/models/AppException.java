package id.co.bni.calculator.models;

import org.springframework.http.HttpStatus;

/**
 * AppException - Custom exception yang membawa apiOrder dan httpStatus.
 *
 * Gunakan exception ini di controller/service untuk throw error
 * yang secara eksplisit membawa api_order endpoint yang error.
 *
 * Contoh penggunaan:
 *   // Di service, endpoint ke-3 (api_order="03"):
 *   throw new AppException("03", HttpStatus.NOT_FOUND, "Laptop dengan ID " + id + " tidak ditemukan");
 *
 *   // Di service, endpoint ke-2 (api_order="02"):
 *   throw new AppException("02", HttpStatus.BAD_REQUEST, "Field brand tidak boleh kosong");
 *
 * GlobalExceptionHandler akan mengambil apiOrder dari AppException
 * untuk membuat responseCode yang sesuai.
 *
 * Catatan: Jika tidak menggunakan AppException (misal NullPointerException biasa),
 * GlobalExceptionHandler akan fallback ke ApiOrderContext (ThreadLocal)
 * yang di-set oleh @ApiOrder annotation + ApiOrderInterceptor.
 */
public class AppException extends RuntimeException {

    private final String apiOrder;
    private final HttpStatus httpStatus;

    /**
     * @param apiOrder   2 digit urutan API, contoh: "01", "02", "03"
     * @param httpStatus HTTP status code (misal: HttpStatus.NOT_FOUND, HttpStatus.BAD_REQUEST)
     * @param message    Pesan error yang menjelaskan penyebab error
     */
    public AppException(String apiOrder, HttpStatus httpStatus, String message) {
        super(message);
        this.apiOrder = apiOrder;
        this.httpStatus = httpStatus;
    }

    /**
     * Shortcut untuk HTTP 404 Not Found.
     */
    public static AppException notFound(String apiOrder, String message) {
        return new AppException(apiOrder, HttpStatus.NOT_FOUND, message);
    }

    /**
     * Shortcut untuk HTTP 400 Bad Request.
     */
    public static AppException badRequest(String apiOrder, String message) {
        return new AppException(apiOrder, HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Shortcut untuk HTTP 409 Conflict.
     */
    public static AppException conflict(String apiOrder, String message) {
        return new AppException(apiOrder, HttpStatus.CONFLICT, message);
    }

    /**
     * Shortcut untuk HTTP 500 Internal Server Error.
     */
    public static AppException internalError(String apiOrder, String message) {
        return new AppException(apiOrder, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public String getApiOrder() { return apiOrder; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}
