package DAO;

import Beans.Registrar;
public interface RegistrarDAO {
    public void insert(Registrar professor) throws Exception;
    public void update(Registrar professor) throws Exception;
    public Registrar findById(String id) throws Exception;
}
