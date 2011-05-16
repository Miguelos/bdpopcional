import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.ResultSet;
//import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.freixas.jcalendar.JCalendar;

//import com.mysql.jdbc.Statement;


public class PanelSQL extends JPanel{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Statement statement = null;

	private JPanel seleccionPanel = null;
	private JComboBox codActividadBox = null;
	private JComboBox productorBox = null;
	private JButton fechaBox = null;
	private long fecha = 0; //tiempo trascurrido desde la epoca
	private JCalendar calendario = null;
	private JComboBox parcelaBox = null;
	private JComboBox codAbonoBox = null;
	
	
	/**
	 * Método getInstance del patrón Singleton
	 * @return instancia única de la clase PanelConsulta
	 */

	
    /**
     * Constructora ...
     */
	public  PanelSQL(){
		super();
		
		//getStatement();
		
		setLayout(new BorderLayout());
		add(getSeleccionPanel(), BorderLayout.NORTH);

		setVisible(true);
		setEnabled(true);
	}
	
	/**
	 * 
	 * @return
	 */
	public Statement getStatement() {
		try {
			if(statement == null){
					statement = Principal.getStatement();
			}
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param statement
	 */
	public void setStatement(Statement statement) {
		this.statement = statement;
	}
	
	/**
	 * Panel donde van los combo box
	 * @return
	 */
	public JPanel getSeleccionPanel() {
		if(seleccionPanel == null){
			seleccionPanel = new JPanel();
			seleccionPanel.setLayout(new FlowLayout());
			seleccionPanel.add(getCodActividadBox());
			seleccionPanel.add(getProductorBox());
			seleccionPanel.add(getFechaBox());
			seleccionPanel.add(getCodAbonoBox());
		}
		return seleccionPanel;
	}

	public JComboBox getCodActividadBox() {
		if(codActividadBox == null){
			// Mostrar los codigos de actividad disponibles
			try {
			ResultSet rs = (ResultSet) getStatement().executeQuery("select distinct id from maestra_actividades;");
			rs.last();
			int rowCount = rs.getRow();
			rs.first();
			Object[] lista = new Object[rowCount];
			lista[0] = rs.getObject(1);
			System.out.println(rowCount);
				//TODO obtener los strings del resultset rs
			int i = 1;
			while (rs.next()){
				   lista[i] = rs.getObject(1);
				   System.out.println(i);
				   i++;
				}
				rs.close();
			codActividadBox = new JComboBox(lista);	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return codActividadBox;
	}

	public JComboBox getProductorBox() {
		if(productorBox == null){
			try {
				ResultSet rs = (ResultSet) getStatement().executeQuery("select distinct id from maestra_productor;");
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				Object[] lista = new Object[rowCount];
				lista[0] = rs.getObject(1);
				System.out.println("ROWCOUNT"+rowCount);
					//TODO obtener los strings del resultset rs
				int i = 1;
				while (rs.next()){
					   lista[i] = rs.getObject(1);
					   System.out.println(i);
					   i++;
					}
					rs.close();
					productorBox = new JComboBox(lista);	
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return productorBox;
	}
	
	public JButton getFechaBox() {
		if(fechaBox == null){
			fechaBox = new JButton();
			fechaBox.setText("Fecha");
			fechaBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getCalendario();
					
				}
			});

		}
		return fechaBox;
	}
	
	protected void getCalendario() {
		calendario = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME,false);
		final JDialog calendarioDialog = new JDialog();
		final JPanel calendarioPanel = new JPanel();
		calendarioPanel.setLayout(new BorderLayout());
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getFechaBox().setText(calendario.getCalendar().get(Calendar.YEAR)+"-"+
						(1+calendario.getCalendar().get(Calendar.MONTH))+"-"+
						calendario.getCalendar().get(Calendar.DAY_OF_MONTH));
				fecha = calendario.getCalendar().getTime().getTime();
				System.out.println(fecha);
				calendarioDialog.setEnabled(false);		
				calendarioDialog.setVisible(false);

			}
		});
		calendarioPanel.add(calendario, BorderLayout.CENTER);
		calendarioPanel.add(okButton, BorderLayout.SOUTH);
		calendarioPanel.setEnabled(true);
		calendarioPanel.setVisible(true);
		calendarioDialog.setContentPane(calendarioPanel);
		calendarioDialog.setLocationRelativeTo(null);
		calendarioDialog.setSize(500, 400);
		calendarioDialog.setEnabled(true);
		calendarioDialog.setVisible(true);
		

	}

	public JComboBox getParcelaBox() {
		if(parcelaBox == null){
			Object[] lista = {"123","321","213"};;
			// Mostrar los codigos de actividad disponibles
//			try {
	//			ResultSet rs = (ResultSet) getStatement().executeQuery(/*TODO obtener todos los cod actividad*/"");
				//TODO obtener los strings del resultset rs
				
//				rs.close();
				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			parcelaBox = new JComboBox(lista);

		}
		return parcelaBox;
	}
	
	public JComboBox getCodAbonoBox() {
		if(codAbonoBox == null){
			Object[] lista = {"123","321","213"};;
			// Mostrar los codigos de actividad disponibles
//			try {
	//			ResultSet rs = (ResultSet) getStatement().executeQuery(/*TODO obtener todos los cod actividad*/"");
				//TODO obtener los strings del resultset rs
				
//				rs.close();
				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			codAbonoBox = new JComboBox(lista);
		}
		return codAbonoBox;
	}
	

}
