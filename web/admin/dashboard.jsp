<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Admin Dashboard</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Bảng điều khiển Admin</h2>
        <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Đăng xuất</a>
    </div>

    <div class="row gy-3">
        <div class="col-md-4">
            <div class="card p-3 border-primary shadow-sm">
                <h5 class="text-primary">Quản lý Khách hàng (CRM)</h5>
                <p class="small text-muted">Module 5: Xem danh sách, cộng điểm, cấp VIP.</p>
                <a class="btn btn-primary btn-sm" href="<%=request.getContextPath()%>/admin/customers">Mở CRM</a>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card p-3 shadow-sm">
                <h5>Quầy Lễ Tân (Check-in/out)</h5>
                <p class="small text-muted">Module 7: Xử lý nhận/trả phòng, thanh toán.</p>
                <a class="btn btn-outline-success btn-sm" href="<%=request.getContextPath()%>/reception/home.jsp">Tới Quầy Lễ Tân</a>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card p-3 shadow-sm">
                <h5>Quản lý phòng</h5>
                <p class="small text-muted">Module 1 & 4 (Hòa & Long làm)</p>
                <button class="btn btn-outline-secondary btn-sm" disabled>Đang phát triển</button>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card p-3 shadow-sm">
                <h5>Quản lý kho & Minibar</h5>
                <p class="small text-muted">Module 2 (Phong làm)</p>
                <button class="btn btn-outline-secondary btn-sm" disabled>Đang phát triển</button>
            </div>
        </div>

        <div class="col-md-4">
            <div class="card p-3 shadow-sm">
                <h5>Gói SaaS & AI</h5>
                <p class="small text-muted">Module 8 & 10 (Hòa làm)</p>
                <button class="btn btn-outline-secondary btn-sm" disabled>Đang phát triển</button>
            </div>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>