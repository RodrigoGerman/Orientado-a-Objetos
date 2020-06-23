package vista.panelsgit;

import vista.componentsgit.buttonsgit.ButtonTabbedGit;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.BorderFactory;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
    * La clase PanelTabGit es una clase que hereda de la clase JPanel e implementa la interfaz ActionListener,
    * se encarga de crear PanelTabGit especifico para la aplicacion GIT-RGL, lo que realiza es agregar en el
    * JTabbedPane una nueva pestaña con el nombre del elemento de la columna 0, a esta pestaña se le agrega 
    * un boton para cerrarla.
    * @author German Lopez Rodrigo
    * @version 6/12/2016/Final 
*/
public class PanelTabGit extends JPanel implements ActionListener{
    /** * Objeto privado de tipo JTabbedPane para referencial el panel de pestañas donde se esta trabajando.*/
	private JTabbedPane pane;

    /** * Metodo Constructor para crear un PanelTabGit.*/
	public PanelTabGit(){}

    /** *Metodo Constructor que recibe el JTabbedPane lo que realiza es crear una nueva pestaña en el JTabbedPane
    y le agrega un boton para que se pueda cerrar dicha pestaña.*/
	public PanelTabGit(JTabbedPane pane){
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);
        
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(PanelTabGit.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        ButtonTabbedGit button = new ButtonTabbedGit(17);
		add(button);
        button.addActionListener(this);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    /** * Metodo sobrescrito de la interfaz ActionListener, el cual elimina la pestaña donde se esta trabajando cuando
    el cliente presiona el boton.*/
    @Override
    public void actionPerformed(ActionEvent e) {
		int i = pane.indexOfTabComponent(PanelTabGit.this);
        if (i != -1) {
			pane.remove(i);
		}
	}	
}