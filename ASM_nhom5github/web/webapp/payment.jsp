<%-- 
    Document   : payment
    Created on : Feb 17, 2026, 1:34:19 PM
    Author     : Huynh Huy
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<style>
body{
margin:0;
height:100vh;
display:flex;
justify-content:center;
align-items:center;
font-family:Segoe UI;
background:linear-gradient(270deg,#ff512f,#dd2476,#1fa2ff,#12d8fa);
background-size:600% 600%;
animation:bg 14s ease infinite;
}
@keyframes bg{
0%{background-position:0% 50%}
50%{background-position:100% 50%}
100%{background-position:0% 50%}
}

.box{
width:440px;
padding:35px;
border-radius:25px;
background:white;
text-align:center;
box-shadow:0 0 40px rgba(0,0,0,.3);
animation:pop .6s ease;
}
@keyframes pop{
from{transform:scale(.7);opacity:0}
to{transform:scale(1);opacity:1}
}

.info{
background:#f5f7fa;
padding:15px;
border-radius:15px;
margin-bottom:20px;
font-size:17px;
}

.qr{
margin:20px 0;
animation:float 2s ease-in-out infinite;
}
@keyframes float{
0%,100%{transform:translateY(0)}
50%{transform:translateY(-10px)}
}

button{
width:100%;
padding:15px;
border:none;
border-radius:15px;
background:linear-gradient(45deg,#11998e,#38ef7d);
color:white;
font-size:18px;
cursor:pointer;
transition:.3s;
}
button:hover{
transform:scale(1.06);
box-shadow:0 0 20px #38ef7d;
}
</style>

<div class="box">

<h2>💳 Secure Payment</h2>

<div class="info">
Room: <b>${booking.room.roomId}</b><br>
Total: <b>$${booking.totalPrice}</b>
</div>

<div class="qr">
<img src="https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=PAY-${booking.bookingId}">
</div>

<form action="confirmPayment" method="post" onsubmit="pay()">
<input type="hidden" name="id" value="${booking.bookingId}">
<input type="hidden" name="email" value="${booking.email}">
<button id="payBtn">Confirm Payment</button>
</form>

</div>

<script>
function pay(){
let b=document.getElementById("payBtn");
b.innerHTML="Verifying...";
b.style.opacity=".7";
}
</script>


