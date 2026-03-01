<%-- 
    Document   : Voucher
    Created on : Feb 16, 2026, 12:05:10 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Khuyến mãi (Voucher)</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-4">
        <h2 class="text-center text-primary mb-4 fw-bold">HỆ THỐNG QUẢN LÝ VOUCHER</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center alert-dismissible fade show shadow-sm">
                <strong>Lỗi Dữ Liệu:</strong> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center alert-dismissible fade show shadow-sm">
                <strong>Thành công:</strong> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <div class="card mb-4 shadow-sm">
            <div class="card-header bg-primary text-white fw-bold">Thêm / Cập nhật Mã Giảm Giá</div>
            <div class="card-body">
                <form action="VoucherServlet" method="POST">
                    <input type="hidden" name="action" value="save">
                    <div class="row mb-3">
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Mã Voucher (Code)</label>
                            <input type="text" name="voucherCode" class="form-control border-primary" required placeholder="VD: SUMMER2026">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Số tiền giảm (VNĐ)</label>
                            <input type="number" name="discountValue" class="form-control" required placeholder="100000">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Đơn tối thiểu (VNĐ)</label>
                            <input type="number" name="minOrderValue" class="form-control" value="0">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold">Số lượt dùng</label>
                            <input type="number" name="usageLimit" class="form-control" value="50">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold">Ngày bắt đầu</label>
                            <input type="datetime-local" name="startDate" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-danger">Ngày kết thúc</label>
                            <input type="datetime-local" name="endDate" class="form-control border-danger" required>
                        </div>
                    </div>
                    <div class="text-end">
                        <button type="reset" class="btn btn-secondary me-2">Làm mới</button>
                        <button type="submit" class="btn btn-success px-4 fw-bold">Lưu Voucher</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white fw-bold">Danh sách Voucher đang hoạt động</div>
            <div class="card-body p-0">
                <table class="table table-bordered table-hover mb-0">
                    <thead class="table-light">
                        <tr class="text-center">
                            <th>Mã Code</th>
                            <th>Giảm giá (VNĐ)</th>
                            <th>Đơn Tối thiểu</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                            <th>Đã dùng</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${voucherList}" var="v">
                            <tr class="text-center align-middle">
                                <td><strong class="text-primary">${v.voucherCode}</strong></td>
                                <td class="text-danger fw-bold">${v.discountValue}</td>
                                <td>${v.minOrderValue}</td>
                                <td>${v.startDate}</td>
                                <td>${v.endDate}</td>
                                <td><span class="badge bg-info text-dark">${v.usedCount} / ${v.usageLimit}</span></td>
                                <td>
                                    <a href="VoucherServlet?action=delete&code=${v.voucherCode}" 
                                       class="btn btn-sm btn-outline-danger" 
                                       onclick="return confirm('Bạn có chắc muốn xóa mã này?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:if test="${empty voucherList}">
                    <div class="alert alert-warning text-center m-3">
                        Chưa có mã giảm giá nào trong hệ thống!
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
