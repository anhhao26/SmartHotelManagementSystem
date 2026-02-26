<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>SmartHotel - Welcome</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
  <div class="card shadow-sm">
    <div class="card-body text-center">
      <h1 class="display-6 mb-3">SmartHotel Management</h1>
      <p class="lead mb-4">Hệ thống quản lý khách sạn — demo Module 5 (CRM) & Module 7 (Check-in/Billing)</p>
      <div class="d-flex justify-content-center gap-2">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/login.jsp">Đăng nhập</a>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/guest/register.jsp">Đăng ký</a>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/reception/home.jsp">Trang Lễ tân</a>
      </div>
    </div>
  </div>
  <footer class="mt-4 text-center text-muted small">
    © SmartHotel — Demo project
  </footer>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>