package vista;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controlador.ServerRegisterGit;
import controlador.ThreadAppGit;
import controlador.ServerLoginGit;
import controlador.ActualizarDataGit;

/* *Clase encargada de establecer la comunicacion entre cliente y servidor dentro de la aplicacion.*/
public class ServidorGit extends JFrame implements ActionListener{
	private Socket service = null;
	private ServerSocket server = null;
	private boolean escuchando = true;
	private	DataInputStream dis = null;
	private	String accion = "";
	private JButton activar,cancelar;
	private final int PORT = 5432;

	ServidorGit(){}

	ServidorGit(String title){
		super(title);
		JPanel server = new JPanel(new GridLayout(1,1));
		activar = new JButton("Iniciar Servidor");
		server.add(activar);
		activar.addActionListener(this);
		cancelar = new JButton("Cerrar Servidor");
		server.add(cancelar);
		cancelar.addActionListener(this);
		getContentPane().add(server);
		setResizable(false);
		setSize(350,250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == activar){
			new HiloServer().start();
		}
		else{
			System.exit(1);
		}
	}

	class HiloServer extends Thread{
		public void run(){
			try{
				server = new ServerSocket(PORT);
				while(escuchando){
					service = server.accept();
					dis = new DataInputStream(service.getInputStream());
					accion = dis.readUTF();
					if(accion.equals("Login")){
						//Hilo para seguir con un usuarios y seguir recibiendo peticiones
						new ServerLoginGit(service).start();
					}
					if(accion.equals("App")){
						//Hilo para seguir con un usuarios y seguir recibiendo peticiones
						new ThreadAppGit(service).start();
					}
					if(accion.equals("Register")){
						new ServerRegisterGit(service).start();
					}
					if(accion.equals("Actualizar")){
						new ActualizarDataGit(service).start();
					}
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
	}

	public static void main(String[]args){
		new ServidorGit("ServidorGit");
	}

}