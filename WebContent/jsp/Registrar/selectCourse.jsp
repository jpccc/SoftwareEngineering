<%@ page import="Beans.Course" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑课程信息</title>
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Registrar/css/user.css">
    <link rel="stylesheet" href="/SoftwareEngineering/jsp/Registrar/css/scroll.css">
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
        <h2 id="title">编辑课程信息</h2>
    </div>
    <div class="head_right">

        <hr id="line_r">
    </div>
</div>

<%
    List<Course> total = (List<Course>) session.getAttribute("allC");
    int index = Integer.parseInt(request.getParameter("index"));
    Course curr = total.get(index);
%>

<div class="body">
    <div class="table_one">
        <form action="/SoftwareEngineering/OpenRegisServlet?method=addOne" method="post">
            <input type="hidden" value="<%=index%>" name="index"/>
            <table class="table_info" border="1">
                <tbody>
                <tr>
                    <th>课程ID</th>
                    <th>课程名称</th>
                    <th>系号</th>
                    <th>开课时间</th>
                    <th>结课时间</th>
                    <th>课程价格</th>
                </tr>
                <tr>
                    <td><%=curr.getCourse_id()%>
                    </td>
                    <td><%=curr.getCourse_name()%>
                    </td>
                    <td><%=curr.getDept_id()%>
                    </td>
                    <td><input type="date" name="beginTime"/></td>
                    <td><input type="date" name="endTime"/></td>
                    <td><input type="number" step="0.01" value="<%=curr.getPrice()%>" name="price"></td>
                </tr>
                </tbody>
            </table>

            <table class="table_info" border="1">
                <tbody>
                <tr>
                    <th>日期</th>
                    <th>第1节</th>
                    <th>第2节</th>
                    <th>第3节</th>
                    <th>第4节</th>
                    <th>第5节</th>
                    <th>第6节</th>
                    <th>第7节</th>
                    <th>第8节</th>
                </tr>
                <tr>
                    <td>星期一</td>
                    <td><input type="checkbox" name="timeMask" value="0"/></td>
                    <td><input type="checkbox" name="timeMask" value="1"/></td>
                    <td><input type="checkbox" name="timeMask" value="2"/></td>
                    <td><input type="checkbox" name="timeMask" value="3"/></td>
                    <td><input type="checkbox" name="timeMask" value="4"/></td>
                    <td><input type="checkbox" name="timeMask" value="5"/></td>
                    <td><input type="checkbox" name="timeMask" value="6"/></td>
                    <td><input type="checkbox" name="timeMask" value="7"/></td>
                </tr>
                <tr>
                    <td>星期二</td>
                    <td><input type="checkbox" name="timeMask" value="8"/></td>
                    <td><input type="checkbox" name="timeMask" value="9"/></td>
                    <td><input type="checkbox" name="timeMask" value="10"/></td>
                    <td><input type="checkbox" name="timeMask" value="11"/></td>
                    <td><input type="checkbox" name="timeMask" value="12"/></td>
                    <td><input type="checkbox" name="timeMask" value="13"/></td>
                    <td><input type="checkbox" name="timeMask" value="14"/></td>
                    <td><input type="checkbox" name="timeMask" value="15"/></td>
                </tr>
                <tr>
                    <td>星期三</td>
                    <td><input type="checkbox" name="timeMask" value="16"/></td>
                    <td><input type="checkbox" name="timeMask" value="17"/></td>
                    <td><input type="checkbox" name="timeMask" value="18"/></td>
                    <td><input type="checkbox" name="timeMask" value="19"/></td>
                    <td><input type="checkbox" name="timeMask" value="20"/></td>
                    <td><input type="checkbox" name="timeMask" value="21"/></td>
                    <td><input type="checkbox" name="timeMask" value="22"/></td>
                    <td><input type="checkbox" name="timeMask" value="23"/></td>
                </tr>
                <tr>
                    <td>星期四</td>
                    <td><input type="checkbox" name="timeMask" value="24"/></td>
                    <td><input type="checkbox" name="timeMask" value="25"/></td>
                    <td><input type="checkbox" name="timeMask" value="26"/></td>
                    <td><input type="checkbox" name="timeMask" value="27"/></td>
                    <td><input type="checkbox" name="timeMask" value="28"/></td>
                    <td><input type="checkbox" name="timeMask" value="29"/></td>
                    <td><input type="checkbox" name="timeMask" value="30"/></td>
                    <td><input type="checkbox" name="timeMask" value="31"/></td>
                </tr>
                <tr>
                    <td>星期五</td>
                    <td><input type="checkbox" name="timeMask" value="32"/></td>
                    <td><input type="checkbox" name="timeMask" value="33"/></td>
                    <td><input type="checkbox" name="timeMask" value="34"/></td>
                    <td><input type="checkbox" name="timeMask" value="35"/></td>
                    <td><input type="checkbox" name="timeMask" value="36"/></td>
                    <td><input type="checkbox" name="timeMask" value="37"/></td>
                    <td><input type="checkbox" name="timeMask" value="38"/></td>
                    <td><input type="checkbox" name="timeMask" value="39"/></td>
                </tr>
                <tr>
                    <td>星期六</td>
                    <td><input type="checkbox" name="timeMask" value="40"/></td>
                    <td><input type="checkbox" name="timeMask" value="41"/></td>
                    <td><input type="checkbox" name="timeMask" value="42"/></td>
                    <td><input type="checkbox" name="timeMask" value="43"/></td>
                    <td><input type="checkbox" name="timeMask" value="44"/></td>
                    <td><input type="checkbox" name="timeMask" value="45"/></td>
                    <td><input type="checkbox" name="timeMask" value="46"/></td>
                    <td><input type="checkbox" name="timeMask" value="47"/></td>
                </tr>
                <tr>
                    <td>星期日</td>
                    <td><input type="checkbox" name="timeMask" value="48"/></td>
                    <td><input type="checkbox" name="timeMask" value="49"/></td>
                    <td><input type="checkbox" name="timeMask" value="50"/></td>
                    <td><input type="checkbox" name="timeMask" value="51"/></td>
                    <td><input type="checkbox" name="timeMask" value="52"/></td>
                    <td><input type="checkbox" name="timeMask" value="53"/></td>
                    <td><input type="checkbox" name="timeMask" value="54"/></td>
                    <td><input type="checkbox" name="timeMask" value="55"/></td>
                </tr>
                </tbody>
            </table>
            <input type="submit" value="确认开课"/>
        </form>
    </div>
</div>


<div class="fix_place">
    <button class="function" onclick="show()">功能</button>
    <input type="checkbox" id="sideMenu">
    <div class="navigate">
        <aside id="list">
            <h2>功能列表</h2>
            <ul id="sideul">
                <a href="/SoftwareEngineering/jsp/Registrar/RegistrarPage.jsp">
                    <li>首页</li>
                </a>
                <a href="/SoftwareEngineering/CloseRegisServlet">
                    <li>关闭注册</li>
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
