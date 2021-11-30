package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDao {
	
	private Connection con;
	
	/**
	 * Faz a conexão no banco de dados 
	 * @return conexao - com o banco de dados
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		String hostName = "localhost"; 		// servidor
		String dbName = "projeto_livraria";
		String user = "servidor-livraria";
		String password = "123@456";
		
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		con = DriverManager.getConnection(
				String.format("jdbc:jtds:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;", 
						hostName,dbName,user,password));
		return con;
	}

}
