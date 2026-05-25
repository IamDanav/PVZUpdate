import javax.swing.JFrame;
import Core.GamePanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Plants vs Zombies - OOP Lab");
        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
