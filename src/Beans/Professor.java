package Beans;

import java.sql.Date;

public class Professor {
    String p_id;
    String p_name;
    String password;
    Date birthday;
    int identify_num;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    String status;
    int dept_id;
    public Professor(String _id, String _name, Date _birthday, int _identify_num, String _status, int _dept_id, String _password){
        p_id=_id;
        p_name=_name;
        password=_password;
        birthday=_birthday;
        identify_num=_identify_num;
        status=_status;
        dept_id=_dept_id;
    }
    public Professor(){
        p_id = "null";
    }
}
