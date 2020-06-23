package vista.tablesgit;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
	* La clase TableModelGit es una clase que hereda de la clase AbstractTableModel debido a que la clase TableModelGit es
	* un modelo en especifico para las tablas de la aplicacion GIT - RGL, se encarga de crear un modelo para las tablas.
	* @author German Lopez Rodrigo
	* @version 2/12/2016/Final 
*/
public class TableModelGit extends AbstractTableModel{
	/** * Arreglo de tipo String[] que almacena el nombre de las columnas de la tabla.*/
	protected String[] columnNames;
	/** * Objeto Vector de Vector<Object> que almacena los datos que contendra la tabla.*/
	protected Vector<Vector<Object>> data;

	/** * Metodo Contructor que crear un TableModelGit pero sin ningun dato.*/
	public TableModelGit(){}

	/** * Metodo Contructor que crea un TableModelGit, recibe un Vector<Vector<Object>> que se refiere a los datos de la tabla,
	recibe una Arreglo de String[] que almacena los nombres de las columnas.*/
	public TableModelGit(Vector<Vector<Object>> data,String[] columnNames){
		this.data = data;
		this.columnNames = columnNames;
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, regresa el titulo de una columna.*/
	@Override
	public String getColumnName(int colm){
		return columnNames[colm];
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, regresa la cantidad de columnas que tiene la tabla.*/
	@Override
	public int getColumnCount(){
		return columnNames.length;
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, regresa la cantidad de filas de la tabla.*/
	@Override
	public int getRowCount(){
		return data.size();
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, regresa true si la columna es diferente de 0
	eso quiere decir que si es editable esa columna .*/
	@Override
	public boolean isCellEditable(int row,int column){
		if(0 != column)
			return false;
		return true;
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica,regresa Objeto que se encuentra en esa fila y
	es columna.*/
	@Override
	public Object getValueAt(int row,int col){
		if(data.size() > 0)
			return ((Vector<Object>)data.get(row)).get(col);
		return 0;
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, regresa la Clase  que tiene una columna en especifico.*/
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}

	/** * Metodo que se sobrescribe, se le asigna un accion mas especifica, se encarga de asignar los valores a la tabla.*/
	@Override
	public void setValueAt(Object value,int row,int col){
		if(data.size() > 0){
			((Vector<Object>)data.get(row)).setElementAt(value,col);
			fireTableCellUpdated(row,col);
		}
	}

	/** * Metodo que sirve para añadir un nuevo Objeto a la tabla.*/
	public void insertData(Object[] values){
		if(existe(values[0]) == -1){
			data.add(new Vector<Object>());
			for(int i =0;i<values.length;i++){
				((Vector<Object>)data.get(data.size()-1)).add(values[i]);
			}
			fireTableDataChanged();
		}
	}

	/** * Metodo que sirve para sobrescribir un Objeto que se encuentra en la tabla.*/
	public void overwriteData(Object[] values){
		int e = existe(values[0]);
		for(int i =0;i<values.length;i++){
			((Vector<Object>)data.get(e)).add(i,values[i]);
		}
		fireTableDataChanged();
	}

	/** * Metodo que sirve para saber si un Objeto ya existe en la tabla-*/
	public int existe(Object title){
		int pos = -1;
		for(int i = 0;i<getRowCount();i++){
			boolean e = ((Vector<Object>)data.get(i)).contains(title);
			if(e)
				return i;
		}
		return pos;
	}

	/** * Metodo que sirve para eliminar una columna.*/
	public void removeRow(int row){
		data.removeElementAt(row);
		fireTableDataChanged();
	}
}