package Entities;

public class LawnMower {
    public int row;
    public double x = 0;
    public boolean active = false;
    public boolean used = false;

    public LawnMower(int row) {
        this.row = row;
    }
    public void update() {
        if(active) {
            x += 8;
        }
    }
}
