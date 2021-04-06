import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame
{
    public Window()
    {

//        this.setIconImage(new ImageIcon(getClass().getResource("../images/SnakeIconApp.png")).getImage());

//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../images/SnakeIconApp.png")));
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 347);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args)
    {
        Window w = new Window();
    }
}