package id.co.bni.calculator.controllers;

import id.co.bni.calculator.models.ApiResponse;
import id.co.bni.calculator.models.CalculatorRequest;
import id.co.bni.calculator.services.CalculatorService;
import id.co.bni.calculator.utils.ResponseCodeHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CalculatorControllerTest
 * ============================================================================
 * Unit test untuk semua REST endpoint di CalculatorController:
 *
 *   Request Body (POST):
 *     - POST /api/calculator/add
 *     - POST /api/calculator/subtract
 *     - POST /api/calculator/multiply
 *     - POST /api/calculator/divide
 *
 *   Query Param (GET):
 *     - GET /api/calculator/add?a=10&b=5
 *     - GET /api/calculator/subtract?a=10&b=5
 *     - GET /api/calculator/multiply?a=10&b=5
 *     - GET /api/calculator/divide?a=10&b=5
 *
 *   Path Variable (GET):
 *     - GET /api/calculator/add/10/5
 *     - GET /api/calculator/subtract/10/5
 *     - GET /api/calculator/multiply/10/5
 *     - GET /api/calculator/divide/10/5
 *
 * Strategi: Pure unit test dengan Mockito (tanpa @WebMvcTest)
 * → lebih cepat dan tidak load Spring context.
 */
@ExtendWith(MockitoExtension.class)
class CalculatorControllerTest {

    @Mock
    private CalculatorService calculatorService;

    @Mock
    private ResponseCodeHelper rcHelper;

    @InjectMocks
    private CalculatorController calculatorController;

    private CalculatorRequest request;

    @BeforeEach
    void setUp() {
        request = new CalculatorRequest(10, 5);
    }

    // =========================================================================
    // ADD
    // =========================================================================

