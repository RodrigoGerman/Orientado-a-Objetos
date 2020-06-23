package controlador;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import modelo.Usuario;
import modelo.Proyecto;
import vista.exceptionsgit.ExceptionUser;
import java.util.ArrayList;

/** 
	* La clase SocketClientGit es la encargada de establecer una conexion entre el usuario que opera el sistema con el servidor,
	* consta de metodos que son las acciones que puede realizar el usuario dentro de la aplicacion.
	* @author German Lopez Rodrigo
    * @version 8/12/2016/Final
*/
public class SocketClientGit extends Socket{
	/** * Variable final y estatica que establece el puerto de conexion entre el usuario y el servidor. */
	static final int PORT = 5432;
	/** * Variable de tipo ObjectOutputStream, para poder enviar objetos al servidor.*/
	ObjectOutputStream enviar_object = null;
	/** * Variable de tipo ObjectInputStream, para poder recibir objetos del servidor.*/
	ObjectInputStream recibir_object = null;
	/** * Variable de tipo DataInputStream, para poder recibir datos del servidor.*/
	DataInputStream recibir_data = null;
	/** * Variable de tipo DataOutputStream, para poder enviar datos al servidor.*/
	DataOutputStream enviar_data = null;

	/** * Constructor sin argumentos.*/
	public SocketClientGit(){}

	/** * Contructor que recibe una direccion ip para establecer la conexion entre el cliente y el servidor.*/
	public SocketClientGit(String ip,int port) throws IOException{
		super(ip,port);
	}

	/** * Metodo para desconectar la conexion entre el cliente y el servidor.*/
	public void desconectar(){
		try{
			if(this != null)
				this.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/** * Metodo para asignar al servidor el usuario que entro a la aplicacion*/
	public void setUser(Usuario user){
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("App");
			enviar_object = new ObjectOutputStream(this.getOutputStream());
			enviar_object.writeObject(user);
		}catch(IOException e){}
	}

	/** * Metodo que regresa nombre los usuarios registrados del sistema.*/
	public ArrayList<String> getUsers(){
		ArrayList<String> usuarios = new ArrayList<String>();
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("Users");
			recibir_object = new ObjectInputStream(this.getInputStream());
			usuarios = (ArrayList<String>)recibir_object.readObject();
		}catch(Exception e){}
		return usuarios;
	}

