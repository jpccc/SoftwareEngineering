package DAO;

import Beans.Professor;

import java.util.List;

public interface ProfessorDAO {
    public void insert(Professor professor) throws Exception;
    public void update(Professor professor) throws Exception;
    public void delete(String id) throws Exception;
    public List<Professor> findByName(String name) throws Exception;
    public Professor findById(String id) throws Exception;
    public List<Professor> showAll() throws Exception;
}
