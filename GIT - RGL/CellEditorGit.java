package vista.tablesgit.celleditorgit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.Color;

/**
	* La clase CellEditorGit es una clase abstracta que hereda de la clase DefaultCellEditor
	* se encarga de crear CellEditor especifico para la aplicacion GIT-RGL, lo que realiza es agregar en la 
	* columna 0 de los datos los hace botones para que generen nuevas pestanas en el JTabbedPane donde se
	* esta trabajando.
	* @author German Lopez Rodrigo
	* @version 6/12/2016/Final 
*/
public abstract class CellEditorGit extends DefaultCellEditor implements ActionListener{
	/** * Objecto de tipo JButton el cual es el boton que se agrega en cada columna 0.*/
	protected JButton button;
	/** * Variable tipo String para colocar el texto del boton de la columna 0.*/
	protected String label;
	/** * Objecto de tipo JTabbedPane donde se almacena el JTabbedPane donde se esta trabajando.*/
	protected JTabbedPane pane = new JTabbedPane();

	/** * Metodo Constructor que crea un CellEditorGit pero sin un comportamiento en especifico.*/
	public CellEditorGit(){
		super(new JCheckBox());
	}

	/** * Metodo Constructor que crea un CellEditorGit, recibe el JTabbedPane donde se esta trabajando.*/
	public CellEditorGit(JTabbedPane pane){
		super(new JCheckBox());
		this.pane = pane;
		button = new JButton();
		button.setContentAreaFilled(false);
		button.setBackground(new Color(227,227,227));
		button.addActionListener(this);
	}

	/** * Metodo sobrescrito de la interfaz ActionListener, se deja abstracto debido que no se le puede definir aun
	un comportamiento.*/
	@Override
	public abstract void actionPerformed(ActionEvent e);

	/** * Metodo sobrescrito de la clase DefaultCellEditor el cual regresa en vez de texto un button propio.*/
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column){
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		return button;
	}

	/** * Metodo sobrescrito de la clase DefaultCellEditor el cual regresa el valor que se encuentra en la columna.*/
	@Override
	public Object getCellEditorValue(){
		return label;
	}
}