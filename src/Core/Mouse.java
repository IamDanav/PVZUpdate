package Core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    public int x, y;
    public boolean pressed;
    private final GamePanel gamePanel;

    // Connects directly to your GamePanel instance
    public Mouse(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pressed = true;
        // Triggers the game action immediately on click
        gamePanel.handleClick(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        // Updates grid highlights during a mouse drag action
        gamePanel.updateHoverGrid(this.x, this.y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        // Updates grid highlights during normal movement
        gamePanel.updateHoverGrid(this.x, this.y);
    }
}