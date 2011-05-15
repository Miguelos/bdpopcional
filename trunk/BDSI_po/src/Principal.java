import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


public class Principal extends JFrame{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private static Principal principal = null;
	
	/**
	 * Conexion con la base de datos
	 */
	private static Connection connection = null;
	private static Statement statement = null;
	
	/**
	 * Menú de la barra superior.
	 */
	private JMenuBar principalMenu = null;
	private JButton consultaMenu = null;
	private JButton insertarMenu = null;
	
	
	

	/**
	 * Método getInstance del patrón Singleton
	 * @return instancia única de la clase Principal
	 */
    public static Principal getInstance() {
    	if(principal == null) principal = new Principal();
        return principal;
    }	
	
    /**
     * Constructora privada (patrón singleton)
     */
	private Principal() {
		super();
		setLayout(new BorderLayout());
		setJMenuBar(getprincipalMenu());
		setSize(new Dimension(500, 400));
		setLocationRelativeTo(null);
		setVisible(true);
		setEnabled(true);
		
	}


	/**
	 * 
	 * @return
	 */
	public JMenuBar getprincipalMenu() {
		if(principalMenu == null){
			principalMenu = new JMenuBar();
			principalMenu.add(getconsultaMenu());
			principalMenu.add(getinsertarMenu());
		}
		return principalMenu;
	}
	
	/**
	 * 
	 * @return
	 */
	public JButton getconsultaMenu() {
		if(consultaMenu == null){
			consultaMenu = new JButton("Consulta");
			//Colocar el panel consulta en el centro del frame
			consultaMenu.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					setContentPane(PanelConsulta.getInstance());
				}
				
			});
		}
		return consultaMenu;
	}

	/**
	 * 
	 * @return
	 */
	public JButton getinsertarMenu() {
		if(insertarMenu == null){
			insertarMenu = new JButton("Insertar");
			//Colocar el panel insertar en el centro del frame
			insertarMenu.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					setContentPane(PanelInsertar.getInstance());

				}
				
			});
		}
		return insertarMenu;
	}

	
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		if(connection == null){
			//DriverManager.registerDriver(new Driver());
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "mysql121");
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
		try
		{	
			//Conectar con la base de datos
//			getConnection(); //esta linea no es necesaria.
//			getStatement();
	
			//Crear la interfaz
			Principal.getInstance();
			
			//Cerrar la conexión con la base de datos
//			getStatement().close();
//			getConnection().commit();
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("¡ERROR! \n" + e.getMessage());
		} 
	}
	
	/**
	 * El metodo "clone" es sobreescrito por el siguiente que arroja una excepción:
	 */
	public Object clone() throws CloneNotSupportedException {
	        throw new CloneNotSupportedException(); 
	}

}