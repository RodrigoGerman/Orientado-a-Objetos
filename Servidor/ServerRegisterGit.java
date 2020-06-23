package controlador;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import modelo.SocketServerGit;
import modelo.Usuario;
import modelo.DataUser;
import java.io.File;

/* *Clase ServerRegisterGit que hereda de hilo para que se puede comportar como hilo y se la encargada de atender a un
	usuario mientras que el hilo principal sigue en espera de conexiones*/
public class ServerRegisterGit extends Thread{
	/** *Variable de tipo DataInputStream, para poder recibir datos del cliente.*/
	private DataInputStream entrada_data = null;
	/** *Variable de tipo DataOutputStream, para poder enviar datos al cliente.*/
	private DataOutputStream salida_data = null;
	/** *Variable de tipo ObjectInputStream, para poder recibir objetos del cliente.*/
	private ObjectInputStream entrada_object = null;
	/* *Variable de tipo DataUser, que se encarga de llevar el registro de usuarios registrados.*/
	private DataUser dao_user = new DataUser();
	/* */
	private Usuario usuario;
	/* *Varible tipo Socket para establecer la comunicacion entre el servidor con el cliente.*/
	Socket service = null;
 
 	/* *Metodo Constructor sin Argumentos.*/
	public ServerRegisterGit(){}

	/* *Metodo Constructor que recibe un Socket y se lo asigna a SocketServerGit.*/
	public ServerRegisterGit(Socket cliente){
		this.service = cliente;
	}
 
 	/* *Metodo run que realiza el procedimiento de registro de un usuario.*/
	public void run(){
		try{
			dao_user.deserializalDatos();
			entrada_data = new DataInputStream(service.getInputStream());
			String user = entrada_data.readUTF();
			salida_data = new DataOutputStream(service.getOutputStream());
			if(!dao_user.getexistsUsuario(user)){
				salida_data.writeBoolean(true);
				entrada_object = new ObjectInputStream(service.getInputStream());
				usuario = (Usuario)entrada_object.readObject();
				dao_user.setTree(usuario);
				dao_user.serializalDatos();
				createUser(usuario.getUserName());
			}
			else
				salida_data.writeBoolean(false);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(entrada_object != null) entrada_object.close();
				if(salida_data != null) salida_data.close();		
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	/** * Metodo para crear la carpeta personal del nuevo cliente.*/
	private final void createUser(String name){
		File archivo;
		if(!(new File("../Servidor2/"+name)).exists()){
			archivo = new File("../Servidor2/"+name);
			archivo.mkdir();
		}
	}
}