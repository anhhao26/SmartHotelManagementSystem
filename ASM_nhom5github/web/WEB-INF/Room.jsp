<%-- 
    Document   : Room
    Created on : Feb 16, 2026, 12:05:51 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sơ Đồ Phòng - Room Operation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .room-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(160px, 1fr)); gap: 15px; margin-top: 20px; }
        /* Đổi thẻ a thành button nên cần css lại cho đẹp */
        .room-card {
            border-radius: 10px; padding: 25px 10px; text-align: center; color: white !important; 
            font-weight: bold; box-shadow: 0 4px 6px rgba(0,0,0,0.1); 
            display: block; width: 100%; border: none; transition: transform 0.2s, opacity 0.2s;
        }
        .room-card:hover { opacity: 0.85; transform: translateY(-5px); cursor: pointer; }
        
        .status-Available { background-color: #28a745; } 
        .status-Occupied { background-color: #dc3545; }  
        .status-Cleaning { background-color: #ffc107; color: #212529 !important; }  
        .status-Maintenance { background-color: #6c757d; } 
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="text-center text-primary mb-4 fw-bold">SƠ ĐỒ PHÒNG KHÁCH SẠN</h2>
        
        <div class="d-flex justify-content-center gap-3 mb-4">
            <span class="badge bg-success fs-6 px-3 py-2">Trống (Available)</span>
            <span class="badge bg-danger fs-6 px-3 py-2">Đang ở (Occupied)</span>
            <span class="badge bg-warning text-dark fs-6 px-3 py-2">Đang dọn (Cleaning)</span>
            <span class="badge bg-secondary fs-6 px-3 py-2">Bảo trì (Maintenance)</span>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center alert-dismissible fade show shadow-sm">
                <strong>Lỗi quy trình:</strong> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center alert-dismissible fade show shadow-sm">
                <strong>Thành công:</strong> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="room-grid">
            <c:forEach items="${roomList}" var="room">
                <button type="button" class="room-card status-${room.status}" 
                        onclick="openStatusModal('${room.roomId}', '${room.status}')">
                    <h2 class="mb-1">${room.roomId}</h2>
                    <div class="fw-normal mb-2">${room.roomType.typeName}</div>
                    <div class="badge bg-light text-dark" style="font-size: 0.85rem; text-transform: uppercase;">
                        ${room.status}
                    </div>
                </button>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="statusModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <form action="RoomServlet" method="POST">
              <input type="hidden" name="action" value="changeStatus">
              
              <input type="hidden" name="roomId" id="modalRoomId">
              
              <div class="modal-header bg-dark text-white">
                <h5 class="modal-title">Điều chỉnh trạng thái: Phòng <span id="displayRoomId" class="text-warning"></span></h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              
              <div class="modal-body">
                <div class="mb-3">
                    <label class="form-label fw-bold text-secondary">Trạng thái hiện tại:</label>
                    <input type="text" class="form-control bg-light" id="modalCurrentStatus" readonly>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-bold text-primary">Cập nhật sang trạng thái mới:</label>
                    <select class="form-select border-primary" name="newStatus" id="modalNewStatus" required>
                        <option value="Available">Trống (Available) - Sẵn sàng đón khách</option>
                        <option value="Occupied">Đang ở (Occupied) - Khách đang lưu trú</option>
                        <option value="Cleaning">Đang dọn (Cleaning) - Yêu cầu buồng phòng</option>
                        <option value="Maintenance">Bảo trì (Maintenance) - Khóa phòng sửa chữa</option>
                    </select>
                </div>
              </div>
              
              <div class="modal-footer bg-light">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy bỏ</button>
                <button type="submit" class="btn btn-primary px-4">Lưu thay đổi</button>
              </div>
          </form>
        </div>
      </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        function openStatusModal(roomId, currentStatus) {
            // 1. Nhét mã phòng vào form ẩn và tiêu đề Modal
            document.getElementById('modalRoomId').value = roomId;
            document.getElementById('displayRoomId').innerText = roomId;
            
            // 2. Hiển thị trạng thái hiện tại
            document.getElementById('modalCurrentStatus').value = currentStatus;
            
            // 3. Mặc định ô Dropdown sẽ trỏ ngay vào trạng thái hiện tại
            document.getElementById('modalNewStatus').value = currentStatus;
            
            // 4. Lệnh kích hoạt bật Popup lên giữa màn hình
            var myModal = new bootstrap.Modal(document.getElementById('statusModal'));
            myModal.show();
        }
    </script>
</body>
</html>