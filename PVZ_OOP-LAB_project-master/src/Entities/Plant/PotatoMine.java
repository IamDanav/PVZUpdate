package Entities.Plant;

import Core.Game;
import Entities.Plants;

public class PotatoMine extends Plants {

    private int timer = 0;

    private boolean armed = false;

    public PotatoMine(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {

        timer++;


        if (timer >= 50) {
            armed = true;
        }


        if (!armed) return;


        for (int i = 0; i < Game.getInstance().Zombies.size(); i++) {

            var z = Game.getInstance().Zombies.get(i);

            int zCol = (int)((z.x + 30) / 100);

            if (z.row == row && zCol == col) {
                //explode
                Game.getInstance().Zombies.remove(i);
                //remove
                Game.getInstance().grid.cells[row][col].plant = null;

                break;
            }
        }
    }

    public boolean isArmed() {
        return armed;
    }
}
