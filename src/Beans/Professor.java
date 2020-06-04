package Beans;

import java.io.Serializable;
import java.sql.Date;

public class Professor extends User implements Serializable {
    private String p_id;
    private String p_name;
    private String password;
    private Date birthday;
    private String status;
    private int dept_id;
    /**
     * 身份证件，考虑到身份证中有生日信息，生日信息可从身份证获取
     */
    private String identify_num;

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


    public Professor(String _id, String _name, Date _birthday, String _identify_num, String _status, int _dept_id, String _password){
        p_id=_id;
        p_name=_name;
        password=_password;
        birthday=_birthday;
        identify_num=_identify_num;
        status=_status;
        dept_id=_dept_id;
        type=User.USER_PROFESSOR;
    }
    public Professor(){
        p_id = "null";
    }
}
