<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ShowBill</title>
</head>
<body>
	<table align='center' border='1' cellspacing='0'>
    <tr>
        <th>course_id</th>
        <th>course_name</th>
        <th>cost</th>
    </tr>
    <c:if test="${not empty CourseList}">
    	<c:forEach items="${CourseList}" var="Course" varStatus="st">
        	<tr>
            	<td>${Course.course_id}</td>
            	<td>${Course.course_name}</td>
            	<td>${CostList[st.index]}</td>
        	</tr>
    	</c:forEach>
    </c:if>	
    <tr>
        <th>total_cost=${TotalCost}</th>
    </tr>
	</table>
</body>
</html>