import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/vnpay_return")
public class VNPayReturnController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");

        Map<String, String> fields = new HashMap<>();

        // 1️⃣ Collect all parameters
        req.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                fields.put(key, values[0]);
            }
        });

        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // 2️⃣ Rebuild hash data string for verification
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName)
                        .append("=")
                        .append(URLDecoder.decode(fieldValue, StandardCharsets.UTF_8));
                if (itr.hasNext())
                    hashData.append("&");
            }
        }

        // 3️⃣ Generate the hash again using your secret key
        String signValue = VNPayUtils.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());

        // 4️⃣ Validate checksum and payment result
        if (signValue.equals(vnp_SecureHash)) {
            String responseCode = req.getParameter("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                StringBuilder html = new StringBuilder();
                html.append("<html>");
                html.append("<body style=\\\"font-family:sans-serif; text-align:center; margin-top:50px;\\\">");
                html.append("<h2 style=\\\"color:green;\\\">🎉 Giao dịch thành công!</h2>");
                html.append("<p>Mã giao dịch: %s</p>");
                html.append("<p>Số tiền: %s VND</p>");
                html.append("<a href=\\\"/\\\">⬅️ Quay lại trang chủ</a>");
                html.append("</body>");
                html.append("</html>");
                // formatted(req.getParameter("vnp_TxnRef"),
                //         req.getParameter("vnp_Amount"));
            } else {
                // html = "
                //         <html>
                //         <body style="font-family:sans-serif; text-align:center; margin-top:50px;">
                //             <h2 style="color:red;">❌ Giao dịch không thành công</h2>
                //             <p>Mã lỗi: %s</p>
                //             <a href="/">⬅️ Quay lại trang chủ</a>
                //         </body>
                //         </html>
                //         ".formatted(responseCode);
            }
        } else {
            // html = "
            //         <html>
            //         <body style='font-family:sans-serif; text-align:center; margin-top:50px;>
            //             <h2 style='color:orange;'>⚠️ Chữ ký không hợp lệ!</h2>
            //             <a href='/'>⬅️ Quay lại</a>
            //         </body>
            //         </html>
            //         "
            //             ;
        }

        resp.getWriter().write(html);
    }
}
