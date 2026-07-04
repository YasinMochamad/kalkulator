package id.co.bni.calculator.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ResponseCodeHelperTest
 * ============================================================================
 * Unit test untuk ResponseCodeHelper:
 *   - build() method dengan berbagai input (termasuk edge case null/length)
 *   - Semua shortcut method (ok, created, badRequest, dll)
 *   - getProjectOrder()
 *
 * Target: 100% line + branch coverage
 */
class ResponseCodeHelperTest {

    private ResponseCodeHelper helper;

    @BeforeEach
    void setUp() {
        helper = new ResponseCodeHelper();
        // Inject @Value field via reflection
        ReflectionTestUtils.setField(helper, "projectOrder", "01");
    }

    // ============== BUILD METHOD - Branch Coverage ==============

    @Nested
    @DisplayName("build() method - branch coverage")
    class BuildMethodTests {

        @Test
        @DisplayName("Input normal: httpStatus=200, apiOrder=01, projectOrder=01")
        void testBuildWithValidInputs() {
            String result = helper.build("200", "01");
            assertEquals("2000101", result);
        }

        @Test
        @DisplayName("apiOrder = null → fallback ke '01'")
        void testBuildWithNullApiOrder() {
            String result = helper.build("200", null);
            assertEquals("2000101", result);
        }

        @Test
        @DisplayName("apiOrder dengan length != 2 → fallback ke '01'")
        void testBuildWithInvalidLengthApiOrder() {
            String result = helper.build("404", "999");
            assertEquals("4040101", result);
        }

        @Test
        @DisplayName("apiOrder length = 1 → fallback ke '01'")
        void testBuildWithShortApiOrder() {
            String result = helper.build("200", "1");
            assertEquals("2000101", result);
        }

        @Test
        @DisplayName("projectOrder = null → fallback ke '01'")
        void testBuildWithNullProjectOrder() {
            ReflectionTestUtils.setField(helper, "projectOrder", null);
            String result = helper.build("200", "05");
            assertEquals("2000105", result);
        }

        @Test
        @DisplayName("projectOrder length != 2 → fallback ke '01'")
        void testBuildWithInvalidLengthProjectOrder() {
            ReflectionTestUtils.setField(helper, "projectOrder", "999");
            String result = helper.build("500", "03");
            assertEquals("5000103", result);
        }

        @Test
        @DisplayName("Custom projectOrder = '07'")
        void testBuildWithCustomProjectOrder() {
            ReflectionTestUtils.setField(helper, "projectOrder", "07");
            String result = helper.build("200", "02");
            assertEquals("2000702", result);
        }
    }

    // ============== SHORTCUT METHODS - Line Coverage ==============

    @Nested
    @DisplayName("Shortcut methods - HTTP status code")
    class ShortcutMethodTests {

        @Test
        void testOk() {
            assertEquals("2000101", helper.ok("01"));
        }

        @Test
        void testCreated() {
            assertEquals("2010101", helper.created("01"));
        }

        @Test
        void testNoContent() {
            assertEquals("2040101", helper.noContent("01"));
        }

        @Test
        void testBadRequest() {
            assertEquals("4000101", helper.badRequest("01"));
        }

        @Test
        void testUnauthorized() {
            assertEquals("4010101", helper.unauthorized("01"));
        }

        @Test
        void testForbidden() {
            assertEquals("4030101", helper.forbidden("01"));
        }

        @Test
        void testNotFound() {
            assertEquals("4040101", helper.notFound("01"));
        }

        @Test
        void testConflict() {
            assertEquals("4090101", helper.conflict("01"));
        }

        @Test
        void testUnprocessable() {
            assertEquals("4220101", helper.unprocessable("01"));
        }

        @Test
        void testInternalError() {
            assertEquals("5000101", helper.internalError("01"));
        }

        @Test
        void testServiceUnavailable() {
            assertEquals("5030101", helper.serviceUnavailable("01"));
        }
    }

    // ============== GETTER ==============

    @Test
    @DisplayName("getProjectOrder() return projectOrder yang di-set")
    void testGetProjectOrder() {
        assertEquals("01", helper.getProjectOrder());

        ReflectionTestUtils.setField(helper, "projectOrder", "99");
        assertEquals("99", helper.getProjectOrder());
    }
}
