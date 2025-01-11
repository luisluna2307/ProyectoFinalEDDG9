package edu.estructurasdatos.proyectofinaleddg9;

/**
 *
 * @author Luis Luna
 */
public class Meta {

    private int x, y;

    public Meta(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Posición (" + x + ", " + y + ")";
    }
}
