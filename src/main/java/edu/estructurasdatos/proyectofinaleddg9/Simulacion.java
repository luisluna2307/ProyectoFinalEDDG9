package edu.estructurasdatos.proyectofinaleddg9;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Luna
 */
public class Simulacion {

    public static void main(String[] args) {
        // Crear el cuarto
        Cuarto cuarto = new Cuarto(1200, 700);

        // Crear el robot
        Robot robot = new Robot(100, 150, 20); // Posición inicial (100,150) y diámetro 20
        cuarto.setRobot(robot);

        // Crear la meta
        Meta meta = new Meta(900, 500); // Posición de la meta
        cuarto.setMeta(meta);

        // Crear obstáculos
        List<Obstaculo> obstaculos = new ArrayList<>();
        obstaculos.add(new Obstaculo(new int[][]{{200, 300}, {200, 400}, {300, 400}, {300, 300}}));
        obstaculos.add(new Obstaculo(new int[][]{{400, 200}, {400, 300}, {500, 300}, {500, 200}}));
        obstaculos.add(new Obstaculo(new int[][]{{600, 600}, {600, 700}, {700, 700}, {700, 600}}));
        cuarto.setObstaculos(obstaculos);

        // Mostrar el estado del cuarto
        cuarto.mostrarEstado();
    }
}
