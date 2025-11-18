import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/vnpay_ipn")
public class VNPayIPNController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        Map<String, String> fields = new HashMap<>();

        // 1️⃣ Collect all parameters from the request
        req.getParameterMap().forEach((key, values) -> {
            if (values != null && values.length > 0) {
                fields.put(key, values[0]);
            }
        });

        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        // 2️⃣ Sort the fields by key to rebuild the hash string
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append("=")
                        .append(URLDecoder.decode(fieldValue, StandardCharsets.UTF_8));
                if (itr.hasNext()) hashData.append("&");
            }
        }

        // 3️⃣ Generate our own secure hash to compare
        String generatedHash = VNPayUtils.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        String jsonResponse;

        try {
            if (generatedHash.equals(vnp_SecureHash)) {
                String txnRef = fields.get("vnp_TxnRef");
                String amountStr = fields.get("vnp_Amount");
                String responseCode = fields.get("vnp_ResponseCode");

                // Simulated checks
                boolean checkOrderId = checkOrderExists(txnRef);
                boolean checkAmount = checkAmountValid(txnRef, amountStr);
                boolean checkOrderStatus = checkPendingStatus(txnRef);

                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            if ("00".equals(responseCode)) {
                                updatePaymentStatus(txnRef, 1); // ✅ success
                            } else {
                                updatePaymentStatus(txnRef, 2); // ❌ failed
                            }
                            jsonResponse = "{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}";
                        } else {
                            jsonResponse = "{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}";
                        }
                    } else {
                        jsonResponse = "{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}";
                    }
                } else {
                    jsonResponse = "{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}";
                }
            } else {
                jsonResponse = "{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse = "{\"RspCode\":\"99\",\"Message\":\"Unknown Error\"}";
        }

        resp.getWriter().write(jsonResponse);
    }

    // --- Mock database check methods below ---
    private boolean checkOrderExists(String txnRef) {
        // TODO: replace with real DB lookup
        return txnRef != null && txnRef.startsWith("EMP");
    }

    private boolean checkAmountValid(String txnRef, String vnp_Amount) {
        // TODO: validate amount from DB
        return vnp_Amount != null && !vnp_Amount.isEmpty();
    }

    private boolean checkPendingStatus(String txnRef) {
        // TODO: check if order is still pending
        return true;
    }

    private void updatePaymentStatus(String txnRef, int status) {
        // TODO: update DB payment_status
        System.out.println("Transaction " + txnRef + " updated with status " + status);
    }
}
