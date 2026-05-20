package Entities.Plant;

import Core.Game;
import Entities.Plants;

public class Chomper extends Plants {

    private int cooldown = 0;

    public Chomper(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {


        if (cooldown > 0) {
            cooldown--;
            return;
        }


        for (int i = 0; i < Game.getInstance().Zombies.size(); i++) {

            var z = Game.getInstance().Zombies.get(i);

            int zCol = (int)((z.x + 30) / 100);


            if (z.row == row &&
                    (zCol == col || zCol == col + 1)) {


                Game.getInstance().Zombies.remove(i);


                cooldown = 100;

                break;
            }
        }
    }

    public boolean isEating() {
        return cooldown > 0;
    }
}
