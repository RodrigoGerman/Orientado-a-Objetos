package vista.windowsgit;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JList;
import vista.panelsgit.panelform.PanelForm;
import vista.panelsgit.PanelSearchList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.componentsgit.TextFieldGit;
import vista.componentsgit.buttonsgit.ButtonGit;
import java.util.ArrayList;
import modelo.Proyecto;
import modelo.Usuario;
import controlador.SocketClientGit;
import vista.exceptionsgit.ExceptionUser;

/**
	* La clase WindowProyect es una clase que hereda de la clase JDialog debido a que es un ventana de 
	* la aplicacion GIT - RGL que debe depender de otra para que exista, que se encarga del proceso de registro de un nuevo
	* proyecto conectandose al servidor para guardar el proyecto en la base de datos.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class WindowProyect extends JDialog implements ActionListener{
	/** *Objecto privado tipo TextFieldGit la cual tiene una validacion para un campo de nombres.*/
	private TextFieldGit campo_title;
	/** * Objectos privados tipo ButtonGit los cuales tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
	/** * Variable tipo string para guardar el titulo del proyecto nuevo a crear.*/
	private String title = "";
	/** * Objecto tipo PanelSearch para mostar los colaboradores y el nombre del proyecto a agregar.*/
	private PanelSearchList busqueda;
	/** *Variable tipo Usuario, la cual guarda la referencia del usuario actual del sistema.*/
	private Usuario user;
	/** * Variable tipo Proyecto, la cual guarda el proyecto que se acaba de crear.*/
	private Proyecto proyecto = null;
	/** *Variable tipo SocketClientGit, la cual proporciona metodo para establecer la conexion entre cliente y servidor.*/
	private SocketClientGit servidor = null;

	/** * Metodo Constructor que crea un ventana emergente que depende de otra sin ningun componente en su interior.*/
	public WindowProyect(){}

	/** * Metodo Constructor que crea un ventana, recibe la ventana de la que depende y una variable booleana que especifica el modelo.*/
	public WindowProyect(JFrame parent,boolean modal,Usuario user){
		super(parent,"Git RGL - Add Proyect",modal);

		this.user = user;
		this.servidor = WindowGitRGL.servidor;

		Color color = new Color(43,114,172);
		PanelForm proyect = new PanelForm(null,new Color(227,227,227));
		proyect.setLogo(227,10,90,95);
		proyect.setLabel("Title:",color,150,120,64,20);
		proyect.setImage("./Logos/usuario.png",150,146,16,16);
		campo_title = new TextFieldGit(" Title",(byte)1,color);
		proyect.setTextField(campo_title,170,145,230,20);

		ArrayList<String> colaboradores = servidor.getUsers();

		busqueda = new PanelSearchList("Añadir Colaboradores","Colaboradores",colaboradores,new ArrayList<String>());
		busqueda.setBounds(50,200,450,250);
		proyect.add(busqueda);

		button1 = new ButtonGit("Agregar");
		proyect.setButton(button1,150,480,100,25);
		button1.addActionListener(this);

		button2 = new ButtonGit("Cancelar");
		proyect.setButton(button2,280,480,120,25);
		button2.addActionListener(this);

		getContentPane().add(proyect);
		setResizable(false);
		setSize(550,550);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** * Metodo sobrescrito de la interfaz ActionListener, el cual se encarga de asignar una tarea especifica a los dos 
	ButtonGit que debe contener la ventana.*/
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button1){
			if(camposVacios()){
				title =	campo_title.getText();
				try{
					proyecto = new Proyecto(title,user);
					setColocaboradores();
					servidor.addProyect(proyecto);
					dispose();
				}catch(ExceptionUser i){
				}catch(Exception i){
					JOptionPane.showMessageDialog(null,"Error. \nError al intentar conectarse al servidor"
					+ ",\npor favor intentalo de nuevo.","Error - Git RGL",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if(e.getSource() == button2){
			dispose();
		}
	}

	/** * Metodo para compobar que los campos no esten vacios.*/
 	protected boolean camposVacios(){
		if((campo_title.getText()).equals(" Title")){
			JOptionPane.showMessageDialog(null,"Error. Ingrese un nombre para el proyecto por favor."
				,"Error - Add Proyect Git RGL",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
 	}

 	/** * Metodo para asignar los colaboradores que tendra este proyecto.*/
 	private void setColocaboradores(){
 		ArrayList<String> colaboradores = busqueda.getColaboradores();
 		if(colaboradores!=null){
 			for(String col : colaboradores){
 				this.proyecto.setColaborador(col);
 			}
 		}
 	}

 	/** * Metodo para regresar el proyecto que se creo.*/
 	public Proyecto getProyecto(){
 		return this.proyecto;
 	}
}