package p.sql;

import java.sql.Connection;
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
		ds.setInitialSize(1);
		ds.setMaxActive(1);
		ds.setMaxIdle(1);
		ds.setMinIdle(1);
		ds.setMaxWait(60000);
		try {
			Connection co = ds.getConnection();
			System.out.println(co);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
