import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.util.Vector;


public class PanelConsulta extends PanelSQL{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static PanelConsulta panelConsulta = null;
	
	private JButton consultarButton = null;
	
	private JTable consultaTable = null;
	private JPanel tablaPanel = null;

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
		add(getConsultaPanel(), BorderLayout.CENTER);

	}
	
	private JPanel getConsultaPanel() {
		if(tablaPanel == null){
			tablaPanel = new JPanel();
			tablaPanel.setBounds(50,100,300,100);
			tablaPanel.setBackground(Color.gray);
			tablaPanel.add(getConsultaTable());
		}
		return tablaPanel;
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

	public void setConsultaTable(DefaultTableModel modelo) {
		getConsultaTable().removeAll();
		getConsultaTable().setModel(modelo);
		getConsultaTable().repaint();
	}

	public JTable getConsultaTable() {
		if(consultaTable == null){
			consultaTable = new JTable();
			consultaTable.setShowHorizontalLines(true);
			consultaTable.setRowSelectionAllowed(true);
			consultaTable.setColumnSelectionAllowed(true);
			consultaTable.setGridColor(Color.gray);
			//consultaTable.
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
			//ResultSet rs = (ResultSet) getStatement().executeQuery("select * from principal ");

			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			/*
			 * METER EL ResultSet en el JTable
			 * 	Para meter los datos en el JTable, usaremos un DefaultTableModel.
			 *  Para ello basta con instanciar el JTable de esta forma
			 */
			
			int rowCount = rsmd.getColumnCount();
			
			Vector cabecera = new Vector();
			
			Vector<Vector<Object>> datos = new Vector();

			for(int i = 1; i<=rowCount; i++) {
				cabecera.addElement(rsmd.getColumnName(i));
			}
			
			datos.add(cabecera);//TODO mal esto se supone que no hace falta
		

			Vector fila = new Vector();
/*			fila[0] = rs.getObject(1);
			fila[1] = rs.getObject(2);
			fila[2] = rs.getObject(3);
			fila[3] = rs.getObject(4);
			fila[4] = rs.getObject(5).toString();
			datos.addElement(fila);
	*/		
			/*
			 * Ahora sólo hay que rellenar el DefaultTableModel con los datos del ResultSet.
			 *  La forma "manual" de hacer esto es la siguiente
			 *  Creamos las columnas.
			 */

			// Bucle para cada resultado en la consulta
			while (rs.next()){
			   // Se crea un array que será una de las filas de la tabla.

			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i = 1; i<= rowCount; i++)
			      fila.addElement(rs.getObject(i)); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

			   // Se añade al modelo la fila completa.
				datos.addElement(fila);
			}
			
			
			modelo = new DefaultTableModel(datos,cabecera);
			setConsultaTable(modelo);
			System.out.println(getConsultaTable().toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en la consulta: \n" + e.getMessage());
		}
	}

}
