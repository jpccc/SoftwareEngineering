<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Beans.Professor" %>
<%@ page import="Beans.Registration" %>
<%@ page import="Beans.Grade" %>
<%@ page import="java.util.List" %>
<%@ page import="Beans.Course" %>
<%@ page import="DAO.CourseDAO" %>
<%@ page import="DAO.CourseDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session.getAttribute("selectReg") == null) {
        session.setAttribute("selectReg", session.getAttribute("registration"));
    }
    Registration reg = (Registration) session.getAttribute("selectReg");
%>
<html>
<head>
    <title>成绩修改页面</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Professor/css/user.css">
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Professor/css/scroll.css">
    <script>
        function show() {
            var x = document.getElementById("sideMenu");

            if (x.checked == true) {
                document.getElementById("list").style.left = "-180px";
                x.checked = false;
            } else {
                document.getElementById("list").style.left = 0;
                x.checked = true;
            }
        }

        function saveGrades() {
            var form = document.getElementById("gradeForm");
            form.action = "/SoftwareEngineering/GradesServlet?method=saveGrades";
            form.submit();
        }

        function subGrades() {
            var x = document.getElementById("gradeForm");
            x.submit();
        }
    </script>
</head>

<body>
<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">提交成绩页面</h2>
    </div>
    <div class="head_right">
        <hr id="line_r">
    </div>
</div>
<div class="body">
    <div class="table_one">
        <table class="table_info" border="1">
            <caption>
                <div class="time_display" style="color:#00ff6b;">
                    <%
                        Date d = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String now = df.format(d);
                    %>
                    当前时间：<%=now %>
                </div>
            </caption>

            <div class="table_final">
                <c:if test="${not empty courseList}">
                    <tr>
                        <th>注册号</th>
                        <th>课程号</th>
                        <th>系号</th>
                        <th>课程名称</th>
                        <th>课程状态</th>
                    </tr>
                    <c:forEach items="${courseList}" var="course">
                        <tr>
                            <th>${course.reg_id}</th>
                            <th>${course.course_id}</th>
                            <th>${course.dept_id}</th>
                            <th>${course.course_name}</th>
                            <th>${course.status}</th>
                            <th><a style="color: red"
                                   href="/SoftwareEngineering/GradesServlet?method=queryStudents&course_id=${course.course_id}&registration_id=${course.reg_id}">修改成绩</a>
                            </th>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${not empty gradeList}">
                    <form id="gradeForm" action="/SoftwareEngineering/GradesServlet?method=submitGrades" method="post">

                        <tr>
                            <th>学生ID</th>
                            <th>学生姓名</th>
                            <th>课程名称</th>
                            <th>学生成绩</th>
                        </tr>
                        <%
                            String course_name = null;
                            List<Grade> grades = (List<Grade>) session.getAttribute("gradeList");
                            if (grades.size() > 0) {
                                CourseDAO courseDAO = new CourseDAOImpl();
                                Course course = courseDAO.findCourse(grades.get(0).getCourse_id(), reg.getReg_id());
                                course_name = course.getCourse_name();
                            }
                        %>
                        <c:forEach begin="0" varStatus="status" var="grade" items="${gradeList}">
                            <tr>
                                <td>${grade.student_id}</td>
                                <td>${grade.student_name}</td>
                                <td><%=course_name%>
                                </td>
                                <td>
                                    <input type="text" name="grade${status.index}" value="${grade.grade}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </form>
                </c:if>
            </div>
        </table>
    </div>

</div>
<div class="bottom">
    <div class="bottom_center">
        ${queryError}
    </div>
</div>

<div class="fix_place">
    <button class="function" onclick="show()">功能</button>
    <c:if test="${not empty gradeList}">
        <div>
            <button class="save" type="button" onclick="saveGrades()">保存</button>
        </div>
        <div>
            <button class="submit" type="submit" onclick="subGrades()">提交</button>
        </div>
    </c:if>

    <input type="checkbox" id="sideMenu">
    <div class="navigate">
        <aside id="list">
            <h2>功能列表</h2>
            <ul id="sideul">
                <a href="/SoftwareEngineering/GradesServlet?method=backToMainPage">
                    <li>教师首页</li>
                </a>
<%--                <form action="/SoftwareEngineering/GradesServlet?method=queryCourses" method="post">--%>
<%--                    <div>--%>
<%--                        注册日期：<input class="inputBox" style="width:100px;height: 20px" type="text" name="year"--%>
<%--                                    value="<%=reg.getYear()%>"/>--%>
<%--                    </div>--%>
<%--                    <div>--%>
<%--                        注册学期：<input class="inputBox" style="width:100px;height: 20px" type="text" name="semester"--%>
<%--                                    value="<%=reg.getSemester()%>"/>--%>
<%--                    </div>--%>
<%--                    <input type="submit" value="查询"/>--%>
<%--                </form>--%>
            </ul>
        </aside>
    </div>
</div>
</body>
</html>