	/** * Metodo para regresar el proyecto que coincide con la estrin recibida.*/
	public Proyecto getProyecto(String proyecto){
		Proyecto proyect = null;
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("getProyect");
			enviar_data.writeUTF(proyecto);
			recibir_object = new ObjectInputStream(this.getInputStream());
			proyect = (Proyecto)recibir_object.readObject();
		}catch(Exception e){}
		return proyect;
	}

	/** * Metodo para conectarse al servidor y validar que los datos ingresados corresponden con una cuenta.*/
	public Usuario validLogin(String user,String pass){
		Usuario usuario = null;
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("Login");
			enviar_data.writeUTF(user);
			enviar_data.writeUTF(pass);
			recibir_object = new ObjectInputStream(this.getInputStream());
			usuario = (Usuario)recibir_object.readObject();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(enviar_data != null) enviar_data.close();
				if(recibir_object != null) recibir_object.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return usuario;
	}

	/** * Metodo para conectarse al servidor y poder registrar el usuario ingresado y almacenarlo en la base de datos.*/
	public void registerUser(Usuario usuario) throws ExceptionUser{
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("Register");
			enviar_data.writeUTF(usuario.getUserName());
			recibir_data = new DataInputStream(this.getInputStream());
			if(!recibir_data.readBoolean())
				throw new ExceptionUser("Error. \nEl nombre de usuario ya se encuentra registrado, por favor intente con otro.");
			else{
				enviar_object = new ObjectOutputStream(this.getOutputStream());
				enviar_object.writeObject(usuario);
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally{
			try{
				if(enviar_data != null) enviar_data.close();
				if(enviar_object != null) enviar_object.close();
				if(recibir_data != null) recibir_data.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	/** * Metodo para conectarse al servidor y regresar la vista grafica de los proyectos que le pertencen a un usuario.*/
	public Vector<Vector<Object>> refreshTableDatas(){
		Vector<Vector<Object>> datos = null;
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("refreshDatas");
			recibir_object = new ObjectInputStream(this.getInputStream());
			datos = (Vector<Vector<Object>>)recibir_object.readObject();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return datos;
	}

	/** * Metodo para conectarse al servidor y regresar la vista grafica de los archivos que le pertencen a un proyecto.*/
	public Vector<Vector<Object>> refreshArchivosDatas(String proyecto){
		Vector<Vector<Object>> datos = null;
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("refreshArchivos");
			enviar_data.writeUTF(proyecto);
			recibir_object = new ObjectInputStream(this.getInputStream());
			datos = (Vector<Vector<Object>>)recibir_object.readObject();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return datos;
	}


	/** * Metodo para conectarse al servidor y añadir un nuevo archivo a un proyecto, regresa una excepcion si ya existe un archivo con ese identificador.*/
	public void addArchivo(File archivo,String proyecto) throws ExceptionUser{
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			recibir_data = new DataInputStream(this.getInputStream());
			enviar_data.writeUTF("addArchivo");
			int tamañoArchivo = (int)archivo.length();
			enviar_data.writeUTF(proyecto);
			enviar_data.writeUTF(archivo.getName());
			enviar_data.writeInt(tamañoArchivo);
			if(recibir_data.readBoolean())
				throw new ExceptionUser("\nError archivo ya existe.");
			else{
				FileInputStream fis = new FileInputStream(archivo.getName());
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(this.getOutputStream());
				byte[] buffer = new byte[tamañoArchivo];
				bis.read(buffer); 
				for( int i = 0; i < buffer.length; i++ ){
					bos.write(buffer[i]); 
				} 
				bis.close();
				bos.close();
			}
		}catch(IOException e){}
	}


	/** * Metodo para conectarse al servidor y actualizar el archivo.*/
	public void updateArchivo(File archivo,String proyecto){
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("overWriteArchivo");
			int tamañoArchivo = (int)archivo.length();
			enviar_data.writeUTF(proyecto);
			enviar_data.writeUTF(archivo.getName());
			enviar_data.writeInt(tamañoArchivo);
			FileInputStream fis = new FileInputStream(archivo.getName());
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(this.getOutputStream());
			byte[] buffer = new byte[tamañoArchivo];
			bis.read(buffer); 
			for( int i = 0; i < buffer.length; i++ ){
				bos.write(buffer[i]); 
			} 
			bis.close();
			bos.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** * Metodo para conectarse al servidor y añadir un nuevo proyecto, regresa una excepcion si ya existe un proyecto con ese identificador.*/
	public void addProyect(Proyecto proyecto) throws ExceptionUser{
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("addProyect");
			enviar_object = new ObjectOutputStream(this.getOutputStream());
			enviar_object.writeObject(proyecto);
			recibir_data = new DataInputStream(this.getInputStream());
			if(recibir_data.readBoolean())
				throw new ExceptionUser("Error. \nTiene un proyecto con ese titulo, por favor intente con otro.");
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** * Metodo para conectarse al servidor yeliminar proyecto, regresa una excepcion si no se puede elimnar el proyecto.*/
	public void delProyect(String proyecto) throws ExceptionUser{
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("delProyect");
			enviar_data.writeUTF(proyecto);
			recibir_data = new DataInputStream(this.getInputStream());
			if(!recibir_data.readBoolean())
				throw new ExceptionUser("Error. \nLo sentimos pero no se pudo eliminar el proyecto, intentelo de nuevo.");
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/** * Metodo para conectarse al servidor y actualozar un proyecto, regresa una excepcion si ya existe un proyecto con ese identificador.*/
	public void updateProyect(Proyecto proyecto){
		try{
			enviar_data = new DataOutputStream(this.getOutputStream());
			enviar_data.writeUTF("updateProyect");
			enviar_object = new ObjectOutputStream(this.getOutputStream());
			enviar_object.writeObject(proyecto);
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}