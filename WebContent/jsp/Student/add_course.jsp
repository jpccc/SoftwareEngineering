<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
         import="java.util.ArrayList" import="Beans.Course" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>学生选课页面</title>
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
            var x = document.getElementById("course_form");
            x.submit();
        }
    </script>
</head>
<body background="images/stu_back.jpg">
<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">学生选课页面</h2>
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
                    <tr class="text_property">
                        <th height="57" class="text-center">课程名</th>
                        <th class="text-center">课程起始时间</th>
                        <th class="text-center">课程结束时间</th>
                        <th class="text-center">WeekDay</th>
                        <th class="text-center">教师姓名</th>
                        <th class="text-center">已选人数</th>
                        <th class="text-center">选课种类</th>
                    </tr>
                    <%
                        SelectCourseDAO select_course_dao = new DAO.SelectCourseDAOImpl();
                        List<Course> list = new ArrayList<Course>();
                        list = select_course_dao.get_all_courses();
                        int name = 0;
                        for (int i = 0; i < list.size(); i++) {
                    %>
                    <tr class="text-value">
                        <td height="62"><%=list.get(i).getCourse_name()%>
                        </td>
                        <td><%=list.get(i).getStart_date()%>
                        </td>
                        <td><%=list.get(i).getEnd_date()%>
                        </td>
                        <td><%=list.get(i).getWeekday()%>
                        </td>
                        <td><%=list.get(i).getWeekday()%>
                        </td>
                        <td><%=list.get(i).getStudent_count()%>
                        </td>

                        <td>
                            <input type="radio"
                                   value="<%=list.get(i).getCourse_id() %>  <%=list.get(i).getReg_id() %> first"
                                   name=<%=name%>
                            />首选
                            <input type="radio"
                                   value="<%=list.get(i).getCourse_id() %>  <%=list.get(i).getReg_id() %> second"
                                   name=<%=name%>
                            />
                            备选
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
                <a href="/SoftwareEngineering/jsp/Student/StudentPage.jsp">
                    <li>学生首页</li>
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