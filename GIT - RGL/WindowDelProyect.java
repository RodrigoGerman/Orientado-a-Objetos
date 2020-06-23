package vista.windowsgit;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JOptionPane;
import vista.panelsgit.panelform.PanelForm;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.componentsgit.buttonsgit.ButtonGit;
import java.util.ArrayList;
import modelo.Proyecto;
import modelo.Usuario;
import controlador.SocketClientGit;
import controlador.PanelSearch;
import vista.tablesgit.tableproyectgit.TableProyectGit;
import java.util.Vector;
import vista.exceptionsgit.ExceptionUser;

/**
	* La clase WindowDelProyect es una clase que hereda de la clase JDialog debido a que es un ventana de 
	* la aplicacion GIT - RGL que debe depender de otra para que exista, que se encarga del proceso de elimiancion de un
	* proyecto conectandose al servidor para guardar el proyecto en la base de datos.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class WindowDelProyect extends JDialog implements ActionListener{
	/** * Objectos privados tipo ButtonGit los cuales tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
	/** * Variable tipo string para guardar el titulo del proyecto nuevo a crear.*/
	private String title = "";
	/** * Objecto tipo PanelSearch para mostar los colaboradores y el nombre del proyecto a agregar.*/
	private PanelSearch busqueda;
	/** *Variable tipo Usuario, la cual guarda la referencia del usuario actual del sistema.*/
	private Usuario user;
	/** * Variable tipo Proyecto, la cual guarda el proyecto que se acaba de crear.*/
	private Proyecto proyecto = null;
	/** *Variable tipo SocketClientGit, la cual proporciona metodo para establecer la conexion entre cliente y servidor.*/
	private SocketClientGit servidor = null;
	/** *Variables tipo TableProyectGit, las cuales construyen tablas con determinados datos.*/
	private TableProyectGit tables; 
	/** *Variable tipo Vector, para guardar datos con una estructura especifica.*/
	private Vector<Vector<Object>> datas;

	/** * Metodo Constructor que crea un ventana emergente que depende de otra sin ningun componente en su interior.*/
	public WindowDelProyect(){}

	/** * Metodo Constructor que crea un ventana, recibe la ventana de la que depende y una variable booleana que especifica el modelo.*/
	public WindowDelProyect(JFrame parent,boolean modal,Usuario user){
		super(parent,"Git RGL - Del Proyect",modal);

		this.servidor = WindowGitRGL.servidor;

		Color color = new Color(43,114,172);
		PanelForm proyect = new PanelForm(null,new Color(227,227,227));
		proyect.setLogo(227,10,90,95);

		datas = servidor.refreshTableDatas();
		tables = new TableProyectGit(datas,new JTabbedPane(),new Color(227,227,227));
		PanelSearch busqueda = new PanelSearch("Busqueda Proyectos:",new Color(227,227,227),tables);
		busqueda.setBounds(50,120,450,350);
		proyect.add(busqueda);

		button1 = new ButtonGit("Eliminar");
		proyect.setButton(button1,150,480,100,25);
		button1.addActionListener(this);

		button2 = new ButtonGit("Salir");
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
			int i = tables.getSelectedRow();
			if(i!=-1){
				try{
					title = tables.getTitleProyect(i);
					servidor.delProyect(title);
					tables.delProyect(i);
				}catch(ExceptionUser l){}
			}
			else
				JOptionPane.showMessageDialog(null,"Porfavor seleccione de la tabla Proyectos"
					+ ",\nel proyecto que desea Eliminar.","Advertencia",JOptionPane.WARNING_MESSAGE);			
		}
		if(e.getSource() == button2){
			dispose();
		}
	}
}