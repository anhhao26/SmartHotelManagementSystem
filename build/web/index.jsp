<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>SmartHotel - Quản Lý Thông Minh</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { 
          background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%); 
          min-height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; 
          font-family: 'Segoe UI', Tahoma, sans-serif; margin: 0;
      }
      .welcome-card { 
          background: rgba(255, 255, 255, 0.95); padding: 50px 40px; 
          border-radius: 20px; box-shadow: 0 20px 50px rgba(0,0,0,0.1); 
          max-width: 650px; width: 100%; text-align: center;
          border-top: 6px solid #0d6efd;
      }
      h1 { font-weight: 900; color: #2c3e50; margin-bottom: 10px; letter-spacing: -1px;}
      .subtitle { color: #6c757d; font-size: 16px; margin-bottom: 40px; line-height: 1.6;}
      
      .btn-portal { 
          padding: 15px 20px; border-radius: 12px; font-weight: bold; font-size: 16px; 
          transition: all 0.3s; width: 100%; display: flex; justify-content: center; align-items: center; gap: 8px;
          text-decoration: none;
      }
      .btn-portal:hover { transform: translateY(-3px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); color: white; }
      
      .btn-guest { background-color: #0d6efd; color: white; border: none; }
      .btn-guest:hover { background-color: #0b5ed7; }
      
      .btn-register { background-color: #198754; color: white; border: none; }
      .btn-register:hover { background-color: #157347; }
      
      .btn-staff { background-color: transparent; color: #2c3e50; border: 2px solid #ced4da; }
      .btn-staff:hover { border-color: #2c3e50; background: #f8f9fa; color: #2c3e50;}
  </style>
</head>
<body>

<div class="welcome-card">
    <div style="font-size: 50px; margin-bottom: 10px;">🏨</div>
    <h1>SMARTHOTEL</h1>
    <div class="subtitle">Hệ thống Quản lý Khách sạn Toàn diện & Thông minh<br>Demo Version 2.0</div>
    
    <div class="row g-3 px-3">
        <div class="col-md-6">
            <a class="btn btn-portal btn-guest" href="${pageContext.request.contextPath}/login.jsp">
                🚪 Đăng Nhập
            </a>
        </div>
        <div class="col-md-6">
            <a class="btn btn-portal btn-register" href="${pageContext.request.contextPath}/guest/register.jsp">
                ✨ Đăng Ký Khách Mới
            </a>
        </div>
        <div class="col-12 mt-3">
            <a class="btn btn-portal btn-staff" href="${pageContext.request.contextPath}/reception/home.jsp">
                🛎️ Chuyển đến Quầy Lễ Tân
            </a>
        </div>
    </div>
</div>

<footer class="mt-4 text-center text-muted fw-bold" style="font-size: 13px; text-transform: uppercase;">
    © 2026 Developed by Nhom 5
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>