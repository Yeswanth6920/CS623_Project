package CS623;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Sample of JDBC for x PostgreSQL ACID is implemented
 */

public class Transaction {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement stmt = null;
	private ResultSet resultSet;

	public void addProduct(Product product)
			throws Exception {
		try {
			// Load the MySQL driver
			Class.forName("org.postgresql.Driver");
			// Old driver
			// Class.forName("com.mysql.jdbc.Driver");

			// Connect to the default database with credentials
			connect = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5433/postgres?useSSL=false&amp;serverTimezone=UTC", "postgres",
					"root");

			// For atomicity
			connect.setAutoCommit(false);

			// For isolation
			connect.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// Maybe a table student1 exist, maybe not
			// create table student(id integer, name varchar(10), primary key(Id))
			// Either the 2 following inserts are executed, or none of them are. This is
			// atomicity.

			// create sql statement

			String sql = "select * from product where prodid=?";
			stmt = connect.prepareStatement(sql);
			stmt.setString(1, product.getProd());

			// execute query
			resultSet = stmt.executeQuery();

			if (!resultSet.next()) {
				// create sql for Product insert (parent)
				sql = "insert into product " + "(prodid, pname, price) " + "values (?, ?, ?)";

				stmt = connect.prepareStatement(sql);

				// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.

				// set the param values for the product
				stmt.setString(1, product.getProd());
				stmt.setString(2, product.getPname());
				stmt.setInt(3, product.getPrice());

				// execute sql insert
				stmt.execute();

				sql = "insert into stock " + "(prodid, depid, quantity) " + "values (?, ?, ?)";
				stmt = connect.prepareStatement(sql);

				// set the param values for the product
				stmt.setString(1, product.getStockProdId());
				stmt.setString(2, product.getDep());
				stmt.setInt(3, product.getQuantity());
				// execute sql insert
				stmt.execute();

			} else {
				// The product p1 changes its name to pp1 in Product and Stock.
				// do the product updation

				// create SQL update statement
				sql = "update product " + "set pname=?, price=?" + " where prodid=?";

				// prepare statement
				stmt = connect.prepareStatement(sql);

				// set params
				stmt.setString(1, product.getPname());
				stmt.setInt(2, product.getPrice());
				stmt.setString(3, product.getProd());

				// execute SQL statement
				stmt.execute();
			}

			connect.commit();

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			connect.rollback();
		} finally {
			// clean up JDBC objects
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

	public void deleteProduct(String prodId) throws Exception {
		try {
			// Load the PostGre driver
			Class.forName("org.postgresql.Driver");
			// Old driver
			// Class.forName("com.mysql.jdbc.Driver");

			// Connect to the default database with credentials
			connect = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5433/postgres?useSSL=false&amp;serverTimezone=UTC", "postgres",
					"root");

			// For atomicity
			connect.setAutoCommit(false);

			// For isolation
			connect.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql to delete stock (child table)
			String sql = "delete from stock where prodid=?";

			// prepare statement
			stmt = connect.prepareStatement(sql);
			// set params
			stmt.setString(1, prodId);

			// preparedStatement sql statement
			stmt.execute();

			// create sql to delete product (parent table)
			sql = "delete from product where prodid=?";

			// prepare statement
			stmt = connect.prepareStatement(sql);
			// set params
			stmt.setString(1, prodId);

			// execute sql statement
			stmt.execute();

			// Commit the transaction For atomocity, consistency, durability
			connect.commit();

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			connect.rollback();
		} finally {
			// clean up JDBC objects
			try {
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
	}

}