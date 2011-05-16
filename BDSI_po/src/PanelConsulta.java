import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private DefaultTableModel modelo = null;
	
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
			consultarButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					consultar((String) getCodActividadBox().getSelectedItem().toString()
							/*...*/);
				}
				
			});
		}
		return consultarButton;
	}

	public void setConsultaTable(JTable consultaTable) {
		this.consultaTable = consultaTable;
	}

	public JTable getConsultaTable() {
		if(consultaTable == null){
			consultaTable = new JTable();
			consultaTable.setEnabled(true);
			consultaTable.setVisible(true);
			repaint();
		}
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

			ResultSet rs = (ResultSet) getStatement().executeQuery("select * from principal where act="+codActividad+";");

			/*
			 * METER EL ResultSet en el JTable
			 * 	Para meter los datos en el JTable, usaremos un DefaultTableModel.
			 *  Para ello basta con instanciar el JTable de esta forma
			 */
			modelo = new DefaultTableModel();
			setConsultaTable(new JTable(modelo));
			modelo.addColumn("cod_Actividad");
			modelo.addColumn("Productor");
			modelo.addColumn("Parcela");
			modelo.addColumn("Abono");
			modelo.addColumn("Fecha");
			rs.last();
			int rowCount = rs.getRow();
			System.out.println("ROWCOUNTnuevo"+rowCount);
			rs.first();
			Object[] fila = new Object[5];
			fila[0] = rs.getObject(1);
			fila[1] = rs.getObject(2);
			fila[2] = rs.getObject(3);
			fila[3] = rs.getObject(4);
			fila[4] = rs.getObject(5).toString();
			modelo.addRow(fila);
			/*
			 * Ahora sólo hay que rellenar el DefaultTableModel con los datos del ResultSet.
			 *  La forma "manual" de hacer esto es la siguiente
			 *  Creamos las columnas.
			 */

			
			// Bucle para cada resultado en la consulta
			while (rs.next()){
			   // Se crea un array que será una de las filas de la tabla.

			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i=0;i<5;i++)
			      fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

			   // Se añade al modelo la fila completa.
			   modelo.addRow(fila);
			}
			
			getConsultaTable().removeAll();
			getConsultaTable().setModel(modelo);
			add(getConsultaTable(), BorderLayout.CENTER);
			System.out.println(getConsultaTable().toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fallo en la consulta: \n" + e.getMessage());

		}
	}

}
