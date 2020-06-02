package Beans;

import java.io.Serializable;
import java.sql.Date;

public class StudentInfo implements Serializable {
    public StudentInfo(){
        s_id=null;
        s_name=null;
        birthday=null;
        identify_num=null;
        status=null;
        dept_id=-1;
        graduate_date=null;
    }
    private String s_id;
    private String s_name;
    private Date birthday;
    private String identify_num;
    private String status;
    private int dept_id;
    private Date graduate_date;

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentify_num() {
        return identify_num;
    }

    public void setIdentify_num(String identify_num) {
        this.identify_num = identify_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public Date getGraduate_date() {
        return graduate_date;
    }

    public void setGraduate_date(Date graduate_date) {
        this.graduate_date = graduate_date;
    }
}
