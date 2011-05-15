import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class PanelInsertar extends PanelSQL{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static PanelInsertar panelInsertar = null;
	
	private JButton insertarButton = null;
	
	/**
	 * Método getInstance del patrón Singleton
	 * @return instancia única de la clase PanelInsertar
	 */
    public static PanelInsertar getInstance() {
    	if(panelInsertar == null) panelInsertar = new PanelInsertar();
        return panelInsertar;
    }	
	
    /**
     * Constructora ...
     */
	private PanelInsertar(){
		super();
		getSeleccionPanel().add(getInsertarButton());
		
	}
	
	public JButton getInsertarButton() {
		if(insertarButton == null){
			insertarButton = new JButton("Insertar");
//			insertar((String) getCodActividad().getSelectedItem().toString()/*...*/);
		}
		return insertarButton;
	}


	/**
	 * http://www.chuidiang.com/java/mysql/resultset_jtable.php
	 * @param codActividad
	 */
	public void insertar(String codActividad/*...*/){
//		try {
			/*
			 * Ejecutar la consulta sql
			 */
	//		/*int i = */getStatement().executeUpdate(
	//					"INSERT INTO actividad VALUES ('" + codActividad + "' )"
	//					);

			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Fallo en el update: \n" + e.getMessage());

//		}
	}


}
