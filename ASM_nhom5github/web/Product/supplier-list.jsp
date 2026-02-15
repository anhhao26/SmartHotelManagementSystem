<%-- 
    Document   : supplier-list
    Created on : Feb 10, 2026, 6:44:44 PM
    Author     : ntpho
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Nhà Cung Cấp</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-dark bg-dark mb-4">
        <a class="navbar-brand" href="products">QUAY VỀ KHO HÀNG</a>
    </nav>

    <div class="container">
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Không thể xóa Nhà cung cấp này vì họ đang có hàng trong kho! Hãy xóa hàng hóa trước.
            </div>
        </c:if>

        <div class="d-flex justify-content-between mb-3">
            <h3>Danh sách Đối tác & Nhà cung cấp</h3>
            <a href="products?action=newSupplier" class="btn btn-primary">+ Thêm NCC Mới</a>
        </div>

        <table class="table table-bordered table-striped">
            <thead class="thead-light">
                <tr>
                    <th>ID</th>
                    <th>Tên Nhà Cung Cấp</th>
                    <th>SĐT</th>
                    <th>Địa chỉ</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="s" items="${listSuppliers}">
                    <tr>
                        <td>${s.supplierID}</td>
                        <td>${s.supplierName}</td>
                        <td>${s.contactPhone}</td>
                        <td>${s.address}</td>
                        <td>
                            <a href="products?action=editSupplier&id=${s.supplierID}" class="btn btn-sm btn-info">Sửa</a>
                            <a href="products?action=deleteSupplier&id=${s.supplierID}" 
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Bạn có chắc muốn xóa đối tác này?(khi xóa đối tác mọi sản phẩm liên kết với nó sẽ bị xóa theo)');">
                               Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>