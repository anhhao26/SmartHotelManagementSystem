<%-- Document : product-form Created on : Feb 10, 2026, 3:18:43 PM Author : ntpho --%>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%@ taglib uri="jakarta.tags.core" prefix="c" %>
            <!DOCTYPE html>
            <html>

            <head>
                <title>Form Sản phẩm</title>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            </head>

            <body>
                <div class="container mt-5">
                    <div class="card shadow">
                        <div class="card-header bg-primary text-white">
                            <h4>
                                <c:if test="${product != null}">Cập nhật Sản phẩm</c:if>
                                <c:if test="${product == null}">Nhập Kho Mới</c:if>
                            </h4>
                        </div>
                        <div class="card-body">
                            <form action="products" method="post">

                                <c:if test="${product != null}">
                                    <input type="hidden" name="action" value="update" />
                                    <input type="hidden" name="id" value="${product.productID}" />
                                </c:if>
                                <c:if test="${product == null}">
                                    <input type="hidden" name="action" value="insert" />
                                </c:if>

                                <div class="form-group">
                                    <label>Tên sản phẩm:</label>
                                    <input type="text" class="form-control" name="name" value="${product.productName}"
                                        required />
                                </div>

                                <div class="form-group">
                                    <label>Phân loại hàng hóa (*):</label>
                                    <div class="border p-2 rounded">
                                        <div class="custom-control custom-radio custom-control-inline">
                                            <input type="radio" id="trade1" name="isTradeGood" value="1"
                                                class="custom-control-input" ${product==null || product.isTradeGood
                                                ? 'checked' : '' } onclick="toggleSellingPrice(true)">
                                            <label class="custom-control-label font-weight-bold text-success"
                                                for="trade1">
                                                Hàng bán lẻ (Revenue)
                                            </label>
                                        </div>

                                        <div class="custom-control custom-radio custom-control-inline ml-4">
                                            <input type="radio" id="trade2" name="isTradeGood" value="0"
                                                class="custom-control-input" ${product !=null && !product.isTradeGood
                                                ? 'checked' : '' } onclick="toggleSellingPrice(false)">
                                            <label class="custom-control-label font-weight-bold text-secondary"
                                                for="trade2">
                                                Vật tư tiêu hao (Cost)
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 form-group">
                                        <label>Đơn vị (Lon/Gói):</label>
                                        <input type="text" class="form-control" name="unit" value="${product.unit}"
                                            required />
                                    </div>
                                    <div class="col-md-6 form-group">
                                        <label>Số lượng:</label>
                                        <input type="number" class="form-control" name="quantity"
                                            value="${product.quantity}" ${product !=null
                                            ? 'readonly style="background-color: #e9ecef;"' : 'required' } />
                                        <c:if test="${product != null}">
                                            <small class="text-danger">Muốn tăng số lượng, hãy ra ngoài bấm nút
                                                "Nhập".</small>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 form-group">
                                        <label>Giá Nhập (Cost):</label>
                                        <input type="number" class="form-control" name="costPrice"
                                            value="${product.costPrice}" ${product !=null
                                            ? 'readonly style="background-color: #e9ecef;"' : 'required' } />
                                    </div>

                                    <div class="col-md-6 form-group">
                                        <label class="text-primary font-weight-bold">Giá Bán (Selling):</label>
                                        <input type="number" id="sellingPriceInput" class="form-control border-primary"
                                            name="sellingPrice" value="${product.sellingPrice}" required />
                                        <small id="priceNote" class="text-muted">Giá này dùng để tính tiền cho
                                            khách.</small>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label>Nhà cung cấp:</label>
                                    <div class="input-group">
                                        <select name="supplierID" class="form-control">
                                            <c:forEach var="s" items="${listSuppliers}">
                                                <option value="${s.supplierID}"
                                                    ${product.supplier.supplierID==s.supplierID ? 'selected' : '' }>
                                                    ${s.supplierName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <div class="input-group-append">
                                            <a href="products?action=newSupplier"
                                                class="btn btn-outline-success font-weight-bold">+ Thêm NCC Mới</a>
                                        </div>
                                    </div>
                                </div>

                                <button type="submit" class="btn btn-primary btn-block">Lưu Dữ Liệu</button>
                                <a href="products" class="btn btn-secondary btn-block">Hủy bỏ</a>
                            </form>
                        </div>
                    </div>
                </div>
                <script>
                    function toggleSellingPrice(isTrade) {
                        var input = document.getElementById("sellingPriceInput");
                        var note = document.getElementById("priceNote");

                        if (isTrade) {
                            // Nếu là Hàng bán: Cho nhập, màu nền trắng
                            input.readOnly = false;
                            input.style.backgroundColor = "white";
                            // input.value = ""; // Hoặc giữ nguyên giá cũ nếu muốn
                            input.setAttribute("required", "true"); // Bắt buộc nhập
                            note.innerText = "Giá này dùng để tính tiền cho khách.";
                        } else {
                            // Nếu là Hàng tiêu dùng: Khóa lại, Set về 0
                            input.value = 0;
                            input.readOnly = true;
                            input.style.backgroundColor = "#e9ecef"; // Màu xám
                            input.removeAttribute("required"); // Không bắt buộc (vì đã set 0)
                            note.innerText = "Hàng nội bộ không có giá bán (Mặc định = 0).";
                        }
                    }

                    // Gọi 1 lần khi trang vừa load để set trạng thái đúng theo dữ liệu cũ
                    window.onload = function () {
                        // Kiểm tra xem radio nào đang checked
                        var isTrade = document.getElementById("trade1").checked;
                        toggleSellingPrice(isTrade);
                    };
                </script>
            </body>

            </html>