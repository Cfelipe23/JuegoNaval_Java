package Controlador;

import java.util.ArrayList;
import java.util.Random;

public class JuegoControlador {

    private String[][] tableroA;//tablero donde se colocaron los barcos 
    private String[][] tableroB;//tablero donde mostrara los marcadores puestos
    private int contadorBarcos;
    private ArrayList<String> posicionesBarcos;//lista de las posiciones de los barcos
    private ArrayList<String> posicionesCorrectaJugador;//lista de las posciones puestas correctas

    public JuegoControlador(int filas, int columnas) {
        tableroA = new String[filas][columnas];
        tableroB = new String[filas][columnas];
        inicializarTableros();
        contadorBarcos = 0;
        posicionesBarcos = new ArrayList<>();
        posicionesCorrectaJugador = new ArrayList<>();
    }


    public ArrayList<String> getPosicionesBarcos() {
        return this.posicionesBarcos;
    }
    
    public ArrayList<String> getPosicionesCorrectaJugador() {
        return this.posicionesCorrectaJugador;
    }

    public String[][] getTableroA() {
        return tableroA;
    }

    public String[][] getTableroB() {
        return tableroB;
    }

    public int getContadorBarcos() {
        return contadorBarcos;
    }

    private void inicializarTableros() {
        for (int i = 0; i < tableroA.length; i++) {
            for (int j = 0; j < tableroA[i].length; j++) {
                tableroA[i][j] = "~";
                tableroB[i][j] = "~";
            }
        }
    }

    public void posicionarBarcos() {
        Random random = new Random();
        int[] tamañosBarcos = { 1, 4, 2, 2, 1 };

        for (int tamañoBarco : tamañosBarcos) {
            boolean colocado = false;

            while (!colocado) {
                if (intentarColocarBarco(tamañoBarco, random)) {
                    colocado = true;
                    contadorBarcos++; // Incrementar el contador cuando se coloca un barco
                } else {
                    break;
                }
            }
        }

    }

    private boolean intentarColocarBarco(int tamañoBarco, Random random) {
        int x = tableroA.length, y = tableroA[0].length;
        boolean colocado = false;

        for (int intento = 0; intento < 100; intento++) {

            int fila = random.nextInt(x);
            int columna = random.nextInt(y);

            boolean horizontal = random.nextBoolean();

            if (horizontal) {
                if ((columna + tamañoBarco) <= y && esPosicionLibre(fila, columna, tamañoBarco, true)) {
                    for (int i = 0; i < tamañoBarco; i++) {
                        tableroA[fila][columna + i] = "X";
                        posicionesBarcos.add(fila + "," + (columna ));

                    }
                    colocado = true;
                    break;
                }
            } else {
                if (fila + tamañoBarco <= x && esPosicionLibre(fila, columna, tamañoBarco, false)) {
                    for (int i = 0; i < tamañoBarco; i++) {
                        tableroA[fila + i][columna] = "X";
                        posicionesBarcos.add(fila + "," + (columna ));

                    }
                    colocado = true;
                    break;
                }
            }
        }
        return colocado;
    }

    private boolean esPosicionLibre(int fila, int columna, int tamañoBarco, boolean horizontal) {
        int x = tableroA.length, y = tableroA[0].length;

        for (int i = 0; i < tamañoBarco; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? columna + i : columna;

            if (f < 0 || f >= x || c < 0 || c >= y || !tableroA[f][c].equals("~")) {
                return false;
            }
        }

        for (int i = 0; i < tamañoBarco; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? columna + i : columna;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (!(dx == 0 && dy == 0)) {
                        int nuevaFila = f + dx;
                        int nuevaColumna = c + dy;
                        if (nuevaFila >= 0 && nuevaFila < x && nuevaColumna >= 0 && nuevaColumna < y) {
                            if (tableroA[nuevaFila][nuevaColumna].equals("X")) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean verificarAtaque(int x, int y) {
        if (x >= tableroA.length || y >= tableroA[0].length) {
            return false;
        } else if (tableroA[x][y].equals("P") || tableroA[x][y].equals("-")) {
            return false;
        } else if (tableroA[x][y].equals("X")) {
            posicionesCorrectaJugador.add(x + "," + y);
            return true;
        } else {
            tableroB[x][y] = "-";
            tableroA[x][y] = "-";
            return false;
        }
    }

    public  boolean MensajeGandor(ArrayList<String> posicionesJugador, ArrayList<String> posicionesGanadoras,
            int intentos) {
        // Compara tamaños y contenido
        if (posicionesJugador.size() == posicionesGanadoras.size()
                && posicionesJugador.containsAll(posicionesGanadoras)) {
            System.out.println("¡Felicidades! Has ganado.");
            return true; // Indica que el jugador ha ganado
        }

        // Mensaje si se han agotado los intentos
        if (intentos == 0) {
            System.out.println("Has perdido por muchos intentos.");
        } 
        // Indica que el jugador no ha ganado
        return false; 
    }

}
