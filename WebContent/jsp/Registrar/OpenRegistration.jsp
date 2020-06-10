<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>开启注册</title>
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
<body>
<div class="head">
    <div class="head_left">
        <hr id="line_l">
    </div>
    <div class="head_center">
        <h2 id="title">开启注册</h2>
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
                <div class="title">
                    已选课程列表
                </div>
            </caption>
            <div class="table_final">
                <c:if test="${not empty selectedC}">
                    <tr class="text-property">
                        <th>课程ID</th>
                        <th>课程名称</th>
                        <th>系号</th>
                        <th>课程价格</th>
                    </tr>

                    <c:forEach begin="0" varStatus="status" var="course" items="${selectedC}">
                        <tr class="text-value">
                            <td>${course.course_id}</td>
                            <td>${course.course_name}</td>
                            <td>${course.dept_id}</td>
                            <td>${course.price}</td>
                            <td>
                                <form action="OpenRegisServlet?method=removeOne" method="post">
                                    <input type="hidden" value="${status.index}" name="index"/>
                                    <input type="submit" value="移除"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </div>
            <c:if test="${empty selectedC}">
                <h4>您尚未选择任何课程</h4>
            </c:if>
        </table>
        <table class="table_info" border="1">
            <caption>
                <div class="title">
                    候选课程列表
                </div>
            </caption>

            <div class="table_final">
                <tr class="text-property">
                    <th>课程ID</th>
                    <th>课程名称</th>
                    <th>系号</th>
                    <th>参考价格</th>
                </tr>

                <tr class="text-value">
                    <c:forEach begin="0" varStatus="status" var="course" items="${allC}">
                        <td>${course.course_id}</td>
                        <td>${course.course_name}</td>
                        <td>${course.dept_id}</td>
                        <td>${course.price}</td>
                        <td>
                            <form action="/SoftwareEngineering/jsp/Registrar/selectCourse.jsp" method="post">
                                <input type="hidden" value="${status.index}" name="index"/>
                                <input type="submit" value="添加课程"/>
                            </form>
                        </td>
                    </c:forEach>
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
    <a class="submit" href="/SoftwareEngineering/OpenRegisServlet?method=submitReg">提交</a>
    <a class="cancel" href="/SoftwareEngineering/OpenRegisServlet?method=cancelReg">取消操作</a>
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
