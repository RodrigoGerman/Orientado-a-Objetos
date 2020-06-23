package controlador;

import modelo.Archivo;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Vector;

/**
    * La clase PanelArchivo es una clase que hereda de la clase JPanel e implementa la interfaz ActionListener,se encarga 
    * de crear un panel especifico para cada archivo, se conecta a un servidor para optener los datos de ese archivo y 
    * se actualiza cada vez que haya un cambio.
    * @author German Lopez Rodrigo
    * @version 7/12/2016/Final 
*/
public class PanelArchivo extends JPanel implements ActionListener{
	/** * Arreglo privado de JButtons para agregar 3 botones al panel los cuales realizan las operaciones posibles en un archivo.*/
	private JButton[] botones = new JButton[3];
	/** * Objeto de tipo JTextArea para mostrar el archivo que se selcciono.*/
	private JTextArea textarea = new JTextArea(5,18);
	/** * Varible String para guardar el nombre del archivo.*/
	private String fileName;

	/** * Metodo Constructor sin argumentos que crea un PanelArchivo sin ningun comportamiento.*/
	public PanelArchivo(){}

	/** * Metodo Constructor que crea un PanelProyectGit que recibe un String con el nombre del archivo con el cual se conectara 
	al servidor para optener los datos mas reciente del archivo con ese nombre.*/
	public PanelArchivo(String fileName){
		super(new BorderLayout(10,2));
		this.fileName = fileName;
		readFile();
		textarea.setEditable(false);

		JPanel archivo = new JPanel(new BorderLayout());
		TitledBorder bord = new TitledBorder(new EtchedBorder(),"Archivo");
		archivo.setBorder(bord);
		archivo.add(new JScrollPane(textarea),BorderLayout.CENTER);

		add(archivo,BorderLayout.CENTER);

        JPanel opciones = new JPanel(new GridLayout(1,0));
        TitledBorder borde = new TitledBorder(new EtchedBorder(),"Opciones");
        opciones.setBorder(borde);

		String[] opci = {"Editar","Guardar","Eliminar"};
        String[] info = {"Editar archivo","Guardar cambios del archivo","Eliminar Archivo"};

        for(int i=0;i<3;i++){
            botones[i] = new JButton(opci[i]);
            botones[i].setContentAreaFilled(false);
            botones[i].setToolTipText(info[i]);
            botones[i].addActionListener(this);
            opciones.add(botones[i]);
        }

		JPanel versiones = new JPanel(new GridLayout(1,0));
		versiones.setBorder(new TitledBorder(new EtchedBorder(),"Versiones"));

		//Generar arbol apartir del servidor
		//metodo que reciba la carpeta y vaya agregando las versiones
		DefaultMutableTreeNode archivos = new DefaultMutableTreeNode(fileName);
		DefaultMutableTreeNode padre = new DefaultMutableTreeNode("Versiones");
		DefaultMutableTreeNode hijo = new DefaultMutableTreeNode(fileName);
		DefaultMutableTreeNode hijo2 = new DefaultMutableTreeNode(fileName);
		DefaultTreeModel modelo = new DefaultTreeModel(archivos);
		modelo.insertNodeInto(hijo,padre,0);
		modelo.insertNodeInto(hijo2,padre,1);
		modelo.insertNodeInto(padre,archivos,0);
		JTree tree = new JTree(modelo);
		versiones.add(tree);

        JPanel east = new JPanel(new BorderLayout(10,10));
       	east.add(opciones,BorderLayout.NORTH);
       	east.add(versiones,BorderLayout.CENTER);

		add(east,BorderLayout.EAST);
		add(new JSeparator(),BorderLayout.SOUTH);
	}

	/** * Metodo para saber si existe el fichero.*/
	public boolean getExistsFile(){
		File file = new File(fileName);
		return file.exists();
	}

	/** * Metodo para leer el archivo del servidor y mostrarlo en el textarea.*/
	public void readFile(){
		FileReader fichero = null;
		BufferedReader br;
		try{
			fichero = new FileReader(fileName);
			br = new BufferedReader(fichero);
			String line = br.readLine();
			while(line != null){
				textarea.append(line + "\n");
				line = br.readLine();
			}
		}catch(IOException ioe){
			System.out.println("\nError, el archivo no existe.\n");
		}finally{
			try{
				if(fichero != null)
					fichero.close();
			}catch(Exception e){
				System.out.println("Error: " + e.toString());
			}
		}
	}

	/** * Metodo sobrescrito de la interfaz ActionListener que se encarga de dar un comportamiento para cada uno de los
	botones que aparecen en el panel.*/
	@Override
    public void actionPerformed(ActionEvent e){
		Object campo = e.getSource();
        //Agregar c
		if(botones[0] == campo){
           	textarea.setEditable(true);
           	//notificar al servidor
        }
        //ELiminar Colaborador
		if(botones[1] == campo){
			textarea.setEditable(false);

			//guardar y subir
           /* int i = lista.getSelectedIndex();
            if(i>=0)
                colaboradores.remove(i);
            else
                JOptionPane.showMessageDialog(null,"Porfavor seleccione de la lista de colaboradores"
                    + ",\nel colaborador que desea Quitar.","Advertencia",JOptionPane.WARNING_MESSAGE);*/
        }
        //Crear
    	if(botones[2] == campo){
            //Jdialog
    	}
    }

	//Metodo para guardar en el archivo los nuevos usuarios que se añadieron.
	/*public void writeFile(){
		FileWriter fichero = null;
		PrintWriter pw = null;
		try{
			fichero = new FileWriter(fileName,true);
			pw = new PrintWriter(fichero);
			for(int i=this.size;i<users.size();i++)
				pw.println(users.get(i));
		}catch(IOException ioe){
			System.out.println("\nError, el archivo no existe.\n");
		}finally{
			try{
				if(pw != null)
					pw.close();
			}catch(Exception e){
				System.out.println("\nError: " + e.toString());
			}
		}
	}	*/
}