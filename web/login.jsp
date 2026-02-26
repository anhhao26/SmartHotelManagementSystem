<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Đăng nhập | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <div class="card shadow-sm">
        <div class="card-body">
          <h3 class="card-title mb-3">Đăng nhập</h3>

          <c:if test="${not empty param.err}">
            <div class="alert alert-danger">${param.err}</div>
          </c:if>

          <% String err = (String) request.getAttribute("err"); if (err != null) { %>
            <div class="alert alert-danger"><%= err %></div>
          <% } %>

          <form action="${pageContext.request.contextPath}/login" method="post" novalidate>
            <div class="mb-3">
              <label class="form-label">Username</label>
              <input class="form-control" name="username" required>
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input class="form-control" name="password" type="password" required>
            </div>
            <div class="d-flex justify-content-between align-items-center">
              <button class="btn btn-primary" type="submit">Đăng nhập</button>
              <a href="${pageContext.request.contextPath}/guest/register.jsp">Chưa có tài khoản? Đăng ký</a>
            </div>
          </form>

        </div>
      </div>

      <div class="text-center mt-3 text-muted small">SmartHotel demo</div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>