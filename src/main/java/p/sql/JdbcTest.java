package p.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=CTT";
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "123456";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			String sql = "select * from t1 where id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, 1);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				String name = set.getString(2);
				System.out.println(name);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
