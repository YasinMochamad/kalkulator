package id.co.bni.calculator.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig - Register ApiOrderInterceptor ke Spring MVC.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ApiOrderInterceptor apiOrderInterceptor;

    public WebConfig(ApiOrderInterceptor apiOrderInterceptor) {
        this.apiOrderInterceptor = apiOrderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiOrderInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/actuator/**", "/health", "/favicon.ico");
    }
}
