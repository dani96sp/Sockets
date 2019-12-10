package Ejemplo2;

import Ejemplo2.GestionServicio;

import java.net.*;
import java.io.*;

public class Servidor {

	public static void main(String[] args) {
		
		ServerSocket servicio = null;
		Socket socketServicio = null;
		
		int i = 0;
		String cadena;
		
		try{
			// Crear socket servidor
			servicio= new ServerSocket(9999);
			
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			while (true) {
			// Aceptar un cliente
			socketServicio= servicio.accept();
			System.out.println("Cliente " + i);
			i++;
			new GestionServicio(socketServicio).start();
			socketServicio = null;
			
			}
			
			
		} catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		}
	}
}
