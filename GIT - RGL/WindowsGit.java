package vista.windowsgit;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**	
	* La clase abtracta WindowsGit es un molde para las ventanas de la aplicacion GIT - RGL, consta de dos metodos setBorder,setIconGit
	* los cuales sirven para colocar un estilo personalizado a la ventana y el icono de la aplicacion que todo ventana de GIT - RGL debe tener.
	* @author German Lopez Rodrigo
	* @version 29/11/2016/Final 
*/
public abstract class WindowsGit extends JFrame{

	/** *Constructor sin argumentos para crear una ventana cualquiera.*/
	WindowsGit(){}

	/** *Contructor que recibe dos enteros que correponden al tamaño de la ventana, le colaca un borde especial, el icono 
	de la aplicacionn GIT - RGL y que se encuentre a la mitad de la pantalla del cliente.*/
	WindowsGit(String title,int x,int y){
		super(title);
		setBorder();
		setIconGit();
		setSize(x,y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/** *Metodo setBorder para colocar un estilo tipo MetalLookAndFeel a la ventana.*/
	private final void setBorder(){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}catch(Exception e){
			System.out.println("LookAndFeel not supported");
		}
	}

	/** *Metodo que coloca el icono de la aplicacion GIT - RGL .*/
	private final void setIconGit(){
		ImageIcon ii = new ImageIcon("./Logos/Git-RGL.png");
		setIconImage(ii.getImage());
	}
}