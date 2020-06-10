package Controller;
import Beans.Professor;
import Beans.Student;
import Beans.Registrar;
import DAO.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarServlet extends BaseServlet {
    public boolean formatCheck(String string){
        if (string == null || string.equals("")) {
            return false;
        }
        else
            return true;
    }
    public static boolean hasSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean valideIdCard(String idCard) {

        String idCardPattern = "^\\d{17}(\\d|X)$";  // 前17位为数字，最后一位为数字或X
        String provinces = "11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82";

        // 验证长度
        if (idCard.length() != 18) {
            return false;
        }
        // 验证格式
        if (!Pattern.matches(idCardPattern, idCard)) {
            return false;
        }
        // 验证省级代码
        if (!provinces.contains(idCard.substring(0,2))) {
            return false;
        }
        // 验证年月日
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            java.util.Date birthday = df.parse(idCard.substring(6, 14));
            java.util.Date min = df.parse("19000101");
            java.util.Date max = df.parse(df.format(new java.util.Date()));
            if (birthday.before(min) || birthday.after(max)) {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        char residues[] = {'1', '0', 'X', '9', '8', '7','6', '5', '4', '3', '2'};
        long sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += Integer.valueOf(idCard.substring(i, i+1)) * (Math.pow(2,(18-1-i))%11);
        }
        return idCard.charAt(17) == residues[(int)(sum % 11)];
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public void addProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id="P"+generateID(req);
        String p_name = req.getParameter("p_name");
        if(p_name==null||hasSpecialChar(p_name)){
            req.setAttribute("error", "name含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String time = req.getParameter("birthday");
        if(time==null){
            req.setAttribute("error", "birthday不能为空！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        if(identify_num==null||!valideIdCard(identify_num)){
            req.setAttribute("error", "身份证格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String status = req.getParameter("status");
        if(status==null||hasSpecialChar(status)){
            req.setAttribute("error", "status含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String sdept_id = req.getParameter("dept_id");
        if(sdept_id==null||!isInteger(sdept_id)){
            req.setAttribute("error", "部门编号需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        int dept_id = Integer.parseInt(sdept_id);
        String password = req.getParameter("password");
        if(password==null||hasSpecialChar(password)){
            req.setAttribute("error", "密码含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            ProfessorDAO professorDAO = new ProfessorDAOImpl();
            Professor professor = professorDAO.findById(p_id);
            if (professor.getP_id().equals("null")) {
                professor = new Professor(p_id, p_name, birthday, identify_num, status, dept_id, password);
                professorDAO.insert(professor);
                List<Professor> list = new ArrayList<Professor>();
                list.add(professor);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "id已存在！");
                req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
                return;
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String s_id="S"+generateID(req);
        String s_name = req.getParameter("s_name");
        if(s_name==null||hasSpecialChar(s_name)){
            req.setAttribute("error", "name含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/NewStudent.jsp").forward(req, resp);
            return;
        }
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        if(identify_num==null||!valideIdCard(identify_num)){
            req.setAttribute("error", "身份证格式有误");
            req.getRequestDispatcher("/jsp/Registrar/NewStudent.jsp").forward(req, resp);
            return;
        }
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String graduate_time = req.getParameter("birthday");
        java.util.Date graduate_Date = new SimpleDateFormat("yyyy-MM-dd").parse(graduate_time);
        Date graduate = new Date(graduate_Date.getTime());
        String password = req.getParameter("password");
        if(password==null||hasSpecialChar(password)){
            req.setAttribute("error", "密码含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/NewStudent.jsp").forward(req, resp);
            return;
        }
        //格式在jsp页面进行判断
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.findById(s_id);
            if (student.getS_id().equals("null")) {
                student= new Student(s_id, s_name, birthday, identify_num, status, dept_id,graduate, password);
                studentDAO.insert(student);
                List<Student> list = new ArrayList<Student>();
                list.add(student);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "id has existed!");
                req.getRequestDispatcher("/jsp/Registrar/NewStudent.jsp").forward(req, resp);
                return;
            }
        }else{
            req.setAttribute("error", "Incorrect login information!");
            backToIndex(req, resp);
        }
    }
    public boolean notBlank(String string){
        if(string != null && !string.equals(""))
            return true;
        else return false;
    }
    public void searchProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idString=req.getParameter("by_id");
        String name=req.getParameter("by_name");
        List<Professor> list=new ArrayList<Professor>();
        ProfessorDAO professorDAO = new ProfessorDAOImpl();
        if(!notBlank(idString)&&!notBlank(name)){
            list=professorDAO.showAll();
        }
        if(notBlank(idString)&&!notBlank(name)){
            //only id
            Professor professor = professorDAO.findById(idString);
            if (professor.getP_id().equals(idString)){//found!
                list.add(professor);
            }//not found add nothing!
        }
        if(!notBlank(idString)&&notBlank(name)){
            //only name
            List<Professor> resultList = professorDAO.findByName(name);
            for(Professor professor:resultList){
                if(professor!=null)
                    list.add(professor);
                //found and add
            }
            //not found add nothing!
        }
        HttpSession session = req.getSession();
        session.setAttribute("list",list);
        resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
    }
    public void searchStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idString=req.getParameter("by_id");
        String name=req.getParameter("by_name");
        List<Student> list_stu=new ArrayList<Student>();
        StudentDAO studentDAO = new StudentDAOImpl();
        if(!notBlank(idString)&&!notBlank(name)){
            list_stu=studentDAO.showAll();
        }
        if(notBlank(idString)&&!notBlank(name)){
            //only id
            Student student = studentDAO.findById(idString);
            if (student.getS_id().equals(idString)){//found!
                list_stu.add(student);
            }//not found add nothing!
        }
        if(!notBlank(idString)&&notBlank(name)){
            //only name
            List<Student> resultList = studentDAO.findByName(name);
            for(Student student:resultList){
                if(student!=null)
                    list_stu.add(student);
                //found and add
            }
            //not found add nothing!
        }
        HttpSession session = req.getSession();
        session.setAttribute("list_stu",list_stu);
        resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
    }
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //此为查询界面跳转到修改页面的函数
        ProfessorDAO professorDAO = new ProfessorDAOImpl();
        try {
            String id = req.getParameter("id");
            if(formatCheck(id)) {
                Professor professor = professorDAO.findById(id);
                HttpSession session=req.getSession();
                session.setAttribute("professor", professor);
                req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", "id错误无法修改");
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyStu(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //此为查询界面跳转到修改页面的函数
        StudentDAO studentDAO = new StudentDAOImpl();
        try {
            String id = req.getParameter("id");
            if(formatCheck(id)) {
                Student student = studentDAO.findById(id);
                HttpSession session=req.getSession();
                session.setAttribute("student",student);
                req.getRequestDispatcher("/jsp/Registrar/MaintainStudent.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", "id错误无法修改");
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Professor professor1 = (Professor) req.getSession().getAttribute("professor");
        if(professor1==null){
            req.setAttribute("error", "信息有误！");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        String p_id=professor1.getP_id();
        String p_name = req.getParameter("p_name");
        if(p_name==null||hasSpecialChar(p_name)){
            req.setAttribute("error", "name含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        String time = req.getParameter("birthday");
        if(time==null){
            req.setAttribute("error", "birthday不能为空！");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        if(identify_num==null||!valideIdCard(identify_num)){
            req.setAttribute("error", "身份证格式有误");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        String status = req.getParameter("status");
        if(status==null||hasSpecialChar(status)){
            req.setAttribute("error", "status包含非法字符");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        String sdept_id = req.getParameter("dept_id");
        if(sdept_id==null||!isInteger(sdept_id)){
            req.setAttribute("error", "部门编号需为数字");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        int dept_id = Integer.parseInt(sdept_id);
        String password = req.getParameter("password");
        if(password==null||hasSpecialChar(password)){
            req.setAttribute("error", "密码含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        RegistrarDAO registererDAO = new RegistrarDAOImpl();
        if(registerer!=null){
            Registrar server_register = registererDAO.findById(registerer.getR_id());
            if(registerer.getPassword().equals(server_register.getPassword())) {
                ProfessorDAO professorDAO = new ProfessorDAOImpl();
                Professor professor = new Professor(p_id, p_name, birthday, identify_num, status, dept_id, password);
                professorDAO.update(professor);
                List<Professor> list = new ArrayList<Professor>();
                list.add(professor);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            }else{
                req.setAttribute("error", "登录信息有误！");
                backToIndex(req, resp);
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void modifyStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Student student1 = (Student) req.getSession().getAttribute("student");
        String s_id = req.getParameter(student1.getS_id());
        String s_name = req.getParameter("s_name");
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String time1 = req.getParameter("graduate_date");
        java.util.Date utilDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(time1);
        Date graduate = new Date(utilDate1.getTime());
        String password = req.getParameter("password");

        System.out.println(s_id+1239+s_name);

        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        RegistrarDAOImpl registererDAO = new RegistrarDAOImpl();
        if(registerer!=null){
            Registrar server_register = registererDAO.findById(registerer.getR_id());
            if(registerer.getPassword().equals(server_register.getPassword())) {
                StudentDAO studentDAO = new StudentDAOImpl();
                Student student = new Student(s_id, s_name, birthday, identify_num, status, dept_id,graduate,password);
                studentDAO.update(student);
                List<Student> list = new ArrayList<Student>();
                list.add(student);
                req.setAttribute("list_stu", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
            }else{
                req.setAttribute("error", "登录信息有误！");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void deleteProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("id");
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            ProfessorDAO professorDAO = new ProfessorDAOImpl();
            Professor professor = professorDAO.findById(p_id);
            if (!professor.getP_id().equals("null")) {
                professorDAO.delete(p_id);
                resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
            } else {
                req.setAttribute("error", "id不存在！");
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }
    }
    public void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String s_id = req.getParameter("id");
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.findById(s_id);
            if (!student.getS_id().equals("null")) {
                studentDAO.delete(s_id);
                resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
            } else {
                req.setAttribute("error", "id不存在！");
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }
    }
    public void backToIndex(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("registration");
        session.removeAttribute("selectedC");
        session.removeAttribute("allC");
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
    public String generateID(HttpServletRequest req){
        HttpSession sessions=req.getSession();
        ServletContext applications=sessions.getServletContext();
        Integer Stu_ID;
        if(applications.getAttribute("Stu_ID")!=null)
        {
            Stu_ID= (Integer) applications.getAttribute("Stu_ID");
            Stu_ID=Stu_ID+1;
            applications.setAttribute("Stu_ID",Stu_ID);
        }
        else{
            Integer ID=new Integer(100);
            Stu_ID=ID;
            applications.setAttribute("Stu_ID",ID);
        }
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        String _id=year+Stu_ID;
        return _id;
    }
}
