package Beans;

import java.io.Serializable;

/**
 * �γ�ע����Ϣʵ���࣬�ڽ���session֮�󼴻�ȡ<br/>
 * ȫ����Ϊsession�е�һ��attribute����Ϊ��registration��<br/>
 * Ĭ�ϻ�ȡ�������µ�һ��ע�����Ϣ<br/>
 * ������ݿ�����ȫû�м�¼���attributeӦΪnull<br/>
 * �����Ҫ֧�ֲ�ѯ������ǰ�����ݣ����ṩ������ݺ�ѧ�ڻ�ȡ�ö����DAO
 */
public class Registration implements Serializable {
    public Registration(){
        reg_id=-1;
        status=null;
        year=-1;
        semester=null;
    }

    /**
     * ���һ�ογ�ע����̣��������Һ��и��ֶεı�
     */
    private int reg_id;
    /**
     * ����ȡ��open�����ߡ�closed�����ֱ����ǰ����ѡ�ν׶κͿγ�ע����ϣ������жϸ��ֶε�֪�Ƿ���Խ��в���
     */
    private String status;
    /**
     * ��Ӧ��ѧ�����
     */
    private int year;
    /**
     * ��Ӧѧ�ڣ���ȡֵΪ��spring�����ߡ�fall��
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
