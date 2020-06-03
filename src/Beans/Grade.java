package Beans;

import java.io.Serializable;

public class Grade implements Serializable {
    public Grade(){
        student_id=null;
        course_id=null;
        reg_id=-1;
        grade=null;
    }
    private String student_id;
    private String course_id;
    private int reg_id;
    private String grade;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
