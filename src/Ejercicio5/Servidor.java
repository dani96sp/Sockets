package Ejercicio5;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.*;
import java.io.*;

public class Servidor{
    public static void main (String[] args){

        ServerSocket server;
        Socket connection;

        DataInputStream input;
        DataOutputStream output;
        BufferedInputStream entrada;
        BufferedOutputStream salida;

        byte[] byteArray;
        int in;

        final String carpetaArchivosStr = System.getProperty("user.dir") + "\\archivos\\";

        int cantidad;
        String[] nombres;
        String nombrefichero;

        try{
            //Servidor Socket en el puerto 9999
            server = new ServerSocket( 9999 );
            while(true) {
                //Aceptar conexiones
                connection = server.accept();
                input = new DataInputStream(connection.getInputStream());
                output = new DataOutputStream((connection.getOutputStream()));

                // Leemos los archivos de la carpeta ./archivos/
                File carpeta = new File(carpetaArchivosStr);
                FilenameFilter filtro = new FilenameFilter() {
                    @Override
                    public boolean accept(File carpeta, String nombre) {
                        File archivo = new File(carpeta.getAbsolutePath() + "\\" + nombre);
                        String lastFourDigits = "";     //substring containing last 4 characters

                        if (nombre.length() > 4)
                        {
                            lastFourDigits = nombre.substring(nombre.length() - 4);
                        } else {
                            lastFourDigits = nombre;
                        }
                        return (lastFourDigits.equals(".txt") && !archivo.isDirectory());
                    }
                };

                // Recogemos los nombres de archivo con un filtro para que contengan .txt y no sean directorios
                nombres = carpeta.list(filtro);
                // La cantidad
                cantidad = nombres.length;

                // Mandamos al cliente la cantidad de archivos
                output.writeInt(cantidad);

                // Mandamos al cliente los nombres de los archivos
                for(String s : nombres) {
                    output.writeUTF(s);
                }

                // Leemos el nombre del fichero que introduce el cliente por consola
                nombrefichero = input.readUTF();

                if (nombrefichero.equals("0")) break;

                boolean encontrado = false;
                for(String s : nombres) {
                    if (s.equals(nombrefichero)) {
                        encontrado = true;
                        break;
                    }
                }

                //Fichero a transferir
                String filename = carpetaArchivosStr + nombrefichero;

                File ficheroTransferir = new File( filename );

                if (!encontrado) {
                    output.writeBoolean(false);
                } else {
                    output.writeBoolean(true);

                    entrada = new BufferedInputStream(new FileInputStream(ficheroTransferir));
                    salida = new BufferedOutputStream(connection.getOutputStream());

                    //Enviamos el fichero
                    byteArray = new byte[8192];
                    while ((in = entrada.read(byteArray)) != -1) {
                        salida.write(byteArray, 0, in);
                    }
                    entrada.close();
                    salida.close();
                }
                input.close();
                output.close();
                connection.close();
            }
            server.close();
        }catch (Exception e ) {
            System.err.println(e);
        }
    }
}