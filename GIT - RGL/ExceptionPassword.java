package vista.exceptionsgit;

/** 
	* Clase ExceptionPassword es una clase que hereda de Exception, la cual nos ayuda a crear nuestra propia
	* excepcion al momento de validar una contraseña.
	* @author German Lopez Rodrigo
	* @version 29/11/2016/Final
 */
import javax.swing.JOptionPane;

public class ExceptionPassword extends Exception{

	/** * Metodo que envia el mensaje de que la contraseña es invalida.*/
	public ExceptionPassword(){
		JOptionPane.showMessageDialog(null,"Contraseña Invalida"
		,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/** * Metodo que envia el mensaje que se recibe cuando ocurre la excepcion.*/
	public ExceptionPassword(String message){
		JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
	}
}