package vista.panelsgit.panelform;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.JPasswordField;

/**
	* La clase PanelLogin es una clase que hereda de la clase PanelForm debido a que es un panel en el fondo, 
	* pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear panel con un comportamiento especifico
	* para la ingresion de datos e ingresion de contraseñas.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public final class PanelLogin extends PanelForm{

	/** * Metodo constructor que crear un PanelForm pero sin ninguna comportamiento ni estilo especifico.*/
	public PanelLogin(){}

	/** * Metodo constructor que crear un PanelLogin, recibe un distribucion de posiciones y un color para 
	colocar al panel.*/
	public PanelLogin(LayoutManager posicion,Color colors){
		super(posicion,colors);
	}

	/** * Metodo para agregar un campo de contraseña en una posicion especifica al panel.*/
	public void setPasswordField(JPasswordField campo,int x,int y,int lon,int width){
		campo.setBounds(x,y,lon,width);
		this.add(campo);
	}
}