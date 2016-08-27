package test.jdbc.JDBC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConn {

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
			Class.forName(Config.DRIVER_CLASS);
			conn = DriverManager.getConnection(Config.DATABASE_URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	// 执行SQL的声明对象
	private Statement statement;
	private PreparedStatement preparedStatement;
	private CallableStatement callableStatement;
	
	// 结果集对象
	private ResultSet resultSet;
	public ResultSet getResultSet() {
		return resultSet;
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
	 * 关闭连接
	 */
	public void close(){
		try {
			// 关闭结果集
			if (resultSet != null) {
				resultSet.close();
			}
			
			// 关闭声明
			if (statement != null) {
				statement.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (callableStatement != null) {
				callableStatement.close();
			}
			
			// 关闭conn连接
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
