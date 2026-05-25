package Map;
import Entities.Plant.*;
import Core.GamePanel;
import java.awt.*;

public class Grid {
    public int rows, cols;
    public Cell[][] cells;

    public Grid(int r, int c) {
        rows = r;
        cols = c;
        cells = new Cell[r][c];
        for (int i = 0; i < r; i++) for (int j = 0; j < c; j++) cells[i][j] = new Cell(i, j);
    }

    public void update() {
        for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) cells[i][j].update();
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int offsetY = 112;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (cells[i][j].plant != null) {
                    PlantType type = PlantType.PEASHOOTER;
                    if (cells[i][j].plant instanceof SunFlower) type = PlantType.SUNFLOWER;
                    else if (cells[i][j].plant instanceof WallNut) type = PlantType.WALL_NUT;
                    else if (cells[i][j].plant instanceof CherryBomb) type = PlantType.CHERRYBOMB;
                    else if (cells[i][j].plant instanceof PotatoMine) type = PlantType.POTATOMINE;
                    else if (cells[i][j].plant instanceof SnowPea) type = PlantType.SNOWPEA;
                    else if (cells[i][j].plant instanceof Repeater) type = PlantType.REPEATER;
                    else if (cells[i][j].plant instanceof Chomper) type = PlantType.CHOMPER;
                    GamePanel.drawPlantIcon(g2, type, j * 100 + 50, i * 100 + offsetY + 54, 0.95);
                }
            }
        }
    }
}
