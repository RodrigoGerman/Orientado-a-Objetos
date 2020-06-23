package controlador;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import modelo.DataUser;
import modelo.Bitacoras;
import modelo.Usuario;
/**Clase ServerLoginGit que hereda de hilo para que se puede comportar como hilo y se la encargada de atender a un
	usuario mientras que el hilo principal sigue en espera de conexiones*/
public class ServerLoginGit extends Thread{
	/* *Variable de tipo DataUser, que se encarga de llevar el registro de usuarios registrados.*/
	private DataUser dao_user = new DataUser();
	/** *Variable de tipo Socket, para poder efectuar la conexion entre cliente y servidor del programa.*/
	private Socket service = null;
	/** *Variable de tipo ObjectOutputStream, para poder enviar objetos al cliente.*/
	private ObjectOutputStream salida_object = null;
	/** *Variable de tipo ObjectInputStream, para poder recibir objetos del cliente.*/
	private ObjectInputStream entrada_object = null;
	/** *Variable de tipo DataInputStream, para poder recibir datos del cliente.*/
	private DataInputStream entrada_data = null;
	/** *Variable de tipo DataOutputStream, para poder enviar datos al cliente.*/
	private DataOutputStream salida_data = null;
	/** *Variable de tipo Bitacoras, que se encarga de hacer un registro de los inicios de sesion correctos como incorrectos.*/
	private Bitacoras bitacora = new Bitacoras();
	/** *Variable de tipo Usuario, que corresponde al usuario que esta operando la aplicacion.*/
	private Usuario usuario;

	/** *Metodo Constructor sin argumentos.*/
	public ServerLoginGit(){}

	/** *Metodo Constructor que recibe un Socket y se le asgigna a SocketServerGit.*/
	public ServerLoginGit(Socket client){
		this.service = client;
	}

	/** *Metodo run que realiza la validacion del login.*/
	public void run(){
		try{
			entrada_data = new DataInputStream(service.getInputStream());
			dao_user.deserializalDatos();
			String user = entrada_data.readUTF();
			String pass = entrada_data.readUTF();
			this.usuario = dao_user.validUsuario(user,pass);
			salida_object = new ObjectOutputStream(service.getOutputStream());
			if(usuario != null){
				bitacora.setUserCorrect(usuario);
				salida_object.writeObject(this.usuario);
			}
			else{
				bitacora.setUserIncorrect(user,pass);
				salida_object.writeObject(this.usuario);
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(entrada_data != null) entrada_data.close();
				if(salida_object != null) salida_object.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
}