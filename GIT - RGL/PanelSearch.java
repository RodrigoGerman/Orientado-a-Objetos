package controlador;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.JScrollPane;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import vista.componentsgit.TextFieldGit;

/**
	* La clase PanelLogin es una clase que hereda de la clase JPanel debido a que es un panel en el fondo, 
	* pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear panel con un comportamiento especifico
	* para la ingresion de datos y busque de un dato en especifico.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public final class PanelSearch extends JPanel{
	/** * Objecto de tipo TableRowSorter para poder ordener la tabla de datos donde se va a buscar.*/
	private TableRowSorter<AbstractTableModel> ordenar;
	/** * Componente de tipo Textfield para capturar la palabra a buscar del usuario.*/
	private TextFieldGit filtro;

	 /** * Metodo Constructor sin argumentos que crea un PanelSearch sin ningun comportamiento.*/
	public PanelSearch(){}

	/** * Metodo Constructor que crea un PanelSearch, recibe un String con el nombre del panel, un Color para el 
	color del panel y una JTable donde buscara los datos.*/
	public PanelSearch(String title,Color color,JTable tableSearch){
		super(new BorderLayout(50,30));
		setBackground(color);
		AbstractTableModel modelo = (AbstractTableModel)tableSearch.getModel();
		setBorder(new TitledBorder(new EtchedBorder(),title));
		filtro = new TextFieldGit(" Busqueda",(byte)2,Color.BLACK);
		filtro.addKeyListener(new KeyEventText());
		add(filtro,BorderLayout.NORTH);
		add(new JScrollPane(tableSearch),BorderLayout.CENTER);
		ordenar = new TableRowSorter<AbstractTableModel>(modelo);
		tableSearch.setRowSorter(ordenar);
	}

	/** * Clase interna KeyEventText la cual crea un objeto que es un evento del tipo KeyAdapter 
	el cual se encarga de filtar en la tabla que se envio lo que esta escribiendo el usuario.*/
	protected class KeyEventText extends KeyAdapter{
		@Override
		public void keyTyped(KeyEvent e){
			int c = (int)e.getKeyChar();
			if(c > 32 && c < 47 || c > 58 && c<=64 || c >= 91 && c<=96 || c >= 123)
				e.consume();
			else{
				String filtro2 = filtro.getText();

				String[] text = filtro2.split(" ");

				RowFilter<AbstractTableModel,Object> rf = null;
					
				List<RowFilter<Object,Object>> rfs = new ArrayList<RowFilter<Object,Object>>();

				for(int i=0;i<text.length;i++){
					rfs.add(RowFilter.regexFilter(text[i],0,1,2));
				}

				rf = RowFilter.andFilter(rfs);

				ordenar.setRowFilter(rf);
			}
		}
	}
}