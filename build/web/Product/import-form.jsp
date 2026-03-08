<%-- 
    Document   : import-form
    Created on : Feb 11, 2026, 3:22:39 PM
    Author     : ntpho
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Nhập Kho</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 500px;">
        <div class="card shadow">
            <div class="card-header bg-warning text-dark font-weight-bold">
                NHẬP THÊM HÀNG: ${product.productName}
            </div>
            <div class="card-body">
                <form action="products" method="post">
                    <input type="hidden" name="action" value="saveImport">
                    <input type="hidden" name="id" value="${product.productID}">
                    
                    <div class="form-group">
                        <label>Số lượng tồn hiện tại:</label>
                        <input type="text" class="form-control" value="${product.quantity}" disabled>
                    </div>

                    <div class="form-group">
                        <label>Số lượng NHẬP THÊM (*):</label>
                        <input type="number" name="quantityToAdd" class="form-control" min="1" required>
                        <small class="text-muted">Nhập số lượng thực tế về kho.</small>
                    </div>

                    <div class="form-group">
                        <label>Giá nhập mới (VNĐ) (*):</label>
                        <input type="number" name="newCostPrice" class="form-control" value="${product.costPrice}" required>
                        <small class="text-muted">Cập nhật giá vốn nếu có thay đổi.</small>
                    </div>

                    <button type="submit" class="btn btn-warning btn-block font-weight-bold">Xác nhận Nhập Kho</button>
                    <a href="products" class="btn btn-secondary btn-block">Hủy</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
