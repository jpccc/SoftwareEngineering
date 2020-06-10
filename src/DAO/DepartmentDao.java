package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDao {
    public Map<String,String> getAll() throws Exception {
        Map<String,String> all=new HashMap<>();
        String sql = "select * from department";
        PreparedStatement preparedStatement = null;
        ResultSet rs=null;
        Connection conn = null;
        try {
            conn = DAO.DruidManager.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            rs=preparedStatement.executeQuery();
            while (rs.next()){
                all.put(Integer.toString(rs.getInt(1)),rs.getString(2));
            }
        } catch (Exception e) {
            throw new Exception("get deptName 操作出现异常");
        } finally {
            DAO.DruidManager.close(conn,preparedStatement,rs);
        }
        return all;
    }
}
