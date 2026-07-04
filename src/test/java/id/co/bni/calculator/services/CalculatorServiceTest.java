package id.co.bni.calculator.services;

import id.co.bni.calculator.models.AppException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CalculatorServiceTest
 * ============================================================================
 * Unit test untuk business logic di CalculatorService:
 *   - add
 *   - subtract
 *   - multiply
 *   - divide (termasuk kasus pembagian dengan nol)
 */
class CalculatorServiceTest {

    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testAdd() {
        double result = calculatorService.add(10, 5);
        assertEquals(15.0, result);
    }

    @Test
    void testAddWithNegativeNumbers() {
        double result = calculatorService.add(-10, 5);
        assertEquals(-5.0, result);
    }

    @Test
    void testSubtract() {
        double result = calculatorService.subtract(10, 5);
        assertEquals(5.0, result);
    }

    @Test
    void testSubtractResultNegative() {
        double result = calculatorService.subtract(5, 10);
        assertEquals(-5.0, result);
    }

    @Test
    void testMultiply() {
        double result = calculatorService.multiply(10, 5);
        assertEquals(50.0, result);
    }

    @Test
    void testMultiplyByZero() {
        double result = calculatorService.multiply(10, 0);
        assertEquals(0.0, result);
    }

    @Test
    void testDivide() {
        double result = calculatorService.divide(10, 5);
        assertEquals(2.0, result);
    }

    @Test
    void testDivideByZeroThrowsException() {
        AppException exception = assertThrows(AppException.class, () -> {
            calculatorService.divide(10, 0);
        });

        assertEquals("04", exception.getApiOrder());
        assertEquals("Pembagian dengan nol tidak diperbolehkan", exception.getMessage());
    }
}
