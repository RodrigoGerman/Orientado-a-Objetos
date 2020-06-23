package modelo;

import javax.swing.ImageIcon;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.StringBuffer;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TreeMap;
import vista.exceptionsgit.ExceptionEmail;
import vista.exceptionsgit.ExceptionPassword;

/**
    * La clase Usuario es una clase que implementa la interfaz Serializable, se encarga de poder expresar un usuario del
    * sistema, tine un TreeMap de proyectos donde el puede ser el dueño o otro usuario puede ser dueño del proyecto,posee 
    * atributos que guardan los datos personales del cliente.
    * @author German Lopez Rodrigo
    * @version 2/12/2016/Final 
*/
public class Usuario implements Serializable{
	/** * Variable long para determinar un version para serializar.*/
	private static final long serialVersionUID = 123456789L;
	/** * Variables tipo String para guardar los datos personales del usuario.*/
	private String user,pass,email,nacimiento,name,apellido;
	/** * Variable tipo ImageIcon para guardar la imagen de perfil.*/
	private ImageIcon foto;
	/** * Variables tipo int para almacenar la edad y el id del usuario.*/
	private int edad,id;
	/** * Variable tipo char para almacenar el sexo del usuario.*/
	private char sexo;
	/** * Se crea un TreeMap para almacenar los proyectos que posee el usuario.*/
	private TreeMap<Integer,Proyecto> proyectos = new TreeMap<Integer,Proyecto>();

	/** * Metodo constructor sin argumentos.*/
	public Usuario(){}

	/** * Metodo constructor para inicializar todas las variables.*/
	public Usuario(String user,String pass,String nombre,String apellido,String email) throws ExceptionEmail,ExceptionPassword{
		//usuario
		setUserName(user);
		//email
		setEmail(email);
		//password
		setPassword(pass);
		//nombre
		setNombre(nombre);
		//apellido
		setApellido(apellido);
		//hashcode
		hashCode();
	}

	/** * Metodo para asignar el nombre del usuario.*/
	public void setUserName(String user){
		this.user = user;
	}

	/** * Metodo para asignar la contraseña del usuario lanza un excepcion del tipo ExceptionPassword la cual valida que 
	la contraseña debe de ser de una longitud minima de 8 y solo puede contener letras y numeros.*/
	public void setPassword(String pass) throws ExceptionPassword{
		if(pass.length() >= 8 && pass.matches("([a-zA-Z0-9ñ])+")){
			this.pass = getCifrar(pass);
		}
		else{
			throw new ExceptionPassword("Error. \nLa contraseña debe tener una longitud minima de 8 caracteres"
				+ ",\npor favor introduzca una nueva con este requisito.");
		}
	}

	/** *Metodo que solicita el email  del cliente y lo asigna, valida que tenga un @ , un dominio y una terminacion valida,
	lanza una excepcion del tipo  ExceptionEmail si los el email no es valido.*/
	public void setEmail(String email) throws ExceptionEmail{
		Pattern mail = Pattern.compile("^[a-zA-Z0-9_%-ñ]+@[a-zA-Z]+[\\.[a-zA-Z]+]*(\\.[A-Za-z]{2,})$");
		if((mail.matcher(email).matches()))
			this.email = email;
		else{
			throw new ExceptionEmail("Error. \nEl email no es valido, por favor introduzca uno valido.");
		}
	}

	/** * Metodo para asignar el nombre del usuario.*/
	public void setNombre(String name){
		this.name = name;
	}

	/** * Metodo para asignar el apellido del usuario.*/
	public void setApellido(String apellido){
		this.apellido = apellido;
	}

	/** * Metodo para asignar la imagen de perfil del usuario.*/
	public void setIcon(ImageIcon foto){
		this.foto = foto;
	}

	/** * Metodo para asignar el sexo del usuario.*/
	public void setSexo(char sexo){
		this.sexo = sexo;
	}

	/** * Metodo para asignar la edad del usuario.*/
	public void setEdad(int edad){
		this.edad = edad;
	}

	/** * Metodo para asignar la fecha de nacimiento del usuario.*/
	public void setNacimiento(String nacimiento){
		this.nacimiento = nacimiento;
	}

	/** * Metodo para generar un identificador numerico unico para cada usuario.*/
	@Override
	public int hashCode(){
		int name = (this.user).hashCode()%997;
		byte[] ids_user = (this.pass).getBytes();
		int id=0;
		for(byte b:ids_user){
			Byte id_user = new Byte(b);
			id += id_user.intValue();
		}
		id = name+id;
		if(id < 0)
			id*=-1;
		this.id = id;
		return id;
	}

	/** * Metodo que regresa el id completo del Usuario.*/
	public int getIdUser(){
		return this.id;
	}

	/** * Metodo que permite regresa el nombre de usuario.*/
	public String getUserName(){
		return this.user;
	}

	/** * Metodo que permite regresar el id del nombre de usuario.*/
	public int getId(){
		int name = (this.user).hashCode()%997;
		if(name < 0)
			name*=-1;
		return name;
	}

	/** * Metodo privado que regresa un String la cual es la cadena encriptada que se recibe.*/
	private String getCifrar(String sincifrar){
		StringBuffer hexString = new StringBuffer();
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	    	md.update(sincifrar.getBytes());
			byte[] hash = md.digest();

		   	for(byte b:hash)
		    	hexString.append(Integer.toHexString((0xF & b)));

		}catch(NoSuchAlgorithmException e){
			 System.err.println("I'm sorry, but SHA-256 is not a valid message digest algorithm");
		}finally{
			return hexString.toString();
		}
   	}

   	/** * Metodo para saber si dos usuarios son iguales, el cual compara sus ids.*/
	@Override
	public boolean equals(final Object object){
		if(this == object)
			return true;

		if(!(object instanceof Usuario))
			return false;

		final Usuario usuario = (Usuario)object;
		
		return (this.id == usuario.getIdUser());
	}

	/** * Metodo para representar el estado del objeto, mostrando su nombre de usuario y su contraseña encriptada.*/
	public String toString(){
		return this.user + "|" + this.pass;
	}

	/** * Metodo para añadir un proyecto al TreeMap , si existe no se agrega.*/
	public void addProyect(Proyecto proyect){
		int pos = proyect.getId();
		if(!proyectos.containsKey(pos)){
			proyectos.put(pos,proyect);
		}
	}

	/** * Metodo para optener un Proyecto atravez de su llave.*/
	public Proyecto getProyect(int key){
		return proyectos.get(key);
	}

	/** * Metodo para eliminar un Proyecto atravez de su llave.*/
	public void delProyect(int key){
		proyectos.remove(key);
	}

	/** * Metodo que regresa el TreeMap de los proyectos que posee un usuario.*/
	public TreeMap<Integer,Proyecto> getProyectos(){
		return proyectos;
	}
}