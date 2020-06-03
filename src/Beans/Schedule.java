package Beans;

import java.util.List;

public class Schedule {
	String student_id;
	List<String> primary;
	List<String> alternate;
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
}
