<%-- 
    Document   : supplier-form
    Created on : Feb 10, 2026, 6:13:31 PM
    Author     : ntpho
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thông tin Nhà Cung Cấp</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 600px;">
        <div class="card shadow">
            <div class="card-header bg-success text-white">
                <h5 class="mb-0">
                    <c:if test="${supplier != null}">Cập nhật Nhà Cung Cấp</c:if>
                    <c:if test="${supplier == null}">Thêm Nhà Cung Cấp Mới</c:if>
                </h5>
            </div>
            <div class="card-body">
                <form action="products" method="post">
                    <input type="hidden" name="action" value="saveSupplier">
                    
                    <c:if test="${supplier != null}">
                        <input type="hidden" name="id" value="${supplier.supplierID}">
                    </c:if>
                    
                    <div class="form-group">
                        <label>Tên Nhà Cung Cấp (*):</label>
                        <input type="text" name="name" class="form-control" 
                               value="${supplier.supplierName}" required>
                    </div>
                    
                    <div class="form-group">
                        <label>Số điện thoại:</label>
                        <input type="text" name="phone" class="form-control" 
                               value="${supplier.contactPhone}">
                    </div>
                    
                     <div class="form-group">
                        <label>Địa chỉ:</label>
                        <input type="text" name="address" class="form-control" 
                               value="${supplier.address}">
                    </div>
                    
                    <div class="d-flex justify-content-between mt-4">
                        <a href="products?action=listSuppliers" class="btn btn-secondary">Quay lại</a>
                        <button type="submit" class="btn btn-success">Lưu dữ liệu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>