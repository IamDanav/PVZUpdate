package Entities;



public class Sun {
    public int x, y;
    public int targetY;     // điểm dừng
    public int value = 25;
    public boolean falling = true;
    private int speed = 2;
    private int life = 300;


    public Sun(int x, int startY, int targetY) {
        this.x = x;
        this.y = startY;
        this.targetY = targetY;
    }
    public boolean isDead() {
        return life <= 0;
    }

    public void update() {
        if (falling) {
            y += speed;

            if (y >= targetY) {
                y = targetY;
                falling = false;
            }
        }
        else {
            life--;
        }
    }
}
