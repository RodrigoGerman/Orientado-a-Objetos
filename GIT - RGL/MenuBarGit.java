package vista.componentsgit;

import java.awt.LayoutManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JMenuBar;

/**
    * La clase MenuBarGit es una clase que hereda de la clase JMenuBar debido a que es una barra de menu en el fondo, 
    * pero tiene un cierto comportamiento para la aplicacion GIT - RGL, se encarga de crear una barra de menu
    * con un comportamiento especifico.
    * @author German Lopez Rodrigo
    * @version 2/12/2016/Final 
*/
public class MenuBarGit extends JMenuBar{
    /** * Objeto privado de tipo Color que asigna el color a la barra de menu de la aplicacion.*/
	private Color bgColor = Color.WHITE;

    /** * Metodo Constructor que crear un MenuBarGit sin ninguno comportamiento en especial.*/
	public MenuBarGit(){}
	
	/** * Metodo Constructor que recibe una distribucion de posiciones y un color que se colocalan ala barra de menu.*/
	public MenuBarGit(Color color){
		super();
        setColor(color);
	}

    /** * Metodo setColor para asignar el color a la barra de menu.*/ 
    public void setColor(Color color) {
        this.bgColor = color;
    }

    /** *Metodo sobrescrito paintComponent que se encarga de asignar el color que va a tener la barra de menu.*/
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.bgColor);
        g2d.fillRect(0,0,getWidth(),getHeight());
    }
}