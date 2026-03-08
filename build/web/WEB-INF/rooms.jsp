<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Danh Sách Phòng Khách Sạn - SmartHotel</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    /* Chuyển nền sang sáng sủa, thanh lịch */
    body { font-family: 'Segoe UI', Tahoma, sans-serif; background-color: #f4f7f6; color: #333; margin: 0; padding: 30px 20px 50px;}
    
    /* Làm nổi bật tiêu đề */
    .title { text-align: center; font-size: 34px; font-weight: 900; margin-bottom: 40px; color: #2c3e50; text-transform: uppercase; letter-spacing: 1px;}
    
    .container-rooms { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 25px; max-width: 1200px; margin: 0 auto; }
    
    /* Thiết kế lại thẻ phòng: nền trắng, bo góc, bóng mờ */
    .card-room { 
        background: #ffffff; border-radius: 16px; padding: 30px 20px; 
        box-shadow: 0 4px 20px rgba(0,0,0,0.05); transition: all 0.3s ease; 
        text-align: center; border: 1px solid #e9ecef; position: relative;
    }
    .card-room:hover { transform: translateY(-8px); box-shadow: 0 12px 25px rgba(13, 110, 253, 0.15); border-color: #0d6efd; }
    
    .roomId { font-size: 28px; font-weight: 900; color: #2c3e50; margin-bottom: 5px; }
    
    /* Cập nhật lại màu các nhãn trạng thái cho hợp nền trắng */
    .badge-status { display: inline-block; padding: 6px 15px; border-radius: 20px; font-size: 12px; font-weight: 800; margin-top: 10px; text-transform: uppercase; letter-spacing: 0.5px;}
    .bg-Available { background-color: #d1e7dd; color: #0f5132; border: 1px solid #badbcc;} 
    .bg-Occupied { background-color: #f8d7da; color: #842029; border: 1px solid #f5c2c7;}  
    .bg-Cleaning { background-color: #fff3cd; color: #664d03; border: 1px solid #ffecb5;}  
    .bg-Maintenance { background-color: #e2e3e5; color: #41464b; border: 1px solid #d3d6d8;} 

    .type { margin-top: 15px; font-size: 15px; color: #6c757d; font-weight: 600;}
    /* Giá tiền làm nổi bật bằng màu đỏ tươi */
    .price { margin-top: 10px; font-size: 22px; color: #dc3545; font-weight: 800; }
    
    /* Nút bấm Đặt phòng sang trọng */
    .btn-book { 
        margin-top: 25px; width: 100%; padding: 14px; border: none; border-radius: 10px; 
        background: linear-gradient(45deg, #0d6efd, #0b5ed7); color: white; 
        font-size: 16px; font-weight: bold; cursor: pointer; transition: 0.3s; box-shadow: 0 4px 10px rgba(13, 110, 253, 0.2);
    }
    .btn-book:hover { transform: scale(1.03); box-shadow: 0 8px 15px rgba(13, 110, 253, 0.3); }
    
    /* Nút bị vô hiệu hóa (Khi phòng không trống) */
    .btn-disabled { background: #e9ecef !important; color: #adb5bd !important; cursor: not-allowed !important; box-shadow: none !important; transform: none !important;}

    /* Nút quay lại mảnh mai, lịch sự */
    .back-btn { 
        display: inline-block; margin-bottom: 20px; padding: 10px 20px; 
        background: #ffffff; color: #6c757d; text-decoration: none; border-radius: 8px; 
        border: 1px solid #ced4da; font-weight: bold; transition: 0.2s;
    }
    .back-btn:hover { background: #f8f9fa; color: #0d6efd; border-color: #0d6efd; }
</style>
</head>
<body>

<div class="container" style="max-width: 1200px; margin: 0 auto;">
    <a href="${pageContext.request.contextPath}/guest/profile.jsp" class="back-btn">⬅ Quay lại Hồ sơ</a>
</div>

<div class="title">🏨 Khám Phá Phòng Khách Sạn</div>

<div class="container-rooms">
    <c:forEach var="r" items="${rooms}">
        <div class="card-room">
            <div class="roomId">Phòng ${r.roomNumber}</div>
            
            <div>
                <span class="badge-status bg-${r.status}">
                    ${r.status == 'Available' ? '✅ Sẵn sàng' : 
                      r.status == 'Occupied' ? '🔒 Đang có khách' : 
                      r.status == 'Cleaning' ? '🧹 Đang dọn dẹp' : '🛠 Đang bảo trì'}
                </span>
            </div>

            <div class="type">Loại phòng: <b style="color: #0d6efd;">${r.roomType.typeName}</b></div>
            <div class="price"><fmt:formatNumber value="${r.price}" pattern="#,###"/> VNĐ <span style="font-size: 14px; color: #6c757d;">/ Đêm</span></div>
            
            <c:choose>
                <c:when test="${r.status == 'Available'}">
                    <a href="${pageContext.request.contextPath}/webapp/search.jsp?roomId=${r.roomNumber}" style="text-decoration: none;">
                        <button class="btn-book">ĐẶT PHÒNG NÀY</button>
                    </a>
                </c:when>
                <c:otherwise>
                    <button class="btn-book btn-disabled" disabled>KHÔNG THỂ ĐẶT</button>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>

<c:if test="${empty rooms}">
    <div style="max-width: 1200px; margin: 0 auto;">
        <h4 style="color: #dc3545; background: #fff; padding: 40px; border-radius: 12px; text-align: center; box-shadow: 0 4px 15px rgba(0,0,0,0.05); border: 1px solid #f5c2c7;">
            ⚠️ Hệ thống chưa có dữ liệu phòng hoặc đã hết phòng trống!
        </h4>
    </div>
</c:if>

</body>
</html>