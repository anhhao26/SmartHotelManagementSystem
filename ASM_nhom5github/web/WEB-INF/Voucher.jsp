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
        <h2 class="text-center text-primary mb-4">HỆ THỐNG QUẢN LÝ VOUCHER</h2>
        
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">Thêm / Cập nhật Mã Giảm Giá</div>
            <div class="card-body">
                <form action="VoucherServlet" method="POST">
                    <input type="hidden" name="action" value="save">
                    <div class="row mb-3">
                        <div class="col-md-3">
                            <label>Mã Voucher (Code)</label>
                            <input type="text" name="voucherCode" class="form-control" required placeholder="VD: SUMMER2026">
                        </div>
                        <div class="col-md-3">
                            <label>Số tiền giảm (VNĐ)</label>
                            <input type="number" name="discountValue" class="form-control" required placeholder="100000">
                        </div>
                        <div class="col-md-3">
                            <label>Đơn tối thiểu (VNĐ)</label>
                            <input type="number" name="minOrderValue" class="form-control" value="0">
                        </div>
                        <div class="col-md-3">
                            <label>Số lượt dùng</label>
                            <input type="number" name="usageLimit" class="form-control" value="50">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label>Ngày bắt đầu</label>
                            <input type="datetime-local" name="startDate" class="form-control" required>
                        </div>
                        <div class="col-md-6">
                            <label>Ngày kết thúc</label>
                            <input type="datetime-local" name="endDate" class="form-control" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success">Lưu Voucher</button>
                    <button type="reset" class="btn btn-secondary">Làm mới</button>
                </form>
            </div>
        </div>

        <div class="card">
            <div class="card-header bg-dark text-white">Danh sách Voucher đang hoạt động</div>
            <div class="card-body">
                <table class="table table-bordered table-hover">
                    <thead class="table-light">
                        <tr>
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
                            <tr>
                                <td><strong>${v.voucherCode}</strong></td>
                                <td class="text-danger">${v.discountValue}</td>
                                <td>${v.minOrderValue}</td>
                                <td>${v.startDate}</td>
                                <td>${v.endDate}</td>
                                <td>${v.usedCount} / ${v.usageLimit}</td>
                                <td>
                                    <a href="VoucherServlet?action=delete&code=${v.voucherCode}" 
                                       class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Bạn có chắc muốn xóa mã này?');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
