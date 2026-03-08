<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>Thanh Toán - SmartHotel</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body { background-color: #f4f7f6; font-family: 'Segoe UI', sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
    .payment-card { background: white; width: 400px; padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.08); text-align: center; border-top: 5px solid #198754; }
    h3 { color: #198754; font-weight: 800; margin-bottom: 5px; }
    .sub-title { color: #6c757d; font-size: 14px; margin-bottom: 25px; }
    
    .bill-info { background: #f8f9fa; border: 1px dashed #ced4da; padding: 15px; border-radius: 10px; margin-bottom: 25px; text-align: left;}
    .bill-info p { margin: 5px 0; font-size: 15px; color: #495057;}
    .total-price { font-size: 24px; color: #dc3545; font-weight: 900; text-align: center; margin-top: 10px; border-top: 1px solid #dee2e6; padding-top: 10px;}
    
    .qr-box { padding: 10px; background: white; border: 1px solid #e9ecef; border-radius: 10px; display: inline-block; margin-bottom: 25px; box-shadow: 0 4px 10px rgba(0,0,0,0.05);}
    
    .btn-pay { background-color: #198754; color: white; width: 100%; padding: 14px; border-radius: 8px; font-weight: bold; font-size: 16px; border: none; transition: 0.3s; }
    .btn-pay:hover { background-color: #157347; transform: translateY(-2px); box-shadow: 0 5px 15px rgba(25, 135, 84, 0.3); }
</style>
</head>
<body>

<div class="payment-card">
    <h3>Thanh Toán An Toàn</h3>
    <div class="sub-title">Quét mã QR bằng ứng dụng ngân hàng</div>

    <div class="bill-info">
        <p>Phòng đặt: <b>${booking.room.roomNumber}</b></p>
        <p>Mã đơn hàng: <b>#BK-${booking.bookingID}</b></p>
        <p>Khách hàng: <b>${booking.customer.fullName}</b></p>
        <div class="total-price">
            <fmt:formatNumber value="${booking.totalAmount}" pattern="#,###"/> VNĐ
        </div>
    </div>

    <div class="qr-box">
        <img src="https://api.qrserver.com/v1/create-qr-code/?size=180x180&data=PAY-${booking.bookingID}-${booking.totalAmount}" alt="QR Code">
    </div>

    <form action="${pageContext.request.contextPath}/confirmPayment" method="post" onsubmit="pay()">
        <input type="hidden" name="id" value="${booking.bookingID}">
        <input type="hidden" name="email" value="${booking.customer.email}">
        <button type="submit" id="payBtn" class="btn-pay">Tôi đã thanh toán xong</button>
    </form>
</div>

<script>
    function pay(){ 
        let b = document.getElementById("payBtn"); 
        b.innerHTML = "Đang xác minh giao dịch..."; 
        b.style.opacity = "0.7"; 
        b.disabled = true;
    }
</script>
</body>
</html>