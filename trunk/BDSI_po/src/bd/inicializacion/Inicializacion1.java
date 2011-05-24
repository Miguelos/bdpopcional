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
			getStatement().executeUpdate("DROP TABLE principal;");
			getStatement().executeUpdate("DROP TABLE maestra_actividades;");
			getStatement().executeUpdate("DROP TABLE maestra_productor;");
			getStatement().executeUpdate("DROP TABLE maestra_parcela;");
			getStatement().executeUpdate("DROP TABLE maestra_abono;");

	
			
			getStatement().executeUpdate("CREATE TABLE maestra_actividades (id INT UNIQUE NOT NULL, descr VARCHAR(45) UNIQUE, PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_productor (id INT UNIQUE NOT NULL, descr VARCHAR(45) UNIQUE, PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_parcela (id INT UNIQUE NOT NULL, descr VARCHAR(45) UNIQUE, PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE maestra_abono (id INT UNIQUE NOT NULL, descr VARCHAR(45) UNIQUE, PRIMARY KEY (id));");
			getStatement().executeUpdate("CREATE TABLE principal (act INT NOT NULL, prod INT NOT NULL, parc INT NOT NULL, abo INT NOT NULL, fecha DATETIME NOT NULL, PRIMARY KEY (act,prod,parc,abo,fecha));");
	
			//Datos de las maestras
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(1,'Actividad1');");
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(2,'Actividad2');");
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(3,'Actividad3');");
				getStatement().executeUpdate("INSERT INTO maestra_actividades values(4,'Actividad5');");

				getStatement().executeUpdate("INSERT INTO maestra_productor values(1,'Productor1');");
				getStatement().executeUpdate("INSERT INTO maestra_productor values(2,'Productor2');");
				getStatement().executeUpdate("INSERT INTO maestra_productor values(3,'Productor3');");
				getStatement().executeUpdate("INSERT INTO maestra_productor values(5,'Productor5');");

				getStatement().executeUpdate("INSERT INTO maestra_parcela values(1,'Parcela1');");
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(2,'Parcela2');");
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(3,'Parcela3');");
				getStatement().executeUpdate("INSERT INTO maestra_parcela values(6,'Parcela6');");

				getStatement().executeUpdate("INSERT INTO maestra_abono values(1,'Abono1');");
				getStatement().executeUpdate("INSERT INTO maestra_abono values(2,'Abono2');");
				getStatement().executeUpdate("INSERT INTO maestra_abono values(3,'Abono3');");
				getStatement().executeUpdate("INSERT INTO maestra_abono values(9,'Abono9');");

				getStatement().executeUpdate("INSERT INTO principal values(1,2,3,3,'2011-05-11 13:06:57.062');");
				getStatement().executeUpdate("INSERT INTO principal values(3,2,2,3,'2011-05-14 13:06:57.062');");
				getStatement().executeUpdate("INSERT INTO principal values(1,2,4,3,'2011-05-12 13:06:57.062');");

				
				
			//Foreign Keys
				getStatement().executeUpdate("ALTER TABLE principal ADD FOREIGN KEY (act) REFERENCES maestra_actividades(id);");
				getStatement().executeUpdate("ALTER TABLE principal ADD FOREIGN KEY (prod) REFERENCES maestra_productor(id);");
				getStatement().executeUpdate("ALTER TABLE principal ADD FOREIGN KEY (parc) REFERENCES maestra_parcela(id);");
				getStatement().executeUpdate("ALTER TABLE principal ADD FOREIGN KEY (abo) REFERENCES maestra_abono(id);");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
