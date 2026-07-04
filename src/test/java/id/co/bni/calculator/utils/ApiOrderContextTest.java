package id.co.bni.calculator.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ApiOrderContextTest
 * ============================================================================
 * Unit test untuk ApiOrderContext (ThreadLocal utility class):
 *   - set / get / clear
 *   - getOrDefault dengan berbagai input (branch coverage)
 *   - Private constructor (via reflection)
 *
 * Target: 100% line + branch coverage
 */
class ApiOrderContextTest {

    @AfterEach
    void tearDown() {
        // WAJIB clear ThreadLocal untuk mencegah pollution antar test
        ApiOrderContext.clear();
    }

    // ============== SET & GET ==============

    @Test
    @DisplayName("set() lalu get() → value yang di-set ter-return")
    void testSetAndGet() {
        ApiOrderContext.set("05");
        assertEquals("05", ApiOrderContext.get());
    }

    @Test
    @DisplayName("get() tanpa set sebelumnya → return null")
    void testGetWithoutSet() {
        assertNull(ApiOrderContext.get());
    }

    // ============== CLEAR ==============

    @Test
    @DisplayName("clear() menghapus nilai yang di-set")
    void testClear() {
        ApiOrderContext.set("03");
        assertEquals("03", ApiOrderContext.get());

        ApiOrderContext.clear();
        assertNull(ApiOrderContext.get());
    }

    // ============== getOrDefault - Branch Coverage ==============

    @Test
    @DisplayName("getOrDefault: nilai sudah di-set → return nilai yang di-set")
    void testGetOrDefaultWithValueSet() {
        ApiOrderContext.set("07");
        String result = ApiOrderContext.getOrDefault("00");
        assertEquals("07", result);
    }

    @Test
    @DisplayName("getOrDefault: nilai null → return defaultOrder")
    void testGetOrDefaultWithNullValue() {
        // pastikan ThreadLocal kosong
        ApiOrderContext.clear();
        String result = ApiOrderContext.getOrDefault("99");
        assertEquals("99", result);
    }

    @Test
    @DisplayName("getOrDefault: nilai string kosong → return defaultOrder")
    void testGetOrDefaultWithEmptyValue() {
        ApiOrderContext.set("");
        String result = ApiOrderContext.getOrDefault("88");
        assertEquals("88", result);
    }

    @Test
    @DisplayName("getOrDefault: nilai sudah di-set non-kosong → return nilai (bukan default)")
    void testGetOrDefaultDoesNotReturnDefaultWhenSet() {
        ApiOrderContext.set("12");
        String result = ApiOrderContext.getOrDefault("00");
        assertNotEquals("00", result);
        assertEquals("12", result);
    }

    // ============== PRIVATE CONSTRUCTOR - via Reflection ==============

    @Test
    @DisplayName("Private constructor melempar UnsupportedOperationException")
    void testPrivateConstructorThrowsException() throws NoSuchMethodException {
        Constructor<ApiOrderContext> constructor = ApiOrderContext.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException wrapped = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        // Verifikasi root cause adalah UnsupportedOperationException
        Throwable cause = wrapped.getCause();
        assertNotNull(cause);
        assertInstanceOf(UnsupportedOperationException.class, cause);
        assertTrue(cause.getMessage().contains("Utility class"));
    }
}
