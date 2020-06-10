package Beans;

import java.io.Serializable;
import java.util.List;

/**
 * selection表中的设计没有直接体现选课的顺序，为设计中的不合理，可能导致此实体类的问题<br/>
 * 考虑解决办法为：<br/>
 * 1.在每一项后加入一个新属性，为其编号1-4<br/>
 * 2.利用select_status项，取值可以为‘enrolled in’<br/>
 * ，‘primary（加上序号）’，‘alternative（加上序号）’<br/>
 * 3.进行更大的修改，将表项拆分，设置一个关系表项，其中包含一个人的选课列表
 * selection表项改为grades表项，只记录成绩信息
 * 如果有想法请联系我       --李睿宸
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
