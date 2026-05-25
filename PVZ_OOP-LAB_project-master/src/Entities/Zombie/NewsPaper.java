package Entities.Zombie;
import Entities.Zombies;

public class NewsPaper extends Zombies {

    private boolean angry = false;

    public NewsPaper(int row) {
        super(row, 150, 1);
    }

    @Override
    public void update() {

        if (!angry && hp <= 70) {
            angry = true;
            speed = 3;
        }

        x -= speed;
    }
}