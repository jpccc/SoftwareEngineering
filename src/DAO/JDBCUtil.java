package DAO;

import java.sql.*;

public class JDBCUtil {
    public static Connection getMysqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=UTC",
                "root", "root");
    }

    public static void close(Connection conn, ResultSet rs, PreparedStatement st) throws SQLException {
        st.close();
        rs.close();
        conn.close();
    }
}
