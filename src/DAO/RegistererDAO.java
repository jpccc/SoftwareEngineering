package DAO;

import Beans.Registerer;

import java.util.List;

public interface RegistererDAO {
    public void insert(Registerer professor) throws Exception;
    public void update(Registerer professor) throws Exception;
    public Registerer findById(String id) throws Exception;
}
