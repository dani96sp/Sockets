package Ejercicio4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
			
		Socket clienteEcho = null;
		Socket clienteHora = null;
		DataInputStream entradaEcho;
		DataOutputStream salidaEcho;
		String cadenaEcho = "";
		DataInputStream entradaHora;
		DataOutputStream salidaHora;
		String cadenaHora = "";
		
		try{
			// Acciones
			String entrada = "";
			while (!entrada.equals("0")) {
				System.out.println("Elija el servicio: (0 salir)");
				System.out.println("1 - Servicio Echo.");
				System.out.println("2 - Servicio Hora.");

				Scanner sc = new Scanner(System.in);
				entrada = sc.nextLine();
				switch (entrada) {
					case "1":
						// Crear socket cliente
						clienteEcho= new Socket("localhost",9998);
						// Obtener los InputStream y/o OutputStream del servidor
						entradaEcho = new DataInputStream (clienteEcho.getInputStream());
						salidaEcho = new DataOutputStream (clienteEcho.getOutputStream());

						System.out.println("Ha elegido el Servicio Echo. Introduzca lo que quiere imprimir por pantalla:");
						cadenaEcho = sc.nextLine();
						salidaEcho.writeUTF(cadenaEcho);
						cadenaEcho = entradaEcho.readUTF();
						System.out.println(cadenaEcho);
						// Cerrar los canales de entrada, salida y el socket cliente
						salidaEcho.close();
						entradaEcho.close();
						clienteEcho.close();
						break;
					case "2":
						// Crear socket cliente
						clienteHora= new Socket("localhost",9999);
						// Obtener los InputStream y/o OutputStream del servidor
						entradaHora = new DataInputStream (clienteHora.getInputStream());
						salidaHora = new DataOutputStream (clienteHora.getOutputStream());

						System.out.println("Ha elegido el Servicio Hora.");
						salidaHora.writeUTF("hora");
						cadenaHora = entradaHora.readUTF();
						System.out.println(cadenaHora);
						// Cerrar los canales de entrada, salida y el socket cliente
						salidaHora.close();
						entradaHora.close();
						clienteHora.close();
						break;
					case "0":
						break;
					default:
						System.out.println("Elija uno de los de la lista.");
				}
			}

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
