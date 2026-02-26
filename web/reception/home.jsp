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
  <title>Receptionist Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-white"> <div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h3>Quản lý Quầy Lễ Tân</h3>
    <div>
      <%-- Nếu là Admin thì hiện thêm nút Quay lại --%>
      <% if (isAdmin) { %>
        <a class="btn btn-outline-light me-2" href="<%=request.getContextPath()%>/admin">Quay lại Admin</a>
      <% } %>
      <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Đăng xuất</a>
    </div>
  </div>

  <div class="row">
      <div class="col-md-4">
          <div class="card p-3 bg-secondary text-white shadow-sm border-0">
              <h5>Nghiệp vụ Nhận / Trả phòng</h5>
              <p class="small">Thực hiện Check-in, Check-out, thanh toán tiền phòng và dịch vụ Minibar.</p>
              <a class="btn btn-primary btn-sm mt-2" href="<%=request.getContextPath()%>/reception/checkout.jsp">Vào màn hình Check-in / Check-out</a>
          </div>
      </div>
  </div>

  <div class="mt-4">
    <div class="alert alert-info border-0 shadow-sm">
        <b>Ghi chú hệ thống:</b> Khi thực hiện Check-out, hệ thống sẽ tự động tạo hóa đơn, trừ số lượng sản phẩm trong kho và chuyển phòng sang trạng thái <b>Cleaning</b> (Đang dọn).
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>