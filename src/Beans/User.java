package Beans;

public class User {
    User(){
        type=USER_UNKNOWN;
    }
    public int getType(){
        return type;
    }
    public int type;
    public static final int USER_UNKNOWN=-1;
    public static final int USER_STUDENT=0;
    public static final int USER_PROFESSOR=1;
    public static final int USER_REGISTERER=2;
}
