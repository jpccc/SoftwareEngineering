<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 	<form action="/SoftwareEngineering/ViewReportCardServlet" type="get"> -->
<!-- 		<input type="text" value="0" name="student_id"> -->
<!-- 		<input type="submit" value="查看成绩单"> -->
<!-- 	</form> -->
	<table align='center' border='1' cellspacing='0'>
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
	</table>
</body>
</html>