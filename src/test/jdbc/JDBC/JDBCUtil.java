package test.jdbc.JDBC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class JDBCUtil {
	
	enum StatementType {STATEMENT, PREPARED_STATEMENT, CALLABLE_STATEMENT};
	
	private Conn connBase = new Conn();
	private Connection conn = connBase.getConn();
	
	private Statement stm = null;
	private PreparedStatement preStm = null;
	private CallableStatement callStm = null;
	
	private ResultSet resultSet = null;
	
	// 执行指定SQL语句
	public Boolean executeSql(String sql) {
		try {
			preStm = conn.prepareStatement(sql);
			preStm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 执行查找语句
	public ResultSet query(String sql) {
		return query(sql, null, null);
	}
	public ResultSet query(String sql, List<Object> param) {
		return query(sql, param, null);
	}
	public ResultSet query(String sql, StatementType type) {
		return query(sql, null, type);
	}
	public ResultSet query(String sql, List<Object> param, StatementType type) {
		StatementType _type = type;
		if (_type == null) {
			_type = StatementType.PREPARED_STATEMENT;
		}
		try {
			switch (_type) {
			case STATEMENT:
				stm = conn.createStatement();
				resultSet = stm.executeQuery(sql);
				break;
			case PREPARED_STATEMENT:
				preStm = getPreStm(conn, sql, param);
				resultSet = preStm.executeQuery();
				break;
			case CALLABLE_STATEMENT:
				callStm = conn.prepareCall(sql);
				resultSet = callStm.executeQuery();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	// 执行更新、删除语句
	public Integer modify(String sql) {
		return modify(sql, null, null);
	}
	public Integer modify(String sql, List<Object> param){
		return modify(sql, param, null);
	}
	public Integer modify(String sql, StatementType type){
		return modify(sql, null, type);
	}
	public Integer modify(String sql, List<Object> param, StatementType type) {
		Integer result = 0;
		StatementType _type = type;
		if (_type == null) {
			_type = StatementType.PREPARED_STATEMENT;
		}
		try {
			switch (_type) {
			case STATEMENT:
				stm = conn.createStatement();
				result = stm.executeUpdate(sql);
				break;
			case PREPARED_STATEMENT:
				preStm = getPreStm(conn, sql, param);
				result = preStm.executeUpdate();
				break;
			case CALLABLE_STATEMENT:
				callStm = conn.prepareCall(sql);
				result = callStm.executeUpdate();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	// 执行更新、删除语句，返回ID值
	public Integer modifyOne(String sql) {
		return modifyOne(sql, null, null);
	}
	public Integer modifyOne(String sql, List<Object> param){
		return modifyOne(sql, param, null);
	}
	public Integer modifyOne(String sql, StatementType type){
		return modifyOne(sql, null, type);
	}
	public Integer modifyOne(String sql, List<Object> param, StatementType type) {
		Integer result = 0;
		StatementType _type = type;
		if (_type == null) {
			_type = StatementType.PREPARED_STATEMENT;
		}
		try {
			switch (_type) {
			case STATEMENT:
				stm = conn.createStatement();
				stm.executeUpdate(sql);
				resultSet = stm.getGeneratedKeys();
				if (resultSet.next()) {
					result = resultSet.getInt(1);
				}
				break;
			case PREPARED_STATEMENT:
				preStm = getPreStm(conn, sql, param);
				preStm.executeUpdate();
				resultSet = preStm.getGeneratedKeys();
				if (resultSet.next()) {
					result = resultSet.getInt(1);
				}
				break;
			case CALLABLE_STATEMENT:
				callStm = conn.prepareCall(sql);
				callStm.executeUpdate();
				resultSet = callStm.getGeneratedKeys();
				if (resultSet.next()) {
					result = resultSet.getInt(1);
				}
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// PreparedStatement 参数注入
	private PreparedStatement getPreStm(Connection conn, String sql, List<Object> param) {
		try {
			preStm = conn.prepareStatement(sql);
			if (param != null){
				for (int i = 0; i < param.size(); i++) {
					Object obj = param.get(i);
					if (obj instanceof Integer) {
						preStm.setInt(i+1, (Integer) obj);
					} else if (obj instanceof String) {
						preStm.setString(i+1, (String) obj);
					} else if (obj instanceof Date) {
						preStm.setDate(i+1, (java.sql.Date) obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return preStm;
	}
	
}
