<%@ page import="Beans.Professor" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>
<%@ page import="DAO.DepartmentDao" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
    <script type="text/javascript" src="/SoftwareEngineering/jsp/Registrar/js/subPro.js"></script>
    <title>维护教师信息</title>
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
<h1 style="color: antiquewhite">管理教授档案</h1>
<form class="modifyInfo" action="/SoftwareEngineering/RegistrarServlet?method=modifyProfessor&id=${professor.p_id}"
      method="post" onsubmit="return subPro();">
    <div style="color: #00ff6b" class="inputBox">
        id<br><input type="text" class="password" name="p_id" value="${professor.p_id}" disabled><br>
        姓名<br><input type="text" class="password" name="p_name" value="${professor.p_name}"><br>
        生日<br><input type="Date" class="password" name="birthday" value="${professor.birthday}"><br>
        identify number<br><input type="text" class="password" name="identify_num" value="${professor.identify_num}"><br>
        状态<br><input type="text" class="password" name="status" value="${professor.status}"><br>
        学院<br><select class="select" name="dept_id">
            <c:forEach items="${dept_list}" var="dep">
                <option value="${dep.key}">${dep.value}</option>
            </c:forEach>
        </select>
        <br>
        密码 <input type="text" class="password" name="password" value="${professor.password}"><br>
    </div>
    <br>
    <input type="submit" value="修改" class="button">
    <br>
    <br><br>
    <div class="error">
        ${error}
    </div>
    <br>
    <input type="button" value="删除" class="button" style="background-color: #874343;color: white" onclick="firm()"/>
</form>
<a href="/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp" style="text-align: center;color: white">返回上层</a>
</body>
<script language="javascript">
    function firm() {
        if (confirm("确定删除？")) {
            location.href = "/SoftwareEngineering/RegistrarServlet?method=deleteProfessor&id=${professor.p_id}";
        }
    }
</script>
</html>
