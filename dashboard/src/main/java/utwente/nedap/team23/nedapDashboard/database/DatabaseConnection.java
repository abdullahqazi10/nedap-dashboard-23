package utwente.nedap.team23.nedapDashboard.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {

	private static Connection instance;
	private static final String HOST = "localhost/";
	private static final String DBNAME = "NedapDump";
	public static final String URL = "jdbc:mysql://" + HOST + DBNAME ;

	private DatabaseConnection(){}

	public static Connection getInstance() {

		if(instance == null) { 
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
				instance = DriverManager.getConnection(URL, "root", "California@24"); 
				instance.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // to avoid a dirty read if job is being 
																						 // written since it can fail
				System.out.println("Connection to Database " + DBNAME + " succesfully established");
				
			} catch(ClassNotFoundException e){
				throw new RuntimeException("Class not found error.");
				
			} catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("SQLState: " + e.getSQLState());
			}
		}
		return instance;
	}
}
