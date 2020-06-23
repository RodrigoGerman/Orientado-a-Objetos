package vista.componentsgit.buttonsgit;

import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
	* La clase ButtonTabbedGit es una clase que hereda de la clase ButtonGit debido a que es un boton de la 
	* aplicacion GIT - RGL en el fondo, pero tiene un cierto comportamiento el cual es hacer un boton
	* de un tamaño en especifico que tiene un cruz en su interior.
	* @author German Lopez Rodrigo
	* @version 4/12/2016/Final 
*/
public class ButtonTabbedGit extends ButtonGit{

	/** *Metodo Constructor para crear un ButtonTabbedGit sin ningun comportamiento.*/
	public ButtonTabbedGit(){}

	/** *Metodo Constructor para crear un ButtonTabbedGit que recibe un int que se refiere al tamaño que tendra el boton.*/
	public ButtonTabbedGit(int size){
		setPreferredSize(new Dimension(size, size));
		setToolTipText("Cerrar esta Pestaña");
		setUI(new BasicButtonUI());
		setContentAreaFilled(false);
		setFocusable(false);
		setBorder(BorderFactory.createEtchedBorder());
		setBorderPainted(false);
		addMouseListener(new EventMouse());
		setRolloverEnabled(true);
	}

	/** * Metodo para actualizar su UI.*/
	@Override
	public void updateUI(){
	}

	 /** *Metodo sobrescrito paintComponent que se encarga de asignar el color que va a tener el boton.*/
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		if (getModel().isPressed()) {
			g2.translate(1, 1);
		}
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		if(getModel().isRollover()){
			g2.setColor(new Color(137,15,15));
		}
		int delta = 6;
		g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
		g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
		g2.dispose();
	}


	/** *Clase interna EventMouse que hereda de MouseAdapter, crea un objeto que en el fondo es un evento,
	el cual realiza es dibujar el borde cuando se presiona o quitar el borde cuando el mouse no lo este seleccionando.*/
	class EventMouse extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
			AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	}
}

