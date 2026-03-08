<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // Lấy Role từ session để kiểm tra xem có phải Admin đang ghé thăm không
    String currentRole = (String) session.getAttribute("ROLE");
    boolean isAdmin = currentRole != null && (currentRole.equalsIgnoreCase("ADMIN") || currentRole.equalsIgnoreCase("MANAGER") || currentRole.equalsIgnoreCase("SUPERADMIN"));
%>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Reception Dashboard | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; color: #333; padding-bottom: 50px;}
      .navbar-custom { background-color: #198754 !important; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
      .dashboard-header { margin: 40px 0 30px; color: #198754; font-weight: 800; text-align: center; }
      
      .module-card { 
          background: #fff; border-radius: 15px; padding: 35px 25px; height: 100%;
          box-shadow: 0 4px 15px rgba(0,0,0,0.03); transition: all 0.3s ease;
          border: 1px solid #e9ecef; display: flex; flex-direction: column; justify-content: space-between;
          border-top: 5px solid #198754;
      }
      .module-card:hover { transform: translateY(-5px); box-shadow: 0 10px 25px rgba(25, 135, 84, 0.15); border-color: #198754;}
      
      .module-title { font-weight: 800; font-size: 1.3rem; margin-bottom: 12px; color: #212529; text-align: center;}
      .module-desc { color: #6c757d; font-size: 0.95rem; margin-bottom: 25px; line-height: 1.6; text-align: center;}
      
      .btn-module { font-weight: bold; border-radius: 8px; padding: 14px; transition: 0.3s; font-size: 16px;}
      .btn-module:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(25, 135, 84, 0.3); }
      
      .alert-custom { border-radius: 12px; border: 1px solid #badbcc; box-shadow: 0 4px 15px rgba(0,0,0,0.02); background-color: #d1e7dd; color: #0f5132; }
  </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark navbar-custom px-4 py-3">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold" href="#">🛎 QUẦY LỄ TÂN</a>
        <div class="ms-auto d-flex align-items-center">
            <% if (isAdmin) { %>
                <a href="<%=request.getContextPath()%>/admin" class="btn btn-outline-light btn-sm fw-bold me-3">⬅ Quay lại Admin</a>
            <% } %>
            <a href="<%=request.getContextPath()%>/logout" class="btn btn-light btn-sm fw-bold text-success px-3 rounded-pill">Đăng xuất</a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="dashboard-header">Hệ Thống Dành Cho Lễ Tân</h2>
    
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="module-card">
                <div>
                    <div class="module-title">Nghiệp vụ Nhận / Trả phòng</div>
                    <div class="module-desc">Thực hiện quy trình Check-in cho khách đến, Check-out, thanh toán tiền phòng và cập nhật phí dịch vụ Minibar.</div>
                </div>
                <a href="<%=request.getContextPath()%>/reception/checkout.jsp" class="btn btn-success btn-module w-100">
                    Vào màn hình Check-in / Check-out
                </a>
            </div>
        </div>
    </div>

    <div class="row justify-content-center mt-5">
        <div class="col-md-8">
            <div class="alert alert-custom p-4">
                <h5 class="fw-bold mb-2">📌 Ghi chú hệ thống:</h5>
                <p class="mb-0" style="font-size: 15px;">Khi thực hiện <b>Check-out</b>, hệ thống sẽ tự động tạo hóa đơn, trừ số lượng sản phẩm Minibar trong kho và tự động chuyển phòng sang trạng thái <b>Cleaning</b> (Đang dọn dẹp) trên Sơ đồ phòng.</p>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>