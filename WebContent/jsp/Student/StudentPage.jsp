<%@ page import="Beans.Student" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/2
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>学生页面</title>
</head>
<body>
这里是学生页面
<%Student student= (Student) session.getAttribute("user");%>
<%=student.getS_name()%>
<%=student.getStatus()%>
<%=student.getBirthday()%>
<%=student.getIdentify_num()%>
</body>
</html>
