package vista.tablesgit.tablearchivogit;

import vista.tablesgit.TableGit;
import vista.tablesgit.TableModelGit;
import vista.tablesgit.celleditorgit.celleditorarchivogit.CellEditorArchivoGit;
import javax.swing.JTabbedPane;
import java.awt.Color;
import java.util.Vector;

/**
	* La clase TableArchivosGit es una clase final que hereda de la clase TableGit debido a que es un tabla en el fondo, 
	* se encarga de crear un tabla para visualiza los archivos que contiene un proyecto de la aplicacion GIT-RGL.
	* @author German Lopez Rodrigo
	* @version 3/12/2016/Final 
*/
public final class TableArchivosGit extends TableGit{
	/** * Arreglo de String que guarda las titulos de las columnas de la tabla.*/
	private static String[] columnNames = {"Titulo","Ultima Actualizacion","Estado Edicion"};
	/** * Objeto de tipo JTabbedPane el cual almacena el panel de pestañas donde se esta trabajando.*/
	private JTabbedPane pane;

	/** * Constructor sin argumentos para contruir un TableArchivosGit pero sin ningun comportamiento especifico.*/
	public TableArchivosGit(){}

	/** * Constructor que recibe un Vector que representa los datos que contendra la tabla, el JTabbedPane donde se esta trabajando
	para añadir una nueva pestaña cuando presiona un archivo de la tabla y un Color para asignar el color a la tabla.*/
	public TableArchivosGit(Vector<Vector<Object>> datos,JTabbedPane pane,Color color){
		super(datos,columnNames,color);
		this.pane = pane;
		setCentrado(2);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorArchivoGit(this.pane));
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
		setCentrado(2);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorArchivoGit(this.pane));
	}

	/** * Metodo para sobrescribir un archivo de la tabla.*/
	public void overWriteArchivo(Object[] archivo){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		dm.overwriteData(archivo);
		setModel(dm);
		setCentrado(2);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorArchivoGit(this.pane));
	}

	/** * Metodo para eliminar un archivo de la tabla.*/
	public void delArchivo(int colm){
		TableModelGit dm = new TableModelGit(this.data,this.columnNames);
		dm.removeRow(colm);
		setModel(dm);
		setCentrado(2);
		column = getColumnModel().getColumn(0);
		column.setCellEditor(new CellEditorArchivoGit(this.pane));
	}
}