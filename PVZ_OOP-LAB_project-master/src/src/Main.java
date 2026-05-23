import Core.GamePanel;
import java.awt.Dimension;
import javax.swing.JFrame;  // ← THÊM IMPORT

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PVZ");
        GamePanel panel = new GamePanel();
        
        panel.setPreferredSize(new Dimension(900, 600));
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}