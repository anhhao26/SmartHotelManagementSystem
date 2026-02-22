<%-- Document : product-list Created on : Feb 10, 2026, 3:17:40 PM Author : ntpho --%>

    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Quản lý Kho (Module 2)</title>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            </head>

            <body>

                <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
                    <a class="navbar-brand" href="#">HOTEL ADMIN - MODULE 2 (KHO)</a>
                </nav>

                <div class="container-fluid">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h3>Danh sách Vật tư & Hàng hóa</h3>

                        <div class="d-flex align-items-center">
                            <form action="products" method="get" class="mr-3">
                                <input type="hidden" name="action" value="list">

                                <div class="custom-control custom-switch">
                                    <input type="checkbox" class="custom-control-input" id="switchHidden"
                                        name="showHidden" value="true" ${isShowHidden ? 'checked' : '' }
                                        onchange="this.form.submit()">
                                    <label class="custom-control-label font-weight-bold" for="switchHidden">
                                        Hiện cả hàng đã ẩn
                                    </label>
                                </div>
                            </form>

                            <a href="products?action=listSuppliers" class="btn btn-outline-primary mr-2">Quản lý Nhà
                                Cung Cấp</a>
                            <a href="products?action=new" class="btn btn-success font-weight-bold">+ Nhập hàng mới</a>
                        </div>
                    </div>


                    <table class="table table-bordered table-hover">
                        <thead class="thead-light">
                            <tr>
                                <th>ID</th>
                                <th>Tên sản phẩm</th>
                                <th>Loại hàng</th>
                                <th>Đơn vị</th>
                                <th>Tồn kho</th>
                                <th>Giá Nhập</th>
                                <th>Giá Bán</th>
                                <th>Trạng thái</th>
                                <th style="width: 250px;">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${listProducts}">
                                <tr style="${p.isActive ? '' : 'background-color: #f2f2f2; color: #888;'}">

                                    <td>${p.productID}</td>
                                    <td>${p.productName}</td>

                                    <td>
                                        <c:if test="${p.isTradeGood}">
                                            <span class="badge badge-success">Hàng bán (KD)</span>
                                        </c:if>
                                        <c:if test="${!p.isTradeGood}">
                                            <span class="badge badge-secondary">Tiêu hao (NB)</span>
                                        </c:if>
                                    </td>

                                    <td>${p.unit}</td>

                                    <td style="${p.quantity <= 10 ? 'color:red; font-weight:bold;' : ''}">
                                        ${p.quantity}
                                    </td>

                                    <td>${p.costPrice}</td>
                                    <td class="text-primary font-weight-bold">${p.sellingPrice}</td>

                                    <td>
                                        <c:if test="${p.isActive}">
                                            <span class="badge badge-success">Đang bán</span>
                                        </c:if>
                                        <c:if test="${!p.isActive}">
                                            <span class="badge badge-secondary">Ngừng KD</span>
                                        </c:if>
                                    </td>

                                    <td class="d-flex justify-content-center">
                                        <c:if test="${p.isActive}">
                                            <a href="products?action=edit&id=${p.productID}"
                                                class="btn btn-sm btn-info mr-1">Sửa</a>
                                            <a href="products?action=import&id=${p.productID}"
                                                class="btn btn-sm btn-success mr-1">Nhập</a>

                                            <form action="products" method="POST" style="display:inline;" class="mr-1">
                                                <input type="hidden" name="action" value="softDelete">
                                                <input type="hidden" name="id" value="${p.productID}">
                                                <button type="submit" class="btn btn-sm btn-warning"
                                                    onclick="return confirm('Ngừng kinh doanh sản phẩm này?');">Ẩn</button>
                                            </form>
                                        </c:if>

                                        <c:if test="${!p.isActive}">
                                            <form action="products" method="POST" style="display:inline;" class="mr-1">
                                                <input type="hidden" name="action" value="restore">
                                                <input type="hidden" name="id" value="${p.productID}">
                                                <button type="submit" class="btn btn-sm btn-primary">Khôi phục</button>
                                            </form>
                                        </c:if>

                                        <form action="products" method="POST" style="display:inline;">
                                            <input type="hidden" name="action" value="hardDelete">
                                            <input type="hidden" name="id" value="${p.productID}">
                                            <button type="submit" class="btn btn-sm btn-danger"
                                                onclick="return confirm('Nguy hiểm: Xóa vĩnh viễn không thể khôi phục?(Toàn bộ dữ liệu liên quan sẽ biến mất!');">Xóa</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </body>

            </html>