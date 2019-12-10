package Ejercicio4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;


public class GestionServicio extends Thread{
	DataInputStream entrada;
	DataOutputStream salida;
	Socket socketServicio;
	
	public GestionServicio(Socket s) {
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
				if (cadena.equals("cerrar")) break;

				if (socketServicio.getLocalPort() == 9998) { // ECHO
					salida.writeUTF(cadena);

				} else if (socketServicio.getLocalPort() == 9999) { // HORA
					salida.writeUTF("La hora actual es: " + new Date().toString());
				}
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
