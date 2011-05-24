import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
	
	private JPanel tablaPanel = null;
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
			consultarButton.setBackground(Color.BLACK);
			consultarButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					java.sql.Timestamp ts = null; 
					if(getCalendario() != null) ts = new java.sql.Timestamp(getCalendario().getDate().getTime());
					consultar(getCodActividadBox().getSelectedItem(),
							getProductorBox().getSelectedItem(),
							getParcelaBox().getSelectedItem(),
							getCodAbonoBox().getSelectedItem(),
							ts
							);
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
			consultaTable.setFocusable(false);
			consultaTable.setCellSelectionEnabled(false);
			consultaTable.setDragEnabled(false);
			consultaTable.setEnabled(true);
			consultaTable.setVisible(true);
			repaint();
		}
		return consultaTable;
	}

	/**
	 * http://www.chuidiang.com/java/mysql/resultset_jtable.php
	 * @param object
	 */
	public void consultar(Object act, Object prod, Object parc, Object abo, Timestamp timestamp ){
		try {
			/*
			 * Ejecutar la consulta sql
			 */
			boolean anterior = false;
			String sent = "select * from principal where ";
			if(act != null){
				sent += "act="+act.toString();
				anterior = true; 
			}
			if(prod != null){
				if(anterior) sent += " and ";
				sent += "prod="+prod.toString();
				anterior = true;
			}
			if(parc != null){
				if(anterior) sent += " and ";
				sent += "parc="+parc.toString();				
				anterior = true;
			}
			if(abo != null){
				if(anterior) sent += " and ";
				sent += "abo="+abo.toString();
				anterior = true;
			}
			if(timestamp != null){
				if(anterior) sent += " AND ";
				sent += "fecha='"+timestamp.toString()+"'";
				anterior = true;
			}
			sent += ";";

			if(!anterior){
				//No hay ninguna condición en el where:
				sent = "select * from principal;";
			}

			System.out.println(sent);
			
			
			ResultSet rs = (ResultSet) getStatement().executeQuery(sent);

			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			/*
			 * METER EL ResultSet en el JTable
			 * 	Para meter los datos en el JTable, usaremos un DefaultTableModel.
			 *  Para ello basta con instanciar el JTable de esta forma
			 */
			
			int rowCount = rsmd.getColumnCount();
			
			Vector<Object> cabecera = new Vector<Object>();
			
			Vector<Vector<Object>> datos = new Vector<Vector<Object>>();
			
			for(int i = 1; i<=rowCount; i++) {
				cabecera.addElement(rsmd.getColumnName(i));
			}
	
			datos.add(cabecera);

			// Bucle para cada resultado en la consulta
			while (rs.next()){
			   // Se crea un array que será una de las filas de la tabla.
				Vector<Object> fila = new Vector<Object>();

			   // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
			   for (int i = 1; i<= rowCount; i++)
			      fila.addElement(rs.getObject(i)); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

			   // Se añade al modelo la fila completa.
				datos.addElement(fila);
			}
			

			modelo = new DefaultTableModel(datos,cabecera);
			setConsultaTable(modelo);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en la consulta: \n" + e.getMessage());
		}
	}

}
