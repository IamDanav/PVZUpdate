package Entities.Others;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class LawnMower {
    public int row;
    public double x = 0;
    public boolean active = false;
    public boolean used = false;
    private Image image;
    
    public LawnMower(int row) {
        this.row = row;
        loadImage();
    }
    
    private void loadImage() {
        try {
            java.net.URL imgURL = getClass().getResource("/image/others/lawnmower/lawnmower.png");
            if (imgURL != null) {
                image = new ImageIcon(imgURL).getImage();
            }
        } catch (Exception e) {
            System.err.println("Error loading lawnmower image: " + e.getMessage());
        }
    }
    
    public void update() {
        if(active) {
            x += 8;
        }
    }
    
    public void draw(Graphics g, int offsetY) {
        int y = row * 100 + offsetY + 30;
        if (image != null) {
            g.drawImage(image, (int)x, y, 50, 40, null);
        } else {
            // Fallback: vẽ hình chữ nhật màu xám
            g.setColor(java.awt.Color.GRAY);
            g.fillRect((int)x, y, 50, 40);
        }
    }
}
