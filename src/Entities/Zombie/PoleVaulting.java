package Entities.Zombie;

import Core.Game;
import Entities.Zombies;

public class PoleVaulting extends Zombies {

    private boolean jumped = false;

    public PoleVaulting(int row) {
        super(row, 175, 2);
    }

    @Override
    public void update() {


        if (slowTimer > 0) {
            slowTimer--;
        } else {
            speed = originalSpeed;
        }

        int col = (int)((x + 30) / 100);

        // check trong map
        if (col >= 0 &&
                col < Game.getInstance().grid.cols &&
                row >= 0 &&
                row < Game.getInstance().grid.rows) {

            var cell = Game.getInstance().grid.cells[row][col];


            if (!jumped && cell.plant != null) {

                x -= 100;

                jumped = true;

                speed = 1;

                return;
            }

           //after jumping the first time, eat tree
            if (jumped && cell.plant != null) {

                cell.plant.hp -= 1;

                if (cell.plant.hp <= 0) {
                    cell.plant = null;
                }

                return;
            }
        }


        x -= speed;
    }
}