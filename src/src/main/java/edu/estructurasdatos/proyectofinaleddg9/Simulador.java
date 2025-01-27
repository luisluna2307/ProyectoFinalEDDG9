/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.estructurasdatos.proyectofinaleddg9;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Anthony
 */
public class Simulador extends JPanel {
    private Ambiente ambiente;
    private List<Point> ruta;
    private GrafoMatriz<Point> grafo;
    private int pasoActual;

    public Simulador(Ambiente ambiente, List<Point> ruta, GrafoMatriz<Point> grafo) {
        this.ambiente = ambiente;
        this.ruta = ruta;
        this.grafo = grafo;
        this.pasoActual = 0;
        this.setPreferredSize(new Dimension(1200, 700));
    }

    public void iniciarSimulacion() {
        JFrame frame = new JFrame("Simulación - Grafo Completo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        CaminoMasCorto ventanaCamino = new CaminoMasCorto();
        ventanaCamino.mostrar();

        Timer timer = new Timer(500, e -> {
            if (pasoActual < ruta.size()) {
                pasoActual++;
                repaint();
                ventanaCamino.repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 1200, 700);

        g.setColor(Color.RED);
        for (Rectangle obstaculo : ambiente.getObstaculos()) {
            g.fillRect(obstaculo.x, obstaculo.y, obstaculo.width, obstaculo.height);
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < grafo.getVertices().size(); i++) {
            for (int j = i + 1; j < grafo.getVertices().size(); j++) {
                float peso = 0;
                try {
                    peso = grafo.getPeso(grafo.getVertices().get(i), grafo.getVertices().get(j));
                } catch (Exception ignored) {
                }
                if (peso > 0) {
                    Point p1 = grafo.getVertices().get(i);
                    Point p2 = grafo.getVertices().get(j);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        g.setColor(Color.BLUE);
        for (int i = 1; i < ruta.size(); i++) { // Dibujar TODO el camino más corto
            Point p1 = ruta.get(i - 1);
            Point p2 = ruta.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        g.setColor(Color.GREEN);
        if (pasoActual < ruta.size()) {
            Point robot = ruta.get(pasoActual);
            g.fillOval(robot.x - 10, robot.y - 10, 20, 20);
        }

        g.setColor(Color.MAGENTA);
        Point inicio = ambiente.getPosRobot();
        g.fillOval(inicio.x - 10, inicio.y - 10, 20, 20);
        Point meta = ambiente.getPosMeta();
        g.setColor(Color.ORANGE);
        g.fillOval(meta.x - 10, meta.y - 10, 20, 20);
    }

    private class CaminoMasCorto extends JPanel {
        public CaminoMasCorto() {
            this.setPreferredSize(new Dimension(1200, 700));
        }

        public void mostrar() {
            JFrame frame = new JFrame("Simulación - Camino Más Corto");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 700);
            frame.add(this);
            frame.pack();
            frame.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, 1200, 700);

            g.setColor(Color.RED);
            for (Rectangle obstaculo : ambiente.getObstaculos()) {
                g.fillRect(obstaculo.x, obstaculo.y, obstaculo.width, obstaculo.height);
            }

            g.setColor(Color.BLUE);
            for (int i = 1; i < ruta.size(); i++) { // Dibujar todo el camino más corto
                Point p1 = ruta.get(i - 1);
                Point p2 = ruta.get(i);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }

            g.setColor(Color.MAGENTA);
            Point inicio = ambiente.getPosRobot();
            g.fillOval(inicio.x - 10, inicio.y - 10, 20, 20);
            g.setColor(Color.ORANGE);
            Point meta = ambiente.getPosMeta();
            g.fillOval(meta.x - 10, meta.y - 10, 20, 20);
        }
    }
}