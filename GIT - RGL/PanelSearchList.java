package vista.panelsgit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import vista.componentsgit.TextFieldGit;

/**
	* La clase PanelSearchList es una clase que hereda de la clase JPanel debido a que es un panel de 
	* la aplicacion GIT - RGL, que se encarga del proceso de agregar nuevos colaboradores y almacenarlos.
	* @author German Lopez Rodrigo
	* @version 10/12/2016/Final 
*/
public class PanelSearchList extends JPanel implements ActionListener{
	/** * Variable String para poner texto al boton de añadir.*/
	private static final String ADD_BUTTON_LABEL = "Add >>";
	/** * Variable String para poner texto al boton de eliminar.*/
	private static final String REMOVE_BUTTON_LABEL = "<< Remove";
	/** * Objecto tipo JList para mostrar los colaboradores del sistema y los que agrega el proyecto.*/
	private JList<String> sourceList,destList;
	/** * Objecto tipo DefaultListModel para hacer didacticas las JList.*/
	private DefaultListModel<String> sourceListModel,destListModel;
	/** * Objectos tipo JButton para añadir las opciones de aceptar o cancelar.*/
	private JButton addButton,removeButton;
	/** * Componente de tipo Textfield para capturar la palabra a buscar del usuario.*/
	private TextFieldGit filtro;

 	/** * Metodo constructor que crea un PanelSearchList pero sin ningun comportamiento.*/
	public PanelSearchList(){}

	/** * Metodo constructor que crea un PanelSearchList, recibe dos string que corresponden a los titulos de las listas
	tambien recibe dos ArrayList los cuales son los colaboradores del sistema y del proyecto.*/
	public PanelSearchList(String title,String title2,ArrayList<String> new_colaborador,ArrayList<String> colaboradores){
		super(new GridLayout(1,1));
		Color color = new Color(227,227,227);
		TitledBorder borde2 = new TitledBorder(new EtchedBorder(),title);
		setBorder(borde2);
		setBackground(color);

		sourceListModel = new DefaultListModel<String>();
		for(String s: new_colaborador){
			if(!colaboradores.contains(s))
				sourceListModel.addElement(s);
		}

		destListModel = new DefaultListModel<String>();
		for(String s: colaboradores){
			destListModel.addElement(s);
		}

		JPanel lista_izquierda = new JPanel(new BorderLayout(20,20));
		TitledBorder borde = new TitledBorder(new EtchedBorder(),title);
		lista_izquierda.setBorder(borde);
		lista_izquierda.setBackground(color);
		sourceList = new JList<String>(sourceListModel);
		filtro = new TextFieldGit(" Busqueda",(byte)2,Color.BLACK);
		filtro.addKeyListener(new KeyEventText());
		lista_izquierda.add(filtro,BorderLayout.NORTH);
		lista_izquierda.add(new JScrollPane(sourceList),BorderLayout.CENTER);

		JPanel panelcentral = new JPanel(new FlowLayout());
		addButton = new JButton(ADD_BUTTON_LABEL);
		panelcentral.add(addButton);
		panelcentral.setBackground(color);
		addButton.addActionListener(this);
		removeButton = new JButton(REMOVE_BUTTON_LABEL);
		panelcentral.add(removeButton);
		removeButton.addActionListener(this);

		JPanel lista_derecha = new JPanel(new BorderLayout(20,20));
		TitledBorder borde3 = new TitledBorder(new EtchedBorder(),title2);
		lista_derecha.setBorder(borde3);
		lista_derecha.setBackground(color);
		destList = new JList<String>(destListModel);
		lista_derecha.add(new JScrollPane(destList),BorderLayout.CENTER);

		add(lista_izquierda);
		add(panelcentral);
		add(lista_derecha);
	}

	/** * Metodo para agregar cierto comportamiento a los dos botones que posee el panel.*/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object campo = e.getSource();
		if(campo == addButton){
			String colaborador = (String)sourceList.getSelectedValue();
			destListModel.addElement(colaborador);
			clearSourceSelected();

		}
		if(campo == removeButton){
			String colaborador = (String)destList.getSelectedValue();
			sourceListModel.addElement(colaborador);
			clearDestinationSelected();
		}
	}

	/** * Metodo para limpiar el elemento seleccionado de la lista de los colaboradores.*/
	private void clearSourceSelected() {
		int pos = sourceList.getSelectedIndex();
		if(pos>=0){
			sourceListModel.removeElementAt(pos);
			sourceList.getSelectionModel().clearSelection();
		}
	}

	/** * Metodo para limpiar el elemento seleccionado de la lista de los colaboradores agregados.*/
	private void clearDestinationSelected() {
		int pos = destList.getSelectedIndex();
		if(pos>=0){
			destListModel.removeElementAt(pos);
			destList.getSelectionModel().clearSelection();
		}
	}

	/** * Clase interna KeyEventText la cual crea un objeto que es un evento del tipo KeyAdapter 
	el cual se encarga de filtar en la lista de los colaboradores lo que esta escribiendo el usuario.*/
	protected class KeyEventText extends KeyAdapter{
		@Override
		public void keyTyped(KeyEvent e){
			int c = (int)e.getKeyChar();
			if(c > 32 && c < 47 || c > 58 && c<=64 || c >= 91 && c<=96 || c >= 123)
				e.consume();
			else{
				String filtro2 = filtro.getText().trim();
				if(sourceListModel.contains(filtro2)){
					sourceList.setSelectedValue(filtro2,true);
				}
			}
		}
	}

	/** * Metodo para regresar los colaboradores que añadio el usuario a la Jlist.*/
	public ArrayList<String> getColaboradores(){
		ArrayList<String> colaboradores;
		if(destListModel.getSize() == 0)
			return new ArrayList<String>();
		else{
			colaboradores = new ArrayList<String>();
			for(int i =0;i<destListModel.getSize()-1;i++){
				colaboradores.add((String)sourceListModel.getElementAt(i));
			}
		}
		return colaboradores;
	}
}