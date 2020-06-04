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
    <link rel="stylesheet" href="css/list.css">
    <link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<div class="container" id="box">
<table class="table_info" border="1">
    <caption class="h3 text-info">学生信息表</caption>
    <tr class="text-danger">
        <th class="text-center">名字</th>
        <th class="text-center">生日</th>
        <th class="text-center">身份证</th>
        <th class="text-center">Status</th>
        <th class="text-center">公寓ID</th>
    </tr>
    <tr class="text-center" v-for="item in myData">
        <%Student student= (Student) session.getAttribute("user");%>
        <td><%=student.getS_name()%></td>
        <td><%=student.getBirthday()%></td>
        <td><%=student.getIdentify_num()%></td>
        <td><%=student.getStatus()%></td>
        <td>公寓ID</td>
    </tr>
</table>
    <div class="form-group">
        <input type="button" value="注册课程" class="btn_btn-primary">
        <input type="button" value="查看分数" class="btn_btn-primary">
    </div>
    <div class="form-group">
        <input type="button" value="查看我的课表" class="btn_btn-list">
    </div>
</div>
</body>
</html>
