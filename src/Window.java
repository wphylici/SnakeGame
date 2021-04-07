import javax.swing.*;
import java.awt.*;
import javax.swing.JInternalFrame;

public class Window extends JFrame
{
    static JFrame f;

    static JLabel l;
    public Window()
    {
        setTitle("Snake");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 667);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args)
    {
        Window w = new Window();
    }
}
