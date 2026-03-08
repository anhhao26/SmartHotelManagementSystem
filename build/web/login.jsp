<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Đăng nhập | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { background-color: #f4f7f6; display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 100vh; margin: 0; font-family: 'Segoe UI', sans-serif; }
      .login-card { background: white; padding: 45px 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); width: 100%; max-width: 420px; border-top: 5px solid #0d6efd; }
      .brand-title { text-align: center; font-weight: 800; color: #0d6efd; margin-bottom: 5px; font-size: 28px;}
      .brand-subtitle { text-align: center; color: #6c757d; font-size: 14px; margin-bottom: 30px; }
      
      .form-control { background-color: #f8f9fa; border: 1px solid #dee2e6; padding: 12px 15px; border-radius: 8px; font-size: 15px;}
      .form-control:focus { background-color: #fff; border-color: #0d6efd; box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.15); }
      .form-label { font-weight: 600; color: #495057; font-size: 13px; text-transform: uppercase; letter-spacing: 0.5px;}
      
      .btn-login { background-color: #0d6efd; color: white; width: 100%; padding: 14px; border-radius: 8px; font-weight: bold; font-size: 16px; margin-top: 10px; transition: 0.3s; border: none;}
      .btn-login:hover { background-color: #0b5ed7; transform: translateY(-2px); box-shadow: 0 6px 15px rgba(13, 110, 253, 0.25); }
      
      .alert-custom { background: #f8d7da; color: #842029; padding: 12px; border-radius: 8px; text-align: center; font-weight: 600; margin-bottom: 20px; font-size: 14px; border: 1px solid #f5c2c7; }
  </style>
</head>
<body>

<div class="login-card">
    <div class="brand-title">🏩 SmartHotel</div>
    <div class="brand-subtitle">Vui lòng đăng nhập để tiếp tục</div>

    <c:if test="${not empty param.err}">
      <div class="alert-custom">⚠️ ${param.err}</div>
    </c:if>

    <% String err = (String) request.getAttribute("err"); if (err != null) { %>
      <div class="alert-custom">⚠️ <%= err %></div>
    <% } %>

    <form action="${pageContext.request.contextPath}/login" method="post" novalidate>
      <div class="mb-3">
        <label class="form-label">Tên đăng nhập</label>
        <input class="form-control" name="username" placeholder="Nhập username..." required>
      </div>
      <div class="mb-4">
        <label class="form-label">Mật khẩu</label>
        <input class="form-control" name="password" type="password" placeholder="Nhập password..." required>
      </div>
      
      <button class="btn-login" type="submit">Đăng Nhập Ngay</button>
      
      <div class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/guest/register.jsp" class="text-decoration-none text-muted" style="font-size: 14px; font-weight: 500;">
            Chưa có tài khoản? <span class="text-primary fw-bold">Đăng ký tại đây</span>
        </a>
      </div>
    </form>
</div>

<div class="text-center mt-4 text-muted fw-bold" style="font-size: 12px; text-transform: uppercase;">
    <a href="${pageContext.request.contextPath}/" class="text-muted text-decoration-none hover-primary">⬅ Về Trang Chủ</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>