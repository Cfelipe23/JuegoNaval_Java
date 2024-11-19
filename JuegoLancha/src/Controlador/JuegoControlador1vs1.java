package Controlador;

import java.util.Scanner;

public class JuegoControlador1vs1 {

    static Scanner scanner = new Scanner(System.in);

    public boolean turnoJugador(JuegoControlador controlador, int intentos) {
        imprimirTablero(controlador.getTableroB());
        System.out.println("Intentos: " + intentos);

        // Leer la posicion a atacar
        System.out.print("-> Ingrese posicion a atacar (x,y): ");
        String puntoAtaque = scanner.next();

        // Dividir la entrada en partes usando la coma
        String[] cortarString = puntoAtaque.split(",");

        // Verificar que las coordenadas tienen dos puntos
        if (cortarString.length == 2) {
            try {
                int coordenadaX = Integer.parseInt(cortarString[0].trim());
                int coordenadaY = Integer.parseInt(cortarString[1].trim());

                if (controlador.verificarAtaque(coordenadaX, coordenadaY)) {
                    System.out.println("¡Acierto!");
                    controlador.getTableroB()[coordenadaX][coordenadaY] = "P";
                    controlador.getTableroA()[coordenadaX][coordenadaY] = "P";
                    imprimirTablero(controlador.getTableroB());
                    return true; // Continua el mismo jugador
                } else {
                    System.out.println("¡Fallaste!");
                    controlador.getTableroB()[coordenadaX][coordenadaY] = "-";
                    return false; // Cambia de jugador
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, ingrese numeros validos.");
            }
        } else {
            System.out.println("Formato invalido. Use x,y.");
        }

        return false; // Cambia de jugador
    }

    private static void imprimirTablero(String[][] tablero) {
        // Implementa la logica para imprimir el tablero aqui.
    }
}
