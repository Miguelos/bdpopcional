import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
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
    	if(panelInsertar == null){
    		panelInsertar = new PanelInsertar();
    	}
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
			insertarButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					insertar((String) getCodActividadBox().getSelectedItem().toString()
							/*...*/);
				}
				
			});		
		}
		return insertarButton;
	}


	/**
	 * http://www.chuidiang.com/java/mysql/resultset_jtable.php
	 * @param codActividad
	 */
	public void insertar(String codActividad/*...*/){
		try {
			/*
			 * Ejecutar la consulta sql
			 */
			String sent = "INSERT INTO principal VALUES ("+codActividad+" , "+codActividad + " ,"+ codActividad + ", "+codActividad+" , 20100502); ";
			System.out.println(sent);

			/*int i = */getStatement().executeUpdate(sent);

			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en el update: \n" + e.getMessage());
			System.out.println("Fallo en el update: \n" + e.getMessage());

		}
	}


}