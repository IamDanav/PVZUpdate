package Entities.Plant;

import Core.Game;
import Entities.Plants;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class PotatoMine extends Plants {

    private int timer = 0;
    private boolean armed = false;

    // Animation sequences
    private final List<Image> growingFrames = new ArrayList<>();
    private final List<Image> armedFrames = new ArrayList<>();
    private int currentFrameIndex = 0;
    private int animationTimer = 0;

    // Tweak this delay to make the sprout animation match your 50-tick arming time!
    private final double frameDelay = 12;

    public PotatoMine(int r, int c) {
        super(r, c);
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
        // Phase 1: One-way growing up frames (e.g., 1=deep, 2=sprouting, 3=breaking dirt, 4=almost up)
        for (int i = 1; i <= 5; i++) {
            growingFrames.add(getSprite("/image/potatomine" + i + ".png"));
        }
        // Phase 2: Armed state loops/blinks while waiting for a zombie
        for (int i = 1; i <= 3; i++) {
            armedFrames.add(getSprite("/image/potato" + i + ".png"));
        }
    }

    private Image getSprite(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) return new ImageIcon(imgURL).getImage();
            System.err.println("File missing: " + path);
        } catch (Exception e) {
            System.err.println("Error reading: " + path);
        }
        return null;
    }

    @Override
    public void update() {
        timer++;

        // Arms the mine after 50 ticks
        if (timer >= 50 && !armed) {
            armed = true;
            currentFrameIndex = 0; // Reset index to loop the armed state cleanly
            animationTimer = 0;
        }

        // --- NEW: One-Way Growth Animation Control ---
        if (!armed) {
            // Processing growing phase
            if (!growingFrames.isEmpty()) {
                animationTimer++;
                if (animationTimer >= frameDelay) {
                    animationTimer = 0;
                    // Move to next frame ONLY if we haven't hit the last growth step yet
                    if (currentFrameIndex < growingFrames.size() - 1) {
                        currentFrameIndex++;
                    }
                }
            }
        } else {
            // Processing armed phase loop
            if (!armedFrames.isEmpty()) {
                animationTimer++;
                if (animationTimer >= frameDelay) {
                    animationTimer = 0;
                    currentFrameIndex = (currentFrameIndex + 1) % armedFrames.size();
                }
            }
        }

        // Underground mines do not detect zombies or explode
        if (!armed) return;

        // Phase 2: Detect and instantly defeat zombie
        for (int i = 0; i < Game.getInstance().Zombies.size(); i++) {
            var z = Game.getInstance().Zombies.get(i);
            int zCol = (int)((z.x + 30) / 100);

            if (z.row == row && zCol == col) {
                // Instantly vaporizes the zombie (1 shot kill!)
                Game.getInstance().Zombies.remove(i);

                // Remove PotatoMine from grid cell
                Game.getInstance().grid.cells[row][col].plant = null;
                break;
            }
        }
    }

    public void draw(Graphics g) {
        int drawX = col * 100 + 15;
        int drawY = row * 100 + 100 + 15;

        List<Image> activeFrames = armed ? armedFrames : growingFrames;

        if (!activeFrames.isEmpty() && currentFrameIndex < activeFrames.size() && activeFrames.get(currentFrameIndex) != null) {
            g.drawImage(activeFrames.get(currentFrameIndex), drawX, drawY, 70, 70, null);
        } else {
            // Backup color block if images are failing to load
            g.setColor(armed ? java.awt.Color.RED : java.awt.Color.DARK_GRAY);
            g.fillRect(drawX, drawY, 70, 70);
        }
    }

    public boolean isArmed() {
        return armed;
    }
}