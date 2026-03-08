<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Đăng ký | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; padding: 40px 0;}
      .register-card { background: white; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.06); border: none; border-top: 5px solid #198754;}
      .form-control, .form-select { border-radius: 8px; border: 1px solid #ced4da; padding: 10px 15px; background-color: #f8f9fa; font-size: 15px;}
      .form-control:focus, .form-select:focus { background-color: #fff; border-color: #198754; box-shadow: 0 0 0 0.25rem rgba(25, 135, 84, 0.15); }
      .form-label { font-weight: 600; color: #495057; font-size: 13px; margin-bottom: 6px; text-transform: uppercase;}
      .btn-register { background-color: #198754; color: white; border-radius: 8px; font-weight: bold; padding: 12px 30px; border: none; transition: 0.3s;}
      .btn-register:hover { background-color: #157347; transform: translateY(-2px); box-shadow: 0 6px 15px rgba(25, 135, 84, 0.25); }
      .btn-back { border-radius: 8px; padding: 12px 20px; font-weight: 600; transition: 0.3s; }
  </style>
</head>
<body>
<div class="container">
  <div class="row justify-content-center">
    <div class="col-lg-8">
      
      <div class="text-center mb-4">
          <h2 style="color: #198754; font-weight: 800;">🏩 SmartHotel</h2>
          <p class="text-muted fw-bold">TẠO TÀI KHOẢN KHÁCH HÀNG THÀNH VIÊN</p>
      </div>

      <div class="card register-card">
        <div class="card-body p-4 p-md-5">

          <% String err = (String) request.getAttribute("err"); if (err != null) { %>
            <div class="alert alert-danger fw-bold text-center rounded-3">⚠️ <%= err %></div>
          <% } %>

          <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="row g-4">
              <div class="col-md-6">
                <label class="form-label text-success">Username *</label>
                <input class="form-control border-success" name="username" placeholder="Tên đăng nhập" required>
              </div>
              <div class="col-md-6">
                <label class="form-label text-success">Password *</label>
                <input class="form-control border-success" name="password" type="password" placeholder="Mật khẩu" required>
              </div>

              <div class="col-md-6">
                <label class="form-label">Họ và Tên *</label>
                <input class="form-control" name="fullName" placeholder="VD: Nguyễn Văn A" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">CCCD / Passport</label>
                <input class="form-control" name="cccd" placeholder="Số định danh">
              </div>

              <div class="col-md-6">
                <label class="form-label">Số điện thoại</label>
                <input class="form-control" name="phone" placeholder="09xxxxxxx">
              </div>
              <div class="col-md-6">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" type="email" placeholder="example@gmail.com">
              </div>

              <div class="col-md-6">
                <label class="form-label">Quốc tịch</label>
                <input class="form-control" name="nationality" placeholder="Việt Nam">
              </div>
              <div class="col-md-6">
                <label class="form-label">Ngày sinh</label>
                <input class="form-control" name="dob" type="date">
              </div>

              <div class="col-12">
                <label class="form-label">Địa chỉ liên hệ</label>
                <input class="form-control" name="address" placeholder="Nhập địa chỉ đầy đủ...">
              </div>

              <div class="col-md-4">
                <label class="form-label">Giới tính</label>
                <select class="form-select" name="gender">
                  <option value="">-- Chọn --</option>
                  <option value="Male">Nam</option>
                  <option value="Female">Nữ</option>
                </select>
              </div>
              <div class="col-md-8">
                <label class="form-label">Ghi chú (Preferences)</label>
                <textarea class="form-control" name="preferences" rows="1" placeholder="Yêu cầu phòng không hút thuốc, v.v..."></textarea>
              </div>

              <div class="col-12 mt-5 d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
                <a class="btn btn-outline-secondary btn-back w-100 w-md-auto text-center" href="${pageContext.request.contextPath}/login.jsp">⬅ Quay lại Đăng nhập</a>
                <button class="btn btn-register w-100 w-md-auto" type="submit">ĐĂNG KÝ TÀI KHOẢN</button>
              </div>
            </div>
          </form>

        </div>
      </div>
      <div class="text-center mt-3 text-muted" style="font-size: 13px;">Hệ thống sẽ tự động đăng nhập sau khi đăng ký thành công.</div>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>