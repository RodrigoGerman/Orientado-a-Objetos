package controlador;

import java.net.ServerSocket;
import java.net.Socket;
import modelo.DataUser;
import modelo.Usuario;
import modelo.Bitacoras;
import modelo.SocketServerGit;

/** *Clase ActualizarDataGit que hereda de Thread para que se puede comportar como hilo, se encargada de actualizar
los datos del sistema utilizando los metodos del objeto SocketServerGit, permitiendo que el hilo principal siga en la 
escucha de nuevas peticiones.*/
public class ActualizarDataGit extends Thread{

	SocketServerGit service = null;

	/** *Metodo constructor sin argumentos.*/
	public ActualizarDataGit(){}

	/** *Metodo constructor que recibe un socket el cual se asigna al objecto SocketServerGit.*/
	public ActualizarDataGit(Socket client){
		try{
			service = new SocketServerGit(client);
		}catch(Exception e){}
	}

	/** *Metodo run el cual efectuaran todos los hilos que sean ActualizarDataGit.*/
	public void run(){
		while(true){
			service.operaciones();
		}
	}
}