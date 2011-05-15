import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;


public class PanelConsulta extends PanelSQL{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static PanelConsulta panelConsulta = null;
	
	private JButton consultarButton = null;
	
	private JTable consultaTable = null;
	
	/**
	 * Método getInstance del patrón Singleton
	 * @return instancia única de la clase PanelConsulta
	 */
    public static PanelConsulta getInstance() {
    	if(panelConsulta == null){
    		panelConsulta = new PanelConsulta();
    	}
        return panelConsulta;
    }	
	
    /**
     * Constructora ...
     */
	private PanelConsulta(){
		super();
		getSeleccionPanel().add(getConsultarButton());


	}
	
	public JButton getConsultarButton() {
		if(consultarButton == null){
			consultarButton = new JButton("Consultar");
			//consultar((String) getCodActividad().getSelectedItem().toString()/*...*/);
		}
		return consultarButton;
	}

	public void setConsultaTable(JTable consultaTable) {
		this.consultaTable = consultaTable;
	}

	public JTable getConsultaTable() {
		return consultaTable;
	}

	/**
	 * http://www.chuidiang.com/java/mysql/resultset_jtable.php
	 * @param codActividad
	 */
	public void consultar(String codActividad/*...*/){
		try {
			/*
			 * Ejecutar la consulta sql
			 */
			ResultSet rs = (ResultSet) getStatement().executeQuery("select * from actividad where codActividad = " + codActividad);

			/*
			 * METER EL ResultSet en el JTable
			 * 	Para meter los datos en el JTable, usaremos un DefaultTableModel.
			 *  Para ello basta con instanciar el JTable de esta forma
			 */
			DefaultTableModel modelo = new DefaultTableModel();
			setConsultaTable(new JTable(modelo));
	
			/*
			 * Ahora sólo hay que rellenar el DefaultTableModel con los datos del ResultSet.
			 *  La forma "manual" de hacer esto es la siguiente
			 *  Creamos las columnas.
			 */
			modelo.addColumn("Cod_Actividad");
			modelo.addColumn("...");
			modelo.addColumn("Abono");
			
			// Bucle para cada resultado en la consulta
			while (rs.next()){
			   // Se crea un array que será una de las filas de la tabla.
			   Object [] fila = new Object[3]; // Hay tres columnas en la tabla

			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<3;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			
			getConsultaTable().setEnabled(true);
			getConsultaTable().setVisible(true);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fallo en la consulta: \n" + e.getMessage());

		}
	}

}
