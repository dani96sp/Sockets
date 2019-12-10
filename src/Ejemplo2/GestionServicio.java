package Ejemplo2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class GestionServicio extends Thread{
	DataInputStream entrada;
	DataOutputStream salida;
	Socket socketServicio;
	
	public GestionServicio (Socket s) {
		this.socketServicio = s;
	}
	
	public void run () {
		String cadena;
		
		try{
						
			// Obtener los InputStream y/o OutputStream del servidor
			entrada = new DataInputStream (socketServicio.getInputStream());
			salida = new DataOutputStream (socketServicio.getOutputStream());
			
			// Acciones a realizar por el servidor
			while (true) {
				cadena = entrada.readUTF();
				System.out.println(cadena);
				if (cadena.equals("adios")) break;
			}
			
			// Cerrar los canales de entrada, salida, el socket cliente y el serversocket
			salida.close();
			entrada.close();
			socketServicio.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
}
