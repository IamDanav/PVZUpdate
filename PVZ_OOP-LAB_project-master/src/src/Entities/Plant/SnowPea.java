package Entities.Plant;

import Core.Game;
import Entities.Bullet.SlowBullet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class SnowPea extends Plants {
    private final List<Image> idleFrames = new ArrayList<>();
    private final List<Image> shootingFrames = new ArrayList<>();
    
    private int currentFrameIndex = 0;
    private boolean isShooting = false;
    private boolean hasShotInThisCycle = false;  // Đã bắn trong chu kỳ này chưa
    
    private static Image cardReady;
    private static Image cardFaded;
    private static Image dragImage;
    
    private Timer gameTimer;
    private int idleFrameDelay = 2;
    private float shootFrameDelay = 18.75f;   // 375ms/frame
    private float frameCounter = 0;
    
    public SnowPea(int r, int c) {
        super(r, c);
        this.hp = 100;
        loadIdleFrames();
        loadShootingFrames();
        loadCardImages();
        loadDragImage();
        startGameTimer();
    }
    
    private void loadIdleFrames() {
        for (int i = 1; i <= 25; i++) {
            String path = "/image/plant/snowpea/snowpea/snowpea" + i + ".png";
            try {
                java.net.URL imgURL = getClass().getResource(path);
                if (imgURL != null) {
                    idleFrames.add(new ImageIcon(imgURL).getImage());
                }
            } catch (Exception e) {}
        }
        if (idleFrames.isEmpty()) {
            try {
                java.net.URL imgURL = getClass().getResource("/image/plant/snowpea/snowpea/snowpea1.png");
                if (imgURL != null) {
                    idleFrames.add(new ImageIcon(imgURL).getImage());
                }
            } catch (Exception e) {}
        }
    }
    
    private void loadShootingFrames() {
        for (int i = 1; i <= 4; i++) {
            String path = "/image/plant/snowpea/snowpeashoot/snowpeashoot" + i + ".png";
            try {
                java.net.URL imgURL = getClass().getResource(path);
                if (imgURL != null) {
                    shootingFrames.add(new ImageIcon(imgURL).getImage());
                }
            } catch (Exception e) {}
        }
        if (shootingFrames.isEmpty() && !idleFrames.isEmpty()) {
            shootingFrames.add(idleFrames.get(0));
        }
    }
    
    private static void loadCardImages() {
        if (cardReady == null) {
            try {
                java.net.URL readyURL = SnowPea.class.getResource("/image/plant/snowpea/snowpeapacket/snowpeapacket1.png");
                java.net.URL fadedURL = SnowPea.class.getResource("/image/plant/snowpea/snowpeapacket/snowpeapacket2.png");
                if (readyURL != null) cardReady = new ImageIcon(readyURL).getImage();
                if (fadedURL != null) cardFaded = new ImageIcon(fadedURL).getImage();
            } catch (Exception e) {}
        }
    }
    
    private static void loadDragImage() {
        if (dragImage == null) {
            try {
                java.net.URL dragURL = SnowPea.class.getResource("/image/plant/snowpea/snowpea/snowpea1.png");
                if (dragURL != null) dragImage = new ImageIcon(dragURL).getImage();
            } catch (Exception e) {}
        }
    }
    
    public static Image getCardReady() {
        loadCardImages();
        return cardReady;
    }
    
    public static Image getCardFaded() {
        loadCardImages();
        return cardFaded;
    }
    
    public static Image getDragImageStatic() {
        loadDragImage();
        return dragImage;
    }
    
    private void startGameTimer() {
        gameTimer = new Timer(20, e -> {
            boolean zombieAhead = Game.getInstance().hasZombieAhead(row, col);
            
            // Nếu có zombie và chưa ở chế độ bắn, bắt đầu chu kỳ bắn mới
            if (zombieAhead && !isShooting) {
                isShooting = true;
                currentFrameIndex = 0;
                frameCounter = 0;
                hasShotInThisCycle = false;
            }
            
            // Animation
            if (isShooting) {
                frameCounter++;
                if (frameCounter >= shootFrameDelay) {
                    frameCounter = 0;
                    if (!shootingFrames.isEmpty()) {
                        currentFrameIndex++;
                        
                        // BẮN ĐẠN TẠI FRAME CUỐI (frame thứ 4, index = 3)
                        if (currentFrameIndex == 3 && !hasShotInThisCycle && zombieAhead) {
                            Game.getInstance().bullets.add(new SlowBullet(row, col * 100 + 70));
                            hasShotInThisCycle = true;
                        }
                        
                        if (currentFrameIndex >= shootingFrames.size()) {
                            // Kết thúc chu kỳ bắn
                            currentFrameIndex = 0;
                            if (!zombieAhead) {
                                isShooting = false;
                                hasShotInThisCycle = false;
                            } else {
                                // Reset để bắt đầu chu kỳ mới
                                hasShotInThisCycle = false;
                            }
                        }
                    }
                }
            } else {
                // Idle animation
                frameCounter++;
                if (frameCounter >= idleFrameDelay) {
                    frameCounter = 0;
                    if (!idleFrames.isEmpty()) {
                        currentFrameIndex++;
                        if (currentFrameIndex >= idleFrames.size()) {
                            currentFrameIndex = 0;
                        }
                    }
                }
            }
            
            // Nếu không còn zombie, quay về idle ngay
            if (!zombieAhead && isShooting) {
                isShooting = false;
                currentFrameIndex = 0;
                frameCounter = 0;
                hasShotInThisCycle = false;
            }
        });
        gameTimer.start();
    }
    
    @Override
    public void update() {
        // Timer riêng đã xử lý
    }
    
    public void draw(Graphics g) {
        int drawX = col * 100 + 15;
        int drawY = row * 100 + 100 + 15;
        
        List<Image> currentFrames = isShooting ? shootingFrames : idleFrames;
        
        if (!currentFrames.isEmpty() && currentFrameIndex < currentFrames.size() && currentFrames.get(currentFrameIndex) != null) {
            g.drawImage(currentFrames.get(currentFrameIndex), drawX, drawY, 65, 65, null);
        } else if (!idleFrames.isEmpty()) {
            g.drawImage(idleFrames.get(0), drawX, drawY, 65, 65, null);
        } else {
            g.setColor(java.awt.Color.CYAN);
            g.fillRect(drawX, drawY, 65, 65);
            g.setColor(java.awt.Color.BLACK);
            g.drawString("SP", drawX + 25, drawY + 35);
        }
    }
}