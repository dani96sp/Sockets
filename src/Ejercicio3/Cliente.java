package Ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

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
			cadena = "";
			while (!cadena.equals("bye")) {
				Scanner sc = new Scanner(System.in);
				cadena = sc.nextLine();
				salida.writeUTF(cadena); // Le enviamos los que escribimos al servidor
				System.out.println(entrada.readUTF()); // El servidor nos lo devuelve y lo imprimimos por pantalla
			}
			
			// Cerrar los canales de entrada, salida y el socket cliente
			salida.close();
			entrada.close();
			cliente.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
