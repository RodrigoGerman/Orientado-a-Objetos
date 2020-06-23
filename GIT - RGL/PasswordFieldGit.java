package vista.componentsgit;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.BorderFactory;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

/**
	* La clase PasswordFieldGit es una clase que hereda de la clase JPasswordField debido a que es un campo de 
	* password en el fondo, pero tiene un cierto comportamiento para la aplicacion GIT - RGL, 
	* se encarga de crear un campo de contraseña con un comportamiento especifico.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class PasswordFieldGit extends JPasswordField implements FocusListener{
	/** * Variable privada tipo String  encargada de almacenar el texto que se le colocala de forma backgraund al campo.*/
	private String texto;

	/** * Metodo constructor que crear un PasswordFieldGit pero sin ninguna comportamiento ni estilo especifico.*/
	public PasswordFieldGit(){}

	/** *Metodo constructor que crea un PasswordFieldGit, recibe el texto del backgraund y el color del borde del campo y
	le da un cierto comportamiento al campo a crear.*/
	public PasswordFieldGit(String texto,Color color){
		super(texto);
		this.texto = texto;
		setForeground(Color.GRAY);
		setBorder(BorderFactory.createLineBorder(color,1));
		addFocusListener(this);
		addKeyListener(new KeyEventPass());
	}

	/** * Metodo sobrescrito de la clase FocusListener el cual limpia el campo cuando el cliente lo este seleccionando.*/
	@Override  
	public void focusGained(FocusEvent e){
		this.setText("");
		this.setForeground(Color.BLACK);
	}

	/** * Metodo sobrescrito de la clase FocusListener el cual coloca un texto al campo cuando el cliente no lo este seleccionando.*/
	@Override  
	public void focusLost(FocusEvent e){ 
		if(this.getPassword().length == 0){  
		    this.setText(this.texto);
		    this.setForeground(Color.GRAY); 
		}
	}

	/** * Clase interna KeyEventPass que hereda de KeyAdapter, esta clase se encarga de crear objetos que son eventos,
	con el comportamiento que al presionar caracteres especiales no los escribe, valida que no sea posible copiar,pegar ni cortar
	de los campos.*/
	protected class KeyEventPass extends KeyAdapter{

		@Override
		public void keyTyped(KeyEvent e){
			char caracter = e.getKeyChar();
			if(!Character.isLetterOrDigit(caracter))
				e.consume();
		}

		@Override
		public void keyPressed(KeyEvent e){
			int caracter = e.getKeyCode();
			if(e.isControlDown() && (caracter == KeyEvent.VK_C || caracter == KeyEvent.VK_V  || caracter == KeyEvent.VK_X)){
				e.consume();
			}

			if(caracter == KeyEvent.VK_ENTER){
				if(e.getModifiers() > 0)
					transferFocusBackward();
				else
					transferFocus();
				e.consume();
			}
		}
	}
}