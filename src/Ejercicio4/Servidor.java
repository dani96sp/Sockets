package Ejercicio4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Servidor {

	public static void main(String[] args) {

		ServerSocket servicioEcho = null;
		ServerSocket servicioHora = null;
		Socket socketServicioEcho = null;
		Socket socketServicioHora = null;

		try{
			// Crear socket servidor
			servicioEcho= new ServerSocket(9998);
			servicioHora= new ServerSocket(9999);

			// Timeouts
			servicioEcho.setSoTimeout(1000);
			servicioHora.setSoTimeout(1000);

			for(;;) {
				while (true) {
					// Aceptar un cliente
					try {
						socketServicioEcho = servicioEcho.accept();
						break;
					} catch (SocketTimeoutException e) {
					}
					try {
						socketServicioHora = servicioHora.accept();
						break;
					} catch (SocketTimeoutException e) {
					}
				}

				// Acciones a realizar por el servidor
				if (socketServicioEcho != null) {
					new GestionServicio(socketServicioEcho).start();
					socketServicioEcho = null;
				}
				if (socketServicioHora != null) {
					new GestionServicio(socketServicioHora).start();
					socketServicioHora = null;
				}
			}
		} catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		}
	}
}
