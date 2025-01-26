package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
import java.util.*;
import java.io.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;


public class Cuarto {

    private int ancho, alto;
    private Robot robot;
    private Meta meta;
    private List<Obstaculo> obstaculos;
    private Graph grafo;
    private GrafoMatriz<String> grafoMatriz;
    private GrafoLista<String> grafoLista;

    public Cuarto(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.obstaculos = new ArrayList<>();
        this.grafoMatriz = new GrafoMatriz<>(50, true);
        this.grafoLista = new GrafoLista<>(true);
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
    
    public void cargarDesdeArchivo(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String linea;

        // Leer posición inicial del robot
        linea = br.readLine();
        int[] posRobot = parsearPosicion(linea);
        this.setRobot(new Robot(posRobot[0], posRobot[1]));

        // Leer posición de la meta
        linea = br.readLine();
        int[] posMeta = parsearPosicion(linea);
        this.setMeta(new Meta(posMeta[0], posMeta[1]));

        // Leer los obstáculos
        List<Obstaculo> obstaculos = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            int[][] obstaculo = parsearObstaculo(linea);
            obstaculos.add(new Obstaculo(obstaculo));
        }
        this.setObstaculos(obstaculos);

        br.close();
    }
    
    private int[] parsearPosicion(String linea) {
        linea = linea.replaceAll("[()\s]", "");
        String[] partes = linea.split(",");
        return new int[]{Integer.parseInt(partes[0]), Integer.parseInt(partes[1])};
    }
    
    private int[][] parsearObstaculo(String linea) {
        String[] puntos = linea.split(";");
        int[][] obstaculo = new int[puntos.length][2];

        for (int i = 0; i < puntos.length; i++) {
            obstaculo[i] = parsearPosicion(puntos[i]);
        }
        return obstaculo;
    }
    
    public void construirGrafoDeVisibilidad(){
        grafo = new SingleGraph("Grafo de Visibilidad");
        System.setProperty("org.graphstream.ui", "swing");

        // Agregar nodo inicial (robot)
        agregarNodo(robot.getX(), robot.getY(), "start");

        // Agregar nodo meta
        agregarNodo(meta.getX(), meta.getY(), "goal");

        // Calcular y agregar vértices de obstáculos desplazados
        int contador = 1;
        for (Obstaculo obstaculo : obstaculos) {
            int[][] vertices = obstaculo.getVertices();
            for (int[] vertice : vertices) {
                int[] desplazado = desplazarVertice(vertice, 10); // Desplazar 10 unidades
                String nodoId = "v" + contador;
                agregarNodo(desplazado[0], desplazado[1], nodoId);
                contador++;
            }
        }

        // Agregar aristas visibles entre nodos
        agregarAristasVisibles();

        // Mostrar el grafo
        try {
            grafo.display();
        } catch (Exception e) {
            System.err.println("No se pudo mostrar el grafo: " + e.getMessage());
        }
        System.out.println(grafoMatriz);
        System.out.println(grafoLista);
    }
    
    private void agregarNodo(int x, int y, String id){
        Node nodo = grafo.addNode(id);
        nodo.setAttribute("xy", x, y);
        nodo.setAttribute("ui.label", id);
        try {
            grafoMatriz.agregarVertice(id);
            grafoLista.agregarVertice(id);
        } catch (Exception e) {
            System.err.println("Error al agregar nodo al grafo: " + id);
        }
    }
    
    private void agregarAristasVisibles(){
        for(Node nodo1 : grafo){
            for(Node nodo2 : grafo){
                if(!nodo1.equals(nodo2) && esVisible(nodo1, nodo2)){
                    conectarNodos(nodo1, nodo2);
                }
            }
        }
    }
    
    private boolean esVisible(Node nodo1, Node nodo2){
        double[] coord1 = obtenerCoordenadas(nodo1);
        double[] coord2 = obtenerCoordenadas(nodo2);
        
        for(Obstaculo obstaculo : obstaculos){
            if(obstaculo.intersectaLinea(coord1, coord2)){
                return false;
            }
        }
        return true;
    }
    
    private void conectarNodos(Node nodo1, Node nodo2){
        double[] coord1 = obtenerCoordenadas(nodo1);
        double[] coord2 = obtenerCoordenadas(nodo2);

        double distancia = Math.sqrt(Math.pow(coord2[0] - coord1[0], 2) + Math.pow(coord2[1] - coord1[1], 2));
        Edge arista = grafo.addEdge(nodo1.getId() + "-" + nodo2.getId(), nodo1, nodo2, true);
        arista.setAttribute("length", distancia);
        arista.setAttribute("ui.label", String.format("%.2f", distancia));

        try {
            grafoMatriz.agregarArco(nodo1.getId(), nodo2.getId(), (float) distancia);
            grafoLista.agregarArco(nodo1.getId(), nodo2.getId(), (float) distancia);
        } catch (Exception e) {
            System.err.println("Error al conectar nodos: " + nodo1.getId() + " y " + nodo2.getId());
        }
    }
    
    private double[] obtenerCoordenadas(Node nodo){
        Object xy = nodo.getAttribute("xy");
        if (xy instanceof Object[]) {
            Object[] coords = (Object[]) xy;
            return new double[]{
                    ((Number) coords[0]).doubleValue(),
                    ((Number) coords[1]).doubleValue()
            };
        }
        throw new IllegalStateException("Coordenadas inválidas para el nodo: " + nodo.getId());
    }
    
    private int[] desplazarVertice(int[] vertice, int distancia){
        return new int[]{vertice[0] + distancia, vertice[1] + distancia};
    }
    
    public void calcularRutaOptima(){
        try{
            List<String> ruta = grafoLista.BFS("start");
            System.out.println("Ruta calculada: " + ruta);
            
            simularMovimiento(ruta);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void simularMovimiento(List<String> ruta){
        System.out.println("Simulacion de movimiento del Robot:");
        for (String nodo : ruta) {
            Node graphNode = grafo.getNode(nodo);
            double[] coords = obtenerCoordenadas(graphNode);
            robot.setPosition((int) coords[0], (int) coords[1]);
            System.out.println("Robot moviéndose a: " + robot);
            try {
                Thread.sleep(1000); // Esperar 1 segundo entre movimientos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("El robot ha llegado a la meta.");
    }
}
