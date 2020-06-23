package controlador;

import modelo.Proyecto;
import modelo.Archivo;
import vista.tablesgit.tablearchivogit.TableArchivosGit;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import java.util.Vector;
import vista.windowsgit.WindowGitRGL;
import vista.windowsgit.WindowColaboradores;
import vista.exceptionsgit.ExceptionUser;

/**
    * La clase PanelProyectGit es una clase que hereda de la clase JPanel e implementa la interfaz ActionListener,se encarga 
    * de crear un panel especifico para cada proyecto, se conecta a un servidor para optener los datos de ese proyecto y 
    * se actualiza cada vez que haya un cambio.
    * @author German Lopez Rodrigo
    * @version 7/12/2016/Final 
*/
public class PanelProyectGit extends JPanel implements ActionListener{
    /** * Objectos privados de tipo TableArchivosGit donde se almacenaran los archivos que contiene un proyecto.*/
    private TableArchivosGit archivos;
    /** * Objeto privado de tipo Proyecto donde se guardara el proyecto que se esta analizando.*/
    private Proyecto proyecto;
    /** * Arreglo privado de JButtons para agregar 5 botones al panel los cuales realizan las operaciones posibles en un proyecto.*/
    private JButton[] botones = new JButton[5];
    /** * Objeto privado JTabbedPane el cual tiene un posicion relativa en el panel y un distribucion definida.*/
    private JTabbedPane tabbedpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
    /** * Objeto privado de tipo Vector<Vector<Object>> el cual almacena los datos mas recientes del proyecto.*/
    private Vector<Vector<Object>> data;
    /** * Objeto privado de tipo JList<String> el cual almacena los colaboradores que tiene el proyecto.*/
    private JList<String> lista;
    /** * Objeto privado de tipo SocketClientGit el cual se conecta al servidor y regresa los datos recientes del servidor.*/
    private SocketClientGit servidor = null;
    /** * Variable privada tipo String el cual guarda el nombre del proyecto que se esta usando.*/
    private String title_proyect = "";
    /** * Objecto tipo JList para mostrar los colaboradores del proyecto.*/
    private JList<String> list_colaboradores;
    /** * Objecto tipo DefaultListModel para hacer didactica las JList.*/
    private DefaultListModel<String> model;

    /** * Metodo Constructor sin argumentos que crea un PanelProyectGit sin ningun comportamiento.*/
	public PanelProyectGit(){}

    /** * Metodo Constructor que crea un PanelProyectGit que recibe un String con el nombre del proyecto con el cual se conectara 
    al servidor para optener los datos mas reciente del proyecto con ese nombre.*/
	public PanelProyectGit(String proyecto){

		super(new BorderLayout(10,20));

        this.title_proyect = proyecto;

        this.servidor = WindowGitRGL.servidor;

        this.proyecto = servidor.getProyecto(proyecto);

		int i = 0;

        JPanel opciones = new JPanel(new GridLayout(0,1,0,5));
        TitledBorder borde = new TitledBorder(new EtchedBorder(),"Opciones");
        opciones.setBorder(borde);

        String[] opci = {"Agregar Colaborador","Quitar Colaborador","Agregar Archivo","Eliminar Archivo"};
        
        String[] info = {"","Quitar al colaborador seleccionado","Agregar archivo ya existente","Eliminar archivo seleccionado"};

        for(i=i;i<4;i++){
            botones[i] = new JButton(opci[i]);
            botones[i].setContentAreaFilled(false);
            botones[i].setToolTipText(info[i]);
            botones[i].addActionListener(this);
            opciones.add(botones[i]);
        }

        JPanel panel_archivos = new JPanel(new BorderLayout());
        setTableArchivos(panel_archivos);

        JPanel panel_colaboradores = new JPanel(new BorderLayout());
        TitledBorder borde2 = new TitledBorder(new EtchedBorder(),"Colaboradores");
        panel_colaboradores.setBorder(borde2);
        updateList();
        panel_colaboradores.add(new JScrollPane(lista),BorderLayout.CENTER);

        JPanel contenedor = new JPanel(new BorderLayout(10,20));
        contenedor.add(panel_colaboradores,BorderLayout.CENTER);
        contenedor.add(opciones,BorderLayout.EAST);
        contenedor.add(panel_archivos,BorderLayout.SOUTH);

        tabbedpane.add("General",contenedor);
        add(tabbedpane);
	}

    /** * Metodo setTableArchivos encargado de actualizar y llenar la tabla de los archivos que contiene un proyecto.*/
    private final void setTableArchivos(JPanel panel){
        TitledBorder borde = new TitledBorder(new EtchedBorder(),"Archivos");
        panel.setBorder(borde);
        data = servidor.refreshArchivosDatas(this.title_proyect);
        archivos = new TableArchivosGit(data,tabbedpane,new Color(227,227,227));
        panel.add(new JScrollPane(archivos),BorderLayout.CENTER);
    }

    /** * Metodo updateList encargado de actualizar y llenas la lista de colaboradores que contiene un  proyecto.*/
    private final void updateList(){
        this.proyecto = servidor.getProyecto(this.title_proyect);
        model = new DefaultListModel<String>();
        for(String s: proyecto.getColaboradores()){
            model.addElement(s);
        }
        lista = new JList<String>(model);
    }

    /** * Metodo sobrescrito de la interfaz ActionListener que se encarga de dar un comportamiento para cada uno de los
    botones que aparecen en el panel.*/
    @Override
    public void actionPerformed(ActionEvent e){
    	Object campo = e.getSource();

        if(botones[0] == campo){
            new WindowColaboradores(null,true,this.proyecto);
        }

        if(botones[1] == campo){
            int i = lista.getSelectedIndex();
            if(i>=0)
                lista.remove(i);
            else
                JOptionPane.showMessageDialog(null,"Porfavor seleccione de la lista de colaboradores"
                    + ",\nel colaborador que desea Quitar.","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        //Agregar
        if(botones[2] == campo){
    		JFileChooser file = new JFileChooser(".");
    		file.showOpenDialog(this);
    		File abre = file.getSelectedFile();
            try{
                servidor.addArchivo(abre,this.title_proyect);
            }catch(ExceptionUser i){
                int resp = JOptionPane.showConfirmDialog(null,"El archivo ya existe,\n¿Desea Sobrescirbirlo?"
                ,"Advertencia",JOptionPane.YES_NO_OPTION);
                if(resp == JOptionPane.YES_OPTION){
                   servidor.updateArchivo(abre,this.title_proyect);
                }
            }
            //data = servidor.refreshArchivosDatas(this.title_proyect);
            //archivos.refreshTable(data);
    	}
        //Eliminar
        if(botones[3] == campo){
            int i = archivos.getSelectedRow();
            if(i>=0){
               archivos.delArchivo(i);
            }
            else
                JOptionPane.showMessageDialog(null,"Porfavor seleccione de la tabla Archivos"
                    + ",\nel archivo que desea Eliminar.","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
    }

}