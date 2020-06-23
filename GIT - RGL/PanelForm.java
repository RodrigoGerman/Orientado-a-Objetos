package vista.panelsgit.panelform;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JTextField;
import vista.panelsgit.PanelGit;

/**
	* La clase PanelForm es una clase que hereda de la clase PanelGit debido a que es un panel en el fondo, 
	* pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear panel con un comportamiento especifico
	* para la ingresion de datos.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class PanelForm extends PanelGit{

	/** * Metodo constructor que crear un PanelForm pero sin ninguna comportamiento ni estilo especifico.*/
	public PanelForm(){}

	/** * Metodo constructor que crear un PanelForm, recibe un distribucion de posiciones y un color para 
	colocar al panel.*/
	public PanelForm(LayoutManager posicion,Color color){
		super(posicion,color);
	}

	/** * Metodo para agregar un boton en una posicion especifica al panel.*/
	public void setButton(JButton button,int x,int y,int lon,int width){
		button.setBounds(x,y,lon,width);
		this.add(button);
	}

	/** * Metodo para agregar un campo de tenxto en una posicion especifica al panel.*/
	public void setTextField(JTextField campo,int x,int y,int lon,int width){
		campo.setBounds(x,y,lon,width);
		this.add(campo);
	}
}