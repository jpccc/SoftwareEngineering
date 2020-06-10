package DAO;

import java.sql.*;

public class JDBCUtil {
    public static Connection getMysqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/course_selection?useSSL=false&serverTimezone=UTC",
                "root", "010233");
    }

    public static void close(Connection conn, ResultSet rs, PreparedStatement st) throws SQLException {
        st.close();
        rs.close();
        conn.close();
    }
}
