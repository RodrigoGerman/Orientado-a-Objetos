package vista.windowsgit;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import vista.componentsgit.buttonsgit.ButtonGit;
import vista.componentsgit.PasswordFieldGit;
import vista.componentsgit.TextFieldGit;
import vista.panelsgit.panelform.PanelLogin;
import vista.exceptionsgit.LogInException;
import controlador.SocketClientGit;
import modelo.Usuario;
import vista.GitRGL;

/**
	* La clase WindowsLogin es una clase que hereda de la clase abstracta WindowsGit debido a que es un ventana de 
	* la aplicacion GIT - RGL que se encarga del proceso del inicio de sesion de un usuario conectandose al servidor para
	* validar el usuario e iniciar la aplicacion GIT - RGL.
	* @author German Lopez Rodrigo
	* @version 1/12/2016/Final 
*/
public class WindowsLogin extends WindowsGit implements ActionListener{
 	/** *Objectos privados tipo ButtonGit los cuales son dos botones que tienen un comportamiento en especifico.*/
	private ButtonGit button1,button2;
	/** *Objecto privado tipo TextFieldGit la cual tiene una validacion para un campo de nombres.*/
	private TextFieldGit campo_name;
	/** *Objecto privado tipo PasswordFieldGit la cual tiene una validacion y un comportamiente especial prra un campo de contraseñas*/
	private PasswordFieldGit campo_pass;
	/** *Variable privada tipo byte encargada de llevar los intentos de inicio de seccion.*/
	private byte attempt;

	/** *Contructor sin argumentos para crear una ventana login sin ningun elemento en su interior.*/
	public WindowsLogin(){}

	/** *Contructor que recibe el titulo de la ventana login, contruye una ventana para iniciar sesion con ciertos componentes
	y cierta distribuccion de los mismos, con un tamaño especifico de ventana de 350*350.*/
	public WindowsLogin(String title){

		super("Git RGL - Login",350,350);

		Color color = new Color(43,114,172);
		PanelLogin login = new PanelLogin(null,new Color(227,227,227));

		login.setLogo(127,10,90,95);
		login.setLabel("Bienvenido",color,133,130,80,20);

		login.setLabel("Usuario:",color,50,150,64,20);
		login.setImage("./Logos/usuario.png",50,176,16,16);
		campo_name = new TextFieldGit(" Nombre de Usuario",(byte)1,color);
		login.setTextField(campo_name,70,175,230,20);

		login.setLabel("Password:",color,50,200,90,20);
		login.setImage("./Logos/candado.png",50,226,16,16);
		campo_pass = new PasswordFieldGit(" Password",color);
		login.setPasswordField(campo_pass,70,225,230,20);

		button1 = new ButtonGit("Ingresar");
		login.setButton(button1,50,265,100,25);
		button1.addActionListener(this);

		button2 = new ButtonGit("Registrarse");
		login.setButton(button2,180,265,120,25);
		button2.addActionListener(this);

		getContentPane().add(login);
		setResizable(false);
		setVisible(true);
	}

	/** *Metodo sobrescrito de la interfaz ActionListener, el cual se encarga de asignar una tarea especifica a los dos 
	ButtonGit que debe contener la ventana.*/
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == button1){
			if(camposVacios()){
				SocketClientGit servidor = null;
				try{
					servidor = new SocketClientGit(GitRGL.ip,GitRGL.port);

					String user = campo_name.getText().trim();
					String pass = String.valueOf(campo_pass.getPassword()).trim();

					Usuario usuario = (Usuario)servidor.validLogin(user,pass);

					if(usuario != null){
						this.dispose();
						new WindowGitRGL("Git - RGL",usuario);
						
					}
					else{
						attempt++;
						if(attempt==5)
							throw new LogInException("Error. \nNumero maximo de intentos.\nAcceso Denegado.");
						else
							JOptionPane.showMessageDialog(null,"Error. \nError los datos ingresados no coinciden"
						+ " con ninguna cuenta, intentelo de nuevo porfavor.","Error - Login Git RGL",JOptionPane.ERROR_MESSAGE);
					}
				}catch(LogInException log){
					setEnabled(false);
				}catch(Exception i){
					JOptionPane.showMessageDialog(null,"Error. \nError al intentar conectarse al servidor"
						+ ",\npor favor intentalo de nuevo.","Error - Login Git RGL",JOptionPane.ERROR_MESSAGE);
				}finally{
					try{
						if(servidor != null) servidor.close();
					}catch(Exception l){
						System.out.println(l.getMessage());
					}
				}
			}
		}
		if(e.getSource() == button2){
			new WindowsRegister(this,true);
		}
	}


	/** *Metodo que se encarga de validar si los campos donde se piden los datos de la cuenta del cliente se encuentras vacios,
	regresa false en caso que los campos se encuentren vacios y false si los campos no estan vacios.*/ 
	protected boolean camposVacios(){
		if((campo_name.getText()).equals(" Nombre de Usuario")){
			JOptionPane.showMessageDialog(null,"Error. \nIngrese un nombre de usuario por favor."
			,"Error -Login Git RGL",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(String.valueOf(campo_pass.getPassword()).equals(" Password")){
			JOptionPane.showMessageDialog(null,"Error. \nIngrese una contraseña por favor."
			,"Error - Login Git RGL",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
 	}
}