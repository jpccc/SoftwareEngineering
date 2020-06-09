<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
         import="java.util.ArrayList" import="Beans.Course" import="Beans.CourseSelection" import="Beans.Student" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>学生课表</title>
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

        function submit() {
            if (confirm("确认退课?")) {
                var x = document.getElementById("course_form");
                x.submit();
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
        <h2 id="title">学生信息表</h2>
    </div>
    <div class="head_right">

        <hr id="line_r">
    </div>
</div>

<div class="body">
    <div class="table_one">
        <form id="course_form" action="/SoftwareEngineering/SelectCourseServlet?op=delete" method="post">
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
                    <tr class="text-property">
                        <th height="57" class="text-center">课程名</th>
                        <th class="text-center">课程起始时间</th>
                        <th class="text-center">课程结束时间</th>
                        <th class="text-center">WeekDay</th>
                        <th class="text-center">教师ID</th>
                        <th class="text-center">已选人数</th>
                        <th class="text-center">删除课程</th>
                    </tr>
                    <%
                        SelectCourseDAO select_course_dao = new DAO.SelectCourseDAOImpl();
                        List<CourseSelection> list = new ArrayList<CourseSelection>();
                        Student student = (Student) session.getAttribute("user");
                        String s_id = student.getS_id();
                        list = select_course_dao.get_schedule(s_id);
                        int name = 0;
                        for (int i = 0; i < list.size(); i++) {
                            Course course = select_course_dao.check_course_from_selection(list.get(i));
                    %>
                    <tr class="text-value">
                        <td height="62"><%=course.getCourse_name()%>
                        </td>
                        <td><%=course.getStart_date()%>
                        </td>
                        <td><%=course.getEnd_date()%>
                        </td>
                        <td><%=course.getWeekday()%>
                        </td>
                        <td><%=course.getProfessor_id()%>
                        </td>
                        <td><%=course.getStudent_count()%>
                        </td>
                        <td>
                            <input type="radio"
                                   value="<%=list.get(i).get_course_id()%> <%=list.get(i).get_reg_id() %> <%=list.get(i).get_student_id() %> <%=list.get(i).get_select_status() %>"
                                   name=<%=name%>
                            />删除
                        </td>
                    </tr>
                    <%
                            name++;
                        }
                    %>
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
    <button class="button_submit" onclick="submit()">提交</button>
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

<span style="font-size:18px;color: red"><body>
	<%

        String head[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};  //课程表嘴上一行
        String left[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
        String main[][] = new String[8][7];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                main[i][j] = "";
            }
        }


        List<CourseSelection> schedule = new ArrayList<CourseSelection>();
        String[][] schedule_flag = new String[8][7];
        schedule = select_course_dao.get_schedule(s_id);
        for (int i = 0; i < schedule.size(); i++) {
            Course course = select_course_dao.check_course_from_selection(schedule.get(i));
            int weekday = course.getWeekday();
            int slot = course.getTimeslot_id();
            for (int m = 0; m < 8; m++) {
                System.out.print("m=" + m + "\n");
                if (SelectCourseDAOImpl.get(slot, m) == 1) {
                    for (int n = 0; n < 7; n++) {
                        System.out.println("n=" + n);
                        if (SelectCourseDAOImpl.get(weekday, n) == 1) {
                            main[m][n] = course.getCourse_name();
                        }
                    }
                }
            }
        }
    %>
	<center><b>大学生课程表</b></center>
	<table width="500" height="100" border="1" align="center">
		<%
            for (int i = 0; i < 9; i++) {   //课程表有9行
                out.print("<tr>");
                for (int j = 0; j < 8; j++) {   //课程表有8列
                    if (i == 0 && j == 0) {
                        out.print("<td>课节\\星期</td>");  //第1行第1列
                    }
                    if (i == 0 && j != 0) {
                        out.print("<td>" + head[j - 1] + "</td>");   //第1行非0列
                    }
                    if (i != 0 && j == 0) {
                        out.print("<td>" + left[i - 1] + "</td>");   //非0行第1列
                    }
                    if (i != 0 && j != 0) {
                        out.print("<td>" + main[i - 1][j - 1] + "</td>");  //非0行非0列
                    }
                }
                out.print("</tr>");
            }
        %>
	</table>
</body></span>

</body>
</html>