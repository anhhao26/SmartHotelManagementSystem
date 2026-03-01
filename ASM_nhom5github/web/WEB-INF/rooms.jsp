<%-- 
    Document   : rooms
    Created on : Mar 1, 2026, 11:43:58?PM
    Author     : Huynh Huy
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Available Rooms</h2>

<c:forEach var="r" items="${rooms}">
<div style="border:1px solid gray;padding:10px;margin:10px">

Room ${r.roomId}<br>
Type: ${r.roomType.typeName}<br>
Price: ${r.roomType.pricePerNight}

<br><br>

<a href="book-room.jsp?roomId=${r.roomId}">
<button>Book</button>
</a>

</div>
</c:forEach>
