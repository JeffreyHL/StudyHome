package study._201705_before_.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC基础实验
 * 
 * @author Jeffrey
 * @Date 2016/08/27
 * 
 *       说明：数据库使用mysql，建表文件见DB.sql
 */
public class JDBCBaseTest {

	public static void main(String[] args) {
		JdbcBase();
	}

	/**
	 * JDBC基本操作处理流程
	 */
	public static void JdbcBase() {
		try {
			/*
			 * 1，加载驱动 在连接数据库之前，首先要加载想要连接的数据库的驱动到JVM，
			 * 成功加载后，会将Driver类的实例注册到DriverManager类中。
			 */
			Class.forName("com.mysql.jdbc.Driver");

			/*
			 * 2，JDBC连接URL 连接URL定义了连接数据库时的协议、子协议、数据源标识。
			 */
			String url = "jdbc:mysql://localhost:3306/test";

			/*
			 * 3，创建数据库连接对象Connection
			 * 要连接数据库，需要向java.sql.DriverManager请求并获得Connection对象，该对象就代表一个数据库的连接。
			 * 使用DriverManager的getConnectin(String url , String username ,
			 * String password )方法传入指定的欲连接的数据库的路径、数据库的用户名和密码来获得。
			 */
			Connection conn = DriverManager.getConnection(url, "root", "admin");

			/*
			 * 4，创建声明对象Statement
			 * 要执行SQL语句，必须获得java.sql.Statement实例，Statement实例分为以下3种类型：
			 * 1、执行静态SQL语句。通常通过Statement实例实现。
			 * 2、执行动态SQL语句。通常通过PreparedStatement实例实现。
			 * 3、执行数据库存储过程。通常通过CallableStatement实例实现。
			 */
			Statement statement = conn.createStatement();
			// PreparedStatement preStatement = conn.prepareStatement("select *
			// from user");
			// CallableStatement callStatement = conn.prepareCall("sqlString");

			/*
			 * 5，执行SQL Statement接口提供了三种执行SQL语句的方法：executeQuery
			 * 、executeUpdate和execute 1、ResultSet executeQuery(String
			 * sqlString)：执行查询数据库的SQL语句，返回一个结果集（ResultSet）对象。 2、int
			 * executeUpdate(String sqlString)：用于执行INSERT、UPDATE或DELETE语句以及SQL
			 * DDL语句，如：CREATE TABLE和DROP TABLE等
			 * 3、execute(sqlString):用于执行返回多个结果集、多个更新计数或二者组合的语句。
			 */
			ResultSet rs = statement.executeQuery("select * from user");
			// int rows = statement.executeUpdate("insert into user (Name, Age,
			// Sex) values('Name','12','0') ");
			// boolean flag = statement.execute("select Name from user");

			/*
			 * 6，结果处理 1、执行更新返回的是本次操作影响到的记录数。 2、执行查询返回的结果是一个ResultSet对象。
			 * ResultSet包含符合SQL语句中条件的所有行，并且它通过一套get方法提供了对这些行中数据的访问。
			 * 使用结果集（ResultSet）对象的访问方法获取数据
			 */
			while (rs.next()) {
				System.out.println(rs.getString("Name"));
				System.out.println("Name:" + rs.getString(2) + "\tAge:" + rs.getInt(3));
			}

			/*
			 * 7，关闭连接 操作完成以后要把所有使用的JDBC对象全都关闭，以释放JDBC资源，关闭顺序和声明顺序相反： 1、关闭记录集
			 * 2、关闭声明 3、关闭连接对象
			 */
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("驱动类加载错误!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL错误");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("其他错误");
			e.printStackTrace();
		}
	}

}
