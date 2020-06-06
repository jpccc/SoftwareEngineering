<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Beans.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>ShowBilling</title>
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
<body background="<c:url value="images/stu_back.jpg"/>">

<div class="head">
    <div class="block_left">
        <div>
            <hr id="line_l">
        </div>
    </div>
    <div class="block_right">
        <div class="head_title">
            <h2 id="title">学生账单表</h2>
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
                <a href="##">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/ViewReportCardServlet">
                    <li>查询成绩</li>
                </a>
                <a href="jsp/Student/add_course.jsp">
                    <li>选择课程</li>
                </a>
                <a href="jsp/Student/show_schedule.jsp">
                    <li>查询课表</li>
                </a>
                <a href="/SoftwareEngineering/BillServlet">
                    <li>查看邮箱</li>
                </a>
            </ul>
        </aside>
    </div>

    <div class="table_one">
        <table class="table_info" border="1">
            <caption>
                <script>
                    document.write(Date());
                </script>
            </caption>

            <div class="table_final">
                <tr>
                <th>course_id</th>
                <th>course_name</th>
                <th>grade</th>
                </tr>

                <c:if test="${not empty CourseList}">
                    <c:forEach items="${CourseList}" var="Course" varStatus="st">
                        <tr>
                            <td>${Course.course_id}</td>
                            <td>${Course.course_name}</td>
                            <td>${GradeList[st.index]}</td>
                        </tr>
                    </c:forEach>
                </c:if>
            </div>
        </table>
    </div>
</div>

<div class="bottom">
    <div class="bottom_left">
        <button onclick="show()">功能</button>
    </div>
</div>

</body>
</html>
