<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sơ Đồ Phòng Khách Sạn | Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: white; color: #333; font-family: 'Segoe UI', Tahoma, sans-serif; padding-bottom: 50px;}
        .navbar-custom { background-color: #0d6efd; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .page-title { color: #2c3e50; font-weight: 800; letter-spacing: 1px; }

        .room-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 20px; margin-top: 30px; }
        
        /* Thẻ phòng cơ bản trên nền trắng */
        .room-card { 
            background: #fff; border-radius: 12px; padding: 25px 15px; text-align: center; 
            box-shadow: 0 4px 15px rgba(0,0,0,0.04); border: 2px solid transparent; 
            transition: all 0.3s ease; width: 100%; position: relative; overflow: hidden;
        }
        .room-card:hover { transform: translateY(-5px); box-shadow: 0 10px 25px rgba(0,0,0,0.1); cursor: pointer; filter: brightness(0.97);}
        
        /* Màu sắc nhận diện trạng thái trên nền trắng */
        .st-Available { border-color: #28a745; } 
        .st-Occupied { border-color: #dc3545; }
        .st-Cleaning { border-color: #ffc107; }
        .st-Maintenance { border-color: #6c757d; }
        
        /* Nội dung bên trong thẻ phòng */
        .r-status { padding: 5px 0; font-size: 12px; font-weight: bold; text-transform: uppercase; width: 100%; position: absolute; top: 0; left: 0; letter-spacing: 1px;}
        
        .st-Available .r-status { background-color: #d1e7dd; color: #0f5132; }
        .st-Occupied .r-status { background-color: #f8d7da; color: #842029; }
        .st-Cleaning .r-status { background-color: #fff3cd; color: #664d03; }
        .st-Maintenance .r-status { background-color: #e2e3e5; color: #41464b; }
        
        .r-number { font-size: 32px; font-weight: 900; color: #2c3e50; margin-top: 15px; margin-bottom: 5px; }
        .r-type { font-size: 13px; font-weight: 700; color: #6c757d; text-transform: uppercase; margin-bottom: 12px;}
        .r-price { font-size: 15px; color: #0d6efd; font-weight: bold; background: #f8f9fa; padding: 4px 8px; border-radius: 6px; display: inline-block; margin-bottom: 5px;}
        
        .btn-action { font-weight: bold; border-radius: 8px; transition: 0.3s; box-shadow: 0 2px 5px rgba(0,0,0,0.1);}
        .btn-action:hover { transform: translateY(-2px); }
    </style>
</head>
<body>

    <nav class="navbar navbar-dark navbar-custom mb-4 px-4 py-3 sticky-top">
        <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/admin"><i class="bi bi-arrow-left"></i> ⬅ VỀ ADMIN DASHBOARD</a>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
            <h2 class="page-title m-0">SƠ ĐỒ PHÒNG KHÁCH SẠN</h2>
            <div>
                <button class="btn btn-success btn-action me-2" data-bs-toggle="modal" data-bs-target="#addRoomModal">+ Thêm Phòng Mới</button>
                <button class="btn btn-warning btn-action text-dark" data-bs-toggle="modal" data-bs-target="#updatePriceModal">$ Chỉnh Giá Nhanh</button>
            </div>
        </div>
        
        <div class="d-flex justify-content-center gap-3 mb-4">
            <span class="badge" style="background-color: #d1e7dd; color: #0f5132; border: 1px solid #198754; font-size: 13px; padding: 8px 15px;">✅ Trống (Available)</span>
            <span class="badge" style="background-color: #f8d7da; color: #842029; border: 1px solid #dc3545; font-size: 13px; padding: 8px 15px;">🔒 Đang ở (Occupied)</span>
            <span class="badge" style="background-color: #fff3cd; color: #664d03; border: 1px solid #ffc107; font-size: 13px; padding: 8px 15px;">🧹 Đang dọn (Cleaning)</span>
            <span class="badge" style="background-color: #e2e3e5; color: #41464b; border: 1px solid #6c757d; font-size: 13px; padding: 8px 15px;">🛠 Bảo trì (Maintenance)</span>
        </div>

        <c:if test="${not empty errorMessage}"><div class="alert alert-danger fw-bold shadow-sm rounded-3 text-center">${errorMessage}</div></c:if>
        <c:if test="${not empty successMessage}"><div class="alert alert-success fw-bold shadow-sm rounded-3 text-center">${successMessage}</div></c:if>

        <div class="room-grid">
            <c:forEach items="${roomList}" var="room">
                <button type="button" class="room-card st-${room.status}" onclick="openStatusModal('${room.roomNumber}', '${room.status}')">
                    <div class="r-status">${room.status}</div>
                    <div class="r-number">${room.roomNumber}</div>
                    <div class="r-type">${room.roomType.typeName}</div>
                    <div class="r-price"><fmt:formatNumber value="${room.price}" pattern="#,###"/> đ / đêm</div>
                    <div class="text-muted" style="font-size: 12px; font-weight: 600;">Tầng ${room.floor}</div>
                </button>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="statusModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content border-0 shadow-lg" style="border-radius: 12px;">
          <form action="<%=request.getContextPath()%>/RoomServlet" method="POST">
              <input type="hidden" name="action" value="changeStatus">
              <input type="hidden" name="roomId" id="modalRoomId">
              <div class="modal-header bg-primary text-white">
                <h5 class="modal-title fw-bold">Cập nhật: Phòng <span id="displayRoomId" class="text-warning"></span></h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
              </div>
              <div class="modal-body p-4">
                <div class="mb-3"><label class="form-label fw-bold text-muted">Trạng thái hiện tại:</label><input type="text" class="form-control bg-light fw-bold" id="modalCurrentStatus" readonly></div>
                <div class="mb-3">
                    <label class="form-label fw-bold text-primary">Chuyển sang trạng thái mới:</label>
                    <select class="form-select border-primary" name="newStatus" id="modalNewStatus" required>
                        <option value="Available">✅ Trống (Available)</option>
                        <option value="Occupied">🔒 Đang ở (Occupied)</option>
                        <option value="Cleaning">🧹 Đang dọn (Cleaning)</option>
                        <option value="Maintenance">🛠 Bảo trì (Maintenance)</option>
                    </select>
                </div>
              </div>
              <div class="modal-footer bg-light"><button type="button" class="btn btn-secondary fw-bold" data-bs-dismiss="modal">Hủy</button><button type="submit" class="btn btn-primary fw-bold px-4">Lưu Thay Đổi</button></div>
          </form>
        </div>
      </div>
    </div>

    <div class="modal fade" id="addRoomModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content border-0 shadow-lg" style="border-radius: 12px;">
                <form action="<%=request.getContextPath()%>/RoomServlet" method="POST">
                    <input type="hidden" name="action" value="addRoom">
                    <div class="modal-header bg-success text-white"><h5 class="modal-title fw-bold">Tạo Phòng Mới</h5><button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div>
                    <div class="modal-body p-4">
                        <div class="mb-3"><label class="form-label fw-bold">Mã Số Phòng</label><input type="text" name="roomId" class="form-control" placeholder="VD: 601" required></div>
                        <div class="mb-3"><label class="form-label fw-bold">Tầng</label><input type="number" name="floor" class="form-control" placeholder="VD: 6" required></div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Chọn Loại Phòng</label>
                            <select name="typeId" class="form-select" required>
                                <c:forEach items="${typeList}" var="type"><option value="${type.roomTypeId}">${type.typeName}</option></c:forEach>
                            </select>
                        </div>
                        <div class="mb-3"><label class="form-label fw-bold text-success">Giá Phòng (VNĐ/Đêm)</label><input type="number" name="price" class="form-control border-success" placeholder="VD: 1500000" step="1000" required></div>
                    </div>
                    <div class="modal-footer bg-light"><button type="submit" class="btn btn-success fw-bold px-4">Xác nhận tạo</button></div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="updatePriceModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content border-0 shadow-lg" style="border-radius: 12px;">
                <form action="<%=request.getContextPath()%>/RoomServlet" method="POST">
                    <input type="hidden" name="action" value="updatePrice">
                    <div class="modal-header bg-warning"><h5 class="modal-title fw-bold text-dark">Điều Chỉnh Giá Từng Phòng</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div>
                    <div class="modal-body p-4">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Chọn Phòng Cần Chỉnh</label>
                            <select name="roomId" class="form-select" required>
                                <c:forEach items="${roomList}" var="r"><option value="${r.roomNumber}">Phòng ${r.roomNumber} - ${r.roomType.typeName} (Cũ: <fmt:formatNumber value="${r.price}" pattern="#,###"/> đ)</option></c:forEach>
                            </select>
                        </div>
                        <div class="mb-3"><label class="form-label fw-bold text-danger">Mức Giá Mới (VNĐ)</label><input type="number" name="newPrice" class="form-control border-danger" step="1000" required></div>
                    </div>
                    <div class="modal-footer bg-light"><button type="submit" class="btn btn-warning fw-bold text-dark px-4">Cập Nhật Ngay</button></div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function openStatusModal(roomId, currentStatus) {
            document.getElementById('modalRoomId').value = roomId;
            document.getElementById('displayRoomId').innerText = roomId;
            document.getElementById('modalCurrentStatus').value = currentStatus;
            document.getElementById('modalNewStatus').value = currentStatus;
            var myModal = new bootstrap.Modal(document.getElementById('statusModal'));
            myModal.show();
        }
    </script>
</body>
</html>