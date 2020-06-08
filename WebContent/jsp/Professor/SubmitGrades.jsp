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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    if(session.getAttribute("selectReg")==null){
        session.setAttribute("selectReg",session.getAttribute("registration"));
    }
    Registration reg= (Registration) session.getAttribute("selectReg");
%>
<html>
<head>
    <title>成绩修改页面</title>
    <link rel="stylesheet" href="css/user.css">
    <script>
        function show(){
            var x=document.getElementById("sidemenu");

            if(x.checked==true){
                document.getElementById("list").style.left="-180px";
                x.checked=false;
            }
            else{
                document.getElementById("list").style.left=0;
                x.checked=true;
            }
        }
        function saveGrades() {
            let form = document.getElementsByTagName("form")[1];
            form.action="/SoftwareEngineering/GradesServlet?method=saveGrades";
            form.submit();
        }
        function subGrades(){
            var x = document.getElementById("gradeForm");
            x.submit();
        }
    </script>
</head>
<body background="<c:url value="images/pro_back.jpg"/>">
<div class="head">
    <div class="block_left">
        <div>
            <hr id="line_l">
        </div>
    </div>
    <div class="block_right">
        <div class="head_title">
            <h2 id="title">成绩修改表</h2>
        </div>
        <div class="inerblock_right">
            <div>
                <hr id="line_r">
            </div>
        </div>
    </div>
</div>

<div class="body">
    <div class="navigate">
        <input type="checkbox" id="sidemenu">
        <aside id="list">
            <h2>功能列表</h2>
            <br/>
            <ul id="sideul">
                <a href="/SoftwareEngineering_war/GradesServlet?method=backToMainPage">
                    <li>教师首页</li>
                </a>
                <a href="jsp/Professor/SelectToTeach.jsp">
                    <li>选课</li>
                </a>
                <form action="/SoftwareEngineering/GradesServlet?method=queryCourses" method="post">
                    <div>
                        注册日期：<input class="inputBox" style="width:50px" type="text" name="year" value="<%=reg.getYear()%>"/>
                    </div>
                    <div>
                        注册学期：<input class="inputBox" style="width:50px" type="text" name="semester" value="<%=reg.getSemester()%>"/>
                    </div>
                    <input type="submit" value="查询"/>
                </form>
            </ul>
        </aside>
    </div>
    <div class="table_one">
    <table class="table_info" border="1">
        <caption class="table_info">
            <script>
                document.write(Date());
            </script>
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
                    <tr >
                        <th>${course.reg_id}</th>
                        <th>${course.course_id}</th>
                        <th>${course.dept_id}</th>
                        <th>${course.course_name}</th>
                        <th>${course.status}</th>
                        <th><a style="color: red" href="/SoftwareEngineering/GradesServlet?method=queryStudents&course_id=${course.course_id}&registration_id=${course.reg_id}">修改成绩</a></th>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${not empty gradeList}">
                <form id="gradeForm" action="/SoftwareEngineering_war/GradesServlet?method=submitGrades" method="post">

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
                </form>
            </c:if>
        </div>
    </table>
    </div>
    <div class="error" style="color: red;font-size: medium">${queryError}</div>
    <div class="bottom">
            <div class="innerBottom_left">
                <div>
                <button onclick="show()">功能</button>
                </div>
                <c:if test="${not empty gradeList}">
                        <div>
                    <button type="button" onclick="saveGrades()">保存</button>
                        </div>
                    <div>
                    <button type="submit" onclick="subGrades()">提交</button>
                    </div>
                </c:if>
            </div>
    </div>
</div>
</body>
</html>
