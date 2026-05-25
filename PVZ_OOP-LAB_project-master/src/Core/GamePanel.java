package Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Entities.Plant.*;
import Entities.Zombie.*;
import Entities.Sun;
import Entities.Zombies;

public class GamePanel extends JPanel {
    private int hoverRow = -1;
    private int hoverCol = -1;
    private final int offsetY = 112;
    private final int cell = 100;
    private boolean shovelMode = false;
    Game game = Game.getInstance();
    private PlantType selectedPlant = null;

    private final PlantType[] cardTypes = {
            PlantType.SUNFLOWER, PlantType.PEASHOOTER, PlantType.POTATOMINE, PlantType.SNOWPEA,
            PlantType.REPEATER, PlantType.CHOMPER, PlantType.CHERRYBOMB, PlantType.WALL_NUT
    };
    private final int[] cardCosts = {50, 100, 25, 175, 200, 150, 150, 50};
    private final String[] cardNames = {"Sunflower", "Peashooter", "Potato", "Snow Pea", "Repeater", "Chomper", "Cherry", "Wall-nut"};

    public GamePanel() {
        setPreferredSize(new Dimension(1040, 650));
        setBackground(new Color(61, 148, 37));
        new GameLoop(() -> {
            game.update();
            repaint();
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { handleClick(e.getX(), e.getY()); }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = e.getX() / cell;
                int row = (e.getY() - offsetY) / cell;
                if (row >= 0 && row < game.grid.rows && col >= 0 && col < game.grid.cols) {
                    hoverRow = row;
                    hoverCol = col;
                } else {
                    hoverRow = -1;
                    hoverCol = -1;
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawSkyAndFence(g2);
        drawTopBar(g2);
        drawLawn(g2);
        game.grid.draw(g2);
        drawBullets(g2);
        drawHover(g2);
        drawMowers(g2);
        drawZombies(g2);
        drawSuns(g2);
        drawStatusText(g2);
    }

    private void drawSkyAndFence(Graphics2D g2) {
        g2.setColor(new Color(177, 218, 110));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(new Color(214, 189, 145));
        for (int x = 0; x < getWidth(); x += 60) {
            g2.fillRoundRect(x, 22, 48, 78, 10, 10);
            g2.setColor(new Color(135, 93, 55));
            g2.drawLine(x + 48, 22, x + 48, 100);
            g2.setColor(new Color(214, 189, 145));
        }
    }

    private void drawTopBar(Graphics2D g2) {
        g2.setColor(new Color(93, 66, 39));
        g2.fillRoundRect(10, 8, 900, 92, 16, 16);
        g2.setColor(new Color(242, 205, 94));
        g2.fillRoundRect(20, 22, 92, 62, 14, 14);
        drawSunIcon(g2, 45, 52, 18);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString(String.valueOf(game.sun), 68, 59);

        for (int i = 0; i < cardTypes.length; i++) {
            int x = 122 + i * 86;
            drawSeedCard(g2, x, 18, cardTypes[i], cardCosts[i], cardNames[i], selectedPlant == cardTypes[i]);
        }
        drawShovel(g2, 825, 20, shovelMode);
        g2.setColor(new Color(69, 54, 42));
        g2.fillRoundRect(925, 16, 90, 36, 10, 10);
        g2.setColor(new Color(78, 228, 58));
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Menu", 943, 40);
    }

    private void drawSeedCard(Graphics2D g2, int x, int y, PlantType type, int cost, String name, boolean selected) {
        g2.setColor(selected ? new Color(255, 232, 96) : new Color(230, 220, 163));
        g2.fillRoundRect(x, y, 74, 72, 10, 10);
        g2.setColor(new Color(87, 57, 33));
        g2.setStroke(new BasicStroke(selected ? 4 : 2));
        g2.drawRoundRect(x, y, 74, 72, 10, 10);
        drawPlantIcon(g2, type, x + 37, y + 30, 0.55);
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(cost), x + 26, y + 66);
    }

    private void drawLawn(Graphics2D g2) {
        g2.setColor(new Color(56, 167, 50));
        g2.fillRect(0, offsetY, 900, 500);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 9; c++) {
                g2.setColor(((r + c) % 2 == 0) ? new Color(67, 181, 61) : new Color(50, 154, 48));
                g2.fillRect(c * cell, offsetY + r * cell, cell, cell);
                g2.setColor(new Color(39, 126, 42, 90));
                g2.drawRect(c * cell, offsetY + r * cell, cell, cell);
            }
        }
        g2.setColor(new Color(34, 116, 33, 90));
        for (int i = 0; i < 120; i++) {
            int x = (i * 73) % 900;
            int y = offsetY + (i * 41) % 500;
            g2.fillOval(x, y, 6, 3);
        }
    }

    private void drawBullets(Graphics2D g2) {
        for (var b : game.bullets) {
            g2.setColor(b.slow ? new Color(117, 216, 255) : new Color(130, 225, 51));
            g2.fillOval((int)b.x, b.row * cell + offsetY + 42, 18, 18);
            g2.setColor(new Color(25, 95, 21));
            g2.drawOval((int)b.x, b.row * cell + offsetY + 42, 18, 18);
        }
    }

