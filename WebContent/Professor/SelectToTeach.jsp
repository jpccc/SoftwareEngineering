<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering_war/Professor/css/searchAndForm.css" />
    <title>教师选课页面</title>
</head>
<body>
<div class="time_display" >
    <%
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(d);
    %>
    当前时间：<%=now %>
</div class="course_list">
<table align="center">
    <form action="/SoftwareEngineering_war/ProfessorServlet?method=searchCourse" method="post">
        <tr>
            <th>按编号：</th>
            <th><input type="text" name="by_id" ></th>
            <th>按课程名:</th>
            <th><input type="text" name="by_name"></th>
            <th><input type="submit" value="查询" class="button"></th>
            <th>${error}</th>
        </tr>
    </form>
</table>
<hr>
<h1 >显示课程信息</h1>
<table border="1" cellpadding=“0” cellspacing="0" align="center" width="70%">
    <tr>
        <th>reg id</th>
        <th>课程id</th>
        <th>院系id</th>
        <th>科目名</th>
        <th>起始日期</th>
        <th>截止日期</th>
        <th>上课日期（周）</th>
        <th>时间戳</th>
        <th>学生数</th>
        <th>当前状态</th>
    </tr>
    <c:if test="${not empty list}">
        <c:forEach items="${list}" var="course">
            <tr>
                <th>${course.reg_id}</th>
                <th>${course.course_id}</th>
                <th>${course.dept_id}</th>
                <th>${course.course_name}</th>
                <th>${course.start_date}</th>
                <th>${course.end_date}</th>
                <th>${course.weekday}</th>
                <th>${course.timeslot_id}</th>
                <th>${course.student_count}</th>
                <th>${course.status}</th>
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
</body>
</html>
