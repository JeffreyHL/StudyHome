package test.jdbc.JDBC;

import java.util.Properties;

public class Config {

	private static Properties prop = new Properties();
	static {
		try {
			// 加载JDBC配置文件
			prop.load(Config.class.getResourceAsStream("jdbcConfig.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static final String DRIVER_CLASS = prop.getProperty("DRIVER_CLASS");
	public static final String DATABASE_URL = prop.getProperty("DATABASE_URL");
	public static final String USER_NAME = prop.getProperty("USER_NAME");
	public static final String PASSWORD = prop.getProperty("PASSWORD");
}
