<%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering_war/jsp/Registrar/css/form.css" />
    <title>新建教授档案</title>
</head>
<body>
<h1>新建教授档案</h1>
<form class="modifyinfo" action="/SoftwareEngineering_war/RegistrarServlet?method=addProfessor" method="post">
    <div class="inputbox" >
        id<br><input type="text" class="password" name="p_id"><br>
        姓名<br><input type="text" class="password" name="p_name" ><br>
        生日<br><input type="Date" class="password" name="birthday"><br>
        identify number<br><input type="text" class="password" name="identify_num" ><br>
        状态<br><input type="text" class="password" name="status" ><br>
        部门id<br><input type="text" class="password" name="dept_id" ><br>
        密码 <input type="text" class="password" name="password" ><br>
    </div>
    <br>
    <input type="submit" value="添加" class="button">
    <br><br>
    <div class="error">
        ${error}
    </div>
</form>
</body>
</html>