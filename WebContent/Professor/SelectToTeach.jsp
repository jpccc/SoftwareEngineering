<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>教师选课页面</title>
</head>
<body>
<div class="time_display" style="text-align: center">
    <%
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(d);
    %>
    当前时间：<%=now %>
</div class="course_list">
<table align="center">
    <form action="/demo_war_exploded/BookServlet?method=find" method="post">
        <tr>
            <th>按编号：</th>
            <th><input type="text" name="byid" ></th>
            <th>按课程名:</th>
            <th><input type="text" name="byname"></th>
            <th><input type="submit" value="查询" class="button"></th>
            <th>${error}</th>
        </tr>
    </form>
</table>
<hr>
<h1 style="text-align: center">显示课程信息</h1>
<table border="1" cellpadding=“0” cellspacing="0" align="center" width="70%">
    <tr>
        <th>编号</th>
        <th>课程名</th>
        <th>作者</th>
        <th>...</th>
    </tr>
    <c:if test="${not empty list}">
        <c:forEach items="${list}" var="course">
            <tr>
                <th>${course.id}</th>
                <th>${course.name}</th>

                <th><a href="/SoftwareEngineering_war/CourseServlet?method=select&id=${course.id}">选课</a></th>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty list}">
        <tr>
            <td colspan="9" style="text-align: center">没有课程可以显示！</td>
        </tr>
    </c:if>
</table>
<div>

</div>
</body>
</html>
