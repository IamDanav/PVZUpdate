package Entities;
import Core.Game;
public class Bullet {
    public int row;
    public double x;
    public boolean slow = false;

    public Bullet(int row, double x, boolean slow) {
        this.row = row;
        this.x = x;
        this.slow = slow;

    }

    public void update() {
        x += 5;
    }
}
