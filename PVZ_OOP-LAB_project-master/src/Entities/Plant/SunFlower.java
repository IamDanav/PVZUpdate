package Entities.Plant;
import Core.Game;
import Entities.Plants;
import Entities.Sun;

public class SunFlower extends Plants {
    public int timer = 0;

    public SunFlower(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {
        timer++;

        if (timer >= 100) {
            int x = col * 100 + 40;

            int startY = 0;
            int targetY = row * 100 + 140; // spawn sun from top and down to targetY

            Game.getInstance().suns.add(new Sun(x, startY, targetY));

            timer = 0;
        }
    }
}