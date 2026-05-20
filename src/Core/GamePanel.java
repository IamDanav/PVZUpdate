package Core;

import javax.swing.*;
import java.awt.*;
import Entities.Plant.*;
import Entities.Zombie.*;
import Entities.Sun;
import Core.Game;
import java.awt.Color;

public class GamePanel extends JPanel {
    private int hoverRow = -1;
    private int hoverCol = -1;
    private int OffsetY = 100;
    private boolean shovelMode = false;
    Game game = Game.getInstance();
    private PlantType selectedPlant = null;

    private Image sunflowerReady;
    private Image sunflowerFade;
    private Image sunflowerPreview;

    private Image peashooterReady;
    private Image peashooterFaded;
    private Image peashooterPreview;

    private Image PotatoMineReady;
    private Image PotatoMineFaded;
    private Image PotatoMinePreview;

    private Image walNutReady;
    private Image walNutFaded;
    private Image walNutPreview;

    private Image repeaterReady;
    private Image repeaterFaded;
    private Image repeaterPreview;

    public GamePanel() {
        loadPeashooterImages();
        loadSunflowerImages();
        loadPotatoImages();
        loadWalnutImages();
        loadRepeaterImages();

        Mouse mouse = new Mouse(this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        new GameLoop(() -> {
            game.update();
            repaint();
        });
    }

    public void updateHoverGrid(int mouseX, int mouseY) {
        int col = mouseX / 100;
        int row = (mouseY - 100) / 100;

        if (row >= 0 && row < game.grid.rows && col < game.grid.cols) {
            hoverRow = row;
            hoverCol = col;
        } else {
            hoverRow = -1;
            hoverCol = -1;
        }
        repaint();
    }

    private void loadPeashooterImages() {
        try {
            java.net.URL readyURL = getClass().getResource("/image/peashooterpacket1.png");
            java.net.URL fadedURL = getClass().getResource("/image/peashooterpacket2.png");
            java.net.URL previewURL = getClass().getResource("/image/flashpeashooter1.png");

            if (readyURL != null) peashooterReady = new ImageIcon(readyURL).getImage();
            if (fadedURL != null) peashooterFaded = new ImageIcon(fadedURL).getImage();
            if (previewURL != null) peashooterPreview = new ImageIcon(previewURL).getImage();
        } catch (Exception e) {
            System.err.println("Error loading Peashooter assets: " + e.getMessage());
        }
    }

    private void loadSunflowerImages(){
        try {
            java.net.URL readyURL = getClass().getResource("/image/sunflowerpacket1.png");
            java.net.URL fadeURL = getClass().getResource("/image/sunflowerpacket2.png");
            java.net.URL previewURL = getClass().getResource("/image/sunfloweridle1.png");

            if (readyURL != null) sunflowerReady = new ImageIcon(readyURL).getImage();
            if (fadeURL != null) sunflowerFade = new ImageIcon(fadeURL).getImage();
            if (previewURL != null) sunflowerPreview = new ImageIcon(previewURL).getImage();
        } catch (Exception e){
            System.err.println("Error loading Sunflower assets:  " + e.getMessage());
        }
    }

    private void loadPotatoImages(){
        try {
            java.net.URL readyURL = getClass().getResource("/image/potatopacket1.png");
            java.net.URL fadedURL = getClass().getResource("/image/potatopacket2.png");
            java.net.URL previewURL = getClass().getResource("/image/potato1.png");

            if (readyURL != null) PotatoMineReady = new ImageIcon(readyURL).getImage();
            if (fadedURL != null) PotatoMineFaded = new ImageIcon(fadedURL).getImage();
            if (previewURL != null) PotatoMinePreview = new ImageIcon(previewURL).getImage();
        } catch (Exception e){
            System.err.println("Error loading PotatoMine assets:  " + e.getMessage());
        }
    }

    private void loadWalnutImages() {
        try {
            java.net.URL readyURL = getClass().getResource("/image/walnutpacket1.png");
            java.net.URL fadedURL = getClass().getResource("/image/walnutpacket2.png");
            java.net.URL previewURL = getClass().getResource("/image/walnut1.png");

            if (readyURL != null) walNutReady = new ImageIcon(readyURL).getImage();
            if (fadedURL != null) walNutFaded = new ImageIcon(fadedURL).getImage();
            if (previewURL != null) walNutPreview = new ImageIcon(walNutPreview != null ? null : previewURL).getImage();
        } catch (Exception e) {
            System.err.println("Error loading WalNut assets: " + e.getMessage());
        }
    }

    private void loadRepeaterImages(){
        try {
            java.net.URL readyURL = getClass().getResource("/image/repeaterpacket1.png");
            java.net.URL fadedURL = getClass().getResource("/image/repeaterpacket2.png");
            java.net.URL previewURL = getClass().getResource("/image/repeater1.png");

            if (readyURL != null) repeaterReady = new ImageIcon(readyURL).getImage();
            if (fadedURL != null) repeaterFaded = new ImageIcon(fadedURL).getImage();
            if (previewURL != null) repeaterPreview = new ImageIcon(previewURL).getImage();
        } catch (Exception e) {
            System.err.println("Error loading Repeater assets: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 900, 100);

        // --- SEED CARD CARD IMPLEMENTATIONS ---
        if (game.sun >= 50 && sunflowerReady != null){
            g.drawImage(sunflowerReady, 50, 20, 60, 60, null );
        } else if (sunflowerFade != null) {
            g.drawImage(sunflowerFade, 50, 20, 60, 60, null);
        }
        g.setColor(Color.BLACK);
        g.drawString("50", 60, 95);

        if (game.sun >= 100 && peashooterReady != null) {
            g.drawImage(peashooterReady, 150, 20, 60, 60, null);
        } else if (peashooterFaded != null) {
            g.drawImage(peashooterFaded, 150, 20, 60, 60, null);
        }
        g.setColor(Color.BLACK);
        g.drawString("100", 160, 95);

        if (game.sun >= 25 && PotatoMineReady != null) {
            g.drawImage(PotatoMineReady, 250, 20, 60, 60, null);
        } else if (PotatoMineFaded != null) {
            g.drawImage(PotatoMineFaded, 250, 20, 60, 60, null);
        }
        g.setColor(Color.BLACK);
        g.drawString("25", 260, 95);

        g.setColor(Color.CYAN);
        g.fillRect(350, 20, 60, 60);
        g.setColor(Color.BLACK);
        g.drawString("175", 360, 95);

        if (game.sun >= 200 && repeaterReady != null) {
            g.drawImage(repeaterReady, 450, 20, 60, 60, null);
        } else if (repeaterFaded != null) {
            g.drawImage(repeaterFaded, 450, 20, 60, 60, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(450, 20, 60, 60);
        }
        g.setColor(Color.BLACK);
        g.drawString("200", 460, 95);

        g.setColor(Color.MAGENTA);
        g.fillRect(550, 20, 60, 60);
        g.setColor(Color.BLACK);
        g.drawString("150", 560, 95);

        g.setColor(Color.RED);
        g.fillRect(650, 20, 60, 60);
        g.setColor(Color.BLACK);
        g.drawString("150", 660, 95);

        if (game.sun >= 50 && walNutReady != null){
            g.drawImage(walNutReady, 750, 20, 60, 60, null );
        } else if (walNutFaded != null) {
            g.drawImage(walNutFaded, 750, 20, 60, 60, null);
        } else {
            g.setColor(Color.ORANGE);
            g.fillRect(750, 20, 60, 60);
        }
        g.setColor(Color.BLACK);
        g.drawString("50", 760, 95);

        // --- HIGHLIGHT HIGHLIGHT SELECTION HOOKS ---
        g.setColor(Color.BLACK);
        if (selectedPlant == PlantType.SUNFLOWER)    g.drawRect(50, 20, 60, 60);
        if (selectedPlant == PlantType.PEASHOOTER)   g.drawRect(150, 20, 60, 60);
        if (selectedPlant == PlantType.POTATOMINE)   g.drawRect(250, 20, 60, 60);
        if (selectedPlant == PlantType.SNOWPEA)      g.drawRect(350, 20, 60, 60);
        if (selectedPlant == PlantType.REPEATER)     g.drawRect(450, 20, 60, 60);
        if (selectedPlant == PlantType.CHOMPER)      g.drawRect(550, 20, 60, 60);
        if (selectedPlant == PlantType.CHERRYBOMB)   g.drawRect(650, 20, 60, 60);
        if (selectedPlant == PlantType.WALL_NUT)     g.drawRect(750, 20, 60, 60);

        if(shovelMode) g.drawRect(850, 20, 40, 40);
        g.setColor(Color.GRAY);
        g.fillRect(850, 20, 40, 40);
        g.setColor(Color.BLACK);
        g.drawString("Shovel", 853, 45);

        game.grid.draw(g);

        // Draw Live Placed Plants
        for (int r = 0; r < game.grid.rows; r++) {
            for (int c = 0; c < game.grid.cols; c++) {
                var plant = game.grid.cells[r][c].plant;
                if (plant instanceof PeaShooter)  ((PeaShooter) plant).draw(g);
                else if (plant instanceof SunFlower) ((SunFlower) plant).draw(g);
                else if (plant instanceof PotatoMine) ((PotatoMine) plant).draw(g);
                else if (plant instanceof WallNut)   ((WallNut) plant).draw(g);
                else if (plant instanceof Repeater)  ((Repeater) plant).draw(g);
            }
        }

        // 1. Draw Lawnmowers Background Layer
        g.setColor(Color.GRAY);
        for(var m : game.mowers) {
            if(!m.used || m.active) {
                g.fillRect((int)m.x, m.row * 100 + OffsetY + 30, 50, 40);
            }
        }

        // 2. Draw Zombies Midground Layer
        // 2. Draw Zombies Midground Layer
        for (var z : game.Zombies) {
            if (z instanceof BrownSuit) {
                ((BrownSuit) z).draw(g);
                continue;
            } else if (z instanceof ConeHead) {
                ((ConeHead) z).draw(g);
                continue;
            } else if (z instanceof BucketHead) {
                ((BucketHead) z).draw(g);
                continue;
            }

            if (z instanceof NewsPaper)  g.setColor(Color.WHITE);
            else if (z instanceof PoleVaulting) g.setColor(Color.BLACK);
            g.fillRect((int)z.x, z.row * 100 + OffsetY + 20, 60, 60);
        }

        // 3. ✅ LAYER FIXED: Draw Active Flying Bullets over the Zombies!
        for (var b : Game.getInstance().bullets) {
            b.draw(g, OffsetY);
        }

        // --- ALPHA SEED SELECTION HOVER PREVIEWS ---
        if (hoverRow != -1) {
            Graphics2D g2 = (Graphics2D) g;
            java.awt.Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

            if (selectedPlant != null && game.grid.cells[hoverRow][hoverCol].plant == null) {
                int hoverX = hoverCol * 100 + 15;
                int hoverY = hoverRow * 100 + 100 + 15;

                if (selectedPlant == PlantType.PEASHOOTER && peashooterPreview != null) {
                    g2.drawImage(peashooterPreview, hoverX, hoverY, 70, 70, null);
                } else if (selectedPlant == PlantType.SUNFLOWER && sunflowerPreview != null) {
                    g2.drawImage(sunflowerPreview, hoverX, hoverY, 70, 70, null);
                } else if (selectedPlant == PlantType.POTATOMINE && PotatoMinePreview != null) {
                    g2.drawImage(PotatoMinePreview, hoverX, hoverY, 70, 70, null);
                } else if (selectedPlant == PlantType.WALL_NUT) {
                    if (walNutPreview != null) g2.drawImage(walNutPreview, hoverX, hoverY, 70, 70, null);
                    else { g2.setColor(Color.ORANGE); g2.fillRect(hoverX, hoverY, 70, 70); }
                } else if (selectedPlant == PlantType.REPEATER) {
                    if (repeaterPreview != null) g2.drawImage(repeaterPreview, hoverX, hoverY, 70, 70, null);
                    else { g2.setColor(Color.GREEN); g2.fillRect(hoverX, hoverY, 70, 70); }
                }
            }

            if (shovelMode && game.grid.cells[hoverRow][hoverCol].plant != null) {
                g2.setColor(Color.RED);
                g2.fillRect(hoverCol * 100, hoverRow * 100 + 100, 100, 100);
            }
            g2.setComposite(originalComposite);
        }

        // 4. Draw Drop Suns Foreground Top Layer
        for (var s : game.suns) {
            s.draw(g);
        }

        g.setColor(Color.BLACK);
        g.drawString("Sun: " + game.sun, 20, 20);
    }

    public void handleClick(int x, int y) {
        for (int i = 0; i < game.suns.size(); i++) {
            var s = game.suns.get(i);
            if (x >= s.x && x <= s.x + 40 && y >= s.y && y <= s.y + 40) {
                game.sun += s.value;
                game.suns.remove(i);
                repaint();
                return;
            }
        }

        if (y <= 100) {
            if (x >= 50 && x <= 110)        selectedPlant = PlantType.SUNFLOWER;
            else if (x >= 150 && x <= 210) selectedPlant = PlantType.PEASHOOTER;
            else if (x >= 250 && x <= 310) selectedPlant = PlantType.POTATOMINE;
            else if (x >= 350 && x <= 410) selectedPlant = PlantType.SNOWPEA;
            else if (x >= 450 && x <= 510) selectedPlant = PlantType.REPEATER;
            else if (x >= 550 && x <= 610) selectedPlant = PlantType.CHOMPER;
            else if (x >= 650 && x <= 710) selectedPlant = PlantType.CHERRYBOMB;
            else if (x >= 750 && x <= 810) selectedPlant = PlantType.WALL_NUT;
            else if(x >= 850 && x <= 890) {
                shovelMode = !shovelMode;
                selectedPlant = null;
            }
            return;
        }

        int col = x / 100;
        int row = (y - 100) / 100;
        if (row >= game.grid.rows || col >= game.grid.cols) return;

        if(shovelMode) {
            game.grid.cells[row][col].plant = null;
            repaint();
            return;
        }

        if (game.grid.cells[row][col].plant == null && selectedPlant != null) {
            switch (selectedPlant) {
                case WALL_NUT:
                    if (game.sun >= 50) { game.grid.cells[row][col].plant = new WallNut(row, col); game.sun -= 50; }
                    break;
                case CHERRYBOMB:
                    if (game.sun >= 150) { game.grid.cells[row][col].plant = new CherryBomb(row, col); game.sun -= 150; }
                    break;
                case SUNFLOWER:
                    if (game.sun >= 50) { game.grid.cells[row][col].plant = new SunFlower(row, col); game.sun -= 50; }
                    break;
                case PEASHOOTER:
                    if (game.sun >= 100) { game.grid.cells[row][col].plant = new PeaShooter(row, col); game.sun -= 100; }
                    break;
                case POTATOMINE:
                    if (game.sun >= 25) { game.grid.cells[row][col].plant = new PotatoMine(row, col); game.sun -= 25; }
                    break;
                case SNOWPEA:
                    if (game.sun >= 175) { game.grid.cells[row][col].plant = new SnowPea(row, col); game.sun -= 175; }
                    break;
                case REPEATER:
                    if (game.sun >= 200) { game.grid.cells[row][col].plant = new Repeater(row, col); game.sun -= 200; }
                    break;
                case CHOMPER:
                    if (game.sun >= 150) { game.grid.cells[row][col].plant = new Chomper(row, col); game.sun -= 150; }
                    break;
            }
        }
        repaint();
    }
}