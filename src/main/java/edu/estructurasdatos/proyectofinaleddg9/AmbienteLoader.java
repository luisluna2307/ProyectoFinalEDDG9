package edu.estructurasdatos.proyectofinaleddg9;

import java.io.*;
import java.util.*;

/**
 *
 * @author Luis Luna
 */
public class AmbienteLoader {

    public static void main(String[] args) {
        String filePath = "src/main/java/edu/estructurasdatos/proyectofinaleddg9/ambiente.txt";

        try {
            cargarAmbiente(filePath);
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void cargarAmbiente(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String linea;

        // Leer posición inicial del robot
        linea = br.readLine();
        int[] posRobot = parsearPosicion(linea);
        System.out.println("Posición inicial del robot: " + Arrays.toString(posRobot));

        // Leer posición de la meta
        linea = br.readLine();
        int[] posMeta = parsearPosicion(linea);
        System.out.println("Posición de la meta: " + Arrays.toString(posMeta));

        // Leer los obstáculos
        List<int[][]> obstaculos = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            int[][] obstaculo = parsearObstaculo(linea);
            obstaculos.add(obstaculo);
        }

        System.out.println("Obstáculos cargados:");
        for (int[][] obs : obstaculos) {
            System.out.println(Arrays.deepToString(obs));
        }

        br.close();
    }

    private static int[] parsearPosicion(String linea) {
        // Eliminar paréntesis y dividir por coma
        linea = linea.replaceAll("[()]", "");
        String[] partes = linea.split(",");
        return new int[]{Integer.parseInt(partes[0]), Integer.parseInt(partes[1])};
    }

    private static int[][] parsearObstaculo(String linea) {
        // Dividir en puntos del rectángulo
        String[] puntos = linea.split(";");
        int[][] obstaculo = new int[puntos.length][2];

        for (int i = 0; i < puntos.length; i++) {
            obstaculo[i] = parsearPosicion(puntos[i]);
        }

        return obstaculo;
    }
}
