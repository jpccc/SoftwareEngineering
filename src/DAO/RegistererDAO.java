package DAO;

import Beans.Registrar;

import java.util.List;

public interface RegistererDAO {
    public void insert(Registrar professor) throws Exception;
    public void update(Registrar professor) throws Exception;
    public Registrar findById(String id) throws Exception;
}
