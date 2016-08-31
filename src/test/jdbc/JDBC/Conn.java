package test.jdbc.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

	// 数据库连接对象
	private Connection conn;
	
	public Connection getConn() {
		try {
			// 当conn为空或设为自动提交时，获取新的conn
			if (conn == null || conn.getAutoCommit()) {
				conn = getConntion();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取数据连接对象
	 */
	private Connection getConntion(){
			try {
				Class.forName(Config.DRIVER_CLASS).newInstance();
				conn = DriverManager.getConnection(Config.DATABASE_URL+Config.DATABASE_NAME, 
						Config.USER_NAME, Config.PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return conn;
	}

	/**
	 * 设置是否自动提交，开启自动提交时，不可回滚事务
	 * @param flag
	 */
	public void setAutoCommit(boolean flag) {
		try {
			conn.setAutoCommit(flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 事务提交
	 */
	public void commit(){
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 事务回滚
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭conn连接
	 */
	public void close(){
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
