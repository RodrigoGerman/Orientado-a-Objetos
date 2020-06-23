package vista.windowsgit;

//color
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import vista.componentsgit.buttonsgit.ButtonGit;
import vista.componentsgit.PasswordFieldGit;
import vista.componentsgit.TextFieldGit;
import vista.panelsgit.panelform.PanelLogin;

/**
	* La clase WindowsPerfil es una clase que hereda de la clase JDialog debido a que es un ventana de 
	* la aplicacion GIT - RGL que debe depender de otra para que exista, que se encarga del proceso de edicion de
	* perfil del usuario actual del sistema.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public final class WindowsPerfil extends JDialog{
	/** * Objectos privados tipo TextFieldGit que tienen una cierta validacion para los campos de datos.*/
	private TextFieldGit campo_user,campo_name,campo_email,campo_apellido;
	/** * Objectos privados tipo PasswordFieldGit que tiene un cierto comportamiento y una validacion para los campos de contraseñas.*/
	private PasswordFieldGit campo_pass,campo_pass2;
	/** * Objectos privados tipo ButtonGit los cuales son dos botones que tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
	/** * OBjetco pirvado tipo JLabel para almacenar la foto de perfil del usuario.*/
	private JLabel lblFoto;
	
	
	/** * Metodo Constructor que crea un ventana emergente que depende de otra sin ningun componente en su interior.*/
	public WindowsPerfil(){}

	/** * Metodo Constructor que crea un ventana, recibe la ventana de la que depende y una variable booleana que especifica el modelo
		* en su interior consta de 3 paneles con campos para pedir datos del cliente y actaulizar, dos botonos para confirmar y cancelar
		* los cambios.*/
	public WindowsPerfil(JFrame parent,String title,boolean modelo){

		super(parent,title,modelo);

		Color color = new Color(43,114,172);
		PanelLogin perfil = new PanelLogin(null,new Color(227,227,227));

		JTabbedPane datos = new JTabbedPane();

		lblFoto = new JLabel("./Logos/foto.png");
		lblFoto.setBounds(160,10,100,110);

		perfil.add(lblFoto);

		PanelLogin cuenta = new PanelLogin(null,new Color(227,227,227));

		cuenta.setLabel(" Nombre de Usuario:",color,50,10,200,20);
		cuenta.setImage("./Logos/usuario.png",50,38,16,16);
		campo_user = new TextFieldGit(" Nombre de Usuario",(byte)1,color);
		cuenta.setTextField(campo_user,70,36,230,20);
		campo_user.setEditable(false);

		cuenta.setLabel("Contraseña:",color,50,55,100,20);
		cuenta.setImage("./Logos/candado.png",50,75,16,16);
		campo_pass = new PasswordFieldGit(" Password",color);
		cuenta.setPasswordField(campo_pass,70,76,230,20);
		campo_pass.setEditable(false);

		cuenta.setLabel("Confirmar Contraseña:",color,50,93,200,20);
		cuenta.setImage("./Logos/candado.png",50,110,16,16);
		campo_pass2 = new PasswordFieldGit(" Password",color);
		cuenta.setPasswordField(campo_pass2,70,110,230,20);
		campo_pass2.setEditable(false);

		PanelLogin personal = new PanelLogin(null,new Color(227,227,227));

		personal.setLabel("Nombre:",color,50,10,100,20);
		personal.setImage("./Logos/nombre.png",50,38,16,16);
		campo_name = new TextFieldGit(" Nombre",(byte)0,color);
		campo_name.setEditable(false);
		personal.setTextField(campo_name,70,36,230,20);

		personal.setLabel("Apellido:",color,50,55,100,20);
		personal.setImage("./Logos/nombre.png",50,75,16,16);
		campo_apellido = new TextFieldGit(" Apellido",(byte)0,color);
		campo_apellido.setEditable(false);
		personal.setTextField(campo_apellido,70,76,230,20);

		PanelLogin trabajo = new PanelLogin(null,new Color(227,227,227));

		trabajo.setLabel("Email:",color,50,55,100,20);
		trabajo.setImage("./Logos/email.png",50,75,16,16);
		campo_email = new TextFieldGit(" Email",(byte)-1,color);
		trabajo.setTextField(campo_email,70,76,230,20);

		datos.add("Datos de Cuenta",cuenta);
		datos.add("Datos Personales",personal);
		datos.add("Datos de Trabajo",trabajo);

		button1 = new ButtonGit("Guardar");
		perfil.setButton(button1,100,490,120,25);
		//button1.addActionListener(this);

		button2 = new ButtonGit("Salir");
		perfil.setButton(button2,250,490,100,25);
		//button2.addActionListener(this);

		datos.setBounds(50,130,350,350);
		perfil.add(datos);
		getContentPane().add(perfil);
		setResizable(false);
		setSize(450,550);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** * Metodo para seleccionar la imagen del usuario.*/
	private void setImage(){
		int resultado;

		JFileChooser fc = new JFileChooser();

		FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG y PNG","jpg","png");

		fc.setFileFilter(filtro);

		resultado = fc.showOpenDialog(null);

		if (JFileChooser.APPROVE_OPTION == resultado){
			fichero = fc.getSelectedFile();

			try{
				ImageIcon icon = new ImageIcon(fichero.toString());

				Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));

				lblFoto.setText(null);
				lblFoto.setIcon(icono);
			}catch(Exception ex){}
		}
	}
}
