package id.co.bni.calculator.configs;

import id.co.bni.calculator.utils.ApiOrderContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ApiOrderInterceptor - Interceptor yang membaca @ApiOrder dari controller method
 * dan menyimpannya ke ApiOrderContext (ThreadLocal).
 *
 * Sehingga jika terjadi exception, GlobalExceptionHandler bisa tahu
 * api_order dari endpoint mana yang error.
 */
@Component
public class ApiOrderInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiOrderInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                              Object handler) {
        if (handler instanceof HandlerMethod handlerMethod) {
            ApiOrder apiOrder = handlerMethod.getMethodAnnotation(ApiOrder.class);
            if (apiOrder != null) {
                ApiOrderContext.set(apiOrder.value());
                log.debug("ApiOrderContext set: apiOrder={} for {} {}",
                          apiOrder.value(), request.getMethod(), request.getRequestURI());
            } else {
                // Endpoint tanpa @ApiOrder, set default "00"
                ApiOrderContext.set("00");
                log.debug("ApiOrderContext set: apiOrder=00 (default) for {} {}",
                          request.getMethod(), request.getRequestURI());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                 Object handler, Exception ex) {
        ApiOrderContext.clear();
    }
}
