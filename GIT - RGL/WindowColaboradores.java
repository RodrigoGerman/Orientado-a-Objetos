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
import vista.componentsgit.buttonsgit.ButtonGit;
import java.util.ArrayList;
import modelo.Proyecto;
import controlador.SocketClientGit;

/**
	* La clase WindowProyect es una clase que hereda de la clase JDialog debido a que es un ventana de 
	* la aplicacion GIT - RGL que debe depender de otra para que exista, que se encarga del proceso de registro de un nuevo
	* proyecto conectandose al servidor para guardar el proyecto en la base de datos.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class WindowColaboradores extends JDialog implements ActionListener{
	/** * Objectos privados tipo ButtonGit los cuales tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
	/** * Objecto tipo PanelSearch para mostar los colaboradores y el nombre del proyecto a agregar.*/
	private PanelSearchList busqueda;
	/** * Variable tipo Proyecto, la cual guarda el proyecto que se acaba de crear.*/
	private Proyecto proyecto = null;
	/** *Variable tipo SocketClientGit, la cual proporciona metodo para establecer la conexion entre cliente y servidor.*/
	private SocketClientGit servidor = null;

	/** * Metodo Constructor que crea un ventana emergente que depende de otra sin ningun componente en su interior.*/
	public WindowColaboradores(){}

	/** * Metodo Constructor que crea un ventana, recibe la ventana de la que depende y una variable booleana que especifica el modelo.*/
	public WindowColaboradores(JFrame parent,boolean modal,Proyecto proyecto){
		super(parent,"Git RGL - Add Collaborators",modal);

		this.proyecto = proyecto;

		this.servidor = WindowGitRGL.servidor;

		Color color = new Color(43,114,172);
		PanelForm proyect = new PanelForm(null,new Color(227,227,227));
		proyect.setLogo(227,10,90,95);

		ArrayList<String> colaboradores = proyecto.getColaboradores();

		ArrayList<String> news_colaboradores = servidor.getUsers();

		busqueda = new PanelSearchList("Añadir Colaboradores","Colaboradores",news_colaboradores,colaboradores);
		busqueda.setBounds(50,120,450,350);
		proyect.add(busqueda);

		button1 = new ButtonGit("Aceptar");
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
			setColocaboradores();
			servidor.updateProyect(proyecto);
			dispose();
		}
		if(e.getSource() == button2){
			dispose();
		}
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
}