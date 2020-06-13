<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Student" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>学生页面</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Student/css/user.css">
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
                <tr class="text-property">
                    <th height="57" class="text-center">名字</th>
                    <th class="text-center">生日</th>
                    <th class="text-center">身份证</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">系ID</th>
                </tr>
                <tr class="text-value">
                    <%Student student = (Student) session.getAttribute("user");%>
                    <td height="62"><%=student.getS_name()%></td>
                    <td><%=student.getBirthday()%></td>
                    <td><%=student.getIdentify_num()%></td>
                    <td><%=student.getStatus()%></td>
                    <td><%=student.getDept_id()%></td>
                </tr>
            </div>
        </table>
    </div>
</div>
<div class="bottom">
    <div class="bottom_center">
        ${StudentError}
        <br>
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
                <a href="/SoftwareEngineering/jsp/Student/StudentPage.jsp">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/ViewReportCardServlet">
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
                <a href="/SoftwareEngineering/StudentServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
            </ul>
        </aside>
    </div>
</div>
</body>
</html>
