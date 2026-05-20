package Entities;

public abstract class Zombies {
    public double x;
    public int row;
    public int hp;
    public double speed;
    public double originalSpeed;
    public int slowTimer = 0;

    public Zombies(int r, int hp, double speed) {
        row = r;
        this.hp = hp;
        this.speed = speed;
        x = 900;
        this.originalSpeed = speed;
    }

    public void update() {

        // slow effect
        if (slowTimer > 0) {
            slowTimer--;
        } else {
            speed = originalSpeed;
        }


        int col = (int)((x + 30) / 100);


        if (col >= 0 &&
                col < Core.Game.getInstance().grid.cols &&
                row >= 0 &&
                row < Core.Game.getInstance().grid.rows) {

            var cell = Core.Game.getInstance().grid.cells[row][col];


            if (cell.plant != null) {

                cell.plant.hp -= 1;

                // plant die
                if (cell.plant.hp <= 0) {
                    cell.plant = null;
                }

                return;
            }
        }

        // continue walk
        x -= speed;
    }
}
