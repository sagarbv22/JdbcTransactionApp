import java.sql.*;
import java.util.Scanner;

import in.Jdbc.util.JdbcUtil;

public class TransactionApp {

	public static void main(String[] args) {

		Connection connection = null;
		Statement stmt = null;
		ResultSet rstmt = null;
		ResultSet rs = null;
		Scanner sc = null;

		connection = JdbcUtil.getConnection();
		try {

			if (connection != null)
				stmt = connection.createStatement();

			if (stmt != null) {

				rstmt = stmt.executeQuery("Select name, balance from account");

			}

			if (rstmt != null) {
				System.out.println("Before Transaction.");
				while (rstmt.next())
					System.out.println(rstmt.getString(1) + "\t" + rstmt.getInt(2));
			}

			System.out.println("\nTransaction begins....");
			connection.setAutoCommit(false);

			if (stmt != null) {
				stmt.executeUpdate("Update account set balance = balance-5000 where name = 'Manohar'");
				stmt.executeUpdate("Update account set balance = balance+5000 where name = 'Sagar'");
				System.out.print("Confirm with [Yes/No]:");
				sc = new Scanner(System.in);

				String option = sc.next();

				if (option.equalsIgnoreCase("yes"))
					connection.commit();
				else
					connection.rollback();
			}

			rs = stmt.executeQuery("Select name, balance from account");
			System.out.println("After Transaction...");
			if (rs != null)
				while(rs.next())
					System.out.println(rs.getString(1) + "\t" + rs.getInt(2));

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JdbcUtil.close(connection, stmt, rstmt);
		}
	}

}
