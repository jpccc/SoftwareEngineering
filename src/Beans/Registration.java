package Beans;

import java.io.Serializable;

/**
 * 课程注册信息实体类，在建立session之后即获取<br/>
 * 全程作为session中的一个attribute，键为‘registration’<br/>
 * 默认获取的是最新的一次注册的信息<br/>
 * 如果数据库中完全没有记录则该attribute应为null<br/>
 * 如果需要支持查询更早以前的数据，再提供根据年份和学期获取该对象的DAO
 */
public class Registration implements Serializable {
    public Registration(){
        reg_id=-1;
        status=null;
        year=-1;
        semester=null;
    }

    /**
     * 标记一次课程注册过程，用来查找含有该字段的表
     */
    private int reg_id;
    /**
     * 可以取‘open’或者‘closed’，分别代表当前正在选课阶段和课程注册完毕，可以判断该字段得知是否可以进行操作
     */
    private String status;
    /**
     * 对应的学期年份
     */
    private int year;
    /**
     * 对应学期，可取值为‘spring’或者‘fall’
     */
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
