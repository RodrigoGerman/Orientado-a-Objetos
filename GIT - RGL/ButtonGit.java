package vista.componentsgit.buttonsgit;

import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
	* La clase ButtonGit es una clase que hereda de la clase JButton debido a que es un boton en el fondo, pero tiene un
	* cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear un boton con un comportamiento especifico.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class ButtonGit extends JButton{

	/** *Metodo Constructor que crear un ButtonGit sin ningun comportamientos.*/
	public ButtonGit(){}

	/** *Metodo Constructor que recibe un texto el cual sera el texto del boton a crear y se le da un comportamiento en 
	especifico.*/
	public ButtonGit(String texto){
		super(texto);
		this.addKeyListener(new KeyEventButton());
	}

	/** * Clase interna KeyEventButton que hereda de KeyAdapter, esta clase se encarga de crear objetos que son eventos 
		* con un cierto comportamiento, que al presionar la tecla enter lo que realizara es tranferir el foco al siguiente componente.*/
	protected class KeyEventButton extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int caracter = e.getKeyCode();
			if(caracter == KeyEvent.VK_ENTER){
				doClick();
				if(e.getModifiers() > 0)
					transferFocusBackward();
				else
					transferFocus();
				e.consume();
			}
		}
	}
}