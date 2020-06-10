package Beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    public Course(){
        reg_id=-1;
        course_id="-1";
        dept_id=-1;
        course_name="-1";
        start_date=null;
        end_date=null;
        weekday=-1;
        timeslot_id=-1;
        professor_id="-1";
        student_count=0;
        status=null;
    }

    /**
     * registration�����е�id�����һ�ο�����Ϣ����ѯѡ����Ϣʱ��Ҫ
     */
    private int reg_id;
    private String course_id;
    /**
     * ������Խ�dept_id��ͬ��Ϊ�жϸ���ʦ���Խ����ſε�����
     */
    private int dept_id;
    private String course_name;
    /**
     * �γ̿�ʼ���ڣ��������жϳ�ͻ�����Ĭ�����пγ�ȫ�̿��ο��Բ�����
     */
    private Date start_date;
    /**
     * �γ̽������ڣ��������жϳ�ͻ�����Ĭ�����пγ�ȫ�̿��ο��Բ�����
     */
    private Date end_date;
    /**
     * �������ֶΣ��γ��������ܼ���Ŀǰû�п���һ�ſ�һ���϶�ε����⣬�������ѡ�β�����Ҫ���ж������
     */
    private int weekday;
    /**
     * ʱ��id����Ӧһ���е���һ��ʱ�Σ�����������ȳ��Ŀγ̣���̶����ڿΣ�һ����Է�Ϊ5��ʱ�Σ�
     * ����ֻ��Ҫ�жϸ�id�Ƿ���ͬ�����ж��Ƿ��ͻ
     */
    private int timeslot_id;
    /**
     * ����Ϊ�գ����û�н��ڽ����ſ�
     */
    private String professor_id;
    /**
     * �Ѿ�ע���˸��ſε�ѧ�����ɹ�ע�����sql�����һ
     * Ϊ�˱����ȡ��ǰ����ʱ���������α����һ��count()sql����������̯
     */
    private int student_count;
    private String status;
    private float price;

    /**
     * parse the two int to a 7*8 Boolean matrix
     * @param weekday the weekday part of course
     * @param timeslot_id the timeslot_id part of course
     * @return a 7*8 matrix true if have this course this day
     */
    public static List<List<Boolean>> parseCourseTime(int weekday,int timeslot_id){
        List<List<Boolean>> res=new ArrayList<>();
        for(int i=0;i<4;i++){
            List<Boolean> temp=new ArrayList<>();
            for(int j=0;j<8;j++){
                temp.add((timeslot_id&1)==1);
                timeslot_id=timeslot_id>>1;
            }
            res.add(temp);
        }
        for(int i=0;i<3;i++){
            List<Boolean> temp=new ArrayList<>();
            for(int j=0;j<8;j++){
                temp.add((weekday&1)==1);
                weekday=weekday>>1;
            }
            res.add(temp);
        }
        return res;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
