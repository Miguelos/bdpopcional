import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
			//Crea panel con 2 filas y 6 columnaws
			seleccionPanel.setLayout(new GridLayout(2, 6));
			seleccionPanel.add(new JLabel("  Actividad:"));
			seleccionPanel.add(new JLabel("  Productor:"));
			seleccionPanel.add(new JLabel("  Parcela:"));
			seleccionPanel.add(new JLabel("  Fecha:"));
			seleccionPanel.add(new JLabel("  Abono:"));
			seleccionPanel.add(new JLabel("          "));
			seleccionPanel.add(getCodActividadBox());
			seleccionPanel.add(getProductorBox());
			seleccionPanel.add(getParcelaBox());
			seleccionPanel.add(getFechaBox());
			seleccionPanel.add(getCodAbonoBox());

		}
		return seleccionPanel;
	}

	public JComboBox getCodActividadBox() {
		if(codActividadBox == null){
			// Mostrar los codigos de actividad disponibles
			try {
				ResultSet rs = (ResultSet) getStatement().executeQuery("select id,descr from maestra_actividades;");
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				Object[] lista = new Object[rowCount+1];
				lista[1] = rs.getObject(1);
				int i = 2;
				while (rs.next()){
					   lista[i] = rs.getObject(1);
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
				ResultSet rs = (ResultSet) getStatement().executeQuery("select id,descr from maestra_productor;");
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				Object[] lista = new Object[rowCount+1];
				lista[1] = rs.getObject(1);
				int i = 2;
				while (rs.next()){
					   lista[i] = rs.getObject(1);
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
	
	public JComboBox getParcelaBox() {
		if(parcelaBox == null){
			try {
				ResultSet rs = (ResultSet) getStatement().executeQuery("select id,descr from maestra_parcela;");
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				Object[] lista = new Object[rowCount+1];
				lista[1] = rs.getObject(1);
				int i = 2;
				while (rs.next()){
					   lista[i] = rs.getObject(1);
					   i++;
				}
				rs.close();
				parcelaBox = new JComboBox(lista);	
			}catch(SQLException e){
				e.printStackTrace();
			}
				
		}
		return parcelaBox;
	}
	
	public JComboBox getCodAbonoBox() {
		if(codAbonoBox == null){
			// Mostrar los codigos de actividad disponibles
			try {
				ResultSet rs = (ResultSet) getStatement().executeQuery("select id,descr from maestra_abono;");
				rs.last();
				int rowCount = rs.getRow();
				rs.first();
				Object[] lista = new Object[rowCount+1];
				lista[1] = rs.getObject(1);
				int i = 2;
				while (rs.next()){
					   lista[i] = rs.getObject(1);
					   i++;
				}
				rs.close();
				codAbonoBox = new JComboBox(lista);	
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return codAbonoBox;
	}
	
	public JButton getFechaBox() {
		if(fechaBox == null){
			fechaBox = new JButton();
			fechaBox.setText("Fecha");
			fechaBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					getPanelCalendario();
					
				}
			});

		}
		return fechaBox;
	}
	
	protected void getPanelCalendario() {
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
	
	/**
	 * @return the calendario
	 */
	public JCalendar getCalendario() {
		return calendario;
	}


}
