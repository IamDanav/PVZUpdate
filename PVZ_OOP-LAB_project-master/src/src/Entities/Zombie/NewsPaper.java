package Entities.Zombie;

import java.awt.Color;
import java.awt.Graphics;

public class NewsPaper extends Zombies {

    private boolean angry = false;

    public NewsPaper(int row) {
        super(row, 150, 1);
    }

    @Override
    public void update() {

        if (!angry && hp <= 70) {
            angry = true;
            speed = 3;
        }

        x -= speed;
    }
    
    public void draw(Graphics g) {
        int baseX = (int) x;
        int baseY = row * 100 + 100;
        
        // Chọn màu dựa trên trạng thái
        Color color;
        if (slowTimer > 0) {
            color = new Color(0, 200, 255, 180); // Xanh nước biển khi bị làm chậm
        } else if (angry) {
            color = new Color(255, 100, 100); // Đỏ nhạt khi angry
        } else {
            color = Color.WHITE;
        }
        
        // Vẽ zombie (hình chữ nhật)
        g.setColor(color);
        g.fillRect(baseX, baseY + 20, 60, 60);
        
        // Vẽ chữ NEWS
        g.setColor(Color.BLACK);
        g.drawString("NEWS", baseX + 10, baseY + 55);
        
        // Nếu angry, vẽ viền đỏ
        if (angry) {
            g.setColor(Color.RED);
            g.drawRect(baseX + 2, baseY + 22, 56, 56);
        }
    }
}