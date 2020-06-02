package DAO;

import Beans.Student;

import java.util.List;

public interface StudentDAO {
    public void insert(Student student) throws Exception;
    public void update(Student student) throws Exception;
    public void delete(String s_id) throws Exception;
    public Student findById(String s_id) throws Exception;
    public List<Student> queryAll() throws Exception;
}
