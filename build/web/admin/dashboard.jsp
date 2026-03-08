<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Admin Dashboard | SmartHotel</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Nền tổng thể trắng xám sáng sủa */
        body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; color: #333; padding-bottom: 50px;}
        
        /* Navbar trắng sang trọng */
        .navbar-custom { background-color: #ffffff; box-shadow: 0 2px 10px rgba(0,0,0,0.06); border-bottom: 1px solid #e9ecef; }
        .navbar-custom .navbar-brand { color: #0d6efd !important; font-weight: 800; letter-spacing: 1px; text-transform: uppercase;}
        
        /* Tiêu đề trang */
        .page-title { color: #2c3e50; font-weight: 900; text-align: center; margin: 40px 0; text-transform: uppercase; letter-spacing: 1px;}
        
        /* Thẻ chức năng (Card) */
        .admin-card { 
            background: #fff; border-radius: 16px; padding: 30px 25px; height: 100%;
            box-shadow: 0 4px 15px rgba(0,0,0,0.03); transition: all 0.3s ease;
            border: 1px solid #e9ecef; display: flex; flex-direction: column; justify-content: space-between;
            position: relative; overflow: hidden;
        }
        .admin-card:hover { transform: translateY(-8px); box-shadow: 0 15px 30px rgba(0,0,0,0.1); border-color: #ced4da;}
        
        .card-icon { font-size: 45px; margin-bottom: 15px; }
        .card-title { font-weight: 800; font-size: 1.25rem; margin-bottom: 10px; color: #2c3e50; }
        .card-desc { color: #6c757d; font-size: 0.95rem; line-height: 1.6; margin-bottom: 25px; font-weight: 500;}
        
        /* Nút bấm (Button) */
        .btn-action { font-weight: bold; border-radius: 10px; padding: 14px; transition: 0.3s; font-size: 15px; border: none; box-shadow: 0 4px 10px rgba(0,0,0,0.1);}
        .btn-action:hover { filter: brightness(1.1); transform: translateY(-2px); box-shadow: 0 6px 15px rgba(0,0,0,0.15); color: white;}
    </style>
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-custom px-4 py-3 sticky-top">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">🏨 SMARTHOTEL ADMIN</a>
            <div class="ms-auto d-flex align-items-center">
                <span class="fw-bold text-muted me-4 d-none d-sm-inline">Xin chào, Quản trị viên</span>
                <a href="<%=request.getContextPath()%>/logout" class="btn btn-outline-danger btn-sm fw-bold px-4" style="border-radius: 8px;">Đăng xuất</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <h2 class="page-title">BẢNG ĐIỀU KHIỂN TRUNG TÂM</h2>
        
        <div class="row g-4">
            
            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #0d6efd;">
                    <div>
                        <div class="card-icon">👥</div>
                        <div class="card-title text-primary">Quản lý Khách hàng (CRM)</div>
                        <div class="card-desc">Module 5: Xem danh sách, cộng điểm, cấp VIP.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/admin/customers" class="btn btn-primary btn-action w-100 text-white">MỞ CRM KHÁCH HÀNG</a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #198754;">
                    <div>
                        <div class="card-icon">🛎️</div>
                        <div class="card-title text-success">Quầy Lễ Tân (Reception)</div>
                        <div class="card-desc">Module 7: Xử lý nhận/trả phòng, thanh toán.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/reception/home.jsp" class="btn btn-success btn-action w-100 text-white">TỚI QUẦY LỄ TÂN</a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #ffc107;">
                    <div>
                        <div class="card-icon">📦</div>
                        <div class="card-title text-warning" style="color: #d39e00 !important;">Quản lý Kho & Minibar</div>
                        <div class="card-desc">Module 2: Quản lý hàng hóa, nhập xuất kho.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/products" class="btn btn-warning btn-action w-100 text-dark fw-bold">MỞ KHO HÀNG</a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #dc3545;">
                    <div>
                        <div class="card-icon">🛏️</div>
                        <div class="card-title text-danger">Sơ đồ phòng (Room Board)</div>
                        <div class="card-desc">Module 4: Kiểm soát trạng thái phòng trống/đang ở/dọn dẹp.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/RoomServlet" class="btn btn-danger btn-action w-100 text-white">MỞ SƠ ĐỒ PHÒNG</a>
                </div>
            </div>

            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #212529;">
                    <div>
                        <div class="card-icon">🏷️</div>
                        <div class="card-title text-dark">Quản lý Khuyến mãi (Voucher)</div>
                        <div class="card-desc">Module 3: Thiết lập mã giảm giá, kiểm soát lượt dùng.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/VoucherServlet" class="btn btn-dark btn-action w-100 text-white">QUẢN LÝ VOUCHER</a>
                </div>
            </div>
            
            <div class="col-lg-4 col-md-6">
                <div class="admin-card" style="border-top: 5px solid #0dcaf0; background-color: #faffff;">
                    <div>
                        <div class="card-icon">📱</div>
                        <div class="card-title text-info" style="color: #055160 !important;">Trang Khách Đặt Phòng</div>
                        <div class="card-desc">Module 6: Front-end cho khách hàng vãng lai tìm và đặt phòng.</div>
                    </div>
                    <a href="<%=request.getContextPath()%>/webapp/search.jsp" target="_blank" class="btn btn-outline-info btn-action w-100 fw-bold" style="border: 2px solid #0dcaf0; color: #055160;">XEM GIAO DIỆN KHÁCH</a>
                </div>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>