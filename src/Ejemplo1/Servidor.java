package Ejemplo1;

import java.net.*;
import java.io.*;

public class Servidor {

	public static void main(String[] args) {
		
		ServerSocket servicio = null;
		Socket socketServicio = null;
		DataInputStream entrada;
		DataOutputStream salida;
		String cadena;
		
		try{
			// Crear socket servidor
			servicio= new ServerSocket(9999);
			
			// Aceptar un cliente
			socketServicio= servicio.accept();
			
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
			servicio.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
