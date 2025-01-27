package edu.estructurasdatos.proyectofinaleddg9;

import java.awt.Point;
import java.util.*;

/**
 *
 * @author Luis Luna
 */
public class AmbienteLoader {

    public static void main(String[] args) {
        try {
            // Cargar ambiente
            String archivo = "src\\main\\java\\edu\\estructurasdatos\\proyectofinaleddg9\\ambiente.txt"; // Cambia por el archivo que uses
            Ambiente ambiente = new Ambiente();
            ambiente.cargarAmbiente(archivo);

            GrafoMatriz<Point> grafo = ambiente.construirGrafo();

            List<Point> ruta = ambiente.calcularRutaOptima(grafo);

            // Imprimir el camino más corto
            System.out.println("Recorrido más corto:");
            for (Point punto : ruta) {
                System.out.println("(" + punto.x + ", " + punto.y + ")");
            }

            Simulador simulador = new Simulador(ambiente, ruta, grafo);
            simulador.iniciarSimulacion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
