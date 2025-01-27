/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;

public class TestSwing {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // Asegurarse de que no esté en modo headless

        JFrame frame = new JFrame("Prueba Swing");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("¡El entorno gráfico funciona!", SwingConstants.CENTER);
        frame.add(label);
        frame.setVisible(true);
    }
}
