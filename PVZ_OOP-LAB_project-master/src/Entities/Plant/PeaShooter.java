package Entities.Plant;
import Core.Game;
import Entities.Bullet;
import Entities.Plants;
public class PeaShooter extends Plants {
private int timer;
public PeaShooter(int r, int c) {
    super(r, c);
}
    @Override
    public void update() {
        timer++;

        // 1.5s/1 bullet
        if (timer >= 15 && Game.getInstance().hasZombieAhead(row, col)) {
            Game.getInstance().bullets.add(
                    new Bullet(row, col * 100 + 50, false)
            );
            timer = 0;
        }
    }
}
