package vista.exceptionsgit;

/** 
	* Clase LogInException es una clase que hereda de Exception, la cual nos ayuda a crear nuestra propia
	* excepcion al momento de que el ingreso a nuestra aplicacion supero el numero de intentos maximo.
	* Utiliza los metodos LogInException para regresar en el excepcion el mensaje que nosotros queramos.
	* @author German Lopez Rodrigo
	* @version 29/10/2016/Final
 */
import javax.swing.JOptionPane;

public class LogInException extends Exception{

	/** * Metodo que envia el mensaje fallo al inicio de sesion si aun quedan intentos.*/
	public LogInException(){
		JOptionPane.showMessageDialog(null,"Fallo al iniciar seccion."
		,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	/** * Metodo que envia el mensaje que se recibe cuando se terminan los intentos.*/
	public LogInException(String message){
		JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
	}
}