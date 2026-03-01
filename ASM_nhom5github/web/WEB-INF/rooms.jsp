<%-- 
    Document   : rooms
    Created on : Mar 1, 2026, 11:43:58?PM
    Author     : Huynh Huy
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>Available Rooms</title>

<style>
body{
    font-family: Arial, sans-serif;
    background:#f5f7fa;
    margin:0;
    padding:20px;
}

.title{
    text-align:center;
    font-size:32px;
    font-weight:bold;
    margin-bottom:30px;
    color:#333;
}

.container{
    display:grid;
    grid-template-columns: repeat(auto-fit,minmax(250px,1fr));
    gap:20px;
}

.card{
    background:white;
    border-radius:12px;
    padding:20px;
    box-shadow:0 4px 12px rgba(0,0,0,0.1);
    transition:0.3s;
}

.card:hover{
    transform:translateY(-5px);
    box-shadow:0 8px 20px rgba(0,0,0,0.15);
}

.roomId{
    font-size:22px;
    font-weight:bold;
    color:#2c3e50;
}

.type{
    margin-top:10px;
    font-size:16px;
    color:#555;
}

.price{
    margin-top:10px;
    font-size:18px;
    color:#e74c3c;
    font-weight:bold;
}

.btn{
    margin-top:15px;
    width:100%;
    padding:10px;
    border:none;
    border-radius:8px;
    background:#3498db;
    color:white;
    font-size:16px;
    cursor:pointer;
    transition:0.3s;
}

.btn:hover{
    background:#2980b9;
}
</style>

</head>
<body>

<div class="title">🏨 Available Rooms</div>

<div class="container">

<c:forEach var="r" items="${rooms}">
<div class="card">

<div class="roomId">Room ${r.roomId}</div>

<div class="type">
Type: ${r.roomType.typeName}
</div>

<div class="price">
${r.roomType.pricePerNight} VND / night
</div>

<a href="book-room.jsp?roomId=${r.roomId}">
<button class="btn">Book Now</button>
</a>

</div>
</c:forEach>

</div>

</body>
</html>
