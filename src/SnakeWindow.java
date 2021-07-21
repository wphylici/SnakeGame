import javax.swing.*;

public class SnakeWindow extends JFrame {
    static public JFrame frame = new JFrame();

    public void start() {
        frame.setTitle("Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(640, 667);
        frame.setLocation(400, 400);
        frame.add(new GameField());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SnakeWindow w = new SnakeWindow();
        w.start();
    }
}


