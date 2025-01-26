package edu.estructurasdatos.proyectofinaleddg9;

import java.io.*;
import java.util.*;

/**
 *
 * @author Luis Luna
 */
public class AmbienteLoader {

    public static void main(String[] args) {
        Cuarto cuarto = new Cuarto(1200, 700);
        String filePath = "src/main/java/edu/estructurasdatos/proyectofinaleddg9/ambiente.txt";

        try {
            cuarto.cargarDesdeArchivo(filePath);
            System.out.println("Ambiente cargado desde archivo.");
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo: " + e.getMessage());
            return;
        }

        cuarto.mostrarEstado();
        cuarto.construirGrafoDeVisibilidad();
        cuarto.calcularRutaOptima();
    }
}
