public class VNPayConfig {
    // VNPay Sandbox Configuration
    public static final String vnp_TmnCode = "DEMOV210";  // Replace with your actual TMN code
    public static final String vnp_HashSecret = "SECRETKEY123456789";  // Replace with your actual secret key
    public static final String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static final String vnp_ApiUrl = "https://api.sandbox.vnpayment.vn";
    
    // Update these for your environment
    public static final String vnp_ReturnUrl = "http://localhost:8080/vnpay_return";
    public static final String vnp_IpnUrl = "http://localhost:8080/vnpay_ipn";
}
