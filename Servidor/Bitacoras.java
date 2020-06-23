package modelo;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

///DAO_User
public class Bitacoras{
	/* *Se crea un hashmap para llevar el conteo de inicios de sesion de varios usuarios.*/
	private HashMap<String,Integer> order = new HashMap<String,Integer>();
	/* *Las variables tipo string es para guardar los datos del cliente que se logeo de forma incorrecta.*/
	private String usuario,password,date;
	/* *Variable tipo entero para llevar el conteo de inicios de sesion de los usuarios incorrectos.*/
	private int intentos;

	/* *Metodo para calcular y asignar la fecha del intento de inicio de sesion.*/
	private final void setFecha(){
		DateFormat fecha = new SimpleDateFormat("EEEE dd/MM/yyyy HH:mm:ss");
		this.date = fecha.format(new Date());
	}

	/* *Metodo para guardar el inicio de sesion correcto en la bitacora.*/
	public void setUserCorrect(Usuario user){
		setFecha();
		writeFile("../Servidor2/Bitacoras/CorrectLogin.txt",user);
	}

	/* *Metodo para guardar el inicio de sesion incorrecto en la bitacora.*/
	public void setUserIncorrect(String user, String pass){
		this.usuario = user;
		this.password = pass;
		setFecha();
		if(order.containsKey(user)){
			this.intentos = order.get(user) + 1;
			order.put(user,this.intentos);
		}	
		else{
			this.intentos = 1;
			order.put(user,1);
		}
		writeFile("../Servidor2/Bitacoras/IncorrectLogin.txt",null);
	}

	/* *Metodo para guardar en un archivo difrente las difrentes bitacoras que se generaron.*/
	public void writeFile(String file,Usuario user){
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(file,true));
			if(user != null)
				bw.write(user + "|" + this.date + "|\n");
			else
				bw.write(this.usuario + "|" + this.password + "|" + this.date + "|" + this.intentos + "|\n");
			bw.close();
		}catch(IOException ioe){
			existsFiles(user);
		}finally{
			try{
				if(bw != null)
					bw.close();
			}catch(Exception e){
				System.out.println("\nError: " + e.toString());
			}
		}
	}


	/* *Metodo para crear los archivos de las bitacoras en caso que no existan.*/
	public void existsFiles(Usuario user){
		File archivo;
		BufferedWriter bw = null;
		try{
			if(!(new File("../Servidor2/Bitacoras")).exists()){
				archivo = new File("../Servidor2/Bitacoras");
				archivo.mkdir();
			}
			archivo = new File("../Servidor2/Bitacoras/IncorrectLogin.txt");
			if(!archivo.exists())
				bw = new BufferedWriter(new FileWriter(archivo));
			archivo = new File("../Servidor2/Bitacoras/CorrectLogin.txt");
			if(!archivo.exists())
				bw = new BufferedWriter(new FileWriter(archivo));
		}catch(IOException ioe){
			System.out.println("\nError, al crear el archivo.\n");
		}finally{
			try{
				if(bw != null)
					bw.close();
			}catch(Exception e){
				System.out.println("\nError: " + e.toString());
			}
		}
	}
}