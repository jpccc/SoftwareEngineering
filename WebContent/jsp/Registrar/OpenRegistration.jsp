<%@ page import="Beans.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="DAO.CourseDAOImpl" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 李睿宸
  Date: 2020/6/8
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>开启注册</title>
</head>
<body>

<h2>已选课程列表</h2>
<c:if test="${not empty selectedC}">
<table>
    <tr>
        <th>课程ID</th>
        <th>课程名称</th>
        <th>系号</th>
        <th>课程价格</th>
    </tr>
    <tbody>
        <c:forEach begin="0" varStatus="status" var="course" items="${selectedC}">
            <tr>
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
    </tbody>
</table>
</c:if>
<c:if test="${empty selectedC}">
    <h4>您尚未选择任何课程</h4>
</c:if>
<hr/>
<h3>候选课程列表</h3>
<table>
    <tr>
        <th>课程ID</th>
        <th>课程名称</th>
        <th>系号</th>
        <th>参考价格</th>
    </tr>
    <tbody>
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
    </tbody>
</table>
<a href="/SoftwareEngineering/OpenRegisServlet?method=submitReg">提交</a>
<a href="/SoftwareEngineering/OpenRegisServlet?method=cancelReg">取消操作</a>
</body>
</html>
