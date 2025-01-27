/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.estructurasdatos.proyectofinaleddg9;


import java.awt.Point;
import java.awt.Rectangle;
import java.io.*;
import java.util.*;
/**
 *
 * @author Anthony
 */
public class Ambiente {
    private Point posRobot;
    private Point posMeta;
    private List<Rectangle> obstaculos;

    public Ambiente() {
        obstaculos = new ArrayList<>();
    }

    public void cargarAmbiente(String archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        linea = br.readLine().replace("(", "").replace(")", "").trim();
        String[] posRobotCoords = linea.split(",");
        posRobot = new Point(
            Integer.parseInt(posRobotCoords[0].trim()),
            Integer.parseInt(posRobotCoords[1].trim())
        );

        linea = br.readLine().replace("(", "").replace(")", "").trim();
        String[] posMetaCoords = linea.split(",");
        posMeta = new Point(
            Integer.parseInt(posMetaCoords[0].trim()),
            Integer.parseInt(posMetaCoords[1].trim())
        );

        obstaculos = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            String[] vertices = linea.split(";");
            List<Point> puntos = new ArrayList<>();
            for (String vertice : vertices) {
                vertice = vertice.replace("(", "").replace(")", "").trim();
                String[] coords = vertice.split(",");
                puntos.add(new Point(
                    Integer.parseInt(coords[0].trim()),
                    Integer.parseInt(coords[1].trim())
                ));
            }
            obstaculos.add(crearRectanguloDesdeVertices(puntos));
        }
        br.close();
    }

    private Rectangle crearRectanguloDesdeVertices(List<Point> puntos) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (Point p : puntos) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public GrafoMatriz<Point> construirGrafo() throws Exception {
        List<Point> vertices = new ArrayList<>();
        vertices.add(posRobot);
        vertices.add(posMeta);

        for (Rectangle obstaculo : obstaculos) {
            vertices.add(new Point(obstaculo.x - 10, obstaculo.y - 10));
            vertices.add(new Point(obstaculo.x + obstaculo.width + 10, obstaculo.y - 10));
            vertices.add(new Point(obstaculo.x - 10, obstaculo.y + obstaculo.height + 10));
            vertices.add(new Point(obstaculo.x + obstaculo.width + 10, obstaculo.y + obstaculo.height + 10));
        }

        GrafoMatriz<Point> grafo = new GrafoMatriz<>(vertices.size(), true);
        for (Point v : vertices) grafo.agregarVertice(v);

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (esVisible(vertices.get(i), vertices.get(j))) {
                    double distancia = vertices.get(i).distance(vertices.get(j));
                    grafo.agregarArco(vertices.get(i), vertices.get(j), (float) distancia);
                }
            }
        }

        return grafo;
    }

    private boolean esVisible(Point a, Point b) {
        for (Rectangle obstaculo : obstaculos) {
            Point[] vertices = new Point[] {
                new Point(obstaculo.x, obstaculo.y),
                new Point(obstaculo.x + obstaculo.width, obstaculo.y),
                new Point(obstaculo.x, obstaculo.y + obstaculo.height),
                new Point(obstaculo.x + obstaculo.width, obstaculo.y + obstaculo.height)
            };

            Point[][] lados = {
                {vertices[0], vertices[1]},
                {vertices[1], vertices[3]},
                {vertices[3], vertices[2]},
                {vertices[2], vertices[0]}
            };

            for (Point[] lado : lados) {
                if (seIntersectan(a, b, lado[0], lado[1])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean seIntersectan(Point p1, Point p2, Point q1, Point q2) {
        int o1 = orientacion(p1, p2, q1);
        int o2 = orientacion(p1, p2, q2);
        int o3 = orientacion(q1, q2, p1);
        int o4 = orientacion(q1, q2, p2);

        if (o1 != o2 && o3 != o4) return true;

        if (o1 == 0 && estaSobreSegmento(p1, q1, p2)) return true;
        if (o2 == 0 && estaSobreSegmento(p1, q2, p2)) return true;
        if (o3 == 0 && estaSobreSegmento(q1, p1, q2)) return true;
        if (o4 == 0 && estaSobreSegmento(q1, p2, q2)) return true;

        return false;
    }

    private int orientacion(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    private boolean estaSobreSegmento(Point p, Point q, Point r) {
        return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
               q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
    }

    public List<Point> calcularRutaOptima(GrafoMatriz<Point> grafo) throws Exception {
        Point inicio = posRobot;
        Point meta = posMeta;

        Map<Point, Float> distancias = new HashMap<>();
        Map<Point, Point> predecesores = new HashMap<>();
        PriorityQueue<Point> cola = new PriorityQueue<>(Comparator.comparing(distancias::get));

        for (Point v : grafo.getVertices()) {
            distancias.put(v, Float.MAX_VALUE);
            predecesores.put(v, null);
        }
        distancias.put(inicio, 0f);
        cola.add(inicio);

        while (!cola.isEmpty()) {
            Point actual = cola.poll();

            for (int i = 0; i < grafo.getVertices().size(); i++) {
                Point vecino = grafo.getVertices().get(i);
                float peso = grafo.getPeso(actual, vecino);

                if (peso > 0) {
                    float nuevaDistancia = distancias.get(actual) + peso;
                    if (nuevaDistancia < distancias.get(vecino)) {
                        distancias.put(vecino, nuevaDistancia);
                        predecesores.put(vecino, actual);
                        cola.add(vecino);
                    }
                }
            }
        }

        List<Point> camino = new ArrayList<>();
        Point actual = meta;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }

        return camino;
    }

    public Point getPosRobot() {
        return posRobot;
    }

    public Point getPosMeta() {
        return posMeta;
    }

    public List<Rectangle> getObstaculos() {
        return obstaculos;
    }
}