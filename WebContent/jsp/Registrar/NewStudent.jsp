<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
    <script type="text/javascript" src="/SoftwareEngineering/jsp/Registrar/js/addUser.js"></script>
    <title>新建学生档案</title>
</head>
<body background="/SoftwareEngineering/jsp/Registrar/images/reg_back.jpg">
<h1>新建学生档案</h1>
<form class="modifyInfo" action="/SoftwareEngineering/RegistrarServlet?method=addStudent" method="post"
      onsubmit="return subInfo();">
    <div class="inputBox">
        id<br><input type="text" class="password" name="s_id"><br>
        姓名<br><input type="text" class="password" name="s_name"><br>
        生日<br><input type="Date" class="password" name="birthday"><br>
        identify number<br><input type="text" class="password" name="identify_num"><br>
        状态<br><input type="text" class="password" name="status"><br>
        部门id<br><input type="text" class="password" name="dept_id"><br>
        毕业时间<br><input type="Date" class="password" name="graduate_date"><br>
        密码 <input type="text" class="password" name="password"><br>
    </div>
    <br>
    <input type="submit" value="添加" class="button">
    <br><br>
    <div class="error">
        ${error}
    </div>
</form>
<a href="jsp/Registrar/RegistrarPage.jsp" style="text-align: center;color: white">返回上层</a>
</body>
</html>
