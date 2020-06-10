package Beans;

import java.io.Serializable;
import java.util.List;

/**
 * selection���е����û��ֱ������ѡ�ε�˳��Ϊ����еĲ��������ܵ��´�ʵ���������<br/>
 * ���ǽ���취Ϊ��<br/>
 * 1.��ÿһ������һ�������ԣ�Ϊ����1-4<br/>
 * 2.����select_status�ȡֵ����Ϊ��enrolled in��<br/>
 * ����primary��������ţ�������alternative��������ţ���<br/>
 * 3.���и�����޸ģ��������֣�����һ����ϵ������а���һ���˵�ѡ���б�
 * selection�����Ϊgrades���ֻ��¼�ɼ���Ϣ
 * ������뷨����ϵ��       --����
 */
public class Schedule implements Serializable {
	private String student_id;
	private List<String> primary;
	private List<String> alternate;
	public Schedule(){
		student_id=null;
		primary=null;
		alternate=null;
	}
	public Schedule(String student_id,List<String> primary,List<String> alternate) {
		this.student_id=student_id;
		this.primary=primary;
		this.alternate=alternate;
	}
	public List<String> getPrimary(){
		return primary;
	}
	public List<String> getAlternate(){
		return alternate;
	}
	public void setPrimary(List<String> primary){
		this.primary=primary;
	}
	public void setAlternate(List<String> alternate){
		this.alternate=alternate;
	}
	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}
}
