package Beans;

import java.io.Serializable;
import java.sql.Date;

public class Course implements Serializable {
    public Course(){
        reg_id=-1;
        course_id=null;
        dept_id=-1;
        course_name=null;
        start_date=null;
        end_date=null;
        weekday=-1;
        timeslot_id=-1;
        professor_id=null;
        student_count=0;
        status=null;
    }

    /**
     * registration对象中的id，标记一次开课信息，查询选课信息时需要
     */
    private int reg_id;
    private String course_id;
    /**
     * 或许可以将dept_id相同作为判断该老师可以教这门课的依据
     */
    private int dept_id;
    private String course_name;
    /**
     * 课程开始日期，可用于判断冲突，如果默认所有课程全程开课可以不考虑
     */
    private Date start_date;
    /**
     * 课程结束日期，可用于判断冲突，如果默认所有课程全程开课可以不考虑
     */
    private Date end_date;
    /**
     * 工作日字段，课程设置在周几，目前没有考虑一门课一周上多次的问题，如果考虑选课部分需要进行额外设计
     */
    private int weekday;
    /**
     * 时段id，对应一天中的哪一个时段，如果不允许不等长的课程（如固定两节课，一天可以分为5个时段）
     * ，则只需要判断该id是否相同即可判断是否冲突
     */
    private int timeslot_id;
    /**
     * 可能为空，如果没有教授教这门课
     */
    private String professor_id;
    /**
     * 已经注册了该门课的学生，成功注册后需sql将其加一
     * 为了避免获取当前人数时对整个开课表进行一次count()sql，将开销均摊
     */
    private int student_count;
    private String status;
    
    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getTimeslot_id() {
        return timeslot_id;
    }

    public void setTimeslot_id(int timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

    public String getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }

    public int getStudent_count() {
        return student_count;
    }

    public void setStudent_count(int student_count) {
        this.student_count = student_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
