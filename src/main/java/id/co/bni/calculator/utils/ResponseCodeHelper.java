package id.co.bni.calculator.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ResponseCodeHelper - Helper untuk membuat responseCode standar BNI (7 digit).
 *
 * Format: [HTTP_STATUS_3digit][project_order_2digit][api_order_2digit]
 *
 * v6.0: api_order SELALU mengikuti urutan endpoint/service.
 *       Baik sukses maupun error, api_order tetap sama.
 */
@Component
public class ResponseCodeHelper {

    @Value("${app.project-order:01}")
    private String projectOrder;

    public String build(String httpStatus, String apiOrder) {
        String pOrder = (projectOrder != null && projectOrder.length() == 2) ? projectOrder : "01";
        String aOrder = (apiOrder != null && apiOrder.length() == 2) ? apiOrder : "01";
        return httpStatus + pOrder + aOrder;
    }

    public String ok(String apiOrder) { return build("200", apiOrder); }
    public String created(String apiOrder) { return build("201", apiOrder); }
    public String noContent(String apiOrder) { return build("204", apiOrder); }
    public String badRequest(String apiOrder) { return build("400", apiOrder); }
    public String unauthorized(String apiOrder) { return build("401", apiOrder); }
    public String forbidden(String apiOrder) { return build("403", apiOrder); }
    public String notFound(String apiOrder) { return build("404", apiOrder); }
    public String conflict(String apiOrder) { return build("409", apiOrder); }
    public String unprocessable(String apiOrder) { return build("422", apiOrder); }
    public String internalError(String apiOrder) { return build("500", apiOrder); }
    public String serviceUnavailable(String apiOrder) { return build("503", apiOrder); }

    public String getProjectOrder() { return projectOrder; }
}
