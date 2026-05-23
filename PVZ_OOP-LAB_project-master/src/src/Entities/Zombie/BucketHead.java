package Entities.Zombie;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class BucketHead extends Zombies {

    // Define the possible visual phases
    private enum ZombiePhase {
        FULL_BUCKET,
        DAMAGED_BUCKET,
        NO_BUCKET,
        NO_ARM
    }

    private ZombiePhase currentPhase = ZombiePhase.FULL_BUCKET;

    // Separate animation frame lists for each phase
    private final List<Image> fullBucketFrames = new ArrayList<>();
    private final List<Image> damagedBucketFrames = new ArrayList<>();
    private final List<Image> noBucketFrames = new ArrayList<>();
    private final List<Image> noArmFrames = new ArrayList<>();

    // Animation ticking mechanics
    private int currentFrameIndex = 0;
    private int animationTimer = 0;
    private final int frameDelay = 3;

    public BucketHead(int row) {
        super(row, 400, 1); // Row, HP=400, Speed=1

        // Load all visual states into memory
        loadFullBucketFrames();
        loadDamagedBucketFrames();
        loadNoBucketFrames();
        loadNoArmFrames();
    }

    private void loadFullBucketFrames() {
        for (int i = 1; i <= 7; i++) {
            fullBucketFrames.add(getSprite("/image/zombie/buckethead/bucketheadwalk/bucketheadwalk/bucketheadwalk" + i + ".png"));
        }
    }

    private void loadDamagedBucketFrames() {
        for (int i = 1; i <= 7; i++) {
            damagedBucketFrames.add(getSprite("/image/zombie/buckethead/bucketheadwalk/bucketheadwalkd/bucketheadwalkd" + i + ".png"));
        }
    }

    private void loadNoBucketFrames() {
        for (int i = 1; i <= 7; i++) {
            noBucketFrames.add(getSprite("/image/zombie/zombie/zombiewalk/zombiewalk" + i + ".png"));
        }
    }

    private void loadNoArmFrames() {
        for (int i = 1; i <= 7; i++) {
            noArmFrames.add(getSprite("/image/zombie/zombie/armlesszombie/armlesszombie" + i + ".png"));
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
        super.update(); // Keep walking behavior alive

        // 1. Calculate the target phase based on health values
        ZombiePhase targetPhase;
        if (this.hp > 260) {
            targetPhase = ZombiePhase.FULL_BUCKET;
        } else if (this.hp > 130) { // Matches your line 72
            targetPhase = ZombiePhase.DAMAGED_BUCKET;
        } else if (this.hp > 50) {  // Matches your line 75
            targetPhase = ZombiePhase.NO_BUCKET;
        } else {
            targetPhase = ZombiePhase.NO_ARM;
        }

        // ✅ FIXED: Save the calculation result and reset frame counts on phase switches
        if (targetPhase != currentPhase) {
            currentPhase = targetPhase;
            currentFrameIndex = 0;
            animationTimer = 0;
        }

        // ✅ FIXED: Declared activeFrames here so lines 81 and 85 can find the symbol!
        List<Image> activeFrames = getActiveFrameList();

        // Progress animation timeline
        if (!activeFrames.isEmpty()) { // Matches your line 81
            animationTimer++;
            if (animationTimer >= frameDelay) { // Matches your line 83
                animationTimer = 0;
                currentFrameIndex = (currentFrameIndex + 1) % activeFrames.size(); // Matches your line 85
            }
        }
    }

    // ✅ FIXED: Fixed spelling typo from currentPhases to currentPhase
    private List<Image> getActiveFrameList() {
        switch (currentPhase) {
            case DAMAGED_BUCKET: return damagedBucketFrames;
            case NO_BUCKET:      return noBucketFrames;
            case NO_ARM:         return noArmFrames;
            case FULL_BUCKET:
            default:             return fullBucketFrames;
        }
    }

    public void draw(Graphics g) {
        int baseX = (int) x;
        int baseY = row * 100 + 100;

        int X_OFFSET = 0;
        int Y_OFFSET = -45;

        List<Image> activeFrames = getActiveFrameList();

        if (!activeFrames.isEmpty() && currentFrameIndex < activeFrames.size() && activeFrames.get(currentFrameIndex) != null) {
            Image img = activeFrames.get(currentFrameIndex);
            img = applySlowFilter(img);  // ← CHỈ THÊM DÒNG NÀY
            g.drawImage(img, baseX + X_OFFSET, baseY + Y_OFFSET, null);
        } else {
            g.setColor(java.awt.Color.GRAY);
            g.fillRect(baseX, baseY + 20, 60, 60);
            if (slowTimer > 0) {
                g.setColor(new java.awt.Color(0, 200, 255, 180));
                g.fillRect(baseX, baseY + 20, 60, 60);
            }
        }
    }
}