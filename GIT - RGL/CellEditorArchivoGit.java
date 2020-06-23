package vista.tablesgit.celleditorgit.celleditorarchivogit;

import javax.swing.JTabbedPane;
import vista.tablesgit.celleditorgit.CellEditorGit;
import vista.panelsgit.PanelTabGit;
import controlador.PanelArchivo;
import java.awt.event.ActionEvent;

/**
	* La clase CellEditorArchivoGit es una clase que hereda de la clase CellEditorGit, se encarga de crear una pestaña
	* con un boton para cerrarla y con un panel especifico para el proyecto que se selecciono del JTabbedPane.
	* @author German Lopez Rodrigo
	* @version 6/12/2016/Final 
*/
public class CellEditorArchivoGit extends CellEditorGit{

	/** * Metodo Contructor sin argumentos para crear CellEditorProyectGit sin inicializar nada.*/
	public CellEditorArchivoGit(){}

	/** * Metodo Contructor que recibe el JTabbedPane en el que se agregara una nueva pestaña del proyecto que selecciono.*/
	public CellEditorArchivoGit(JTabbedPane pane){
		super(pane);
	}

	/** * Metodo sobrescrito de la clase superior CellEditorGit el cual agrega la pestaña al JTabbedPane si no existe.*/
	@Override
	public void actionPerformed(ActionEvent e){
		int cantidad = pane.getTabCount() - 1;
		boolean existe = false;
		for(int i=0;i<=cantidad;i++){
			if(pane.getTitleAt(i).equals(button.getText())){
				existe = true;
				break;
			}
		}
		if(!existe){
			pane.add(button.getText(),new PanelArchivo(button.getText()));
			pane.setTabComponentAt(++cantidad,new PanelTabGit(pane));
		}
	}
}