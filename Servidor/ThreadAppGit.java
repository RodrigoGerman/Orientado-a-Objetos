package controlador;

import java.io.ObjectInputStream;
import java.net.Socket;
import modelo.SocketServerGit;
import modelo.Usuario;

/* *La clase ThreadAppGit que hereda de hilo y es la encargada de atender a un usuario en la ventana principal de la aplicacion
mientras que el hilo principal sigue en espera de conexiones*/
public class ThreadAppGit extends Thread{

	/* *Variable SocketServerGit que permite establecer la conexcion entre cliente y servidor.*/
	SocketServerGit service = null;
	/* *Variable tipo ObjectInputStream para recibir el usuario que esta interactuando con el servidor.*/
	ObjectInputStream entrada_object = null;

	/* *Metodo Constructor sin argumentos.*/
	public ThreadAppGit(){}

	/* *Metodo Constructor que recibe un Socket para asignarselo al SocketServerGit y con el establecer una conexion entre 
	cliente y el servidor.*/
	public ThreadAppGit(Socket client){
		try{
			service = new SocketServerGit(client);
			entrada_object = new ObjectInputStream(client.getInputStream());
			service.setUserActual((Usuario)entrada_object.readObject());
		}catch(Exception e){}
	}

	/* *Metodo run que contine un ciclo infinito para recibir las acciones que desea realizar el usuario.*/
	public void run(){
		while(true){
			service.operaciones();
		}
	}
}