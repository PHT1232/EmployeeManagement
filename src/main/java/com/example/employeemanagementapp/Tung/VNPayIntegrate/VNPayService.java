import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * VNPayService - Handles VNPay integration with JavaFX
 * Launches payment URL in WebView and handles callbacks
 */
public class VNPayService {
    
    private WebView webView;
    private Stage paymentStage;
    private VNPaymentCallback callback;
    
    public interface VNPaymentCallback {
        void onPaymentSuccess(String txnRef, String amount);
        void onPaymentFailure(String message);
        void onPaymentCancel();
    }
    
    /**
     * Open VNPay payment in a new window
     */
    public void initiatePayment(String employeeName, double salary, String bankCode, 
                                VNPaymentCallback callback) {
        this.callback = callback;
        
        try {
            // 1. Generate VNPay URL
            String paymentUrl = VNPayPaymentGenerator.generateVNPayUrl(employeeName, salary, bankCode);
            
            // 2. Create WebView to display payment page
            webView = new WebView();
            WebEngine engine = webView.getEngine();
            
            // 3. Monitor for return URL (payment result)
            engine.locationProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && newVal.contains("/vnpay_return")) {
                    handlePaymentReturn(newVal);
                }
            });
            
            // 4. Load the payment URL
            engine.load(paymentUrl);
            
            // 5. Display in new Stage
            paymentStage = new Stage();
            paymentStage.setTitle("VNPay Payment");
            paymentStage.setWidth(600);
            paymentStage.setHeight(700);
            paymentStage.setScene(new javafx.scene.Scene(webView));
            paymentStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            callback.onPaymentFailure("Failed to initiate payment: " + e.getMessage());
        }
    }
    
    /**
     * Parse the return URL and extract payment status
     */
    private void handlePaymentReturn(String returnUrl) {
        try {
            java.net.URL url = new java.net.URL(returnUrl);
            String query = url.getQuery();
            
            if (query != null) {
                java.util.Map<String, String> params = parseQueryString(query);
                String responseCode = params.get("vnp_ResponseCode");
                String txnRef = params.get("vnp_TxnRef");
                String amount = params.get("vnp_Amount");
                
                if ("00".equals(responseCode)) {
                    Platform.runLater(() -> {
                        callback.onPaymentSuccess(txnRef, amount);
                        paymentStage.close();
                    });
                } else {
                    Platform.runLater(() -> {
                        callback.onPaymentFailure("Payment failed with code: " + responseCode);
                        paymentStage.close();
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                callback.onPaymentFailure("Error processing payment result: " + e.getMessage());
            });
        }
    }
    
    /**
     * Parse URL query string
     */
    private java.util.Map<String, String> parseQueryString(String query) {
        java.util.Map<String, String> params = new java.util.HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] parts = param.split("=");
                if (parts.length == 2) {
                    params.put(parts[0], java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8));
                }
            }
        }
        return params;
    }
}
