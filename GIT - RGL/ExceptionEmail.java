package vista.exceptionsgit;

/** 
	* Clase ExceptionEmail es una clase que hereda de Exception, la cual nos ayuda a crear nuestra propia
	* excepcion al momento de registrar un email.
	* @author German Lopez Rodrigo
	* @version 29/11/2016/Final
 */
import javax.swing.JOptionPane;

public class ExceptionEmail extends Exception{

	/** *Metodo que envia el mensaje que el email no es valido.*/
	public ExceptionEmail(){
		JOptionPane.showMessageDialog(null,"Email Invalido."
		,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/** *Metodo que envia el mensaje que se recibe cuando ocurre la excepcion.*/
	public ExceptionEmail(String message){
		JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
	}
}
