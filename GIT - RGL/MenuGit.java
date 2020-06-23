package vista.componentsgit;

import javax.swing.JMenu;
import javax.swing.ImageIcon;

/**
    * La clase MenuGit es una clase que hereda de la clase JMenu debido a que es menu en el fondo, 
    * pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear menu
    * con un comportamiento especifico.
    * @author German Lopez Rodrigo
    * @version 2/12/2016/Final 
*/
public class MenuGit extends JMenu{

	/** * Metodo Constructor que crear un Menu sin ningun estilo o elementos en el.*/
	public MenuGit(){}

	/** * Metodo Constructor que crea un Menu, recibe un texto para colocarselo.*/
	public MenuGit(String texto){
		super(texto);
	}

	/** *Metodo Constructor que crea un Menu, recibe un texto para colocarselo y una ruta de la imagen que se le quiere agregar.*/
	public MenuGit(String texto,String icono){
		super(texto);
		ImageIcon im1 = new ImageIcon(icono);
   		setIcon(im1);
	}
}