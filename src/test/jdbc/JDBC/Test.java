package test.jdbc.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		JDBCUtil util = new JDBCUtil();
		List<Object> param = new ArrayList<>();
		String sql = "select * from user where age > ? and name like ?";
		param.add(21);
		param.add("%t%");
		ResultSet rs = util.query(sql, param);
		try {
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
