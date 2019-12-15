package Ejercicio5;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente{
    public static void main (String[] args){
        DataInputStream input;
        DataOutputStream output;
        BufferedInputStream entrada;
        BufferedOutputStream salida;
        int in;
        byte[] receivedData;
        final String carpetaArchivosStrCliente = System.getProperty("user.dir") + "\\archivosCliente\\";
        boolean repetir = true;

        try{
            while (repetir) {
                Socket cliente = new Socket("localhost", 9999);
                input = new DataInputStream (cliente.getInputStream());
                output = new DataOutputStream(cliente.getOutputStream());
                entrada = new BufferedInputStream(cliente.getInputStream());
                salida = new BufferedOutputStream(cliente.getOutputStream());

                // Recogemos los nombres de los archivos
                // Primero la cantidad de archivos
                int cantidad = input.readInt();
                System.out.println("Mostrando los nombres de los ficheros: ");
                for (int i = 1; i <= cantidad; i++) {
                    // Y ahora recogemos todos los nombres y los mostramos
                    System.out.println(input.readUTF());
                }

                Scanner sc = new Scanner(System.in);
                System.out.println("Introduzca el nombre del fichero que quiere descargar: (0 salir)");
                String fichero = sc.nextLine();

                output.writeUTF(fichero);

                // Comprobamos si el fichero existe
                if (!input.readBoolean()) {
                    // Si no existe, error
                    System.out.println("No existe un fichero con ese nombre.");
                } else {
                    //Buffer de 1024 bytes
                    receivedData = new byte[1024];

                    //Para guardar fichero recibido
                    salida = new BufferedOutputStream(new FileOutputStream(carpetaArchivosStrCliente + fichero));
                    while ((in = entrada.read(receivedData)) != -1) {
                        salida.write(receivedData, 0, in);
                    }
                    System.out.println("Se ha descargado el archivo correctamente y lo podrá encontrar en la siguiente ruta tras finalizar la ejecución: ");
                    System.out.println(carpetaArchivosStrCliente);
                    salida.close();
                }

                String opcion = "";
                while (!(opcion.equals("2") || opcion.equals("1"))) {
                    System.out.println("¿Quiere descargar algún fichero más?");
                    System.out.println("1 - Si.");
                    System.out.println("2 - No.");
                    opcion = sc.nextLine();
                    if (opcion.equals("1")) {
                        repetir = true;
                    }
                    if (opcion.equals("2")) {
                        repetir = false;
                    }
                }
                entrada.close();
                output.close();
                input.close();
                cliente.close();
            }
        }catch ( Exception e ) {
            System.err.println(e);
        }
    }
}