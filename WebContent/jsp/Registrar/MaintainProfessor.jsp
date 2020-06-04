<%@ page import="Beans.Professor" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %><%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering_war/jsp/Registrar/css/form.css" />
    <title>Title</title>
</head>
<body>
<h1>管理教授档案</h1>
<form class="modifyinfo" action="/SoftwareEngineering_war/RegistrarServlet?method=modifyProfessor&id=${user.p_id}" method="post">
    <div class="inputbox" >
        id<br><input type="text" class="password" name="p_id" value="${user.p_id}" disabled><br>
        姓名<br><input type="text" class="password" name="p_name" value="${user.p_name}"><br>
        生日<br><input type="Date" class="password" name="birthday" value="${user.birthday}"><br>
        identify number<br><input type="text" class="password" name="identify_num" value="${user.identify_num}" ><br>
        状态<br><input type="text" class="password" name="status" value="${user.status}"><br>
        部门id<br><input type="text" class="password" name="dept_id" value="${user.dept_id}"><br>
        密码 <input type="text" class="password" name="password" value="${user.password}"><br>
    </div>
    <br>
    <input type="submit" value="修改" class="button">
    <br>
    <br><br>
    <div class="error">
        ${error}
    </div>
    <br>
    <input type="button" value="删除" class="button" style="background-color: red;color: white" onclick="firm()"/>
</form>
</body>
<script language="javascript">
    function firm() {
        if (confirm("确定删除？")) {
            location.href = "/SoftwareEngineering_war/RegistrarServlet?method=deleteProfessor&id=${user.p_id}";
        }
    }
</script>
</html>
