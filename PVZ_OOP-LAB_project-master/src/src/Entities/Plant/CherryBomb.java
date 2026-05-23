package Entities.Plant;

import Core.Game;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class CherryBomb extends Plants {

    private boolean exploded = false;
    
    // Explode animation frames
    private final List<Image> explodeFrames = new ArrayList<>();
    
    // Card images
    private static Image cardReady;
    private static Image cardFaded;
    
    // Drag image (kéo thả)
    private static Image dragImage;
    
    private int currentFrameIndex = 0;
    private Timer animationTimer;
    private int lastDrawnFrame = -1;  // Lưu frame đã vẽ
    
    public CherryBomb(int r, int c) {
        super(r, c);
        this.hp = 100;
        loadExplodeFrames();
        loadCardImages();
        loadDragImage();
    }
    
    private void loadExplodeFrames() {
        for (int i = 1; i <= 53; i++) {
            String path = "/image/plant/cherrybomb/cherrybomb/cherrybomb" + i + ".png";
            try {
                java.net.URL imgURL = getClass().getResource(path);
                if (imgURL != null) {
                    explodeFrames.add(new ImageIcon(imgURL).getImage());
                }
            } catch (Exception e) {}
        }
        if (!explodeFrames.isEmpty()) {
            exploded = true;
            startExplosionAnimation();
        }
    }
    
    private void startExplosionAnimation() {
        animationTimer = new Timer(16, e -> {
            if (currentFrameIndex < explodeFrames.size() - 1) {
                currentFrameIndex++;
                // Yêu cầu repaint để hiển thị frame mới
                Game.getInstance().grid.cells[row][col].plant = this;
                if (Game.getInstance().grid.cells[row][col].plant != null) {
                    Core.GamePanel.class.cast(null); // Cách khác: gọi repaint từ gamepanel
                }
            }
        });
        animationTimer.start();
    }
    
    private static void loadCardImages() {
        if (cardReady == null) {
            try {
                java.net.URL readyURL = CherryBomb.class.getResource("/image/plant/cherrybomb/cherrybombpacket/cherrybombpacket1.png");
                java.net.URL fadedURL = CherryBomb.class.getResource("/image/plant/cherrybomb/cherrybombpacket/cherrybombpacket2.png");
                if (readyURL != null) cardReady = new ImageIcon(readyURL).getImage();
                if (fadedURL != null) cardFaded = new ImageIcon(fadedURL).getImage();
            } catch (Exception e) {}
        }
    }
    
    private static void loadDragImage() {
        if (dragImage == null) {
            try {
                java.net.URL dragURL = CherryBomb.class.getResource("/image/plant/cherrybomb/cherrybomb/cherrybomb1.png");
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
    
    @Override
    public void update() {
        // Không cần xử lý thêm, timer đã chạy
    }
    
    private void explode() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        
        var zombies = Game.getInstance().Zombies;
        for (int i = 0; i < zombies.size(); i++) {
            var z = zombies.get(i);
            int zCol = (int)(z.x / 100);
            if (Math.abs(z.row - row) <= 1 && Math.abs(zCol - col) <= 1) {
                z.hp = 0;
            }
        }
        Game.getInstance().grid.cells[row][col].plant = null;
    }
    
    public void draw(Graphics g) {
        int drawX = col * 100 + 15;
        int drawY = row * 100 + 100 + 15;
        
        // Đảm bảo currentFrameIndex không vượt quá
        int frameToDraw = Math.min(currentFrameIndex, explodeFrames.size() - 1);
        
        // Kiểm tra nếu là 2 frame cuối (dựa trên frameToDraw)
        boolean isLastTwoFrames = (frameToDraw >= explodeFrames.size() - 2);
        
        if (!explodeFrames.isEmpty() && frameToDraw >= 0 && frameToDraw < explodeFrames.size() && explodeFrames.get(frameToDraw) != null) {
            if (isLastTwoFrames) {
                // 2 frame cuối: vẽ to hơn
                g.drawImage(explodeFrames.get(frameToDraw), drawX - 220, drawY - 150, 500, 400, null);
            } else {
                // Các frame bình thường: 70x70
                g.drawImage(explodeFrames.get(frameToDraw), drawX, drawY, 70, 70, null);
            }
            lastDrawnFrame = frameToDraw;
        } else {
            g.setColor(java.awt.Color.RED);
            g.fillRect(drawX, drawY, 70, 70);
            g.setColor(java.awt.Color.BLACK);
            g.drawString("CB", drawX + 25, drawY + 35);
        }
        
        // Khi đã vẽ xong frame cuối, nổ
        if (frameToDraw >= explodeFrames.size() - 1 && !explodeFrames.isEmpty()) {
            explode();
        }
    }
}