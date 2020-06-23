package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.TreeMap;

/**
    * La clase Proyecto es una clase que implementa la interfaz Serializable, se encarga de poder expresar un Proyecto y 
    * sus atributos que continene que son un mapa de Archivos y una lista de Colaboradores, tiene atributos tipo 
	* String para guardar su titulo, nombre del duelo y la fecha de modificacion.
    * @author German Lopez Rodrigo
    * @version 2/12/2016/Final 
*/

public class Proyecto implements Serializable{
		/** * Variable long para determinar un version para serializar.*/
	private static final long serialVersionUID = 123456789L;
	/** * TreeMap privado para guardar los Archivos que contine un proyecto.*/
	private TreeMap<Integer,Archivo> archivos = new TreeMap<Integer,Archivo>();
	/** * ArrayList privada para guardar los colaboradores que contiene un proyecto.*/
	private ArrayList<String> colaboradores = new ArrayList<String>();
	/** * Variables tipo String que representan el titulo del archivo,nombre del creador y la fecha de edicion.*/
	private String title,name,date;

	/** * Metodo Constructor sin argumentos.*/
	public Proyecto(){}

	/** * Metodo Constructor que recibe el titulo del proyecto y el usuario que lo creo.*/
	public Proyecto(String title,Usuario owner){
		setTitle(title);
		setName(owner);
		setFecha();
	}

	/** * Metodo para asignar el titulo del proyecto a crear.*/
	public void setTitle(String title){
		this.title = title;
	}

	/** * Metodo para regresar el id del proyecto.*/
	public int getId(){
		int name = (this.title).hashCode()%997;
		if(name < 0)
			name*=-1;
		return name;
	}

	/** * Metodo para asignar el nombre del dueño al proyecto a crear.*/
	public void setName(Usuario owner){
		this.name = owner.getUserName();
	}

	/** * Metodo para calcular y asignar la fecha de la creacion del proyecto al crearse o modificarse.*/
	protected final void setFecha(){
		DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.date = fecha.format(new Date());
	}

	/** * Metodo para regresar el titulo del proyecto.*/
	public String getTitle(){
		return this.title;
	}

	/** * Metodo para regresar el creador del proyecto.*/
	public String getOwner(){
		return this.name;
	}

	/** * Metodo para regresar la fecha de creacion o de modificacion del proyecto.*/
	public String getFecha(){
		return this.date;
	}

	/** * Metodo para añadir colaboradores al proyecto.*/
	public void setColaborador(String colaborador){
		colaboradores.add(colaborador);
	}

	/** * Metodo para eliminar colaborador del proyecto.*/
	public void delColaborador(String colaborador){
		colaboradores.remove(colaborador);
	}

	/** * Metodo para regresar la lista de Colaboradores.*/
	public ArrayList<String> getColaboradores(){
		return colaboradores;
	}

	/** * Metodo para añadir un nuevo archivo al proyecto.*/
	public void addArchivo(Archivo archivo){
		int pos = archivo.getId();
		archivos.put(pos,archivo);
	}

	/** * Metodo para sobrescribir un archivo existente.*/
	public void overwriteArchivo(Archivo archivo){
		int pos = archivo.getId();
		archivos.put(pos,archivo);
	}
 
 	/** * Metodo que regresa si existe el archivo.*/
	public boolean existe(Archivo archivo){
		return archivos.containsKey(archivo.getId());
	}

	/** * Metodo que regresa el archivo con la llave deseada.*/
	public Archivo getArchivo(int key){
		return archivos.get(key);
	}

	/** * Metodo que elimina el archivo con la llave deseada.*/
	public void delArchivo(int key){
		archivos.remove(key);
	}
 
 	/** * Metodo que regresa el TreeMap de los archivos que contiene el proyecto.*/
	public TreeMap<Integer,Archivo> getArchivos(){
		return archivos;
	}

	/** * Metodo para representar el estado del objeto, mostrando su nombre de usuario y su contraseña encriptada.*/
	@Override
	public String toString(){
		return this.title + "|" + this.name;
	}
}