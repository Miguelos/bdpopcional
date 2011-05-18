package bd.inicializacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Inicializacion1 {

	/**
	 * Conexion con la base de datos
	 */
	private static Connection connection = null;
	private static Statement statement = null;
	
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if(connection == null){
			//DriverManager.registerDriver(new Driver());
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "root");
		}
		return connection;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static Statement getStatement() throws SQLException {
		if(statement == null){
			statement = (Statement) getConnection().createStatement();
		}
		return statement;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			getStatement().executeUpdate("CREATE TABLE maestra_actividades (id INT NOT NULL, descr VARCHAR(45), PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_productor (id INT NOT NULL, descr VARCHAR(45), PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_parcela (id INT NOT NULL, descr VARCHAR(45), PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_abono (id INT NOT NULL, descr VARCHAR(45), PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE principal (act INT NOT NULL, prod INT NOT NULL, parc INT NOT NULL, abo INT NOT NULL, fecha DATE NOT NULL, PRIMARY KEY (act,prod,parc,abo,fecha));");
	
			//Datos de las maestras
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(1,'Actividad1');");
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(2,'Actividad2');");
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(3,'Actividad3');");
	
				getStatement().executeUpdate("INSERT INTO maestra_productor values(1,'Productor1');");
				getStatement().executeUpdate("INSERT INTO maestra_productor values(2,'Productor2');");
				getStatement().executeUpdate("INSERT INTO maestra_productor values(3,'Productor3');");
	
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(1,'Parcela1');");
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(2,'Parcela2');");
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(3,'Parcela3');");
	
				getStatement().executeUpdate("INSERT INTO maestra_abono values(1,'Abono1');");
				getStatement().executeUpdate("INSERT INTO maestra_abono values(2,'Abono2');");
				getStatement().executeUpdate("INSERT INTO maestra_abono values(3,'Abono3');");
	

			//Foreign Keys
			getStatement().executeUpdate("ALTER TABLE principal ADD FOREIGN KEY (act) REFERENCES maestra_actividades(id);");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
