package DAO;

import Beans.Professor;
import Beans.Student;

import java.util.List;

public interface StudentDAO {
    public void insert(Student student) throws Exception;
    public void update(Student student) throws Exception;
    public void delete(String s_id) throws Exception;
    public Student findById(String s_id) throws Exception;
    public List<Student> findByName(String name) throws Exception;
    public List<Student> queryAll() throws Exception;
    public List<Student> showAll() throws Exception;
}
