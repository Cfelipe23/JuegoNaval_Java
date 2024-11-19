package Main;

import Controlador.JuegoControlador;
import Controlador.JuegoControlador1vs1;

import java.util.Scanner;

public class interfaz {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        try {
            do {
                System.out.println("----Unde-la-Lancha------");
                System.out.println("1. Juega con posiciones ramdom \n2. 1 Vs 1 \n3. Salir ");
                int opcionPrincipal = scanner.nextInt();
                switch (opcionPrincipal) {
                    case 1:// opcion donde se juega contra un solo tablero
                        JuegoConPosicionesRamdon();
                        break;
                    case 2:// opcion donde se juega con otra persona con tableros diferentes
                        Juego1Vs1();
                        break;
                    default:
                        // Preguntar si repetir bucle
                        System.out.println("¿Salir? S/n: ");
                        String continuar = scanner.next().trim().toLowerCase();
                        salir = continuar.equals("s");
                }
            } while (!salir);

        } catch (Exception e) {
            System.out.println("Ingrese opciones validas");
        }

    }

    private static void JuegoConPosicionesRamdon() {
        try {

            // Leer tamaño del tablero desde el usuario
            System.out.print("Introduce el numero de filas del tablero: ");
            int filas = scanner.nextInt();
            System.out.print("Introduce el numero de columnas del tablero: ");
            int columnas = scanner.nextInt();

            // Crear instancia de JuegoControlador
            JuegoControlador juego = new JuegoControlador(filas, columnas);

            // Colocar barcos en el tablero
            juego.posicionarBarcos();
            System.out.println("(Numero de barcos colocados: " + juego.getContadorBarcos() + ")");
            imprimirTablero(juego.getTableroB());
            System.out.println();

            int intentos = 10; // Numero de intentos
            System.out.println("----Tienes '" + intentos + "' intentos----");

            System.out.println("-Separe la posicion por una coma x,y-");

            while (intentos > 0) {
                // Leer la posición a atacar
                System.out.print("->Ingrese posición a atacar: ");
                String puntoAtaque = scanner.next();

                // Dividir la entrada en partes usando la coma como delimitador
                String[] cortarString = puntoAtaque.split(",");

                // Verificar que la entrada tiene exactamente dos partes
                if (cortarString.length == 2) {
                    try {
                        // Divide las coordenadas en X y Y
                        int coordenadaX = Integer.parseInt(cortarString[0].trim());
                        int coordenadaY = Integer.parseInt(cortarString[1].trim());

                        // Envia las coordenadas a verificar la posicion
                        if (juego.verificarAtaque(coordenadaX, coordenadaY)) {
                            System.out.println("+INTENTOS:" + intentos);
                            juego.getTableroB()[coordenadaX][coordenadaY] = "P";
                            juego.getTableroA()[coordenadaX][coordenadaY] = "P";
                            imprimirTablero(juego.getTableroB());

                        } else {
                            intentos--;
                            System.out.println("-INTENTOS:" + intentos);
                            imprimirTablero(juego.getTableroB());
                        }
                        System.out.println();
                        if (juego.MensajeGandor(juego.getPosicionesCorrectaJugador(), juego.getPosicionesBarcos(),
                                intentos)) {
                            break;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Entrada invalida. Por favor, ingrese numeros validos.");
                    }
                } else {
                    System.out.println("Formato valido. Use x,y.");
                }
            }

            // Imprime el tablero A, el tablero con las posiciones de los barcos

            System.out.println("TABLERO FINAL");
            imprimirTablero(juego.getTableroA());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error de capa 8: " + e);
        }

    }

    private static void imprimirTablero(String[][] tablero) {
        // Imprime la guia(Horizontal) del tablero
        System.out.print("   ");
        for (int j = 0; j < tablero[0].length; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        // Imprimera ya el tablero
        for (int i = 0; i < tablero.length; i++) {
            // Guia(Vertical)
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(i + " ");
            }
            // Tablero
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
                if (j >= 10) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void Juego1Vs1() {
        try {
            // Leer tamano del tablero de ambos jugadores
            System.out.print("Introduce el numero de filas del tablero: ");
            int filas = scanner.nextInt();
            System.out.print("Introduce el numero de columnas del tablero: ");
            int columnas = scanner.nextInt();

            // Crear instancia de JuegoControlador para cada jugador
            JuegoControlador jugador1 = new JuegoControlador(filas, columnas);
            JuegoControlador jugador2 = new JuegoControlador(filas, columnas);
            JuegoControlador1vs1 juego1v1 = new JuegoControlador1vs1();

            // Colocar barcos en el tablero para cada jugador
            System.out.println("¡Barcos puestos al azar!");
            jugador1.posicionarBarcos();
            jugador2.posicionarBarcos();
            System.out.println("Barcos Jugador 1: " + jugador1.getContadorBarcos());
            System.out.println("Barcos Jugador 2: " + jugador2.getContadorBarcos());
            int intentosJugador1 = 10;
            int intentosJugador2 = 10;

            boolean jugador1Turno = true; // Indica de quien es el turno

            while (intentosJugador1 > 0 || intentosJugador2 > 0) {

                if (verificarGandor1vs1(jugador1, jugador2, intentosJugador1, intentosJugador2)) {
                    System.out.println("¡Juego terminado!");          
                    break;          
                } else {
                    if (jugador1Turno) {
                        // jugador 1
                        System.out.println();
                        System.out.println("Turno del Jugador 1:");
                        imprimirTablero(jugador2.getTableroB());

                        if (juego1v1.turnoJugador(jugador2, intentosJugador1)) {

                            imprimirTablero(jugador2.getTableroB());
                            continue; // Vuelve a jugar el mismo jugador
                        } else {
                            jugador1Turno = false; // Cambiar turno a Jugador 2
                        }

                    } else {
                        // jugador 2
                        System.out.println();
                        System.out.println("Turno del Jugador 2:");
                        imprimirTablero(jugador1.getTableroB());

                        if (juego1v1.turnoJugador(jugador1, intentosJugador2)) {
                            imprimirTablero(jugador1.getTableroB());
                            continue; // Vuelve a jugar el mismo jugador
                        } else {
                            jugador1Turno = true; // Cambiar turno a Jugador 1
                        }
                    }

                    // Disminuir intentos si ambos jugadores fallan
                    if (jugador1Turno) {
                        intentosJugador1--;
                    } else {
                        intentosJugador2--;
                    }

                }

            }

        } catch (Exception e) {
            System.out.println("Error de capa 8: " + e);
        }
    }

    private static boolean verificarGandor1vs1(JuegoControlador jugador1, JuegoControlador jugador2,
            int intentosJugador1,
            int intentosJugador2) {
        // Verificar si el oponente ha ganado
        if (jugador2.MensajeGandor(jugador2.getPosicionesCorrectaJugador(), jugador2.getPosicionesBarcos(),
                intentosJugador2)) {
            System.out.println("¡El Jugador 1 ha ganado!");
            return true;
        }
        if (jugador1.MensajeGandor(jugador1.getPosicionesCorrectaJugador(), jugador1.getPosicionesBarcos(),
                intentosJugador1)) {
            System.out.println("¡El Jugador 2 ha ganado!");
            return true;
        }
        return false;
    }
}
