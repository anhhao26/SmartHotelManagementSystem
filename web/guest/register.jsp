<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Đăng ký | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card shadow-sm">
        <div class="card-body">
          <h3 class="mb-3">Tạo tài khoản khách</h3>

          <% String err = (String) request.getAttribute("err"); if (err != null) { %>
            <div class="alert alert-danger"><%= err %></div>
          <% } %>

          <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label">Username*</label>
                <input class="form-control" name="username" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">Password*</label>
                <input class="form-control" name="password" type="password" required>
              </div>

              <div class="col-md-6">
                <label class="form-label">Full name*</label>
                <input class="form-control" name="fullName" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">CCCD / Passport</label>
                <input class="form-control" name="cccd">
              </div>

              <div class="col-md-6">
                <label class="form-label">Phone</label>
                <input class="form-control" name="phone">
              </div>
              <div class="col-md-6">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" type="email">
              </div>

              <div class="col-md-6">
                <label class="form-label">Nationality</label>
                <input class="form-control" name="nationality">
              </div>
              <div class="col-md-6">
                <label class="form-label">Date of birth</label>
                <input class="form-control" name="dob" type="date">
              </div>

              <div class="col-12">
                <label class="form-label">Address</label>
                <input class="form-control" name="address">
              </div>

              <div class="col-md-4">
                <label class="form-label">Gender</label>
                <select class="form-select" name="gender">
                  <option value="">--</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                </select>
              </div>

              <div class="col-12">
                <label class="form-label">Preferences</label>
                <textarea class="form-control" name="preferences" rows="2"></textarea>
              </div>

              <div class="col-12 d-flex justify-content-between">
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/login.jsp">Quay lại Đăng nhập</a>
                <button class="btn btn-success" type="submit">Đăng ký</button>
              </div>
            </div>
          </form>

        </div>
      </div>

      <div class="text-center mt-3 text-muted small">Bạn sẽ được auto-login sau khi đăng ký</div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>