<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css" />
    <link rel="stylesheet" href="css/user.css">
    <script>
        function show(){
            var x=document.getElementById("sidemenu");

            if(x.checked==true){
                document.getElementById("list").style.left="-180px";
                x.checked=false;
            }
            else{
                document.getElementById("list").style.left=0;
                x.checked=true;
            }
        }
    </script>
    <title>教授信息</title>
</head>
<body background="images/reg_back.jpg">
<div class="body">
<div class="navigate">
    <input type="checkbox" id="sidemenu">
    <aside id="list">
        <h2>功能列表</h2>
        <br/>
        <ul id="sideul">
            <a href="/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp">
                <li>首页</li>
            </a>
            <a href="##">
                <li>others</li>
            </a>
            <a href="/SoftwareEngineering/GradesServlet?method=backToIndex">
                <li>退出登录</li>
            </a>
        </ul>
    </aside>
</div>
    <div class="table_one">
<div class="time_display" style="color:#00ff6b;">
    <%
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String now = df.format(d);
    %>
    当前时间：<%=now %>
</div class="course_list">
<table align="center">
    <form style="color: #00ff6b" action="/SoftwareEngineering/RegistrarServlet?method=searchProfessor" method="post">
        <tr>
            <th style="color: red">按编号：</th>
            <th><input type="text" name="by_id" ></th>
            <th style="color: red">按姓名:</th>
            <th><input type="text" name="by_name"></th>
            <th><input type="submit" value="查询" class="button"></th>
            <th class="error">${error}</th>
        </tr>
    </form>
</table>
<hr>
<h1 style="color: #00ff6b">显示教授信息</h1>
<table style="color: aqua" border="1" cellpadding=“0” cellspacing="0" align="center" width="80%">
    <tr>
        <th>id</th>
        <th>姓名</th>
        <th>出生日期</th>
        <th>identify number</th>
        <th>状态</th>
        <th>院系id</th>
        <th>密码</th>
        <th>...</th>
    </tr>
    <c:if test="${not empty list}">
        <c:forEach items="${list}" var="professor">
            <tr>
                <th>${professor.p_id}</th>
                <th>${professor.p_name}</th>
                <th>${professor.birthday}</th>
                <th>${professor.identify_num}</th>
                <th>${professor.status}</th>
                <th>${professor.dept_id}</th>
                <th>${professor.password}</th>
                <th><a href="/SoftwareEngineering/RegistrarServlet?method=modify&id=${professor.p_id}" style="color: aqua">编辑信息</a></th>
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
    <div class="bottom_left">
        <button style="left: -18px; top: 377px;" onclick="show()">功能</button>
    </div>
</div>

</body>
</html>
