<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Student" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>ShowBilling</title>
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
<body>

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
        <table class="table_info" border="1">

            <caption>
                <div class="time_display" style="color:#00ff6b;">
                    <%
                        Date d = new Date();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String now = df.format(d);
                    %>
                    当前时间：<%=now %>
                </div class>
            </caption>

            <div class="table_final">
                <tr class="text_property">
                    <th>course_id</th>
                    <th>course_name</th>
                    <th>price</th>
                </tr>

                <c:if test="${not empty CourseList}">
                    <c:forEach items="${CourseList}" var="Course" varStatus="st">
                        <tr class="text-value">
                            <td>${Course.course_id}</td>
                            <td>${Course.course_name}</td>
                            <td>${CostList[st.index]}</td>
                        </tr>
                        <tr>
                            <th>total_cost</th>
                            <td colspan="2">${TotalCost}</td>
                        </tr>
                    </c:forEach>
                </c:if>

            </div>
        </table>
    </div>
</div>

<div class="bottom">
    <div class="bottom_center">
        ${message}
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
                    <li>首页</li>
                </a>
            </ul>
        </aside>
    </div>
</div>

</body>
</html>
