<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>CRM - Quản lý Khách hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between">
        <h3>Module 5 - CRM</h3>
        <div>
            <a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin">Back</a>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Logout</a>
        </div>
    </div>

    <div class="mt-3">
        <div class="alert alert-info">Đây là trang demo CRM. Tại đây sẽ hiển thị danh sách khách, điểm tích lũy, và chức năng nâng hạng VIP.</div>
        </div>
</div>
</body>
</html>