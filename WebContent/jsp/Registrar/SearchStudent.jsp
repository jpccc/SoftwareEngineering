<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
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
    <title>学生信息</title>
</head>
<body>
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
                <div class="title">
                    显示学生信息
                </div>
            </div>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>出生日期</th>
                <th>identify number</th>
                <th>状态</th>
                <th>院系id</th>
                <th>毕业日期</th>
                <th>密码</th>
                <th>...</th>
            </tr>
            <c:if test="${not empty list_stu}">
                <c:forEach items="${list_stu}" var="student">
                    <tr>
                        <th>${student.s_id}</th>
                        <th>${student.s_name}</th>
                        <th>${student.birthday}</th>
                        <th>${student.identify_num}</th>
                        <th>${student.status}</th>
                        <th>${student.dept_id}</th>
                        <th>${student.graduate_date}</th>
                        <th>${student.password}</th>
                        <th><a href="/SoftwareEngineering/RegistrarServlet?method=modifyStu&id=${student.s_id}"
                               style="color: aqua">编辑信息</a></th>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty list}">
                <tr style="color:#00ff6b ">
                    <td colspan="9" style="text-align: center">没有信息可以显示！</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>

<div class="bottom">
    <div class="bottom_center">
        <table align="center">
            <form style="color: #00ff6b" action="/SoftwareEngineering/RegistrarServlet?method=searchStudent"
                  method="post">
                <tr>
                    <th style="color: red">按编号：</th>
                    <th><input type="text" name="by_id"></th>
                    <th style="color: red">按姓名:</th>
                    <th><input type="text" name="by_name"></th>
                    <th><input type="submit" value="查询" class="button"></th>
                    <th class="error">${error}</th>
                </tr>
            </form>
        </table>
    </div>
</div>
<div class="fix_place">
    <button class="function" onclick="show()">功能</button>
    <input type="checkbox" id="sideMenu">
    <div class="navigate">
        <aside id="list">
            <h2>功能列表</h2>
            <ul id="sideul">
                <a href="/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/RegistrarServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
            </ul>
        </aside>
    </div>
</div>
</body>
</html>
