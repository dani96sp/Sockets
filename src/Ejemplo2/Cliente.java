package Ejemplo2;

import java.net.*;
import java.io.*;

public class Cliente {

	public static void main(String[] args) {
			
		Socket cliente = null;
		DataInputStream entrada;
		DataOutputStream salida;
		String cadena;
		
		try{
			// Crear socket cliente
			cliente= new Socket("localhost",9999);
			
			// Obtener los InputStream y/o OutputStream del servidor
			entrada = new DataInputStream (cliente.getInputStream());
			salida = new DataOutputStream (cliente.getOutputStream());
			
			// Acciones
			salida.writeUTF("Prueba 1");
			//cadena = entrada.readUTF();
			salida.writeUTF("Prueba 2");
			salida.writeUTF("Prueba 3");
			salida.writeUTF("adios");
			
			// Cerrar los canales de entrada, salida y el socket cliente
			salida.close();
			entrada.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
