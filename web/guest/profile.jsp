<%@page import="dao.CustomerDAO"%>
<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    model.Account acc = (model.Account) session.getAttribute("acc");
    if (acc == null || !"GUEST".equalsIgnoreCase(acc.getRole())) {
        response.sendRedirect("../login.jsp");
        return;
    }
    Integer cidObj = (Integer) session.getAttribute("CUST_ID");
    if (cidObj == null) { response.sendRedirect("../login.jsp"); return; }
    int cid = cidObj;
    CustomerDAO dao = new CustomerDAO();
    Customer cus = dao.getCustomerByID(cid);
    if (cus == null) { out.println("Không tìm thấy khách!"); return; }
%>
<!DOCTYPE html>
<html>
<head><title>Hồ sơ khách</title></head>
<body>
    <h2>Hồ sơ cá nhân</h2>
    <p>Xin chào, <b><%= cus.getFullName() %></b></p>
    <p>Hạng thành viên: <b><%= cus.getMemberType() %></b></p>
    <p>Điểm: <b><%= cus.getPoints() %></b></p>
    <p>Tổng chi tiêu: <b><%= String.format("%,.0f", cus.getTotalSpending()) %> VNĐ</b></p>

    <h3>Cập nhật thông tin</h3>
    <form action="<%=request.getContextPath()%>/updateProfile" method="post">
        Full name: <input type="text" name="fullName" value="<%=cus.getFullName()%>"><br>
        CCCD/Passport: <input type="text" name="cccd" value="<%=cus.getCccdPassport()%>"><br>
        Phone: <input type="text" name="phone" value="<%=cus.getPhone()%>"><br>
        Email: <input type="email" name="email" value="<%=cus.getEmail()%>"><br>
        Address: <input type="text" name="address" value="<%=cus.getAddress()%>"><br>
        Nationality: <input type="text" name="nationality" value="<%=cus.getNationality()%>"><br>
        Date of Birth: <input type="date" name="dob" value="<%= (cus.getDateOfBirth()!=null)? new java.text.SimpleDateFormat("yyyy-MM-dd").format(cus.getDateOfBirth()) : "" %>"><br>
        Gender:
        <select name="gender">
            <option value="">--</option>
            <option value="Male" <%= "Male".equalsIgnoreCase(cus.getGender()) ? "selected":"" %>>Male</option>
            <option value="Female" <%= "Female".equalsIgnoreCase(cus.getGender()) ? "selected":"" %>>Female</option>
        </select><br>
        Preferences:<br>
        <textarea name="preferences" rows="3" cols="40"><%=cus.getPreferences()==null?"":cus.getPreferences()%></textarea><br>
        <button type="submit">Cập nhật</button>
    </form>

    <p><a href="<%=request.getContextPath()%>/guest/history.jsp">Xem lịch sử lưu trú</a></p>
    <p><a href="<%=request.getContextPath()%>/home.jsp">Về trang chính</a></p>
</body>
</html>