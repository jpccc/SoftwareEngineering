<%@ page import="Beans.Student" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
    <title>维护学生信息</title>
</head>
<body background="images/reg_back.jpg">
<h1 style="color: aqua">管理学生档案</h1>
<form class="modifyInfo" action="/SoftwareEngineering/RegistrarServlet?method=modifyStudent&id=${student.s_id}" method="post">
    <div style="color: #00ff6b" class="inputBox" >
        id<br><input type="text" class="password" name="s_id" value="${student.s_id}" disabled><br>
        姓名<br><input type="text" class="password" name="s_name" value="${student.s_name}"><br>
        生日<br><input type="Date" class="password" name="birthday" value="${student.birthday}"><br>
        identify number<br><input type="text" class="password" name="identify_num" value="${student.identify_num}" ><br>
        状态<br><input type="text" class="password" name="status" value="${student.status}"><br>
        部门id<br><input type="text" class="password" name="dept_id" value="${student.dept_id}"><br>
        密码 <input type="text" class="password" name="password" value="${student.password}"><br>
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
            location.href = "/SoftwareEngineering/RegistrarServlet?method=deleteStudent&id=${student.s_id}";
        }
    }
</script>
</html>