    @Test
    @DisplayName("POST /add → 200 OK dengan hasil penjumlahan (Request Body)")
    void testAddByBody() {
        when(calculatorService.add(10, 5)).thenReturn(15.0);
        when(rcHelper.ok("01")).thenReturn("2000101");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.add(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000101", response.getBody().getResponseCode());
        assertEquals(15.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).add(10, 5);
        verify(rcHelper, times(1)).ok("01");
    }

    @Test
    @DisplayName("GET /add?a=10&b=5 → 200 OK dengan hasil penjumlahan (Query Param)")
    void testAddByQueryParam() {
        when(calculatorService.add(10, 5)).thenReturn(15.0);
        when(rcHelper.ok("01")).thenReturn("2000101");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.addByQueryParam(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000101", response.getBody().getResponseCode());
        assertEquals(15.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).add(10, 5);
        verify(rcHelper, times(1)).ok("01");
    }

    @Test
    @DisplayName("GET /add/10/5 → 200 OK dengan hasil penjumlahan (Path Variable)")
    void testAddByPathVariable() {
        when(calculatorService.add(10, 5)).thenReturn(15.0);
        when(rcHelper.ok("01")).thenReturn("2000101");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.addByPathVariable(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000101", response.getBody().getResponseCode());
        assertEquals(15.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).add(10, 5);
        verify(rcHelper, times(1)).ok("01");
    }

    // =========================================================================
    // SUBTRACT
    // =========================================================================

    @Test
    @DisplayName("POST /subtract → 200 OK dengan hasil pengurangan (Request Body)")
    void testSubtractByBody() {
        when(calculatorService.subtract(10, 5)).thenReturn(5.0);
        when(rcHelper.ok("02")).thenReturn("2000102");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.subtract(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000102", response.getBody().getResponseCode());
        assertEquals(5.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).subtract(10, 5);
        verify(rcHelper, times(1)).ok("02");
    }

    @Test
    @DisplayName("GET /subtract?a=10&b=5 → 200 OK dengan hasil pengurangan (Query Param)")
    void testSubtractByQueryParam() {
        when(calculatorService.subtract(10, 5)).thenReturn(5.0);
        when(rcHelper.ok("02")).thenReturn("2000102");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.subtractByQueryParam(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000102", response.getBody().getResponseCode());
        assertEquals(5.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).subtract(10, 5);
        verify(rcHelper, times(1)).ok("02");
    }

    @Test
    @DisplayName("GET /subtract/10/5 → 200 OK dengan hasil pengurangan (Path Variable)")
    void testSubtractByPathVariable() {
        when(calculatorService.subtract(10, 5)).thenReturn(5.0);
        when(rcHelper.ok("02")).thenReturn("2000102");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.subtractByPathVariable(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000102", response.getBody().getResponseCode());
        assertEquals(5.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).subtract(10, 5);
        verify(rcHelper, times(1)).ok("02");
    }

    // =========================================================================
    // MULTIPLY
    // =========================================================================

    @Test
    @DisplayName("POST /multiply → 200 OK dengan hasil perkalian (Request Body)")
    void testMultiplyByBody() {
        when(calculatorService.multiply(10, 5)).thenReturn(50.0);
        when(rcHelper.ok("03")).thenReturn("2000103");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.multiply(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000103", response.getBody().getResponseCode());
        assertEquals(50.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).multiply(10, 5);
        verify(rcHelper, times(1)).ok("03");
    }

    @Test
    @DisplayName("GET /multiply?a=10&b=5 → 200 OK dengan hasil perkalian (Query Param)")
    void testMultiplyByQueryParam() {
        when(calculatorService.multiply(10, 5)).thenReturn(50.0);
        when(rcHelper.ok("03")).thenReturn("2000103");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.multiplyByQueryParam(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000103", response.getBody().getResponseCode());
        assertEquals(50.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).multiply(10, 5);
        verify(rcHelper, times(1)).ok("03");
    }

    @Test
    @DisplayName("GET /multiply/10/5 → 200 OK dengan hasil perkalian (Path Variable)")
    void testMultiplyByPathVariable() {
        when(calculatorService.multiply(10, 5)).thenReturn(50.0);
        when(rcHelper.ok("03")).thenReturn("2000103");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.multiplyByPathVariable(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000103", response.getBody().getResponseCode());
        assertEquals(50.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).multiply(10, 5);
        verify(rcHelper, times(1)).ok("03");
    }

    // =========================================================================
    // DIVIDE
    // =========================================================================

    @Test
    @DisplayName("POST /divide → 200 OK dengan hasil pembagian (Request Body)")
    void testDivideByBody() {
        when(calculatorService.divide(10, 5)).thenReturn(2.0);
        when(rcHelper.ok("04")).thenReturn("2000104");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.divide(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000104", response.getBody().getResponseCode());
        assertEquals(2.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).divide(10, 5);
        verify(rcHelper, times(1)).ok("04");
    }

    @Test
    @DisplayName("GET /divide?a=10&b=5 → 200 OK dengan hasil pembagian (Query Param)")
    void testDivideByQueryParam() {
        when(calculatorService.divide(10, 5)).thenReturn(2.0);
        when(rcHelper.ok("04")).thenReturn("2000104");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.divideByQueryParam(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000104", response.getBody().getResponseCode());
        assertEquals(2.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).divide(10, 5);
        verify(rcHelper, times(1)).ok("04");
    }

    @Test
    @DisplayName("GET /divide/10/5 → 200 OK dengan hasil pembagian (Path Variable)")
    void testDivideByPathVariable() {
        when(calculatorService.divide(10, 5)).thenReturn(2.0);
        when(rcHelper.ok("04")).thenReturn("2000104");

        ResponseEntity<ApiResponse<Double>> response = calculatorController.divideByPathVariable(10, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("2000104", response.getBody().getResponseCode());
        assertEquals(2.0, response.getBody().getPayload());
        verify(calculatorService, times(1)).divide(10, 5);
        verify(rcHelper, times(1)).ok("04");
    }
}
