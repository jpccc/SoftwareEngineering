package DAO;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DRUID连接池的工具类，调用其类方法getConnection获取连接，一定要记得及时释放资源，否则可能阻�?
 * 资源包括ResultSet，Statement和Connection释放时按顺序释放
 * 利用数据库连接池增加并发�?
 *
 * 使用时请自行修改其中的数据库用户名，url和密�?
 */
public class DruidManager {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/course_selection?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"; // 数据库连接URL
    private static final String DB_USERNAME = "root"; // 数据库用户名
    private static final String DB_PASSWORD = "010233"; // 数据库密�?
    private static final String OLDSYS_URL = "jdbc:mysql://127.0.0.1:3306/old_sys?characterEncoding=utf-8&useSSL=false&serverTimezone=UTC"; // 数据库连接URL
    private static final String OLDSYS_USERNAME = "root"; // 数据库用户名
    private static final String OLDSYS_PASSWORD = "010233"; // 数据库密�?
    private static DruidDataSource druidDataSource=null;//连接池DataSource
    private static DruidDataSource oldSysDatasource=null;
    public static final int OLDSYS_FLAG=1;
    /**
     * 从连接池获取�?个数据库连接，用完需要释�?
     * @return 数据库的�?个Connection，可正常执行操作
     * @throws SQLException 连接错误时抛出异�?
     */
    public static Connection getConnection() throws SQLException {
        return getDruidDataSource().getConnection();
    }
    public static Connection getConnection(int flag)throws SQLException{
        DruidDataSource ds=getDruidDataSource(flag);
        if(ds!=null)return ds.getConnection();
        else return null;
    }
    /**
     * 辅助释放资源
     * @param conn 数据库连�?
     * @param st statement对象
     * @param rs 结果�?
     * @throws SQLException 关闭过程中可能报出异�?
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
                    druidDataSource =CreateDataSource(DB_URL,DB_USERNAME,DB_PASSWORD);
                }
            }
            return druidDataSource;
        }else return druidDataSource;
    }

    private static DruidDataSource CreateDataSource(String URL,String Username,String Password){
        DruidDataSource druidDataSource=new DruidDataSource();
        //连接启动参数
        druidDataSource.setUrl(URL);
        druidDataSource.setUsername(Username);
        druidDataSource.setPassword(Password);
        //连接参数配置
        druidDataSource.setMaxActive(20);//�?大活动连�?
        druidDataSource.setInitialSize(1);//起始连接�?
        druidDataSource.setMinIdle(3);//�?小连接数
        druidDataSource.setMaxWait(50000);

        druidDataSource.setValidationQuery("select 1");//刷新语句

        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);//判断是否有效的间隔时长计时器
        druidDataSource.setMinEvictableIdleTimeMillis(300000);//�?小刷新间�?

        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(150);

        return druidDataSource;
    }

    private static DruidDataSource CreateDataSource(){
        return CreateDataSource(DB_URL,DB_USERNAME,DB_PASSWORD);
    }
    private static DruidDataSource getDruidDataSource(int flag){
        switch (flag){
            case OLDSYS_FLAG: {
                if (oldSysDatasource == null) {
                    synchronized (DruidDataSource.class) {
                        if (oldSysDatasource == null) {
                            oldSysDatasource = CreateDataSource(OLDSYS_URL, OLDSYS_USERNAME, OLDSYS_PASSWORD);
                        }
                    }
                    return oldSysDatasource;
                } else return oldSysDatasource;
            }
            default: return null;
        }
    }

}
