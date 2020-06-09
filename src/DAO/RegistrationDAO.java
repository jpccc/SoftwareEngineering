package DAO;

import Beans.Registration;

import java.util.List;

public interface RegistrationDAO {
    public Registration queryByTime(int year,String semester);
    public Registration queryLatest();
    public int insert(Registration reg);
    public void delete(List<Registration> rList)throws Exception;
    public void deleteByID(int r_id)throws Exception;
    public void update(Registration reg) throws Exception;
}
