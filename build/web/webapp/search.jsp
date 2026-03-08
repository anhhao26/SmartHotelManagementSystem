<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Xác nhận Đặt Phòng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    body { background-color: #f4f7f6; font-family: 'Segoe UI', sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
    .booking-card { background: white; width: 450px; padding: 40px 30px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.08); border-top: 5px solid #0d6efd; }
    h3 { text-align: center; color: #0d6efd; font-weight: 800; margin-bottom: 25px; }
    
    .form-floating > label { color: #6c757d; font-weight: 500; }
    .form-control { border-radius: 8px; background-color: #f8f9fa; border: 1px solid #ced4da; box-shadow: none !important;}
    .form-control:focus { border-color: #0d6efd; background-color: #fff; }
    
    .voucher-input { border: 2px dashed #198754 !important; color: #198754; font-weight: bold; text-transform: uppercase; }
    .voucher-input::placeholder { color: #a3cfbb; font-weight: normal; text-transform: none;}
    
    .btn-submit { background-color: #0d6efd; color: white; width: 100%; padding: 14px; border-radius: 8px; font-weight: bold; font-size: 16px; border: none; transition: 0.3s; margin-top: 10px;}
    .btn-submit:hover { background-color: #0b5ed7; transform: translateY(-2px); box-shadow: 0 5px 15px rgba(13, 110, 253, 0.3); }
    
    .error-msg { color: #dc3545; text-align: center; font-weight: bold; margin-top: 15px; background: #f8d7da; padding: 10px; border-radius: 5px; display: ${empty error ? 'none' : 'block'}; }
</style>
</head>
<body>

<div class="booking-card">
    <h3><i class="bi bi-calendar-check"></i> Xác Nhận Đặt Phòng</h3>

    <form action="${pageContext.request.contextPath}/booking" method="post" onsubmit="loading()">
        
        <div class="form-floating mb-3">
            <input type="text" class="form-control fw-bold text-primary" name="roomId" value="${param.roomId}" readonly required placeholder="Phòng">
            <label>Mã phòng bạn đã chọn</label>
        </div>

        <div class="row g-2 mb-3">
            <div class="col-6">
                <div class="form-floating">
                    <input type="date" class="form-control" name="checkIn" required placeholder="Check In">
                    <label>Ngày nhận (Check-in)</label>
                </div>
            </div>
            <div class="col-6">
                <div class="form-floating">
                    <input type="date" class="form-control" name="checkOut" required placeholder="Check Out">
                    <label>Ngày trả (Check-out)</label>
                </div>
            </div>
        </div>

        <div class="form-floating mb-4">
            <input type="text" class="form-control voucher-input" name="voucherCode" placeholder="Nhập mã giảm giá...">
            <label class="text-success"><i class="bi bi-ticket-perforated"></i> Mã Giảm Giá (Voucher) nếu có</label>
        </div>

        <button type="submit" id="btn" class="btn-submit">Xác Nhận & Thanh Toán</button>
        
        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/rooms" class="text-muted text-decoration-none small hover-primary">⬅ Quay lại chọn phòng khác</a>
        </div>
    </form>

    <div class="error-msg">${error}</div>
</div>

<script>
    function loading(){ 
        let b = document.getElementById("btn"); 
        b.innerHTML = "Đang xử lý hóa đơn..."; 
        b.style.opacity = "0.7"; 
        b.disabled = true;
    }
</script>
</body>
</html>