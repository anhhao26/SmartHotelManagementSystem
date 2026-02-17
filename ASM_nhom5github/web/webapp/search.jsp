<%-- 
    Document   : search
    Created on : Feb 17, 2026, 1:33:44 PM
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
background:linear-gradient(-45deg,#667eea,#764ba2,#6dd5fa,#2980b9);
background-size:400% 400%;
animation:bg 12s ease infinite;
}
@keyframes bg{
0%{background-position:0% 50%}
50%{background-position:100% 50%}
100%{background-position:0% 50%}
}

.card{
width:420px;
padding:35px;
border-radius:25px;
background:rgba(255,255,255,0.15);
backdrop-filter:blur(15px);
box-shadow:0 0 40px rgba(0,0,0,.25);
color:white;
animation:slide .7s ease;
}
@keyframes slide{
from{transform:translateY(40px);opacity:0}
to{transform:translateY(0);opacity:1}
}

h2{text-align:center;margin-bottom:30px}

.field{position:relative;margin-bottom:22px}

.field input{
width:100%;
padding:14px;
border:none;
border-radius:12px;
outline:none;
background:rgba(255,255,255,.2);
color:white;
font-size:15px;
}

.field label{
position:absolute;
left:14px;
top:14px;
color:#ddd;
transition:.3s;
pointer-events:none;
}

.field input:focus + label,
.field input:not(:placeholder-shown)+label{
top:-10px;
font-size:12px;
background:#fff;
color:#333;
padding:2px 6px;
border-radius:5px;
}

button{
width:100%;
padding:15px;
border:none;
border-radius:15px;
background:linear-gradient(45deg,#00dbde,#fc00ff);
color:white;
font-size:18px;
cursor:pointer;
overflow:hidden;
position:relative;
transition:.3s;
}
button:hover{transform:scale(1.05)}

button::after{
content:"";
position:absolute;
width:0;
height:0;
border-radius:50%;
background:rgba(255,255,255,.4);
top:50%;left:50%;
transform:translate(-50%,-50%);
transition:.6s;
}
button:active::after{
width:300px;height:300px;
}

.error{text-align:center;color:#ffb3b3;margin-top:15px}
</style>

<div class="card">

<h2>🏨 Book Luxury Room</h2>

<form action="booking" method="post" onsubmit="loading()">

<div class="field">
<input name="roomId" placeholder=" " required>
<label>Room ID</label>
</div>

<div class="field">
<input type="date" name="checkIn" placeholder=" " required>
<label>Check In</label>
</div>

<div class="field">
<input type="date" name="checkOut" placeholder=" " required>
<label>Check Out</label>
</div>

<div class="field">
<input name="name" placeholder=" " required>
<label>Your Name</label>
</div>

<div class="field">
<input name="email" placeholder=" " required>
<label>Email</label>
</div>

<button id="btn">Book Now</button>

</form>

<div class="error">${error}</div>
</div>

<script>
function loading(){
let b=document.getElementById("btn");
b.innerHTML="Checking...";
b.style.opacity=".7";
}
</script>



