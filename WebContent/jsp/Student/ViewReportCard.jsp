<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>学生成绩页面</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Student/css/user.css">
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
    </script>
</head>
<body background="images/stu_back.jpg">

<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">学生成绩页面</h2>
    </div>
    <div class="head_right">

        <hr id="line_r">
    </div>

</div>

<div class="body">
    <div class="table_one">
        <form id="course_form" action="/SoftwareEngineering/SelectCourseServlet?op=add"
              method="post">
            <table class="table_info" border="1">
                <caption>
                    <div class="time_display" style="color:#00ff6b;">
                        <%
                            Date d = new Date();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String now = df.format(d);
                        %>
                        当前时间：<%=now %>
                    </div class="course_list">
                </caption>

                <div class="table_final">
                    <tr>
                        <th>course_id</th>
                        <th>course_name</th>
                        <th>grade</th>
                    </tr>
                    <c:if test="${not empty CourseList}">
                        <c:forEach items="${CourseList}" var="Course" varStatus="st">
                            <tr>
                                <td>${Course.course_id}</td>
                                <td>${Course.course_name}</td>
                                <td>${GradeList[st.index]}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </div>
            </table>
        </form>
    </div>
</div>

<div class="bottom">
    <div class="bottom_center">
        ${message}
        欢迎使用教务管理系统!
    </div>
</div>

<div class="fix_place">
    <button class="function" onclick="show()">功能</button>
    <input type="checkbox" id="sideMenu">
    <div class="navigate">
        <aside id="list">
            <h2>功能列表</h2>
            <ul id="sideul">
                <a href="##">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Student/ViewReportCard.jsp">
                    <li>查询成绩</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Student/add_course.jsp">
                    <li>选择课程</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Student/show_schedule.jsp">
                    <li>查询课表</li>
                </a>
                <a href="/SoftwareEngineering/BillServlet">
                    <li>查看邮箱</li>
                </a>
            </ul>
        </aside>
    </div>
</div>
</body>
</html>