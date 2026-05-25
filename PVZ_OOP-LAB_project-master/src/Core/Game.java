package Core;
import Entities.Bullet;
import Entities.LawnMower;
import Entities.Sun;
import Entities.Zombie.*;
import Entities.Zombies;
import Map.Grid;
import java.util.ArrayList;

public class Game {
    private static Game instance;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Zombies> Zombies = new ArrayList<>();
    public ArrayList<Sun> suns = new ArrayList<>();
    public int sun = 50; // initial sun
    public Grid grid;
    public ArrayList<LawnMower> mowers = new ArrayList<>();


    // constructor

    private Game() {
        grid = new Grid(5, 9);
        for(int i = 0; i < grid.rows; i++) {
            mowers.add(new LawnMower(i));
        }
    }

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }
    private void handleCollision() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);

            for (int j = 0; j < Zombies.size(); j++) {
                Zombies z = Zombies.get(j);

                if (b.row == z.row && Math.abs(b.x - z.x) < 20) {
                    z.hp -= 20;

                    //  snow bullet làm chậm
                    if (b.slow) {
                        z.speed = z.originalSpeed * 0.5;
                        z.slowTimer = 50;
                    }

                    bullets.remove(i);
                    i--;

                    if (z.hp <= 0) {
                        Zombies.remove(j);
                        j--;
                    }
                    break;
                }
            }
        }
    }
    public void update() {
        // update sun
        for (int i = 0; i < suns.size(); i++) {
            suns.get(i).update();
        }
        //random sun
        if (Math.random() < 0.01) {
            int col = (int)(Math.random() * grid.cols);

            int x = col * 100 + 40;
            int targetY = (int)(Math.random() * grid.rows) * 100 + 140;

            suns.add(new Sun(x, 0, targetY));
        }
        // update exist sun
        for (int i = 0; i < suns.size(); i++) {
            suns.get(i).update();

            if (suns.get(i).isDead()) {
                suns.remove(i);
                i--;
            }
        }
        //  spawn zombie random
        if (Math.random() < 0.02) {

            int row = (int)(Math.random() * grid.rows);

            int type = (int)(Math.random() * 5);

            switch (type) {

                case 0:
                    Zombies.add(new BrownSuit(row));
                    break;

                case 1:
                    Zombies.add(new ConeHead(row));
                    break;

                case 2:
                    Zombies.add(new BucketHead(row));
                    break;

                case 3:
                    Zombies.add(new NewsPaper(row));
                    break;

                case 4:
                    Zombies.add(new PoleVaulting(row));
                    break;
            }
        }

        //  update grid (plant bắn đạn)
        grid.update();

        //  update zombie
        for (int i = 0; i < Zombies.size(); i++) {
            Zombies.get(i).update();
        }
        for (int i = 0; i < Zombies.size(); i++) {
            if (Zombies.get(i).hp <= 0) {
                Zombies.remove(i);
                i--;
            }
        }

        // update bullet
        for (int i = 0; i < bullets.size(); i++) {

            bullets.get(i).update();

            if (bullets.get(i).x > 1000) {
                bullets.remove(i);
                i--;
            }
        }

        //update mower
        for (int i = 0; i < mowers.size(); i++) {

            LawnMower m = mowers.get(i);

            m.update();


            if (m.active) {

                for (int j = 0; j < Zombies.size(); j++) {

                    Zombies z = Zombies.get(j);

                    if (z.row == m.row &&
                            Math.abs(z.x - m.x) < 60) {

                        Zombies.remove(j);
                        j--;
                    }
                }
            }
        }
        //  collision
        handleCollision();
        for (var z : Zombies) {

            if (z.x <= 0) {

                LawnMower mower = mowers.get(z.row);

                // if not used, activate
                if (!mower.used) {

                    mower.active = true;
                    mower.used = true;

                    mower.x = 0;
                }
                else {

                    System.out.println("GAME OVER");
                    System.exit(0);
                }
            }
        }
    }
    public boolean hasZombieAhead(int row, int col) {

        for (var z : Zombies) {

            int zCol = (int)(z.x / 100);

            if (z.row == row && zCol >= col) {
                return true;
            }
        }

        return false;
    }
}
