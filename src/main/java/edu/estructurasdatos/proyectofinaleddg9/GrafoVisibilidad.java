package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GrafoVisibilidad {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "javafx");

        // Crear grafo
        Graph grafo = new SingleGraph("Grafo de Visibilidad");

        // Agregar nodos
        agregarNodo(grafo, "start", 100, 150);
        agregarNodo(grafo, "goal", 900, 500);
        agregarNodo(grafo, "v1", 200, 300);
        agregarNodo(grafo, "v2", 200, 400);
        agregarNodo(grafo, "v3", 300, 400);
        agregarNodo(grafo, "v4", 300, 300);

        // Conectar nodos manualmente por ahora (visibilidad real se verificará después)
        conectarNodos(grafo, "start", "v1");
        conectarNodos(grafo, "v1", "v2");
        conectarNodos(grafo, "v2", "v3");
        conectarNodos(grafo, "v3", "v4");
        conectarNodos(grafo, "v4", "goal");

        // Mostrar grafo
        grafo.display();
    }

    // Método para agregar nodos con coordenadas
    private static void agregarNodo(Graph grafo, String id, double x, double y) {
        Node nodo = grafo.addNode(id);
        nodo.setAttribute("xy", x, y);
        nodo.setAttribute("ui.label", id);
    }

    // Método para conectar nodos y calcular distancia
    private static void conectarNodos(Graph grafo, String id1, String id2) {
        Node n1 = grafo.getNode(id1);
        Node n2 = grafo.getNode(id2);

        // Calcular distancia euclidiana
        double[] coord1 = n1.getAttribute("xy");
        double[] coord2 = n2.getAttribute("xy");
        double distancia = Math.sqrt(Math.pow(coord2[0] - coord1[0], 2) + Math.pow(coord2[1] - coord1[1], 2));

        // Agregar arista con distancia
        Edge arista = grafo.addEdge(id1 + "-" + id2, id1, id2);
        arista.setAttribute("length", distancia);
        arista.setAttribute("ui.label", String.format("%.2f", distancia));
    }
}
