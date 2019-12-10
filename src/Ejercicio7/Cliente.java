package Ejercicio7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Float.NaN;

public class Cliente {


	// Nombre del jugador
	static String nombre = "";
	// Tablero
	static int[] numeros = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	static char[] letras = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
	static String cuadradoRelleno = "■";
	static String cuadradoVacio = " ";
	static boolean[][] tablero = new boolean[10][10];

	public static void main(String[] args) {
		Socket cliente = null;
		DataInputStream entrada;
		DataOutputStream salida;
		String cadena = "";
		boolean correcto = true;

		try{
			while(true) {
				Scanner sc = new Scanner(System.in);
				System.out.println("Bienvenido al juego Hundir la Flota.");

				// Hacemos esta comprobación para que si falla el servidor al que conectarse,
				// repita el bucle pero saltandose esta parte ya que ya ha introducido un nombre
				if (nombre.equals("")) {
					while(nombre.equals("")) { // Seguimos pediendo el nombre mientras no inserte nada
						System.out.println("Introduzca su nombre:(0 para salir)");
						cadena = sc.nextLine();
						if (cadena.equals("0")) break; // Salimos si se inserta 0
						nombre = cadena;
					}
				}

				System.out.println("Introduzca la IP del servidor al que quiere conectarse: (0 salir)");
				cadena = sc.nextLine();
				if (cadena.equals("0")) break; // Salimos si se inserta 0

				System.out.println("Intentando conectar con el servidor " + cadena);

				// Crear socket cliente
				// cliente = new Socket(cadena, 9999);
				cliente = iniciarConexion(cadena);
				if (cliente != null) {
					// Obtener los InputStream y/o OutputStream del servidor
					entrada = new DataInputStream(cliente.getInputStream());
					salida = new DataOutputStream(cliente.getOutputStream());

					// Enviamos el nombre del jugador al servidor
					salida.writeUTF(nombre);

					// Se espera a que haya 2 jugadores
					System.out.print("Se ha conectado correctamente con el servidor.");
					System.out.println("Se iniciará la partida cuando haya 2 jugadores conectados.");

					// Leemos la respuesta del servidor que se dará cuando haya 2 jugadores conectados
					cadena = entrada.readUTF();

					// A partir de aquí empieza el juego con los 2 jugadores conectados en el servidor

					System.out.println("Se ha encontrado oponente, iniciando la partida..");
					crearFlota();

					// Acciones
					salida.writeUTF("Prueba 1");
					cadena = entrada.readUTF();

					// Cerrar los canales de entrada, salida y el socket cliente
					salida.close();
					entrada.close();
					cliente.close();
				}
			}
			System.out.println("Cerrando el programa..");
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error.");
			System.out.println(e);
		}
	}

	private static Socket iniciarConexion(String ip) {
		int puerto = 9999;
		try {
			return new Socket(ip, puerto);
		} catch (IOException e) {
			System.out.println("No se ha podido conectar con el servidor " + ip + ":" + puerto);
			System.out.println(e);
		}
		return null;
	}

	private static void crearFlota() {
		System.out.println("Prepare su flota:");
		for(int n : numeros) {
			if (n == 10) {
				System.out.print(n + cuadradoVacio);
			} else {
				System.out.print(n + cuadradoVacio + cuadradoVacio);
			}
			for(char c : letras) {
				if (n == 1) {
					System.out.print(c + cuadradoVacio);
				} else {
					System.out.print(cuadradoRelleno + cuadradoVacio);
				}
			}
			System.out.println();
		}
	}

	private static void pintarTablero() {
		tablero[0][0] = true;
		tablero[0][1] = true;
		tablero[0][2] = true;
		tablero[0][3] = true;

		tablero[0][7] = true;
		tablero[0][8] = true;
		tablero[0][9] = true;

		tablero[2][0] = true;
		tablero[2][1] = true;
		tablero[2][2] = true;

		tablero[2][4] = true;


		System.out.print("   ");
		for(char c : letras) {
			System.out.print(c + cuadradoVacio);
		}
		System.out.println();
		for(int n = 0; n<numeros.length; n++) {
			if (numeros[n] != 10) {
				System.out.print(cuadradoVacio + numeros[n] + cuadradoVacio);
			} else {
				System.out.print(numeros[n] + cuadradoVacio);
			}
			for(int l = 0; l<letras.length; l++) {
				System.out.print((tablero[n][l] ? cuadradoRelleno : cuadradoVacio) + cuadradoVacio);
			}
			System.out.println();
		}
	}

	private static void nuevaFlota(int numeroBarco) {
		int posNumInicial;
		char letraInicial;
		int posicionLetraInicial = 11;
		boolean posicionIncorrecta = true;

		System.out.println("Posicion el barco número " + numeroBarco + " de un total de " + numeroBarco + " casillas");
		Scanner sc = new Scanner(System.in);
		System.out.println("Indique si quiere hacer el barco horizontal o vertical: ");
		System.out.println("1 - Horizontal");
		System.out.println("2 - Vertical");
		String opcion = sc.nextLine();
		if (opcion.equals("1")) {
			while(posicionIncorrecta) {
				posicionIncorrecta = false;
				System.out.println("Indique la posición inicial del barco de " + numeroBarco + " casillas.");
				System.out.println("Por ejemplo introducir 1A hará un barco empezando desde 1A hasta " + letras[numeroBarco-1]);
				// Siendo horizontal, la posición inicial debe de ser como máximo 10-numeroBarco y no estar ocupado ni en sus alrededores
				String posicion = sc.nextLine();
				if(posicion.length() == 2 || posicion.length() == 3) {
					if (posicion.length() == 2) {
						posNumInicial = posicion.charAt(0)-1;
						letraInicial = posicion.charAt(1);
					} else {
						String numStr = posicion.charAt(0) +""+ posicion.charAt(1);
						posNumInicial = Integer.parseInt(numStr)-1;
						letraInicial = posicion.charAt(2);
					}
					int i = 0;
					for(char letra : letras) {
						if (letra == letraInicial) posicionLetraInicial = i;
						i++;
					}
					// Ya tenemos las posiciones para el tablero, ahora hay que comprobar que sean válidas
					if (posicionLetraInicial == 11) {
						posicionIncorrecta = true;
						System.out.println("La posición no es correcta, revise el tablero y pruebe de nuevo.");
					} else { // Si la posición es correcta
						if (posNumInicial == 0) {
							int numero;
							int letra;
							for(i = 0; i<numeroBarco; i++) {
								for(int j = 0; j<2; j++) {
									// Al ser horizontal, se suma a la letra el numero de barco (casillas que ocupa)
									// j es para comprobar los alrededores, en este caso, una posición a la derecha del barco
									numero = posNumInicial+j;
									letra = posicionLetraInicial + i;
									if (tablero[numero][letra]) { // Si ya hay un barco posición incorrecta
										posicionIncorrecta = true;
										System.out.println("La posición no es correcta, revise el tablero y pruebe de nuevo.");
										break; // Salimos del primer bucle for
									}
								}
								// Salimos del segundo bucle for
								if (posicionIncorrecta) break;
								// TODO
							}
						}
					}
				}

			}
		}

	}
}
