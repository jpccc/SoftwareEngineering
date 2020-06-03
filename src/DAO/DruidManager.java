package DAO;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * DRUID连接池的工具类，调用其类方法getConnection获取连接，一定要记得及时释放资源，否则可能阻塞
 * 资源包括ResultSet，Statement和Connection释放时按顺序释放
 * 利用数据库连接池增加并发度
 *
 * 使用时请自行修改其中的数据库用户名，url和密码
 */
public class DruidManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/course_selection"; // 数据库连接URL
    private static final String DB_USERNAME = "root"; // 数据库用户名
    private static final String DB_PASSWORD = "010233"; // 数据库密码
    private static DruidDataSource druidDataSource=null;//连接池DataSource

    /**
     * 从连接池获取一个数据库连接，用完需要释放
     * @return 数据库的一个Connection，可正常执行操作
     * @throws SQLException 连接错误时抛出异常
     */
    public static Connection getConnection() throws SQLException {
        return getDruidDataSource().getConnection();
    }

    /**
     * 辅助释放资源
     * @param conn 数据库连接
     * @param st statement对象
     * @param rs 结果集
     * @throws SQLException 关闭过程中可能报出异常
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
        //连接启动参数
        druidDataSource.setUrl(DB_URL);
        druidDataSource.setUsername(DB_USERNAME);
        druidDataSource.setPassword(DB_PASSWORD);
        //连接参数配置
        druidDataSource.setMaxActive(20);//最大活动连接
        druidDataSource.setInitialSize(1);//起始连接数
        druidDataSource.setMinIdle(3);//最小连接数
        druidDataSource.setMaxWait(60000);

        druidDataSource.setValidationQuery("select 1");//刷新语句

        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);//判断是否有效的间隔时长计时器
        druidDataSource.setMinEvictableIdleTimeMillis(300000);//最小刷新间隔

        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);

        return druidDataSource;
    }
}
