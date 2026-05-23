package Entities.Plant;

import Core.Game;
import Entities.Bullet.Bullet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Repeater extends Plants {

    private int cooldown = 0; //

    // Animation Containers
    private final List<Image> idleFrames = new ArrayList<>(); //
    private final List<Image> shootingFrames = new ArrayList<>(); //

    private int currentFrameIndex = 0; //
    private int animationTimer = 0; //

    // --- 💡 FIXED: Split into two different delay speeds ---
    private final int idleFrameDelay = 2;   // Slower pacing for relaxed idle breathing
    private final int shootFrameDelay = 16;   // Much faster pacing for snap aggressive firing

    private boolean wasZombieAhead = false; //

    public Repeater(int r, int c) {
        super(r, c);
        this.hp = 300; //
        loadAnimationFrames(); //
    }

    private void loadAnimationFrames() {
        // Load 4 frames for the standard idle loop
        for (int i = 1; i <= 4; i++) {
            idleFrames.add(getSprite("/image/plant/repeater/repeater/repeater" + i + ".png")); //
        }
        // Load 3 frames for the attacking firing loop
        for (int i = 1; i <= 3; i++) {
            shootingFrames.add(getSprite("/image/plant/repeater/repeatershoot/repeatershoot" + i + ".png")); //
        }
    }

    private Image getSprite(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) return new ImageIcon(imgURL).getImage(); //
            System.err.println("Asset not found: " + path); //
        } catch (Exception e) {
            System.err.println("Error loading asset: " + path); //
        }
        return null; //
    }

    @Override
    public void update() {
        boolean zombieAhead = Game.getInstance().hasZombieAhead(row, col); //

        // Reset state mechanics cleanly if the lane status changes
        if (zombieAhead != wasZombieAhead) {
            wasZombieAhead = zombieAhead;
            currentFrameIndex = 0; //
            animationTimer = 0; //
            cooldown = 0; //
        }

        if (zombieAhead) {
            cooldown++; //

            // --- Firing Phase Animation Timing (Using shootFrameDelay) ---
            if (!shootingFrames.isEmpty()) {
                animationTimer++;
                if (animationTimer >= shootFrameDelay) { // 🚀 Uses the fast delay limit!
                    animationTimer = 0;
                    currentFrameIndex = (currentFrameIndex + 1) % shootingFrames.size();
                }
            }

            // --- Instant Double Shot Spawning Logic ---
            // Trigger the bullet spawn exactly on the visual lunge frame index (Frame 1)
            if (cooldown == 40) {
                // Spawns 2 peas side-by-side concurrently
                Game.getInstance().bullets.add(new Bullet(row, col * 100 + 100, false)); //
                Game.getInstance().bullets.add(new Bullet(row, col * 100 + 80, false)); //
            }

            // Cooldown limits reset at 50 ticks to balance attack speed
            if (cooldown >= 50) { //
                cooldown = 0; //
            }
        }
        else {
            cooldown = 0; //

            // --- Idle Breathing Loop Timing (Using idleFrameDelay) ---
            if (!idleFrames.isEmpty()) {
                animationTimer++; //
                if (animationTimer >= idleFrameDelay) { // 💤 Uses the slow delay limit!
                    animationTimer = 0; //
                    currentFrameIndex = (currentFrameIndex + 1) % idleFrames.size(); //
                }
            }
        }
    }

    public void draw(Graphics g) {
        int drawX = col * 100 + 15; //
        int drawY = row * 100 + 100 + 15; //

        List<Image> currentFrames = wasZombieAhead ? shootingFrames : idleFrames; //
        if (currentFrames.isEmpty()) {
            currentFrames = idleFrames; //
        }

        if (!currentFrames.isEmpty() && currentFrameIndex < currentFrames.size() && currentFrames.get(currentFrameIndex) != null) {
            g.drawImage(currentFrames.get(currentFrameIndex), drawX, drawY, 70, 70, null); //
        } else {
            g.setColor(new java.awt.Color(34, 139, 34)); //
            g.fillRect(drawX, drawY, 70, 70); //
        }
    }
}