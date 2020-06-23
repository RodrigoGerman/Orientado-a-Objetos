package vista.tablesgit.tableproyectgit;

import vista.tablesgit.TableGit;
import vista.tablesgit.TableModelGit;
import vista.tablesgit.celleditorgit.celleditorproyectgit.CellEditorProyectGit;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.util.Vector;

/**
	* La clase TableProyectGit es una clase final que hereda de la clase TableGit debido a que es un tabla en el fondo, 
	* se encarga de crear un tabla para visualiza los proyectos de un usuario de la aplicacion GIT-RGL.
	* @author German Lopez Rodrigo
	* @version 3/12/2016/Final 
*/
public final class TableProyectGit extends TableGit{
	/** * Arreglo de String que guarda las titulos de las columnas de la tabla.*/
	private final static String[] columnNames = {"Titulo","Propietario","Fecha de Edicion"};
	/** * Objeto de tipo JTabbedPane el cual almacena el panel de pestañas donde se esta trabajando.*/
	private JTabbedPane pane;

	/** * Constructor sin argumentos para contruir un TableProyectGit pero sin ningun comportamiento especifico.*/
	public TableProyectGit(){}

	/** * Constructor que recibe un Vector que representa los datos que contendra la tabla, el JTabbedPane donde se esta trabajando
	para añadir una nueva pestaña cuando presiona un proyecto de la tabla y un Color para asignar el color a la tabla.*/
	public TableProyectGit(Vector<Vector<Object>> data,JTabbedPane pane,Color color){
		super(data,columnNames,color);
		this.pane = pane;
		setCentrado(3);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorProyectGit(this.pane));
	}

	/** * Metodo para refrescar los datos de la tabla.*/
	public void refreshTable(Vector<Vector<Object>> datas){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		if(dm.getRowCount() > 0){
			for(int i = dm.getRowCount()-1;i>-1;i--){
				dm.removeRow(i);
			}
		}
		this.data = datas;
		for(int i=0;i<datas.size();i++){
			Vector<Object> proyect = datas.elementAt(i);
			Object[] datos = new Object[proyect.size()];
			for(int j=0;j<proyect.size();j++){
				datos[j] = proyect.elementAt(j);
				datos[j] = proyect.elementAt(j);
				datos[j] = proyect.elementAt(j);
			}
			dm.insertData(datos);
		}
		setModel(dm);
		setCentrado(3);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorProyectGit(this.pane));
	}

	/** * Metodo para añadir datos nuevos a la tabla.*/
	public void addTable(Object[] proyect){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		dm.insertData(proyect);
		setModel(dm);
		setCentrado(3);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorProyectGit(this.pane));
	}

	/** * Metodo para regresar el nombre del archivo seleccionado de la tabla.*/
	public String getTitleProyect(int row){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		return (String)getValueAt(row,0);
	}

	/** * Metodo para eliminar un proyecto de la tabla.*/
	public void delProyect(int row){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		dm.removeRow(row);
		setModel(dm);
		setCentrado(2);
	}
}