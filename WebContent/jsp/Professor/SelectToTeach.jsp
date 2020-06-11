<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="Beans.Professor" %>
<%@ page import="Beans.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="Beans.Registration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Professor/css/user.css" />
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Professor/css/scroll.css">
    <title>教师选课页面</title>
    <script>
        function show(){
            var x=document.getElementById("sideMenu");

            if(x.checked==true){
                document.getElementById("list").style.left="-180px";
                x.checked=false;
            }
            else{
                document.getElementById("list").style.left="0";
                x.checked=true;
            }
        }
    </script>
</head>
<body>
</table>
<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">教师选课页面</h2>
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
                <div class="title">已选课程</div>
            </caption>
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
                        <th><a href="/SoftwareEngineering/ProfessorServlet?method=deSelect&course_id=${course.course_id}">取消选课</a></th>
                    </tr>
                </c:forEach>
                <a href="/SoftwareEngineering/ProfessorServlet?method=deSelectAll" style="text-align: center">取消选择所有课程</a>
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
                        <th><a href="/SoftwareEngineering/ProfessorServlet?method=deSelect&course_id=${course.course_id}">取消选课</a></th>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty slist and empty conflictList}">
                <tr>
                    <td colspan="9" style="text-align: center">没有课程可以显示！</td>
                </tr>
            </c:if>

        </table>
        <table class="table_info" border="1">
            <caption>
                <div class="title">显示课程信息</div>
            </caption>
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
                        <th><a href="/SoftwareEngineering/ProfessorServlet?method=select&course_id=${course.course_id}">立即选课</a></th>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty clist}">
                <tr>
                    <td colspan="9" style="text-align: center">没有课程可以显示！</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>



<div class="fix_place">
    <button class="function" onclick="show()">功能</button>
    <form action="/SoftwareEngineering/ProfessorServlet?method=getCourseList&p_id=${user.p_id}" method="post">
        <input  class="refresh" type="submit" value="刷新">
    </form>
    <input type="checkbox" id="sideMenu">
    <div class="navigate">
        <aside id="list">
            <h2>功能列表</h2>
            <ul id="sideul">
                <a href="/SoftwareEngineering/jsp/Professor/ProfessorPage.jsp">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Professor/SelectToTeach.jsp">
                    <li>选则课程</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Professor/SubmitGrades.jsp">
                    <li>提交分数</li>
                </a>
                <a href="/SoftwareEngineering/GradesServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
            </ul>
        </aside>
    </div>
</div>
</body>
</html>
