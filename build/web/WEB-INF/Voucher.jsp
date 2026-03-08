<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Khuyến mãi (Voucher) - SmartHotel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; padding-bottom: 50px; color: #333;}
        .navbar-custom { background-color: #212529; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        
        .custom-card { border: none; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.04); background: #fff; margin-bottom: 25px;}
        .card-header { background-color: #fff; border-bottom: 1px solid #f1f3f5; font-weight: 800; font-size: 1.1rem; padding: 15px 20px;}
        
        .form-control, .form-select { border-radius: 8px; border: 1px solid #ced4da; background-color: #f8f9fa;}
        .form-control:focus { background-color: #fff; border-color: #198754; box-shadow: 0 0 0 0.25rem rgba(25, 135, 84, 0.15); }
        .form-label { font-weight: 600; color: #495057; font-size: 13px; margin-bottom: 5px; text-transform: uppercase;}
        
        .table th { background-color: #f8f9fa; color: #6c757d; font-weight: 700; text-transform: uppercase; font-size: 13px; border-bottom: 2px solid #dee2e6;}
        .table td { vertical-align: middle; font-weight: 500; border-bottom: 1px solid #f1f3f5;}
        
        .btn-custom { font-weight: bold; border-radius: 8px; padding: 10px 20px; transition: 0.3s; }
        .btn-custom:hover { transform: translateY(-2px); box-shadow: 0 4px 10px rgba(0,0,0,0.1);}
    </style>
</head>
<body>
    
    <nav class="navbar navbar-dark navbar-custom mb-4 px-4 py-3">
        <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/admin">⬅ VỀ BẢNG ĐIỀU KHIỂN</a>
    </nav>

    <div class="container">
        <h2 class="fw-bold mb-4" style="color: #212529;">HỆ THỐNG QUẢN LÝ VOUCHER</h2>
        
        <c:if test="${not empty errorMessage}"><div class="alert alert-danger fw-bold text-center shadow-sm rounded-3">${errorMessage}</div></c:if>
        <c:if test="${not empty successMessage}"><div class="alert alert-success fw-bold text-center shadow-sm rounded-3">${successMessage}</div></c:if>
        
        <div class="card custom-card">
            <div class="card-header text-success">✨ PHÁT HÀNH MÃ GIẢM GIÁ MỚI</div>
            <div class="card-body p-4">
                <form action="<%=request.getContextPath()%>/VoucherServlet" method="POST">
                    <input type="hidden" name="action" value="save">
                    <div class="row g-4 mb-4">
                        <div class="col-md-3">
                            <label class="form-label text-primary">Mã Code *</label>
                            <input type="text" name="voucherCode" class="form-control border-primary fw-bold" required placeholder="VD: SUMMER2026">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label text-danger">Số tiền giảm (VNĐ) *</label>
                            <input type="number" name="discountValue" class="form-control border-danger fw-bold text-danger" required placeholder="100000">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Đơn tối thiểu (VNĐ)</label>
                            <input type="number" name="minOrderValue" class="form-control" value="0">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Giới hạn số lượt dùng</label>
                            <input type="number" name="usageLimit" class="form-control fw-bold" value="50">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Ngày giờ bắt đầu</label>
                            <input type="datetime-local" name="startDate" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label text-warning">Ngày giờ kết thúc</label>
                            <input type="datetime-local" name="endDate" class="form-control border-warning" required>
                        </div>
                    </div>
                    <div class="text-end border-top pt-3">
                        <button type="reset" class="btn btn-light fw-bold me-2 btn-custom border">Làm mới</button>
                        <button type="submit" class="btn btn-success btn-custom">Lưu Voucher</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card custom-card">
            <div class="card-header text-dark">📋 DANH SÁCH VOUCHER ĐANG HOẠT ĐỘNG</div>
            <div class="card-body p-0">
                <table class="table table-hover mb-0">
                    <thead>
                        <tr class="text-center">
                            <th>Mã Code</th>
                            <th>Mức Giảm (VNĐ)</th>
                            <th>Đơn Tối thiểu</th>
                            <th>Hiệu lực từ</th>
                            <th>Hết hạn vào</th>
                            <th>Lượt Dùng</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${voucherList}" var="v">
                            <tr class="text-center">
                                <td><span class="badge bg-primary fs-6 px-3 py-2">${v.voucherCode}</span></td>
                                <td class="text-danger fw-bold fs-5"><fmt:formatNumber value="${v.discountValue}" pattern="#,###"/></td>
                                <td><fmt:formatNumber value="${v.minOrderValue}" pattern="#,###"/></td>
                                <td class="text-muted"><fmt:formatDate value="${v.startDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td class="text-muted"><fmt:formatDate value="${v.endDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td><span class="badge bg-secondary fs-6">${v.usedCount} / ${v.usageLimit}</span></td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/VoucherServlet?action=delete&code=${v.voucherCode}" 
                                       class="btn btn-sm btn-outline-danger fw-bold" 
                                       onclick="return confirm('Bạn có chắc muốn vô hiệu hóa mã này?');">Xóa Mã</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${empty voucherList}">
                    <div class="alert alert-warning text-center m-4 fw-bold border-0 bg-light text-muted">
                        Chưa có mã giảm giá nào đang hoạt động!
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>