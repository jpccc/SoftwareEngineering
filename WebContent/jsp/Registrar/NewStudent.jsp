<%@ page import="DAO.DepartmentDao" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
    <script type="text/javascript" src="/SoftwareEngineering/jsp/Registrar/js/addUser.js"></script>
    <title>新建学生档案</title>
</head>
<%
    DepartmentDao dept = new DepartmentDao();
    Map<String, String> dept_list = dept.getAll();
    if (dept_list.size() == 0) {
        request.setAttribute("error", "请先录入系信息!");
        request.getRequestDispatcher("/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
    }
    session.setAttribute("dept_list",dept_list);
%>
<body background="/SoftwareEngineering/jsp/Registrar/images/reg_back.jpg">
<h1>新建学生档案</h1>
<form class="modifyInfo" action="/SoftwareEngineering/RegistrarServlet?method=addStudent" method="post"
      onsubmit="return subInfo();">
    <div class="inputBox">
        姓名<br><input type="text" class="password" name="s_name"><br>
        生日<br><input type="Date" class="password" name="birthday"><br>
        identify number<br><input type="text" class="password" name="identify_num"><br>
        状态<br><input type="text" class="password" name="status"><br>
        学院<br>
        <select class="select" name="dept_id">
            <c:forEach items="${dept_list}" var="dep">
                <option value="${dep.key}">${dep.value}</option>
            </c:forEach>
        </select><br>
        毕业时间<br><input type="Date" class="password" name="graduate_date"><br>
        密码 <input type="text" class="password" name="password"><br>
    </div>
    <br>
    <input type="submit" value="添加" class="button">
    <br><br>
    <div class="error">
        ${error}
    </div>
</form>
<a href="/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp" style="text-align: center;color: white">返回上层</a>
</body>
</html>
