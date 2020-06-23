package vista.panelsgit;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.LayoutManager;

/**
	* La clase abstracta PanelGit es una clase que hereda de la clase JPanel debido a que es un panel en el fondo, 
	* pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear panel con un comportamiento especifico.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public abstract class PanelGit extends JPanel{

	/** * Metodo constructor que crear un PanelGit pero sin ninguna comportamiento ni estilo especifico.*/
	public PanelGit(){}

	/** * Metodo constructor que crear un PanelGit, recibe un distribucion de posiciones y un color para 
	colocar al panel.*/
	public PanelGit(LayoutManager posicion,Color color){
		setLayout(posicion);
		setBackground(color);
	}

	/** * Metodo para agregar una imagen al panel, recibe una string que especifica la ruta de la imagen.*/
	public final void setImage(String ruta){
		JLabel label1 = new JLabel(new ImageIcon(ruta));
		this.add(label1);
	}

	/** * Metodo para agregar una etiqueta de texto al panel de la ventana, recibe un String especificando el texto que
	contendra y un Color del color de las letras*/
	public final void setLabel(String texto,Color color){
		JLabel label1 = new JLabel(texto);
		label1.setForeground(color);
		this.add(label1);
	}

	/** * Metodo para agregar al panel la imagen del logo de la aplicacion Git-RGL.*/
	public final void setLogo(){
		setImage("./Logos/Git-RGL.png");
		setLabel("GIT - RGL",Color.BLACK);
	}

	/** * Metodo para agregar una imagen al panel en una determinada posicion, recibe un String que es la ruta 
	de la imagen, recibe cuatro enteros donde, "x" se refire a cuantos pixeles estara retirado del eje "x", la y 
	se refiere a cuatos pixeles estara retirado del eje "y", el "lon" se refire el ancho que ocupara la imagen
	y "width" se refiere a la altura de la imagen.*/
	public final void setImage(String ruta,int x,int y,int lon,int width){
		JLabel label1 = new JLabel(new ImageIcon(ruta));
		label1.setBounds(x,y,lon,width);
		this.add(label1);
	}

	/** * Metodo para agregar una etiqueta de texto al panel en una determinada posicion, recibe un String que 
	es el texto que contendra la etiqueta y un Color con el color que seran las letras, tambien recibe cuatro enteros
	donde, "x" se refire a cuantos pixeles estara retirado del eje "x", la "y" se refiere a cuatos pixeles estara 
	retirado del eje "y", el lon se refire el ancho que ocupara la etiqueta y "width" se refiere a la altura de la etiqueta.*/
	public final void setLabel(String texto,Color color,int x,int y,int lon,int width){
		JLabel label1 = new JLabel(texto);
		label1.setBounds(x,y,lon,width);
		label1.setForeground(color);
		this.add(label1);
	}

	/** * Metodo para agregar en el panel la imagen de logo de la aplicacion Git-RGL en una determinada posicion,
	recibe cuatro enteros donde, "x" se refire a cuantos pixeles estara retirado del eje "x", la "y" se refiere a 
	cuatos pixeles estara retirado del eje "y", el lon se refire el ancho que ocupara el logo y 
	"width" se refiere a la altura que tendra el logo.*/
	public final void setLogo(int x,int y,int lon,int width){
		setImage("./Logos/Git-RGL.png",x,y,lon,width);
		setLabel("GIT - RGL",Color.BLACK,x+28,y+90,100,15);
	}
}