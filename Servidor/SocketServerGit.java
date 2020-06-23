package modelo;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;

import java.net.ServerSocket;
import java.net.Socket;

/** * La clase SocketServerGit, es una clase que simula un servidor para la aplicacion con operaciones determinadas, para
el buen funcionamiento de la aplicacion.*/
public class SocketServerGit{
	/** * Variable de tipo DataUser, que se encarga de llevar el registro de usuarios registrados.*/
	private DataUser dao_user = new DataUser();
	/** * Variable de tipo DAOProyect, que se encarga de llevar el control de los proyectos de un determinado usuario.*/
	private DAOProyect dao_proyect = new DAOProyect();
	/** * Variable de tipo Bitacoras, que se encarga de hacer un registro de los inicios de sesion correctos como
	incorrectos.*/
	private Bitacoras bitacora = new Bitacoras();
	/** * Variable de tipo Socket, para poder efectuar la conexion entre cliente y servidor del programa.*/
	private Socket service = null;
	/** * Variable de tipo ObjectOutputStream, para poder enviar objetos al cliente.*/
	private ObjectOutputStream salida_object = null;
	/** * Variable de tipo ObjectInputStream, para poder recibir objetos del cliente.*/
	private ObjectInputStream entrada_object = null;
	/** * Variable de tipo DataInputStream, para poder recibir datos del cliente.*/
	private DataInputStream entrada_data = null;
	/** * Variable de tipo DataOutputStream, para poder enviar datos al cliente.*/
	private DataOutputStream salida_data = null;
	/** * Variable de tipo Usuario, que corresponde al usuario que esta operando la aplicacion.*/
	private static Usuario usuario;

	/** * Metodo constructor sin argumentos.*/
	public SocketServerGit(){}

	/** * Metodo constructor para crear el servidor propio de la aplicacion con operaciones ya establecidas.*/
	public SocketServerGit(Socket server){
		this.service = server;
	}

	/** * Metodo del servidor encargado de asignar el usuario que esta trabajando en la maquina actual.*/
	public void setUserActual(Usuario user){
		this.usuario = user;
	}

	/** * Metodo del servidor encargado de regresar al cliente una lista de los nombres de usuario.*/
	public void getUsers(){
		try{
			salida_object = new ObjectOutputStream(service.getOutputStream());
			salida_object.writeObject(dao_user.getUsuarios(this.usuario));
		}catch(Exception e){}
	}


