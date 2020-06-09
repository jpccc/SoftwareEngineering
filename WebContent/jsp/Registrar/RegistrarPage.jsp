<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Registrar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>注册人员页面</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Registrar/css/user.css">
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
<body background="/SoftwareEngineering/images/reg_back.jpg">

<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">注册人员</h2>
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
                <div>
                    当前在线人数:<%=application.getAttribute("onLineCount")%>
                </div>
            </caption>

            <div class="table_final">
                <tr class="text-property">
                    <th height="57" class="text-center">ID</th>
                </tr>
                <tr class="text-value">
                    <%Registrar registrar = (Registrar) session.getAttribute("user");%>
                    <td height="62"><%=registrar.getR_id()%>
                    </td>
                </tr>
            </div>
        </table>
    </div>
</div>
<div class="bottom">
    <div class="bottom_center">
        ${RegistrarError}
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
                <a href="/SoftwareEngineering/RegistrarServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
                <a href="/SoftwareEngineering/OpenRegisServlet?method=startNewReg">
                    <li>开启注册</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp">
                    <li>维护学生信息</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp">
                    <li>维护教授信息</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Registrar/NewProfessor.jsp">
                    <li>新建教授档案</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Registrar/NewStudent.jsp">
                    <li>新建学生档案</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Registrar/CloseRegistration.jsp">
                    <li>关闭注册</li>
                </a>
                <a href="##">
                    <li>查看邮箱</li>
                </a>
            </ul>
        </aside>
    </div>
</div>

</body>
</html>
