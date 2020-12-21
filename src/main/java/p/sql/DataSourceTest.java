package p.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceTest {
	public static void main(String[] args) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(
				"jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=CTT");
		ds.setUsername("root");
		ds.setPassword("123456");
		ds.setInitialSize(2);
//		ds.setMaxActive(1);
//		ds.setMaxIdle(1);
		ds.setMinIdle(2);
		ds.setMaxWait(60000);
		try {
			Connection cnt = ds.getConnection();
			String sql = "select * from t1 where id = ?";
			PreparedStatement ps = cnt.prepareStatement(sql);
			ps.setInt(1, 1);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				String name = set.getString(2);
				System.out.println(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
