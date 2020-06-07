<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Beans.Professor" %>
<%@ page import="Beans.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="Beans.Registration" %><%--
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
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering_war/jsp/Professor/css/searchAndForm.css" />
    <title>教师选课页面</title>
    <%--
        转入本页面时session中应存在一professor对象
     --%>
</head>
<body>
<div class="time_display" >
    <%
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(d);
        //professor test
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
        java.sql.Date birthday = new java.sql.Date(utilDate.getTime());
        Professor professor = new Professor("P1234567","NIKO",birthday,"3412222222222222","0",1,"123456");
        request.getSession().setAttribute("user",professor);
        Registration registration = new Registration();
        registration.setReg_id(2);
        request.getSession().setAttribute("registration",registration);
    %>
    当前时间：<%=now %>
</div>
</table>
<hr>
<h1>已选课程</h1>
<form action="/SoftwareEngineering_war/ProfessorServlet?method=getCourseList&p_id=${professor.p_id}" method="post">
    <input type="submit" value="刷新" class="button">
</form>
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
        <th>...</th>
    </tr>
    <c:if test="${not empty conflictList}">
        <c:forEach items="${conflictList}" var="course">
            <tr style="background-color: red">
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
                <th><a href="/SoftwareEngineering_war/ProfessorServlet?method=deSelect&course_id=${course.course_id}">取消选课</a></th>
            </tr>
        </c:forEach>
        <a href="/SoftwareEngineering_war/ProfessorServlet?method=deSelectAll" style="text-align: center">取消选择所有课程</a>
    </c:if>
    <c:if test="${not empty slist}">
        <c:forEach items="${slist}" var="course">
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
                <th><a href="/SoftwareEngineering_war/ProfessorServlet?method=deSelect&course_id=${course.course_id}">取消选课</a></th>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty slist and empty conflictList}">
        <tr>
            <td colspan="9" style="text-align: center">没有课程可以显示！</td>
        </tr>
    </c:if>
</table>
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
        <th>...</th>
    </tr>
    <c:if test="${not empty clist}">
        <c:forEach items="${clist}" var="course">
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
                <th><a href="/SoftwareEngineering_war/ProfessorServlet?method=select&course_id=${course.course_id}">立即选课</a></th>
            </tr>
        </c:forEach>
    </c:if>
    <c:if test="${empty clist}">
        <tr>
            <td colspan="9" style="text-align: center">没有课程可以显示！</td>
        </tr>
    </c:if>
</table>
</body>
</html>
