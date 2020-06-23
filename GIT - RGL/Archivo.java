package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.Serializable;

/**
    * La clase Archivo es una clase que implementa la interfaz Serializable, se encarga de poder expresar un Archivo y 
    * sus atributos que continene que son un titulo, una fecha de edicion, el nombre del proyecto al que pertenece
    * y si es posible editarlo.
    * @author German Lopez Rodrigo
    * @version 2/12/2016/Final 
*/

public class Archivo implements Serializable{
	/** * Variable long para determinar un version para serializar.*/
	private static final long serialVersionUID = 123456789L;
	/** * Variables tipo String que representan el titulo del archivo,nombre del proyecto al que pertenede y la fecha de edicion.*/
	private String title,name,date;
	/** * Variable tipo boolean statica para que se puede acceder a ella y se pueda validar si es editable el archivo.*/
	private static boolean editable = true;

	/** * Metodo Constructor sin argumentos.*/
	public Archivo(){}

	/** * Metodo Constructor que recibe el nombre del archivo.*/
	public Archivo(String title){
		this.title = title;
		setFecha();
	}

	/** * Metodo para asignar si es posible editar el archivo.*/
	public void setEditable(boolean editable){
		this.editable = editable;
	}

	/** * Metodo para calcular y asignar la fecha de la creacion del proyecto al crearse o modificarse.*/
	protected final void setFecha(){
		DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.date = fecha.format(new Date());
	}

	/** * Metodo para regresar el id del archivo.*/
	public int getId(){
		int name = (this.title).hashCode()%997;
		if(name < 0)
			name*=-1;
		return name;
	}

	/** * Metodo para regresar el titulo del archivo.*/
	public String getTitle(){
		return this.title;
	}

	/** * Metodo para regresar el estado de edicion del archivo.*/
	public Boolean getEditable(){
		return editable;
	}

	/** * Metodo para regresar la fecha de creacion o de modificacion*/
	public String getFecha(){
		return this.date;
	}
}