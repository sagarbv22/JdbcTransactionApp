import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Scanner;

import in.Jdbc.util.JdbcUtil;

public class SavePoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection = null;
		Statement stmt = null;

		connection = JdbcUtil.getConnection();
		try {

			if (connection != null)
				stmt = connection.createStatement();

			if (stmt != null) {
				connection.setAutoCommit(false);
				System.out.println("Transaction begins...");
				stmt.executeUpdate("Insert into account(name, balance) values('vijianna', 500000)");
				stmt.executeUpdate("Insert into account(name, balance) values('vinodamma', 500000)");
				Savepoint sp = connection.setSavepoint();
				stmt.executeUpdate("Insert into account(name, balance) values('lakshmamma', 500000)");
				System.out.println("query rollback");
				connection.rollback(sp);
				connection.commit();
				System.out.println("Transaction ended...");

			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {

			JdbcUtil.close(connection, stmt, null);
		}

	}

}
