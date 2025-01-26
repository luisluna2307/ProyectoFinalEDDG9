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
    
    public int[][] getVertices(){
        return vertices;
    }
    
    public boolean intersectaLinea(double[] coord1, double[] coord2){
        for (int i = 0; i < vertices.length; i++) {
            int[] p1 = vertices[i];
            int[] p2 = vertices[(i + 1) % vertices.length];
            if (lineasSeCortan(coord1, coord2, p1, p2)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean lineasSeCortan(double[] a1, double[] a2, int[] b1, int[] b2) {
        double det = (a2[0] - a1[0]) * (b2[1] - b1[1]) - (a2[1] - a1[1]) * (b2[0] - b1[0]);
        if (det == 0) return false; // Líneas paralelas

        double t = ((b1[0] - a1[0]) * (b2[1] - b1[1]) - (b1[1] - a1[1]) * (b2[0] - b1[0])) / det;
        double u = ((b1[0] - a1[0]) * (a2[1] - a1[1]) - (b1[1] - a1[1]) * (a2[0] - a1[0])) / det;

        return t >= 0 && t <= 1 && u >= 0 && u <= 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Obstaculo: ");
        for (int[] punto : vertices) {
            sb.append("(").append(punto[0]).append(", ").append(punto[1]).append(") ");
        }
        return sb.toString();
    }
}
