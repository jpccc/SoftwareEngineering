package DAO;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DRUID杩炴帴姹犵殑宸ュ叿绫伙紝璋冪敤鍏剁被鏂规硶getConnection鑾峰彇杩炴帴锛屼竴瀹氳璁板緱鍙婃椂閲婃斁璧勬簮锛屽惁鍒欏彲鑳介樆濉�
 * 璧勬簮鍖呮嫭ResultSet锛孲tatement鍜孋onnection閲婃斁鏃舵寜椤哄簭閲婃斁
 * 鍒╃敤鏁版嵁搴撹繛鎺ユ睜澧炲姞骞跺彂搴�
 *
 * 浣跨敤鏃惰鑷淇敼鍏朵腑鐨勬暟鎹簱鐢ㄦ埛鍚嶏紝url鍜屽瘑鐮�
 */
public class DruidManager {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/course_selection?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"; // 鏁版嵁搴撹繛鎺RL
    private static final String DB_USERNAME = "root"; // 鏁版嵁搴撶敤鎴峰悕
    private static final String DB_PASSWORD = "010233"; // 鏁版嵁搴撳瘑鐮�
    private static DruidDataSource druidDataSource=null;//杩炴帴姹燚ataSource

    /**
     * 浠庤繛鎺ユ睜鑾峰彇涓�涓暟鎹簱杩炴帴锛岀敤瀹岄渶瑕侀噴鏀�
     * @return 鏁版嵁搴撶殑涓�涓狢onnection锛屽彲姝ｅ父鎵ц鎿嶄綔
     * @throws SQLException 杩炴帴閿欒鏃舵姏鍑哄紓甯�
     */
    public static Connection getConnection() throws SQLException {
        return getDruidDataSource().getConnection();
    }

    /**
     * 杈呭姪閲婃斁璧勬簮
     * @param conn 鏁版嵁搴撹繛鎺�
     * @param st statement瀵硅薄
     * @param rs 缁撴灉闆�
     * @throws SQLException 鍏抽棴杩囩▼涓彲鑳芥姤鍑哄紓甯�
     */
    public static void close(Connection conn,  PreparedStatement st,ResultSet rs) throws SQLException {
        if(rs!=null)rs.close();
        if(st!=null)st.close();
        if(conn!=null)conn.close();
    }


    private static DruidDataSource getDruidDataSource(){
        if(druidDataSource==null){
            synchronized (DruidDataSource.class) {
                if (druidDataSource == null) {
                    druidDataSource =CreateDataSource();
                }
            }
            return druidDataSource;
        }else return druidDataSource;
    }

    private static DruidDataSource CreateDataSource(){
        druidDataSource=new DruidDataSource();
        //杩炴帴鍚姩鍙傛暟
        druidDataSource.setUrl(DB_URL);
        druidDataSource.setUsername(DB_USERNAME);
        druidDataSource.setPassword(DB_PASSWORD);
        //杩炴帴鍙傛暟閰嶇疆
        druidDataSource.setMaxActive(20);//鏈�澶ф椿鍔ㄨ繛鎺�
        druidDataSource.setInitialSize(1);//璧峰杩炴帴鏁�
        druidDataSource.setMinIdle(3);//鏈�灏忚繛鎺ユ暟
        druidDataSource.setMaxWait(50000);

        druidDataSource.setValidationQuery("select 1");//鍒锋柊璇彞

        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);//鍒ゆ柇鏄惁鏈夋晥鐨勯棿闅旀椂闀胯鏃跺櫒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);//鏈�灏忓埛鏂伴棿闅�

        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(150);

        return druidDataSource;
    }
}
