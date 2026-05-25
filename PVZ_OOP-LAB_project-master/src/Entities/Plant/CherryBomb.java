package Entities.Plant;
import Entities.Plants;
import Core.Game;

public class CherryBomb extends Plants {

    private int timer = 30;

    public CherryBomb(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {
        timer--;

        if (timer <= 0) {
            explode();
        }
    }

    private void explode() {
        var zombies = Game.getInstance().Zombies;

        for (int i = 0; i < zombies.size(); i++) {
            var z = zombies.get(i);


            int zCol = (int)(z.x / 100);


            if (Math.abs(z.row - row) <= 1 && // check 3 * 3
                    Math.abs(zCol - col) <= 1) {

                z.hp = 0;
            }
        }

        Game.getInstance().grid.cells[row][col].plant = null;
    }
}
