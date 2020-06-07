package Beans;

import java.io.Serializable;

public class Registrar extends User implements Serializable {
    public Registrar(){
        r_id=null;
        password=null;
        type=User.USER_REGISTERER;
    }

    private String r_id;
    private String password;

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
