package Entities.Plant;
import Core.Game;
import Entities.Plants;
public class WallNut extends Plants {

    public int hp = 500;

    public WallNut(int r, int c) {
        super(r, c);
    }

    @Override
    public void update() {
        //just static for tanking
    }
}
