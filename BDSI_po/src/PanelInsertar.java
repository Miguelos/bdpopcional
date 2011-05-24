import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JOptionPane;




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
			insertarButton.setBackground(Color.BLACK);
			insertarButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					java.sql.Timestamp ts = null; 
					if(getCalendario() != null) ts = new java.sql.Timestamp(getCalendario().getDate().getTime());
					insertar(getCodActividadBox().getSelectedItem(),
							getProductorBox().getSelectedItem(),
							getParcelaBox().getSelectedItem(),
							getCodAbonoBox().getSelectedItem(),
							ts
							);
				}
				
			});		
		}
		return insertarButton;
	}


	/**
	 * http://www.chuidiang.com/java/mysql/resultset_jtable.php
	 * @param act
	 * @param abo 
	 * @param parc 
	 * @param prod 
	 * @param timestamp 
	 */
	public void insertar(Object act, Object prod, Object parc, Object abo, Timestamp timestamp){
		try {
			/*
			 * Ejecutar la consulta sql
			 */			
			String sent = "INSERT INTO principal VALUES ("+act+" , "+prod+" ,"+parc+", "+abo+" , '"+timestamp.toString()+"'); ";

			/*int i = */getStatement().executeUpdate(sent);

			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Fallo en el update: \n" + e.getMessage());
		} catch (NullPointerException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Es necesario rellenar todos los campos.");
		}
	}


}