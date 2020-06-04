<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Beans.Professor" %>
<%@ page import="Beans.Registration" %>
<%@ page import="Beans.Grade" %>
<%@ page import="java.util.List" %>
<%@ page import="Beans.Course" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page import="DAO.CourseDAOImpl" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/2
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    if(session.getAttribute("selectReg")==null){
        session.setAttribute("selectReg",session.getAttribute("registration"));
    }
%>
<html>
<head>
    <title>提交成绩</title>
    <script type="text/javascript">
        function saveGrades() {
            let form = document.getElementsByTagName("form")[1];
            form.action="/SoftwareEngineering_war/GradesServlet?method=saveGrades";
            form.submit();
        }
    </script>
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
        Registration reg= (Registration) session.getAttribute("selectReg");
    %>
    当前时间：<%=now %>
</div>
<form action="/SoftwareEngineering_war/GradesServlet?method=queryCourses" method="post">
    <input type="text" name="year" value="<%=reg.getYear()%>"/>
    <input type="text" name="semester" value="<%=reg.getSemester()%>"/>
    <input type="submit" value="查询"/>
</form>
<div class="error" style="color: red;font-size: medium">${queryError}</div>
<div class="scroll_table">

        <c:if test="${not empty courseList}">
            <table border="1" cellpadding=“0” cellspacing="0" align="center" width="70%">
            <tr style="background-color: red">
                <th>注册号</th>
                <th>课程号</th>
                <th>系号</th>
                <th>课程名称</th>
                <th>课程状态</th>
            </tr>
            <c:forEach items="${courseList}" var="course">
                <tr >
                    <th>${course.reg_id}</th>
                    <th>${course.course_id}</th>
                    <th>${course.dept_id}</th>
                    <th>${course.course_name}</th>
                    <th>${course.status}</th>
                    <th><a href="/SoftwareEngineering_war/GradesServlet?method=queryStudents&course_id=${course.course_id}&registration_id=${course.reg_id}">修改成绩</a></th>
                </tr>
            </c:forEach>
            </table>
        </c:if>

        <c:if test="${not empty gradeList}">
            <form id="gradeForm" action="/SoftwareEngineering_war/GradesServlet?method=submitGrades" method="post">
                <table border="1" cellpadding=“0” cellspacing="0" align="center" width="70%">
                <tr>
                    <th>学生ID</th>
                    <th>学生姓名</th>
                    <th>课程名称</th>
                    <th>学生成绩</th>
                </tr>
               <%
                   String course_name= null;
                   List<Grade> grades= (List<Grade>) session.getAttribute("gradeList");
                   if(grades.size()>0){
                       CourseDAO courseDAO=new CourseDAOImpl();
                       Course course=courseDAO.findCourse(grades.get(0).getCourse_id(),reg.getReg_id());
                       course_name=course.getCourse_name();
                   }
               %>
                    <c:forEach begin="0" varStatus="status" var="grade" items="${gradeList}">
                        <tr>
                            <td>${grade.student_id}</td>
                            <td>${grade.student_name}</td>
                            <td><%=course_name%></td>
                            <td>
                                    <input type="text" name="grade${status.index}" value="${grade.grade}"/>
                            </td>
                        </tr>
                    </c:forEach>
                <input type="button" onclick="saveGrades()" value="保存"/>
                <input type="submit" value="提交"/>
                </table>
            </form>
        </c:if>
</div>
</body>
</html>
