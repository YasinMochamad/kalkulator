package id.co.bni.calculator.configs;

import java.lang.annotation.*;

/**
 * @ApiOrder - Annotation untuk menandai urutan API (2 digit) di setiap controller method.
 *
 * responseCode 7 digit: [HTTP_STATUS_3digit][project_order_2digit][api_order_2digit]
 *
 * api_order (2 digit terakhir) SELALU mengikuti urutan endpoint/service,
 * termasuk saat terjadi error/exception.
 *
 * Contoh penggunaan:
 *   @GetMapping
 *   @ApiOrder("01")
 *   public ResponseEntity<ApiResponse<List<Laptop>>> getAll() { ... }
 *
 *   @PostMapping
 *   @ApiOrder("02")
 *   public ResponseEntity<ApiResponse<Laptop>> create(@RequestBody Laptop laptop) { ... }
 *
 *   @GetMapping("/{id}")
 *   @ApiOrder("03")
 *   public ResponseEntity<ApiResponse<Laptop>> getById(@PathVariable Long id) { ... }
 *
 * Jika endpoint ke-3 (api_order=03) throw exception, responseCode error tetap
 * menggunakan api_order "03":
 *   - NullPointerException → 5000103
 *   - Not found            → 4040103
 *   - Bad request          → 4000103
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiOrder {
    /**
     * Urutan API (2 digit). Contoh: "01", "02", "03", dst.
     */
    String value();
}
