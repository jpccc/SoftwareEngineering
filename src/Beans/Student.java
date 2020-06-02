package Beans;

import java.io.Serializable;
import java.sql.Date;

public class Student implements Serializable {
    public Student(){
        s_id=null;
        s_name=null;
        birthday=null;
        identify_num=0;
        status=null;
        dept_id=-1;
        graduate_date=null;
        password=null;
    }
    private String s_id;
    private String s_name;
    private Date birthday;
    private int identify_num;
    private String status;
    private int dept_id;
    private Date graduate_date;
    private String password;

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

    public int getIdentify_num() {
        return identify_num;
    }

    public void setIdentify_num(int identify_num) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
