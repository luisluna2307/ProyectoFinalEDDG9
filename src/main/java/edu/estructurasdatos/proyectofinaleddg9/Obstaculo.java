package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
public class Obstaculo {

    private int[][] vertices; // Cada obstáculo es un rectángulo definido por 4 puntos (x, y)

    public Obstaculo(int[][] vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ");
        for (int[] punto : vertices) {
            sb.append("(").append(punto[0]).append(", ").append(punto[1]).append(") ");
        }
        return sb.toString();
    }
}
