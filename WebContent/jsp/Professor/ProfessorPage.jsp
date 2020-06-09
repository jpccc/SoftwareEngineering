<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Professor" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>教师页面</title>
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
</head>
<body background="<c:url value="images/pro_back.jpg"/>">

<div class="head">
    <div class="block_left">
        <div>
            <hr id="line_l">
        </div>
    </div>
    <div class="block_right">
        <div class="head_title">
            <h2 id="title">教师信息表</h2>
        </div>
        <div class="inerblock_right">
            <div>
                <hr id="line_r">
            </div>
        </div>
    </div>
</div>

<div class="body">
    <div class="navigate">
        <input type="checkbox" id="sidemenu">
        <aside id="list">
            <h2>功能列表</h2>
            <br/>
            <ul id="sideul">
                <a href="/SoftwareEngineering/ProfessorServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Professor/SelectToTeach.jsp">
                    <li>选课</li>
                </a>
                <a href="/SoftwareEngineering/jsp/Professor/SubmitGrades.jsp">
                    <li>提交分数</li>
                </a>
                <a href="##">
                    <li>查询课表</li>
                </a>
                <a href="##">
                    <li>查看邮箱</li>
                </a>
                <a href="/SoftwareEngineering/GradesServlet?method=backToIndex">
                    <li>退出登录</li>
                </a>
            </ul>
        </aside>
    </div>

    <div class="table_one">
        <table class="table_info" border="1">
            <div class="time_display" style="color:#00ff6b;">
                <%
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String now = df.format(d);
                %>
                当前时间：<%=now %>
            </div class="course_list">

            <div class="table_final">
                <tr class="text-propetty">
                    <th height="57" class="text-center">名字</th>
                    <th class="text-center">生日</th>
                    <th class="text-center">身份证</th>
                    <th class="text-center">Status</th>
                </tr>
                <tr class="text-value">
                    <%Professor professor = (Professor) session.getAttribute("user");%>
                    <td height="62"><%=professor.getP_name()%></td>
                    <td><%=professor.getBirthday()%></td>
                    <td><%=professor.getIdentify_num()%></td>
                    <td><%=professor.getStatus()%></td>
                </tr>
            </div>
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
