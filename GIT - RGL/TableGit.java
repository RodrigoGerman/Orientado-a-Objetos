package vista.tablesgit;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
	* La clase abstracta TableGit es una clase que hereda de la clase JTable debido a que es un tabla en el fondo, 
	* pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear una table con un comportamiento especifico.
	* @author German Lopez Rodrigo
	* @version 2/12/2016/Final 
*/
public abstract class TableGit extends JTable{
	/** *Objeto de tipo Vector se encarga de almacenar los datos que componen la tabla.*/ 
	protected Vector<Vector<Object>> data;
	/** *Object de tipo TableColumn se encarga de decir a que columnas se haran que esten centradas.*/
	protected TableColumn column;

	/** * Metodo Constructor que crea una TableGit pero sin ningun comportamiento.*/ 
	public TableGit(){}

	/** * Metodo Constructor que crea una TableGit, recibe un Vector que representa los datos que contendra la tabla, un String[]
	que son los nombre de las columnas y un Color que se refiere al color que tendran las columnas de la tabla.*/
	public TableGit(Vector<Vector<Object>> data,String[] columnNames,Color color){
		TableModelGit dm = new TableModelGit(data,columnNames);
		setModel(dm);
		this.data = data;
		setBackground(color);
		setFillsViewportHeight(true);
		setRowSorter(new TableRowSorter<TableModelGit>(dm));
	}

	/** * Metodo que alinea las columnas de una tabla, recibe el indice de las columnas que quiren que se hagan centradas.*/
	protected final void setCentrado(int num_columnNames){
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0;i<num_columnNames;i++){
			column = getColumnModel().getColumn(i);
			column.setCellRenderer(center);
		}
	}

	/** * Metodo abstracto que refresca los datos que se encuentran en la tabla.*/
	public abstract void refreshTable(Vector<Vector<Object>> data);
}