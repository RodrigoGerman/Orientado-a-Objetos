package vista.windowsgit;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import controlador.SocketClientGit;
import modelo.Usuario;
import vista.componentsgit.buttonsgit.ButtonGit;
import vista.componentsgit.PasswordFieldGit;
import vista.componentsgit.TextFieldGit;
import vista.panelsgit.panelform.PanelLogin;
import vista.exceptionsgit.ExceptionUser;
import vista.exceptionsgit.ExceptionEmail;
import vista.exceptionsgit.ExceptionPassword;
import vista.GitRGL;

/**
	* La clase WindowsRegister es una clase que hereda de la clase JDialog debido a que es un ventana de 
	* la aplicacion GIT - RGL que debe depender de otra para que exista, que se encarga del proceso de registro de un usuario
	* conectandose al servidor para guardar el usuario en la base de datos.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class WindowsRegister extends JDialog implements ActionListener{
	/** * Objectos privados tipo TextFieldGit que tienen una cierta validacion para los campos de datos.*/
	private TextFieldGit campo_user,campo_name,campo_email,campo_apellido;
	/** * Objectos privados tipo PasswordFieldGit que tiene un cierto comportamiento y una validacion para los campos de contraseñas.*/
	private PasswordFieldGit campo_pass,campo_pass2;
	/** * Objectos privados tipo ButtonGit los cuales son dos botones que tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
 
	/** * Metodo Constructor que crea un ventana emergente que depende de otra sin ningun componente en su interior.*/
	public WindowsRegister(){}

	/** * Metodo Constructor que crea un ventana, recibe la ventana de la que depende y una variable booleana que especifica el modelo
		* en su interior consta de 6 campos para pedir datos del cliente para poderse registrar, dos botonos para confirmar y cancelar
		* el registro.*/
	public WindowsRegister(JFrame parent,boolean modal){

		super(parent,"Git RGL - Register",modal);

		Color color = new Color(43,114,172);
		PanelLogin login = new PanelLogin(null,new Color(227,227,227));

		login.setLogo(274,10,90,95);
		login.setLabel("Registro",color,285,130,200,20);

		login.setLabel(" Nombre de Usuario:",color,50,155,200,20);
		login.setImage("./Logos/usuario.png",50,183,16,16);
		campo_user = new TextFieldGit(" Nombre de Usuario",(byte)1,color);
		login.setTextField(campo_user,70,180,230,20);

		login.setLabel("Contraseña:",color,50,205,100,20);
		login.setImage("./Logos/candado.png",50,233,16,16);
		campo_pass = new PasswordFieldGit(" Password",color);
		login.setPasswordField(campo_pass,70,230,230,20);

		login.setLabel("Confirmar Contraseña:",color,50,255,200,20);
		login.setImage("./Logos/candado.png",50,283,16,16);
		campo_pass2 = new PasswordFieldGit(" Password",color);
		login.setPasswordField(campo_pass2,70,280,230,20);

		login.setLabel("Nombre:",color,350,155,100,20);
		login.setImage("./Logos/nombre.png",350,183,16,16);
		campo_name = new TextFieldGit(" Nombre",(byte)0,color);
		login.setTextField(campo_name,370,180,230,20);

		login.setLabel("Apellido:",color,350,205,100,20);
		login.setImage("./Logos/nombre.png",350,233,16,16);
		campo_apellido = new TextFieldGit(" Apellido",(byte)0,color);
		login.setTextField(campo_apellido,370,230,230,20);

		login.setLabel("Email:",color,350,255,100,20);
		login.setImage("./Logos/email.png",350,283,16,16);
		campo_email = new TextFieldGit(" Email",(byte)-1,color);
		login.setTextField(campo_email,370,280,230,20);

		button1 = new ButtonGit("Registrarse");
		login.setButton(button1,180,330,120,25);
		button1.addActionListener(this);

		button2 = new ButtonGit("Cancelar");
		login.setButton(button2,350,330,100,25);
		button2.addActionListener(this);

		getContentPane().add(login);
		setResizable(false);
		setSize(650,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/** * Metodo sobrescrito de la interfaz ActionListener, el cual se encarga de asignar una tarea especifica a los dos 
	ButtonGit que debe contener la ventana.*/
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button1){
			if(camposVacios()){
				String pass = String.valueOf(campo_pass.getPassword());
				String pass2 = String.valueOf(campo_pass2.getPassword());
				if(pass.equals(pass2)){
					SocketClientGit servidor = null;
					try{
					
						Usuario usuario = new Usuario(campo_user.getText(),pass,campo_name.getText(),
							campo_apellido.getText(),campo_email.getText());

						servidor = new SocketClientGit(GitRGL.ip,GitRGL.port);
						servidor.registerUser(usuario);
						dispose();

					}catch(ExceptionUser u){
						campo_user.setText("");
					}catch(ExceptionEmail m){
						campo_email.setText("");
					}catch(ExceptionPassword p){
						campo_pass.setText("");
						campo_pass2.setText("");
					}catch(Exception i){
						JOptionPane.showMessageDialog(null,"Error. \nError al intentar conectarse al servidor"
							+ ",\npor favor intentalo de nuevo.","Error - Register Git RGL",JOptionPane.ERROR_MESSAGE);
					
					}finally{
						try{
							if(servidor != null) servidor.close();
						}catch(Exception l){
							System.out.println(l.getMessage());
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Error. Las contraseñas no coinciden, porfavor intentelo de nuevo."
						,"Error - Register Git RGL",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if(e.getSource() == button2){
			dispose();
		}
	}

	/** *Metodo que se encarga de validar si los campos donde se piden los datos de la cuenta del cliente se encuentras vacios,
	regresa false en caso que los campos se encuentren vacios y false si los campos no estan vacios.*/ 
 	protected boolean camposVacios(){
		JTextField[] m = {campo_user,campo_name,campo_apellido,campo_email,campo_pass,campo_pass2};
		String[] campos = {" Nombre de Usuario"," Nombre"," Apellido"," Email"," Password"," Password"};
		for(int i=0;i<campos.length-1;i++){
			if((m[i].getText()).equals(campos[i])){
				JOptionPane.showMessageDialog(null,"Error. Ingrese un" + campos[i] + " por favor."
					,"Error - Register Git RGL",JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
 	}
}