    private void drawHover(Graphics2D g2) {
        if (hoverRow == -1) return;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f));
        if (selectedPlant != null && game.grid.cells[hoverRow][hoverCol].plant == null) {
            drawPlantIcon(g2, selectedPlant, hoverCol * cell + 50, hoverRow * cell + offsetY + 54, 0.85);
        }
        if (shovelMode && game.grid.cells[hoverRow][hoverCol].plant != null) {
            g2.setColor(Color.RED);
            g2.fillRect(hoverCol * cell, hoverRow * cell + offsetY, cell, cell);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawMowers(Graphics2D g2) {
        for (var m : game.mowers) {
            if (!m.used || m.active) {
                int y = m.row * cell + offsetY + 36;
                g2.setColor(new Color(174, 28, 28));
                g2.fillRoundRect((int)m.x + 5, y, 55, 32, 8, 8);
                g2.setColor(Color.DARK_GRAY);
                g2.fillOval((int)m.x + 8, y + 25, 16, 16);
                g2.fillOval((int)m.x + 42, y + 25, 16, 16);
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine((int)m.x + 45, y, (int)m.x + 65, y - 18);
            }
        }
    }

    private void drawZombies(Graphics2D g2) {
        for (var z : game.Zombies) drawZombieIcon(g2, z, (int)z.x + 30, z.row * cell + offsetY + 52);
    }

    private void drawSuns(Graphics2D g2) {
        for (var s : game.suns) drawSunIcon(g2, s.x + 20, s.y + 20, 22);
    }

    private void drawStatusText(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(255, 245, 150));
        g2.drawString("Level 1-7", 760, 630);
    }

    public static void drawPlantIcon(Graphics2D g2, PlantType type, int cx, int cy, double scale) {
        int s = (int)(42 * scale);
        g2.setStroke(new BasicStroke(Math.max(1, (int)(2 * scale))));
        g2.setColor(new Color(35, 120, 38));
        g2.fillRect(cx - 4, cy + s/4, 8, s/2);
        g2.fillOval(cx - s/2, cy + s/2, s/2, s/4);
        g2.fillOval(cx, cy + s/2, s/2, s/4);
        if (type == PlantType.SUNFLOWER) {
            g2.setColor(new Color(255, 215, 45));
            for (int i = 0; i < 10; i++) {
                double a = i * Math.PI / 5;
                g2.fillOval(cx + (int)(Math.cos(a)*s/2) - s/5, cy + (int)(Math.sin(a)*s/2) - s/5, s/2, s/2);
            }
            g2.setColor(new Color(126, 77, 30));
            g2.fillOval(cx - s/3, cy - s/3, 2*s/3, 2*s/3);
        } else if (type == PlantType.WALL_NUT) {
            g2.setColor(new Color(170, 109, 40));
            g2.fillOval(cx - s/2, cy - s/2, s, s + 10);
            g2.setColor(Color.BLACK); g2.fillOval(cx - 8, cy - 5, 4, 4); g2.fillOval(cx + 6, cy - 5, 4, 4);
        } else if (type == PlantType.CHERRYBOMB) {
            g2.setColor(Color.RED); g2.fillOval(cx - 20, cy - 15, s/2, s/2); g2.fillOval(cx, cy - 15, s/2, s/2);
            g2.setColor(new Color(37, 127, 39)); g2.drawLine(cx, cy - 12, cx + 10, cy - 30);
        } else if (type == PlantType.POTATOMINE) {
            g2.setColor(new Color(112, 79, 47)); g2.fillOval(cx - s/2, cy - s/5, s, s/2);
            g2.setColor(Color.LIGHT_GRAY); g2.fillOval(cx - 4, cy - 22, 8, 8); g2.drawLine(cx, cy - 14, cx, cy - 5);
        } else if (type == PlantType.CHOMPER) {
            g2.setColor(new Color(154, 48, 190)); g2.fillOval(cx - s/2, cy - s/2, s, s);
            g2.setColor(Color.WHITE); g2.fillArc(cx - s/2, cy - 5, s, s/2, 0, -180);
        } else {
            g2.setColor(type == PlantType.SNOWPEA ? new Color(120, 218, 255) : new Color(75, 203, 54));
            g2.fillOval(cx - s/2, cy - s/3, s, 2*s/3);
            g2.fillOval(cx + s/4, cy - 10, s/2, s/3);
            g2.setColor(Color.BLACK); g2.fillOval(cx - 8, cy - 5, 4, 4);
            if (type == PlantType.REPEATER) { g2.setColor(new Color(75, 203, 54)); g2.fillOval(cx + s/2, cy - 8, s/2, s/3); }
        }
    }

    private void drawZombieIcon(Graphics2D g2, Zombies z, int cx, int cy) {
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(78, 94, 76));
        g2.fillOval(cx - 18, cy - 48, 36, 36);
        g2.setColor(new Color(93, 65, 45));
        g2.fillRoundRect(cx - 18, cy - 14, 36, 45, 8, 8);
        g2.setColor(new Color(70, 90, 72));
        g2.drawLine(cx - 15, cy + 28, cx - 22, cy + 48);
        g2.drawLine(cx + 13, cy + 28, cx + 21, cy + 48);
        g2.setColor(Color.WHITE); g2.fillOval(cx - 8, cy - 38, 7, 7); g2.fillOval(cx + 5, cy - 38, 7, 7);
        g2.setColor(Color.BLACK); g2.fillOval(cx - 6, cy - 36, 3, 3); g2.fillOval(cx + 7, cy - 36, 3, 3);
        if (z instanceof ConeHead) { g2.setColor(new Color(233, 116, 21)); g2.fillPolygon(new int[]{cx-16,cx,cx+16}, new int[]{cy-48,cy-82,cy-48}, 3); }
        else if (z instanceof BucketHead) { g2.setColor(Color.GRAY); g2.fillRect(cx - 18, cy - 67, 36, 22); }
        else if (z instanceof NewsPaper) { g2.setColor(Color.WHITE); g2.fillRect(cx - 30, cy - 8, 28, 22); g2.setColor(Color.BLACK); g2.drawString("NEWS", cx - 29, cy + 7); }
        else if (z instanceof PoleVaulting) { g2.setColor(new Color(110, 65, 39)); g2.drawLine(cx - 35, cy + 45, cx + 35, cy - 55); }
    }

    private void drawSunIcon(Graphics2D g2, int cx, int cy, int r) {
        g2.setColor(new Color(255, 205, 25));
        for (int i = 0; i < 12; i++) {
            double a = i * Math.PI / 6;
            g2.drawLine(cx, cy, cx + (int)(Math.cos(a) * (r + 9)), cy + (int)(Math.sin(a) * (r + 9)));
        }
        g2.setColor(new Color(255, 233, 64));
        g2.fillOval(cx - r, cy - r, 2*r, 2*r);
        g2.setColor(new Color(247, 178, 28));
        g2.drawOval(cx - r, cy - r, 2*r, 2*r);
    }

    private void drawShovel(Graphics2D g2, int x, int y, boolean active) {
        g2.setColor(active ? new Color(255, 232, 96) : new Color(195, 191, 175));
        g2.fillRoundRect(x, y, 68, 62, 10, 10);
        g2.setColor(new Color(80, 72, 66));
        g2.drawRoundRect(x, y, 68, 62, 10, 10);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(x + 25, y + 45, x + 47, y + 18);
        g2.setColor(new Color(150, 150, 150));
        g2.fillOval(x + 40, y + 10, 18, 18);
    }

    private void handleClick(int x, int y) {
        for (int i = 0; i < game.suns.size(); i++) {
            var s = game.suns.get(i);
            if (x >= s.x && x <= s.x + 40 && y >= s.y && y <= s.y + 40) {
                game.sun += s.value;
                game.suns.remove(i);
                repaint();
                return;
            }
        }
        if (y <= 105) {
            for (int i = 0; i < cardTypes.length; i++) {
                int cx = 122 + i * 86;
                if (x >= cx && x <= cx + 74) {
                    selectedPlant = cardTypes[i]; shovelMode = false; return;
                }
            }
            if (x >= 825 && x <= 893) { shovelMode = !shovelMode; selectedPlant = null; }
            return;
        }
        int col = x / cell;
        int row = (y - offsetY) / cell;
        if (row < 0 || row >= game.grid.rows || col < 0 || col >= game.grid.cols) return;
        if (shovelMode) { game.grid.cells[row][col].plant = null; repaint(); return; }
        if (game.grid.cells[row][col].plant == null && selectedPlant != null) placePlant(row, col);
        repaint();
    }

    private void placePlant(int row, int col) {
        switch (selectedPlant) {
            case WALL_NUT: if (game.sun >= 50) { game.grid.cells[row][col].plant = new WallNut(row, col); game.sun -= 50; } break;
            case CHERRYBOMB: if (game.sun >= 150) { game.grid.cells[row][col].plant = new CherryBomb(row, col); game.sun -= 150; } break;
            case SUNFLOWER: if (game.sun >= 50) { game.grid.cells[row][col].plant = new SunFlower(row, col); game.sun -= 50; } break;
            case PEASHOOTER: if (game.sun >= 100) { game.grid.cells[row][col].plant = new PeaShooter(row, col); game.sun -= 100; } break;
            case POTATOMINE: if (game.sun >= 25) { game.grid.cells[row][col].plant = new PotatoMine(row, col); game.sun -= 25; } break;
            case SNOWPEA: if (game.sun >= 175) { game.grid.cells[row][col].plant = new SnowPea(row, col); game.sun -= 175; } break;
            case REPEATER: if (game.sun >= 200) { game.grid.cells[row][col].plant = new Repeater(row, col); game.sun -= 200; } break;
            case CHOMPER: if (game.sun >= 150) { game.grid.cells[row][col].plant = new Chomper(row, col); game.sun -= 150; } break;
        }
    }
}
