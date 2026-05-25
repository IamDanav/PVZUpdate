package Entities.Plant;
import Entities.Plants;
import Core.Game;
import Entities.Bullet;

public class Repeater extends Plants {

    private int cooldown = 0;

    public Repeater(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {
        cooldown++;

        if (cooldown >= 50 && Game.getInstance().hasZombieAhead(row, col)) {

            Game.getInstance().bullets.add(new Bullet(row, col * 100 + 50, false));
            Game.getInstance().bullets.add(new Bullet(row, col * 100 + 30, false));

            cooldown = 0;
        }
    }
}
