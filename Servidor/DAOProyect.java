package modelo;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.util.TreeMap;
import java.util.Vector;
import java.util.Map;
import java.util.ArrayList;

/** *Clase DAOProyect para optener los proyectos y archivos respectivos a cada proyecto del usuario que esta en la 
aplicacion. */
public class DAOProyect{

	/** *Metodo que nos regresa un Vector que contiene otro Vector el cual contiene los datos: nombre,dueño y fecha 
	de un proyecto que le pertenezca al usuario,recibe un usuario.*/
	public Vector<Vector<Object>> getTableProyects(Usuario user){
		TreeMap<Integer,Proyecto> proyects = user.getProyectos();
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		for(Map.Entry<Integer,Proyecto> entry: proyects.entrySet()){
			Proyecto proyect = entry.getValue();
			Vector<Object> proyecto = new Vector<Object>();
			proyecto.addElement(proyect.getTitle());
			proyecto.addElement(proyect.getOwner());
			proyecto.addElement(proyect.getFecha());
			data.addElement(proyecto);
		}
		return data;
	}

	/** *Metodo que nos regresa un Vector que contiene otro Vector el cual contine el nombre,fecha y estado de 
	ediccion del archivo, recibe el nombre del proyecto.*/
	public Vector<Vector<Object>> getTableArchivos(Proyecto proyect){
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		if(proyect != null){
			TreeMap<Integer,Archivo> arch = proyect.getArchivos();
			for(Map.Entry<Integer,Archivo> entry: arch.entrySet()){
				Archivo archivo = entry.getValue();
				Vector<Object> proyecto = new Vector<Object>();
				proyecto.addElement(archivo.getTitle());
				proyecto.addElement(archivo.getFecha());
				proyecto.addElement(archivo.getEditable());
				data.addElement(proyecto);
			}
		}
		return data;
	}

}