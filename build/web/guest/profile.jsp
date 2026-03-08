<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.service.CustomerService" %>
<%@ page import="com.smarthotel.model.Customer" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Hồ sơ | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      /* Phong cách Flat Design tối giản */
      body { background-color: #f8f9fa; color: #333; font-family: 'Segoe UI', Tahoma, sans-serif; padding-bottom: 40px; }
      
      .navbar-custom { background-color: #0d6efd; box-shadow: 0 1px 5px rgba(0,0,0,0.1); }
      .navbar-brand { font-weight: 700; letter-spacing: 0.5px; }

      .card-flat { border: 1px solid #dee2e6; border-radius: 4px; background: #fff; box-shadow: none; height: 100%;}
      .card-header-flat { background-color: #fff; border-bottom: 1px solid #dee2e6; padding: 20px; }
      .card-title-flat { font-size: 1.1rem; font-weight: 700; color: #212529; margin: 0; text-transform: uppercase;}
      
      .form-control { border-radius: 4px; border: 1px solid #ced4da; padding: 10px 12px; font-size: 14px;}
      .form-control:focus { border-color: #0d6efd; box-shadow: none; }
      .form-label { font-weight: 600; color: #495057; font-size: 13px; margin-bottom: 6px;}
      
      /* Nút Đặt phòng vuông vắn */
      .btn-booking { background-color: #ffc107; color: #000; font-weight: 700; border-radius: 4px; border: none; padding: 15px; font-size: 15px; transition: 0.2s;}
      .btn-booking:hover { background-color: #e0a800; color: #000; }
      
      .btn-outline-custom { border: 1px solid #ced4da; color: #495057; font-weight: 600; border-radius: 4px; padding: 12px; background: #fff;}
      .btn-outline-custom:hover { background-color: #e9ecef; color: #212529; }
      
      .btn-save { font-weight: 600; border-radius: 4px; padding: 10px 30px; }
      
      /* Cột thông tin trái */
      .profile-name { font-size: 1.3rem; font-weight: 700; color: #0d6efd; margin-bottom: 15px;}
      .info-row { display: flex; justify-content: space-between; border-bottom: 1px dashed #dee2e6; padding: 12px 0;}
      .info-row:last-child { border-bottom: none; }
      .info-label { color: #6c757d; font-size: 13px; font-weight: 600;}
      .info-value { font-weight: 700; color: #212529; font-size: 14px;}
  </style>
</head>
<body>
<%
  Object o = session.getAttribute("acc");
  if (o == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }
  Integer cid = (Integer) session.getAttribute("CUST_ID");
  if (cid == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }

  CustomerService cs = new CustomerService();
  Customer cus = cs.getById(cid);
  if (cus == null) { out.println("<div class='alert alert-danger m-3'>Không tìm thấy thông tin khách!</div>"); return; }
  
  boolean isVip = cus.getMemberType() != null && cus.getMemberType().toUpperCase().contains("VIP");
%>

<nav class="navbar navbar-expand-lg navbar-dark navbar-custom mb-4 px-3">
  <div class="container">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/">SMARTHOTEL</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto align-items-center">
        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/guest/history.jsp">Lịch sử</a></li>
        <li class="nav-item ms-3"><a class="btn btn-outline-light btn-sm fw-bold" href="<%=request.getContextPath()%>/logout" style="border-radius: 4px;">Đăng xuất</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container py-2">
  <div class="row g-4">
    <div class="col-lg-4">
      <div class="card card-flat mb-3">
        <div class="card-body p-4">
          <div class="profile-name"><%= cus.getFullName() %></div>
          
          <div class="info-row">
              <span class="info-label">Hạng thẻ</span>
              <span class="badge <%= isVip ? "bg-warning text-dark" : "bg-secondary" %>" style="border-radius: 4px;"><%= cus.getMemberType() != null ? cus.getMemberType() : "Standard" %></span>
          </div>
          <div class="info-row">
              <span class="info-label">Điểm tích lũy</span>
              <span class="info-value text-success"><%= cus.getPoints() %> Pts</span>
          </div>
          <div class="info-row">
              <span class="info-label">Tổng chi tiêu</span>
              <span class="info-value text-danger"><%= String.format("%,.0f", cus.getTotalSpending()) %> đ</span>
          </div>
        </div>
      </div>
      
      <a class="btn btn-booking w-100 mb-2" href="<%=request.getContextPath()%>/rooms">
         🏨 KHÁM PHÁ & ĐẶT PHÒNG NGAY
      </a>
      <a class="btn btn-outline-custom w-100" href="<%=request.getContextPath()%>/">Về trang chủ</a>
    </div>

    <div class="col-lg-8">
      <div class="card card-flat">
        <div class="card-header-flat d-flex justify-content-between align-items-center">
            <h5 class="card-title-flat">THÔNG TIN HỒ SƠ</h5>
            <a class="btn btn-sm btn-outline-primary" style="border-radius: 4px;" href="<%=request.getContextPath()%>/guest/history.jsp">Lịch sử lưu trú</a>
        </div>
        <div class="card-body p-4">
          <form action="${pageContext.request.contextPath}/updateProfile" method="post">
            <div class="row g-3">
              <div class="col-md-6">
                <label class="form-label">Họ và Tên</label>
                <input class="form-control fw-bold" name="fullName" value="<%= cus.getFullName() %>" required>
              </div>
              <div class="col-md-6">
                <label class="form-label">CCCD / Passport</label>
                <input class="form-control" name="cccd" value="<%= cus.getCccdPassport()!=null?cus.getCccdPassport():"" %>">
              </div>

              <div class="col-md-6">
                <label class="form-label">Số điện thoại</label>
                <input class="form-control" name="phone" value="<%= cus.getPhone()!=null?cus.getPhone():"" %>">
              </div>
              <div class="col-md-6">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" value="<%= cus.getEmail()!=null?cus.getEmail():"" %>" type="email">
              </div>

              <div class="col-12">
                <label class="form-label">Địa chỉ liên hệ</label>
                <input class="form-control" name="address" value="<%= cus.getAddress()!=null?cus.getAddress():"" %>">
              </div>

              <div class="col-md-6">
                <label class="form-label">Quốc tịch</label>
                <input class="form-control" name="nationality" value="<%= cus.getNationality()!=null?cus.getNationality():"" %>">
              </div>
              <div class="col-md-6">
                <label class="form-label">Ngày sinh</label>
                <input class="form-control" name="dob" type="date" value="<%= (cus.getDateOfBirth()!=null)? new java.text.SimpleDateFormat("yyyy-MM-dd").format(cus.getDateOfBirth()) : "" %>">
              </div>

              <div class="col-12">
                <label class="form-label">Ghi chú (Preferences)</label>
                <textarea class="form-control" name="preferences" rows="3"><%= cus.getPreferences()!=null?cus.getPreferences():"" %></textarea>
              </div>

              <div class="col-12 mt-4 pt-3 border-top text-end">
                <button type="submit" class="btn btn-success btn-save">Lưu Cập Nhật</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>