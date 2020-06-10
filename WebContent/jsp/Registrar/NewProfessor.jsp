<%@ page import="DAO.DepartmentDao" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: mlixi
  Date: 2020/6/1
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8"); %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/SoftwareEngineering/jsp/Registrar/css/form.css"/>
    <script type="text/javascript" src="/SoftwareEngineering/jsp/Registrar/js/addUser.js"></script>
    <title>新建教授档案</title>
</head>
<%
    DepartmentDao dept = new DepartmentDao();
    Map<String, String> dept_list = dept.getAll();
    if (dept_list.size() == 0) {
        request.setAttribute("error", "请先录入系信息!");
        request.getRequestDispatcher("/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
    } else {
%>
<body background="/SoftwareEngineering/jsp/Registrar/images/reg_back.jpg">
<h1>新建教授档案</h1>
<form class="modifyInfo" action="/SoftwareEngineering/RegistrarServlet?method=addProfessor" method="post"
      onsubmit="return subPro();">
    <div class="inputBox">
        姓名<br><input type="text" class="password" name="p_name"><br>
        生日<br><input type="Date" class="password" name="birthday"><br>
        identify number<br><input type="text" class="password" name="identify_num"><br>
        状态<br><input type="text" class="password" name="status"><br>
        部门id
        <select class="password" name="dept_id">
            <%
                Set<String> id_set = dept_list.keySet();
                for (String id : id_set) {
            %>
            <option value=<%=id%>>计算机</option>
            <%
                    }
                }
            %>
        </select>
        <br>
        密码 <input type="text" class="password" name="password"><br>
    </div>
    <br>
    <input type="submit" value="添加" class="button">
    <br><br>
    <div class="error">
        ${error}
    </div>
</form>
<a href="/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp" style="text-align: center;color: white">返回上层</a>
</body>
</html>
