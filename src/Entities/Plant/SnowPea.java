package Entities.Plant;
import Core.Game;
import Entities.Bullet;
import Entities.Plants;

public class SnowPea extends Plants {
    private int cooldown = 0;
    public SnowPea(int r, int c) {
        super(r, c);
    }
    @Override
    public void update() {
        cooldown++;

        if (cooldown >= 50 && Game.getInstance().hasZombieAhead(row, col)) {
            Game.getInstance().bullets.add(new Bullet(
                    row,
                    col * 100 + 50,
                    true
            ));
            cooldown = 0;
        }
    }
}
