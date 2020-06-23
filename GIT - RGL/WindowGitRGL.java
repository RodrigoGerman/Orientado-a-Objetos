package vista.windowsgit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.Box;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.util.Vector;
import vista.GitRGL;
import vista.componentsgit.MenuBarGit;
import vista.componentsgit.MenuGit;
import vista.tablesgit.tableproyectgit.TableProyectGit;
import vista.panelsgit.panelform.PanelForm;
import vista.panelsgit.PanelTabGit;
import vista.exceptionsgit.ExceptionUser;
import modelo.Usuario;
import modelo.Proyecto;
import controlador.SocketClientGit;
import controlador.PanelSearch;
import controlador.PanelProyectGit;

/**
	* La clase WindowsLogin es una clase que hereda de la clase abstracta WindowsGit debido a que es un ventana de 
	* la aplicacion GIT - RGL que se encarga de todo el funcionamiento de la aplicacion.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/

public class WindowGitRGL extends WindowsGit implements ActionListener{
	/** *Variable tipo MenuBarGit, la cual posee un  estilo personalizado.*/
	private MenuBarGit barra;
	/** *Variables tipo MenuGit, para mostrar los diferentes acciones que puede realizar el usuario.*/
	private MenuGit icono,proyectos,buscar,perfil;
	/** *Variables tipo JMenuItem, para mostrar las acciones que puede realizar el usuario.*/
	private JMenuItem ver_perfil,cerrar_sesion,agregar_proyecto,eliminar_proyecto;
	/** *Variable tipo JTabbedPane, para mostrar diferentes pestañas.*/
	private JTabbedPane tabbedPane;
	/** *Variables tipo TableProyectGit, las cuales construyen tablas con determinados datos.*/
	private TableProyectGit table,tables; 
	/** *Variable tipo Usuario, la cual guarda la referencia del usuario actual del sistema.*/
	private Usuario user;
	/** *Variables tipo Vector, para guardar datos con una estructura especifica.*/
	private Vector<Vector<Object>> data,datas;
	/** *Variable tipo SocketClientGit, la cual proporciona metodo para establecer la conexion entre cliente y servidor.*/
	public static SocketClientGit servidor = null;

	/** *Constructor sin argumentos*/
	public WindowGitRGL(){}

	/** *Constructor que construye la ventana principal del programa,recibe una String que corresponder 
	 al titulo de la venta y un usuarios que corresponde al usuario actual en el sistema.*/
	public WindowGitRGL(String title,Usuario user){

		super(title,500,500);

		this.user = user;

		try{
			servidor = new SocketClientGit(GitRGL.ip,GitRGL.port);
			servidor.setUser(user);
		}catch(Exception i){
			JOptionPane.showMessageDialog(null,"Error. \nError al intentar conectarse al servidor"
			+ ",\npor favor intentalo de nuevo.","Error - Git RGL",JOptionPane.ERROR_MESSAGE);
		}

		tabbedPane = new JTabbedPane();

		tabbedPane.setTabPlacement(JTabbedPane.LEFT);

		PanelForm proyectos = new PanelForm(new BorderLayout(),new Color(227,227,227));
		TitledBorder borde2 = new TitledBorder(new EtchedBorder(),"Proyectos");
		proyectos.setBorder(borde2);

		addMenuBar(this);

		data = servidor.refreshTableDatas();

		table = new TableProyectGit(data,tabbedPane,new Color(227,227,227));
		proyectos.add(new JScrollPane(table),BorderLayout.NORTH);
		proyectos.add(new JSeparator());

		datas = servidor.refreshTableDatas();;
		tables = new TableProyectGit(datas,tabbedPane,new Color(227,227,227));
		PanelSearch busqueda = new PanelSearch("Busqueda Proyectos:",new Color(227,227,227),tables);
		
    	tabbedPane.addTab("Proyectos",proyectos);
    	tabbedPane.addTab("Busqueda",busqueda);

		getContentPane().add(tabbedPane);
		setBackground(new Color(55,105,141));
		setMinimumSize(new Dimension(550,550));
		setVisible(true);
	}

	/** * Metodo para añadir una barra de menu a la ventana con datos establecidos.*/
	private final void addMenuBar(JFrame ventana){
		barra = new MenuBarGit(new Color(55,105,141));
		ventana.setJMenuBar(barra);

		icono = new MenuGit("","./Logos/3.png");

		proyectos = new MenuGit("Proyectos");
		agregar_proyecto = new JMenuItem("Agregar Proyecto");
		agregar_proyecto.addActionListener(this);
		eliminar_proyecto = new JMenuItem("Eliminar Proyecto");
		eliminar_proyecto.addActionListener(this);

		buscar = new MenuGit("Ayuda");

		perfil = new MenuGit("Perfil","./Logos/usuario1.png");
		ver_perfil = new JMenuItem("Ver Perfil");
		ver_perfil.addActionListener(this);
		cerrar_sesion = new JMenuItem("Cerrar Sesion");
		cerrar_sesion.addActionListener(this);

		perfil.add(ver_perfil);
		perfil.add(cerrar_sesion);

		proyectos.add(agregar_proyecto);
		proyectos.add(eliminar_proyecto);

		barra.add(icono);
		barra.add(proyectos);
		barra.add(Box.createHorizontalGlue());
		barra.add(buscar);
		barra.add(perfil);
	}

	/** * Metodo que se implementa de la interfaz ActionListener para realizar diferentes acciones dependiendo del
	componente que las ocasione.*/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object campo = e.getSource();

		if(campo == ver_perfil){
			//new WindowsPerfil(this,"Perfil User GIT - RGL",true);
		}

		if(campo == cerrar_sesion){
			this.dispose();
			new WindowsLogin("5");
		}

		if(campo == agregar_proyecto){
			WindowProyect proyect = new WindowProyect(this,true,user);
			proyect.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosed(WindowEvent e){
					Proyecto proyecto = proyect.getProyecto();
					if(proyecto != null){
						int cantidad = tabbedPane.getTabCount() - 1;
						data = servidor.refreshTableDatas();
						table.refreshTable(data);
						tables.refreshTable(data);
						tabbedPane.add(proyecto.getTitle(),new PanelProyectGit(proyecto.getTitle()));
						tabbedPane.setTabComponentAt(++cantidad,new PanelTabGit(tabbedPane));
					}
				}	
			});
		}

		if(campo == eliminar_proyecto){
			WindowDelProyect proyect = new WindowDelProyect(this,true,user);
			proyect.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosed(WindowEvent e){
					data = servidor.refreshTableDatas();
					table.refreshTable(data);
				}	
			});
		}
    }
}