	/** * Metodo del servidor para desconectarlo.*/
	public void desconectar(){
		try{
			if(service != null)
				service.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/** * Metodo del servidor que se encarga de agregar un nuevo proyecto al usuario y crear la carpeta en el servidor.*/
	public void addProyects(){
		try{
			String user = this.usuario.getUserName() + "/";
			entrada_object = new ObjectInputStream(service.getInputStream());
			salida_data = new DataOutputStream(service.getOutputStream());
			existsCarpetas();
			File archivo;
			Proyecto proyecto = (Proyecto)entrada_object.readObject();
			String title = proyecto.getTitle();
			if(!(new File("../Servidor2/"+user+title)).exists()){
				archivo = new File("../Servidor2/"+user+title);
				archivo.mkdir();
				this.usuario.addProyect(proyecto);
				dao_user.setTree(usuario);
				dao_user.serializalDatos();
				salida_data.writeBoolean(false);
			}
			else{
				salida_data.writeBoolean(true);
			}
		}catch(Exception ioe){
			System.out.println("\nError, al crear el archivo.\n");
		}
	}

	/** * Metodo del servidor que se encarga de agregar un nuevo archivo al proyecto y crear el archivo en el servidor.*/
	public void addArchivo(){
		try{
			String user = this.usuario.getUserName() + "/";
			entrada_data = new DataInputStream(service.getInputStream());
			salida_data = new DataOutputStream(service.getOutputStream());
			String title_proyect = entrada_data.readUTF() + "/";
			String title_archivo = entrada_data.readUTF();
			int tamaño = entrada_data.readInt();
			int key = (title_proyect).hashCode()%997;
			if(key < 0)
				key*=-1;
			if(!((new File("../Servidor2/"+user+title_proyect+title_archivo)).exists())){
				salida_data.writeBoolean(false);
				FileOutputStream fos = new FileOutputStream("../Servidor2/"+user+title_proyect+title_archivo);
				BufferedOutputStream out = new BufferedOutputStream(fos);
				BufferedInputStream in = new BufferedInputStream(service.getInputStream());
				byte[] buffer = new byte[tamaño];
				for(int i = 0; i < buffer.length; i++){
					buffer[i] = (byte)in.read(); 
				}
				out.write(buffer); 
				out.flush(); 
				in.close();
				out.close();
				Proyecto proyecto = this.usuario.getProyect(key);
				proyecto.addArchivo(new Archivo(title_archivo));
				this.usuario.addProyect(proyecto);
				dao_user.setTree(usuario);
				dao_user.serializalDatos();
			}
			else{
				salida_data.writeBoolean(true);
			}
        
		}catch(Exception e){}
	}


	/** * Metodo del servidor que se encarga de sobreescribir un archivo ya existente.*/
	public void overWriteArchivo(){
		try{
			String user = this.usuario.getUserName() + "/";
			entrada_data = new DataInputStream(service.getInputStream());
			salida_data = new DataOutputStream(service.getOutputStream());
			String title_proyect = entrada_data.readUTF() + "/";
			String title_archivo = entrada_data.readUTF();
			int tamaño = entrada_data.readInt();
			int key = (title_proyect).hashCode()%997;
			if(key < 0)
				key*=-1;
			FileOutputStream fos = new FileOutputStream("../Servidor2/"+user+title_proyect+title_archivo,true);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(service.getInputStream());
			byte[] buffer = new byte[tamaño];
			for(int i = 0; i < buffer.length; i++){
				buffer[i] = (byte)in.read(); 
			}
			out.write(buffer); 
			out.flush(); 
			in.close();
			out.close();
		}catch(IOException e){}
	}

	/** * Metodo del servidor que se encarga de eliminar un proyecto del usuario y eliminarla del servidor.*/
	public void delProyects(){
		try{
			String user = this.usuario.getUserName() + "/";
			entrada_data = new DataInputStream(service.getInputStream());
			salida_data = new DataOutputStream(service.getOutputStream());
			File archivo;
			String title = entrada_data.readUTF();
			int key = (title).hashCode()%997;
			if(key < 0)
				key*=-1;
			if((new File("../Servidor2/"+user+title)).exists()){
				archivo = new File("../Servidor2/"+user+title);
				archivo.delete();
				this.usuario.delProyect(key);
				dao_user.setTree(usuario);
				dao_user.serializalDatos();
				salida_data.writeBoolean(true);
			}
			else{
				salida_data.writeBoolean(false);
			}
		}catch(Exception ioe){
			System.out.println("\nError, al crear el archivo.\n");
		}
	}

	/** * Metodo para saber si existen las carpetas y en caso contrario crearlas.*/
	public void existsCarpetas(){
		String user = this.usuario.getUserName();
		File archivo;
		if(!(new File("../Servidor2")).exists()){
			archivo = new File("../Servidor2");
			archivo.mkdir();
		}
		if(!(new File("../Servidor2/"+user)).exists()){
			archivo = new File("../Servidor2/"+user);
			archivo.mkdir();
		}
	}


	/** * Metodo para regresar el proyecto con el que se esta trabajando.*/
	public void getProyects(){
		try{
			entrada_data = new DataInputStream(service.getInputStream());
			String title = entrada_data.readUTF();
			int key = (title).hashCode()%997;
			if(key < 0)
				key*=-1;
			Proyecto proyecto = this.usuario.getProyect(key);
			salida_object = new ObjectOutputStream(service.getOutputStream());
			salida_object.writeObject(proyecto);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** * Metodo del servidor que actualiza los proyectos que posee un usuario, regresa un Vector con todos los
	proyectos que posee el usuario*/
	public void refreshData(){
		try{
			salida_object = new ObjectOutputStream(service.getOutputStream());
			salida_object.writeObject(dao_proyect.getTableProyects(this.usuario));
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	/** * Metodo del servidor que actualiza un proyecto que posee un usuario en el servidor.*/
	public void updateProyects(){
		try{
			entrada_object = new ObjectInputStream(service.getInputStream());
			this.usuario.addProyect((Proyecto)entrada_object.readObject());
			dao_user.setTree(usuario);
			dao_user.serializalDatos();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** * Metodo del servidor que esta al pendiente del nombre de un proyecto y que regresa al cliente un
	Vector con todos los archivos que consta el proyecto mandado.*/
	public void refreshArchivos(){
		try{
			entrada_data = new DataInputStream(service.getInputStream());
			String title = entrada_data.readUTF();
			int key = (title).hashCode()%997;
			if(key < 0)
				key*=-1;
			Proyecto proyecto = this.usuario.getProyect(key);
			salida_object = new ObjectOutputStream(service.getOutputStream());
			salida_object.writeObject(dao_proyect.getTableArchivos(proyecto));
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	/** * Metodo del servidor que recibe la accion que quiere recibir el usuario en la aplicacion, no regresa nada y no recibe
	nada debido aque esta en la espera de una cadena del cliente.*/
	public void operaciones(){
		String title ="";
		try{
			entrada_data = new DataInputStream(service.getInputStream());
			title = entrada_data.readUTF();
			switch(title){
				case "addProyect":
					addProyects();
					break;
				case "delProyect":
					delProyects();
					break;
				case "updateProyect":
					updateProyects();
					break;
				case "getProyect":
					getProyects();
					break;
				case "addArchivo":
					addArchivo();
					break;
				case "overWriteArchivo":
					overWriteArchivo();
					break;	
				case "Perfil":

					break;
				case "refreshDatas":
					refreshData();
					break;
				case "refreshArchivos":
					refreshArchivos();
					break;
				case "Users":
					getUsers();
					break;
				default:
					break;
			}
		}catch(Exception e){
		}
	}
}