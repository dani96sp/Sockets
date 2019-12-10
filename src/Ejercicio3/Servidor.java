package Ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

			while (true) {
				// Aceptar un cliente
				socketServicio = servicio.accept();

				// Acciones a realizar por el servidor
				new GestionServicio(socketServicio).start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
