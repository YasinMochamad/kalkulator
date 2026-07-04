package id.co.bni.calculator.models;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * ApiResponse - Standard Response BNI (v6.0)
 * ============================================
 * Format responseCode (7 digit):
 *   [HTTP_STATUS_3digit] + [project_order_2digit] + [api_order_2digit]
 *
 * api_order (2 digit terakhir) SELALU mengikuti urutan endpoint/service,
 * baik untuk response sukses maupun error.
 *
 * Contoh endpoint GET /api/laptop (api_order=01):
 *   Sukses → 2000101
 *   Error  → 5000101  (bukan 5000199!)
 *
 * @param <T> Tipe data payload
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean isSuccess;
    private String responseCode;
    private String responseMessage;
    private T payload;

    private ApiResponse() {}

    private ApiResponse(boolean isSuccess, String responseCode, String responseMessage, T payload) {
        this.isSuccess = isSuccess;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.payload = payload;
    }

    // ====== Static Factory Methods ======

    public static <T> ApiResponse<T> ok(String fullResponseCode, T payload) {
        return new ApiResponse<>(true, fullResponseCode, "Sukses", payload);
    }

    public static <T> ApiResponse<T> created(String fullResponseCode, T payload) {
        return new ApiResponse<>(true, fullResponseCode, "Data berhasil dibuat", payload);
    }

    public static <T> ApiResponse<T> success(String fullResponseCode, String message) {
        return new ApiResponse<>(true, fullResponseCode, message, null);
    }

    public static <T> ApiResponse<T> success(String fullResponseCode, String message, T payload) {
        return new ApiResponse<>(true, fullResponseCode, message, payload);
    }

    public static <T> ApiResponse<T> error(String fullResponseCode, String errorMessage) {
        return new ApiResponse<>(false, fullResponseCode, errorMessage, null);
    }

    public static <T> ApiResponse<T> errorWithPayload(String fullResponseCode, String errorMessage, T payload) {
        return new ApiResponse<>(false, fullResponseCode, errorMessage, payload);
    }

    // ====== Getters ======
    public boolean isSuccess() { return isSuccess; }
    public String getResponseCode() { return responseCode; }
    public String getResponseMessage() { return responseMessage; }
    public T getPayload() { return payload; }

    // ====== Setters ======
    public void setSuccess(boolean success) { isSuccess = success; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }
    public void setResponseMessage(String responseMessage) { this.responseMessage = responseMessage; }
    public void setPayload(T payload) { this.payload = payload; }

    @Override
    public String toString() {
        return "ApiResponse{isSuccess=" + isSuccess +
                ", responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", payload=" + payload + '}';
    }
}
