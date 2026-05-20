package Entities.Zombie;

import Core.Game;
import Entities.Zombies;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class ConeHead extends Zombies {

    // Define the possible visual phases matching the structure of Buckethead
    private enum ZombiePhase {
        FULL_CONE,
        DAMAGED_CONE,
        NO_CONE,
        NO_HAND,
    }

    private ZombiePhase currentPhase = ZombiePhase.FULL_CONE;

    // Separate animation frame lists for each state track
    private final List<Image> walkFullCone = new ArrayList<>();
    private final List<Image> walkDamagedCone = new ArrayList<>();
    private final List<Image> walkNoCone = new ArrayList<>();
    private final List<Image> walkNoHand = new ArrayList<>();
    // Biting animation frame containers
    private final List<Image> attackFullCone = new ArrayList<>();
    private final List<Image> attackDamagedCone = new ArrayList<>();
    private final List<Image> attackNoCone = new ArrayList<>();
    private final List<Image> attackNoHand = new ArrayList<>();
    // Animation ticking mechanics
    private int currentFrameIndex = 0;
    private int animationTimer = 0;
    private final int frameDelay = 3; //

    // Eating behavior mechanics
    private boolean isAttacking = false;
    private int biteCooldown = 0;

    public ConeHead(int row) {
        super(row, 280, 1); // Row, HP=280 (scaled down from Buckethead's 400), Speed=1

        // Load all visual states into memory
        loadWalkFrames();
        loadAttackFrames();
    }

    private void loadWalkFrames() {
        for (int i = 1; i <= 7; i++) {
            walkFullCone.add(getSprite("/image/coneheadwalk" + i + ".png"));
            walkDamagedCone.add(getSprite("/image/coneheadwalkd" + i + ".png"));
            walkNoCone.add(getSprite("/image/zombiewalk" + i + ".png")); // Reuses baseline zombie assets
            walkNoHand.add(getSprite("/image/armlesszombie" + i + ".png"));
        }

    }

    private void loadAttackFrames() {
        for (int i = 1; i <= 7; i++) {
            attackFullCone.add(getSprite("/image/coneheadeat" + i + ".png"));

            attackNoCone.add(getSprite("/image/zombieeating" + i + ".png")); // Reuses baseline zombie assets

        }
        for (int i = 1; i <= 7; i++) {
            attackDamagedCone.add(getSprite("/image/coneheadeatd" + i + ".png"));
        }
        for (int i = 8; i <= 14; i++) {
            attackDamagedCone.add(getSprite("/image/coneheadeatd" + i + ".png"));
        }
        for (int i = 1; i <= 7; i++){
            attackNoHand.add(getSprite("/image/armlesszombieeating" + i + ".png"));
        }
    }

    private Image getSprite(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) return new ImageIcon(imgURL).getImage(); //
            System.err.println("File missing: " + path); //
        } catch (Exception e) {
            System.err.println("Error reading: " + path); //
        }
        return null; //
    }

    @Override
    public void update() {
        // Find if there's a plant inside our current cell spot to eat
        int myCol = (int) (x / 100);
        boolean foundPlantToEat = false;

        if (myCol >= 0 && myCol < Game.getInstance().grid.cols && row >= 0 && row < Game.getInstance().grid.rows) {
            var cell = Game.getInstance().grid.cells[row][myCol];

            if (cell.plant != null) {
                foundPlantToEat = true;
                isAttacking = true;

                // Munch/Bite down on plant health pool over time ticks
                biteCooldown++;
                if (biteCooldown >= 20) { //
                    cell.plant.hp -= 20; //
                    biteCooldown = 0; //

                    // Clear plant from grid if health bottoms out
                    if (cell.plant.hp <= 0) {
                        cell.plant = null; //
                        isAttacking = false; //
                    }
                }
            }
        }

        // Only move forward down the lane if NOT busy eating a plant
        if (!foundPlantToEat) {
            isAttacking = false; //
            super.update(); // Keeps walking physics alive
        }

        // Calculate the target phase based on remaining health thresholds
        ZombiePhase targetPhase;
        if (this.hp > 180) {
            targetPhase = ZombiePhase.FULL_CONE;
        } else if (this.hp > 80) {
            targetPhase = ZombiePhase.DAMAGED_CONE;
        } else if (this.hp > 40){
            targetPhase = ZombiePhase.NO_CONE;
        } else {
            targetPhase = ZombiePhase.NO_HAND;
        }

        // Reset tracking counts on phase switches
        if (targetPhase != currentPhase) {
            currentPhase = targetPhase; //
            currentFrameIndex = 0; //
            animationTimer = 0; //
        }

        List<Image> activeFrames = getActiveFrameList(); //

        if (!activeFrames.isEmpty()) { //
            animationTimer++; //
            if (animationTimer >= frameDelay) { //
                animationTimer = 0; //

                // --- 🛠️ FIXED: Play DAMAGED_CONE Eating Sequence Non-Looping ---
                if (isAttacking && currentPhase == ZombiePhase.DAMAGED_CONE) {
                    if (currentFrameIndex < activeFrames.size() - 1) {
                        currentFrameIndex++; // One-way sequential step progression
                    }
                    // When it reaches the last frame index, it freezes there without looping!
                } else {
                    // Standard continuous cycle loop for all other active states
                    currentFrameIndex = (currentFrameIndex + 1) % activeFrames.size(); //
                }
            }
        }
    }

    /**
     * Shifts between walking lists and biting lists depending on status
     */
    private List<Image> getActiveFrameList() {
        if (!isAttacking) {
            switch (currentPhase) {
                case DAMAGED_CONE: return walkDamagedCone;
                case NO_CONE:      return walkNoCone;
                case FULL_CONE:
                default:           return walkFullCone;
            }
        } else {
            switch (currentPhase) {
                case DAMAGED_CONE: return attackDamagedCone;
                case NO_CONE:      return attackNoCone;
                case NO_HAND:      return attackNoHand;
                case FULL_CONE:
                default:           return attackFullCone;
            }
        }
    }

    public void draw(Graphics g) {
        int baseX = (int) x; //
        int baseY = row * 100 + 100; //

        int X_OFFSET = 0; //
        int Y_OFFSET = -15; //

        List<Image> activeFrames = getActiveFrameList(); //

        if (!activeFrames.isEmpty() && currentFrameIndex < activeFrames.size() && activeFrames.get(currentFrameIndex) != null) {
            g.drawImage(activeFrames.get(currentFrameIndex), baseX + X_OFFSET, baseY + Y_OFFSET, null); //
        } else {
            // Fallback rendering default color code block if textures fail to load
            g.setColor(java.awt.Color.ORANGE);
            g.fillRect(baseX, baseY + 20, 60, 60);
        }
    }
}