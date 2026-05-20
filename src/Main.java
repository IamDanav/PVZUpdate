
import javax.swing.JFrame;
import Core.GamePanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PVZ");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setSize(1920, 1280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
