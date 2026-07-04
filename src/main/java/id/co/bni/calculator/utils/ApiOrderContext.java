package id.co.bni.calculator.utils;

/**
 * ApiOrderContext - ThreadLocal untuk menyimpan api_order aktif per-request.
 *
 * Diisi oleh ApiOrderInterceptor sebelum controller method dipanggil.
 * Dibaca oleh GlobalExceptionHandler saat terjadi exception.
 * Di-clear otomatis setelah request selesai.
 *
 * Alur:
 *   1. Request masuk → ApiOrderInterceptor.preHandle()
 *   2. Baca @ApiOrder dari handler method → set ke ThreadLocal
 *   3. Controller method dieksekusi
 *   4a. Sukses → Controller pakai api_order langsung
 *   4b. Error  → GlobalExceptionHandler baca dari ApiOrderContext.get()
 *   5. ApiOrderInterceptor.afterCompletion() → clear ThreadLocal
 */
public final class ApiOrderContext {

    private static final ThreadLocal<String> CURRENT_API_ORDER = new ThreadLocal<>();

    /**
     * Private constructor untuk mencegah instantiation.
     * Class ini adalah utility class yang hanya berisi static methods.
     */
    private ApiOrderContext() {
        throw new UnsupportedOperationException("Utility class - tidak boleh di-instantiate");
    }

    /**
     * Set api_order untuk request thread saat ini.
     * Dipanggil oleh ApiOrderInterceptor.
     */
    public static void set(String apiOrder) {
        CURRENT_API_ORDER.set(apiOrder);
    }

    /**
     * Get api_order untuk request thread saat ini.
     * Returns null jika belum di-set (endpoint tanpa @ApiOrder).
     */
    public static String get() {
        return CURRENT_API_ORDER.get();
    }

    /**
     * Get api_order dengan fallback default.
     * Jika belum di-set, return defaultOrder.
     */
    public static String getOrDefault(String defaultOrder) {
        String order = CURRENT_API_ORDER.get();
        return (order != null && !order.isEmpty()) ? order : defaultOrder;
    }

    /**
     * Clear api_order setelah request selesai.
     * WAJIB dipanggil di afterCompletion untuk mencegah memory leak.
     */
    public static void clear() {
        CURRENT_API_ORDER.remove();
    }
}
