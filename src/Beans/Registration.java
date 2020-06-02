package Beans;

import java.io.Serializable;

public class Registration implements Serializable {
    public Registration(){
        reg_id=-1;
        status=null;
        year=-1;
        semester=null;
    }
    private int reg_id;
    private String status;
    private int year;
    private String semester;

    public int getReg_id() {
        return reg_id;
    }

    public void setReg_id(int reg_id) {
        this.reg_id = reg_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
