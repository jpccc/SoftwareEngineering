<%@ page import="com.lxb.Professor.Professor" %>
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
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering_war/Registrar/css/form.css" />
    <title>Title</title>
</head>
<body>
<h1>管理教授档案</h1>
<%
    //test session professor
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
    Date birthday = new Date(utilDate.getTime());
    request.getSession().setAttribute("professor",new Professor("P1234561","josh",birthday,1,"1",1,"123456"));
%>
<form class="modifyinfo" action="/RegistrarServlet?method=modifyProfessor&id=${professor.id}" method="post">
    <div class="inputbox" >
        id<br><input type="text" class="password" name="p_id" value="${professor.id}" disabled><br>
        姓名<br><input type="text" class="password" name="p_name" value="${professor.name}"><br>
        生日<br><input type="Date" class="password" name="birthday" value="${professor.birthday}"><br>
        identify number<br><input type="text" class="password" name="identify_num" value="${professor.identify_num}" ><br>
        状态<br><input type="text" class="password" name="status" value="${professor.status}"><br>
        部门id<br><input type="text" class="password" name="dept_id" value="${professor.dept_id}"><br>
        密码 <input type="text" class="password" name="password" value="${professor.password}"><br>
    </div>
    <br>
    <input type="submit" value="修改" class="button">
    <br><br>
    <div class="error">
        ${error}
    </div>
</form>
</body>
</html>
