package Ejercicio7;

/**
 * 7.- Construir una aplicación para simular el juego de hundir la flota. Tenéis total libertad
 * para el diseño de la aplicación. La única restricción es que debe permitir que dos
 * usuarios jueguen desde diferentes equipos.
 */

/**
 * Nota. Una vez finalizadas todas las actividades, crear una carpeta llamada Unidad 3 e
 * incluir en ella todos los archivos .java. Comprimir y enviar al correo del profesor.
 * Debido a la complejidad que se puede desarrollar en la aplicación de la actividad 7, ésta
 * será valorada con hasta 4 puntos, mientras que las demás se valorarán hasta 1 punto;
 * computando entre todas un total de hasta 10 puntos.
 */

public class Main {

    public static void main(String[] args) {
        int[] numeros = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        char[] letras = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        String cuadradoRelleno = "■";
        String cuadradoVacio = " ";
        boolean[][] tablero = new boolean[10][10];

        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                tablero[i][j] = false;
            }
        }

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

//        for(int n : numeros) {
//            if (n != 10) {
//                System.out.print(cuadradoVacio + n + cuadradoVacio);
//            } else {
//                System.out.print(n + cuadradoVacio);
//            }
//            for(char c : letras) {
//                if (n == 1) {
//                    System.out.print(c + cuadradoVacio);
//                } else {
//                    System.out.print((tablero[n-1][c-1] ? cuadradoRelleno : cuadradoVacio) + cuadradoVacio);
//                }
//            }
//            System.out.println();
//        }

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


}
