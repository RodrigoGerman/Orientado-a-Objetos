package vista.exceptionsgit;

/** 
	* Clase ExceptionUser es una clase que hereda de Exception, la cual nos ayuda a crear nuestra propia
	* excepcion al momento de registrar un usuario.
	* @author German Lopez Rodrigo
	* @version 29/11/2016/Final
 */
import javax.swing.JOptionPane;

public class ExceptionUser extends Exception{

	/** * Metodo que envia el mensaje que el usuario no es valido.*/
	public ExceptionUser(){
		JOptionPane.showMessageDialog(null,"Usuario Invalido."
		,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/** * Metodo que envia el mensaje que se recibe cuando ocurre la excepcion.*/
	public ExceptionUser(String message){
		JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
	}
}