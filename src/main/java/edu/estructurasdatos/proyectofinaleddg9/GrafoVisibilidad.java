package edu.estructurasdatos.proyectofinaleddg9;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GrafoVisibilidad {

    public static void main(String[] args) {
        // Cambiar a swing para evitar problemas de JavaFX
        System.setProperty("org.graphstream.ui", "swing");
        System.setProperty("java.awt.headless", "false");

        // Crear grafo
        Graph grafo = new SingleGraph("Grafo de Visibilidad");

        // Agregar nodos
        agregarNodo(grafo, "start", 100, 150);
        agregarNodo(grafo, "goal", 900, 500);
        agregarNodo(grafo, "v1", 200, 300);
        agregarNodo(grafo, "v2", 200, 400);
        agregarNodo(grafo, "v3", 300, 400);
        agregarNodo(grafo, "v4", 300, 300);

        // Conectar nodos manualmente por ahora
        conectarNodos(grafo, "start", "v1");
        conectarNodos(grafo, "v1", "v2");
        conectarNodos(grafo, "v2", "v3");
        conectarNodos(grafo, "v3", "v4");
        conectarNodos(grafo, "v4", "goal");

        // Mostrar el grafo
        grafo.display();
    }

    private static void agregarNodo(Graph grafo, String id, double x, double y) {
        Node nodo = grafo.addNode(id);
        nodo.setAttribute("xy", x, y);  // Coordenadas
        nodo.setAttribute("ui.label", id);  // Etiqueta del nodo
    }

    private static void conectarNodos(Graph grafo, String id1, String id2) {
        Node n1 = grafo.getNode(id1);
        Node n2 = grafo.getNode(id2);

        if (n1 == null || n2 == null) {
            System.out.println("Error: Uno o ambos nodos no existen: " + id1 + ", " + id2);
            return;
        }

        double[] coord1 = obtenerCoordenadas(n1);
        double[] coord2 = obtenerCoordenadas(n2);

        double distancia = Math.sqrt(Math.pow(coord2[0] - coord1[0], 2) + Math.pow(coord2[1] - coord1[1], 2));
        Edge arista = grafo.addEdge(id1 + "-" + id2, id1, id2, true);
        arista.setAttribute("length", distancia);
        arista.setAttribute("ui.label", String.format("%.2f", distancia));
    }

    private static double[] obtenerCoordenadas(Node nodo) {
        Object xy = nodo.getAttribute("xy");

        if (xy instanceof Object[]) {
            Object[] coords = (Object[]) xy;
            return new double[]{
                ((Number) coords[0]).doubleValue(),
                ((Number) coords[1]).doubleValue()
            };
        } else {
            throw new IllegalStateException("Coordenadas inválidas para el nodo: " + nodo.getId());
        }
    }
}
