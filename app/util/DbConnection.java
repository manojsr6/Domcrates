package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * It is a singleton class which creates only one instance of an object.
 * @author Manoj S Ramaswamy
 *
 */
public class DbConnection {
	private static DbConnection dbconnection;
	public Connection conn;
	
	private DbConnection() throws SQLException {
		conn= (Connection) DriverManager.getConnection("jdbc:mysql://root:password@localhost/domaincrates");
	}
	
	/**
	 * This function is used to get connection from mySQL database by declaring an object
	 * @return
	 * @throws SQLException
	 */
	public static DbConnection getConnection() throws SQLException {
		if(dbconnection == null)
		{
			dbconnection= new DbConnection();
			return dbconnection;
		}
		return dbconnection;
	}
}
