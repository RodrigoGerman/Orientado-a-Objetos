package modelo;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.Serializable;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;

/** *Clase DataUser es una clase que implementa Serializable debido a que hace serializable el mapa de usuarios de  la aplicacion,
permite agregar mas usuarios y puede validar si existe un nombre de usuario en el sistema.*/
public class DataUser implements Serializable{
	/* *Se crea un TreeMap para guardar los usuarios registrados de la aplicacion.*/
	private static TreeMap<Integer,Usuario> usuarios = new TreeMap<Integer,Usuario>();

	/** *Metodo para agregar un usuario al arbol, si existe no se agrega, recibe un objeto tipo Usuario.*/
	public void setTree(Usuario user){
		int pos = user.getId();
		usuarios.put(pos,user);
	}

	/** *Metodo que permite regresar un usuairo del sistema.*/
	public Usuario getTree(int key){
		return usuarios.get(key);
	}

	/** * Metodo que permite regresar los nombres de usuario del sistema.*/
	public ArrayList<String> getUsuarios(Usuario user){
		ArrayList<String> usernames = new ArrayList<String>();
		for(Map.Entry<Integer,Usuario> entry: usuarios.entrySet()){
			Usuario usuario = entry.getValue();
			if(!usuario.equals(user)){
				usernames.add(usuario.getUserName());
			}
		}
		return usernames;
	}

	/** *Metodo que permite validar si el nombre de usuario ya existe en el sistema.*/
	public boolean getexistsUsuario(String user){
		int name = (user).hashCode()%997;
		if(name < 0)
			name*=-1;
		return usuarios.containsKey(name);
	}

	/** *Metodo que permite validar si los datos ingresados coinciden con una cuenta en la aplicacion.*/
	public Usuario validUsuario(String username,String password){
		Usuario userk = new Usuario(username,password);
		Usuario user = (Usuario)usuarios.get(userk.getId());
		if(user != null){
			if(userk.equals(user))
				return user;
		}
		return null;
	}

	/** *Metodo que permite serializar el TreeMap de usuarios del sistema.*/
	public void serializalDatos(){
		try{
			FileOutputStream file = new FileOutputStream("./DAO/users.ser");
			ObjectOutputStream stream = new ObjectOutputStream(file);
			stream.writeObject(usuarios);
			stream.close();
		}catch(IOException e){
			existFile();
		}
	}

	/** *Metodo que permite deserializar el TreeMap de usuarios del sistema.*/
	public void deserializalDatos(){
		try{
			FileInputStream file = new FileInputStream("./DAO/users.ser");
			ObjectInputStream stream = new ObjectInputStream(file);
			usuarios = (TreeMap<Integer,Usuario>)stream.readObject();
			stream.close();
		}catch(Exception e){
			existFile();
		}
	}
	
	/** *Metodo que crea el .ser en caso de que el archivo no exista.*/
	public void existFile(){
		File archivo;
		try{
			if(!(new File("./DAO")).exists()){
				archivo = new File("./DAO");
				archivo.mkdir();
			}
			archivo = new File("./DAO/users.ser");
			if(!archivo.exists())
				serializalDatos();
		}catch(Exception ioe){
			System.out.println("\nError, al crear el archivo.\n");
		}
	}
}