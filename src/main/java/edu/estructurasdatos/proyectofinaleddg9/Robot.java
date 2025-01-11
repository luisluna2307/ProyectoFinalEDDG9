package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
public class Robot {

    private int x, y;
    private int diametro;

    public Robot(int x, int y, int diametro) {
        this.x = x;
        this.y = y;
        this.diametro = diametro;
    }

    @Override
    public String toString() {
        return "Posición (" + x + ", " + y + "), Diámetro: " + diametro;
    }
}
