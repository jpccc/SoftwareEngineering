<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Professor" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>教师页面</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Professor/css/user.css">
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
<body>

<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">教师信息表</h2>
    </div>
    <div class="head_right">

        <hr id="line_r">
    </div>
</div>

<div class="body">

    <div class="table_one">
        <table class="table_info" border="1">
            <div class="time_display" style="color:#00ff6b;">
                <%
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String now = df.format(d);
                %>
                当前时间：<%=now %>
            </div>

            <div class="table_final">
                <tr class="text-property">
                    <th height="57" class="text-center">名字</th>
                    <th class="text-center">生日</th>
                    <th class="text-center">身份证</th>
                    <th class="text-center">Status</th>
                </tr>
                <tr class="text-value">
                    <%Professor professor = (Professor) session.getAttribute("user");%>
                    <td height="62"><%=professor.getP_name()%>
                    </td>
                    <td><%=professor.getBirthday()%>
                    </td>
                    <td><%=professor.getIdentify_num()%>
                    </td>
                    <td><%=professor.getStatus()%>
                    </td>
                </tr>
            </div>
        </table>
    </div>
</div>

<div class="bottom">
    <div class="bottom_center">
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
                <a href="/SoftwareEngineering/jsp/Professor/ProfessorPage.jsp">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Professor/SelectToTeach.jsp">
                    <li>选择课程</li>
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
