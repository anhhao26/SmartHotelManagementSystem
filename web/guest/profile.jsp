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
</head>
<body>
<%
  Object o = session.getAttribute("acc");
  if (o == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }
  Integer cid = (Integer) session.getAttribute("CUST_ID");
  if (cid == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }

  CustomerService cs = new CustomerService();
  Customer cus = cs.getById(cid);
  if (cus == null) { out.println("<div class='p-3'>Không tìm thấy thông tin khách!</div>"); return; }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/">SmartHotel</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/guest/history.jsp">Lịch sử</a></li>
        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/logout">Đăng xuất</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container py-4">
  <div class="row">
    <div class="col-md-4">
      <div class="card shadow-sm">
        <div class="card-body">
          <h5 class="card-title"><%= cus.getFullName() %></h5>
          <p class="card-text small mb-1"><b>Hạng: </b><%= cus.getMemberType() %></p>
          <p class="card-text small mb-1"><b>Điểm: </b><%= cus.getPoints() %></p>
          <p class="card-text small"><b>Tổng chi tiêu: </b><%= String.format("%,.0f", cus.getTotalSpending()) %> VNĐ</p>
        </div>
      </div>
      <div class="mt-3">
        <a class="btn btn-outline-secondary w-100" href="<%=request.getContextPath()%>/">Về trang chính</a>
      </div>
    </div>

    <div class="col-md-8">
      <div class="card shadow-sm">
        <div class="card-body">
          <h5 class="card-title mb-3">Cập nhật hồ sơ</h5>
          <form action="${pageContext.request.contextPath}/updateProfile" method="post">
            <div class="row g-2">
              <div class="col-md-6">
                <label class="form-label">Full name</label>
                <input class="form-control" name="fullName" value="<%= cus.getFullName() %>">
              </div>
              <div class="col-md-6">
                <label class="form-label">CCCD/Passport</label>
                <input class="form-control" name="cccd" value="<%= cus.getCccdPassport()!=null?cus.getCccdPassport():"" %>">
              </div>

              <div class="col-md-6">
                <label class="form-label">Phone</label>
                <input class="form-control" name="phone" value="<%= cus.getPhone()!=null?cus.getPhone():"" %>">
              </div>
              <div class="col-md-6">
                <label class="form-label">Email</label>
                <input class="form-control" name="email" value="<%= cus.getEmail()!=null?cus.getEmail():"" %>" type="email">
              </div>

              <div class="col-12">
                <label class="form-label">Address</label>
                <input class="form-control" name="address" value="<%= cus.getAddress()!=null?cus.getAddress():"" %>">
              </div>

              <div class="col-md-6">
                <label class="form-label">Nationality</label>
                <input class="form-control" name="nationality" value="<%= cus.getNationality()!=null?cus.getNationality():"" %>">
              </div>

              <div class="col-md-6">
                <label class="form-label">Date of Birth</label>
                <input class="form-control" name="dob" type="date" value="<%= (cus.getDateOfBirth()!=null)? new java.text.SimpleDateFormat("yyyy-MM-dd").format(cus.getDateOfBirth()) : "" %>">
              </div>

              <div class="col-12">
                <label class="form-label">Preferences</label>
                <textarea class="form-control" name="preferences" rows="3"><%= cus.getPreferences()!=null?cus.getPreferences():"" %></textarea>
              </div>

              <div class="col-12 mt-2 d-flex justify-content-end">
                <button class="btn btn-success">Lưu thay đổi</button>
              </div>
            </div>
          </form>
        </div>
      </div>

      <div class="mt-3">
        <a class="btn btn-outline-primary" href="<%=request.getContextPath()%>/guest/history.jsp">Xem lịch sử lưu trú</a>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>