package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
import java.util.List;

public class Cuarto {

    private int ancho, alto;
    private Robot robot;
    private Meta meta;
    private List<Obstaculo> obstaculos;

    public Cuarto(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setObstaculos(List<Obstaculo> obstaculos) {
        this.obstaculos = obstaculos;
    }

    public void mostrarEstado() {
        System.out.println("Dimensiones del cuarto: " + ancho + "x" + alto);
        System.out.println("Robot: " + robot);
        System.out.println("Meta: " + meta);
        System.out.println("Obstáculos:");
        for (Obstaculo o : obstaculos) {
            System.out.println(o);
        }
    }
}
