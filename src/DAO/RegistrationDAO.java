package DAO;

import Beans.Registration;

public interface RegistrationDAO {
    public Registration queryByTime(int year,String semester);
    public Registration queryLatest();

}
