<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"  import="DAO.SelectCourseDAO" import="DAO.SelectCourseDAOImpl" import="java.util.List"
		 import="java.util.ArrayList" import="Beans.Course"%>
<html>
<head>
	<title>学生选课页面</title>
	<link rel="stylesheet" href="css/select.css">
	<script>
		function show(){
			 var x = document.getElementById("sidemenu");

			if(x.checked==true){
				document.getElementById("list").style.left="-180px";
				x.checked=false;
			}
			else{
				document.getElementById("list").style.left=0;
				x.checked=true;
			}
		}
		function submit() {
			var x = document.getElementById("course_form");
			x.submit();
		}
		function start() {
			alert("welcome");
			document.getElementById("list").style.left="-180px";
			var x = document.getElementById("sidemenu");
			x.checked=false;
		}
	</script>
</head>
<body background="images/stu_back.jpg" onpageshow="start()">

<div class="head">
	<div class="block_left">
		<div>
			<hr id="line_l">
		</div>
	</div>
	<div class="block_right">
		<div class="head_title">
			<h2 id="title">学生选课页面</h2>
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
		<input type="checkbox"  id="sidemenu">
		<aside id="list">
			<h2>课程列表</h2>
			<br/>
			<ul id="sideul">
				<a href="/SoftwareEngineering/jsp/Student/StudentPage.jsp">
					<li>学生首页</li>
				</a>
				<a href="/SoftwareEngineering/jsp/Student/show_schedule.jsp">
					<li>查询课表</li>
				</a>
				<a href="##">
					<li>others</li>
				</a>
				<a href="##">
					<li>others</li>
				</a>
			</ul>
		</aside>
	</div>

	<div class="table_one">
		<form id="course_form" action="http://localhost:8080/SoftwareEngineering/SelectCourseServlet?op=add" method="post">
			<table class="table_info" border="1">
				<caption>
					<script>
						document.write(Date());
					</script>
				</caption>

				<div class="table_final">
					<tr class="text-propetty">
						<th height="57" class="text-center">课程名</th>
						<th class="text-center">课程起始时间</th>
						<th class="text-center">课程结束时间</th>
						<th class="text-center">WeekDay</th>
						<th class="text-center">教师姓名</th>
						<th class="text-center">已选人数</th>
						<th class="text-center">选课种类</th>
					</tr>
					<%
						SelectCourseDAO select_course_dao=new DAO.SelectCourseDAOImpl();
						List<Course> list=new ArrayList<Course>();
						list=select_course_dao.get_all_courses();
						int name=0;
						for (int i = 0; i < list.size(); i++) {
					%>
					<tr class="text-value">
						<td height="62"><%=list.get(i).getCourse_name()%></td>
						<td><%=list.get(i).getStart_date()%></td>
						<td><%=list.get(i).getEnd_date()%></td>
						<td><%=list.get(i).getWeekday()%></td>
						<td><%=list.get(i).getWeekday()%></td>
						<td><%=list.get(i).getStudent_count()%></td>

						<td>
							<input  type="radio"  value="<%=list.get(i).getCourse_id() %>  <%=list.get(i).getReg_id() %> first" name=<%=name%>
							/>首选
							<input  type="radio" value="<%=list.get(i).getCourse_id() %>  <%=list.get(i).getReg_id() %> second" name=<%=name%>
							/>
							备选
						</td>
					</tr>
					<%
							name++;
						}
					%>
				</div>
			</table>
		</form>
	</div>
</div>
<div class="bottom">
	<div class="bottom_left">
		<button class="button_right" onclick="submit()">提交</button>
		<button class="button_left" onclick="show()">功能</button>
	</div>
	<div class="bottom_right">
		${message}
	</div>
</div>
</body>
</html>