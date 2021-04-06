import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener
{
    private final int SIZE = 320;
    private final int CELL_SIZE = 10;
    private final int ALL_DOTS = 400;
    private Image iconApp;
    private Image body;
    private Image food;
    private int foodX;
    private int foodY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private int timePlus = 150;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private Graphics g;

    public GameField()
    {

        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame()
    {
        dots = 3;
        for (int i = 0; i < dots; i++)
        {
            x[i] = 50 - i * CELL_SIZE;
            y[i] = 50;
        }
        timer = new Timer(timePlus, this);
        timer.start();
        createFood();
    }

    public void createFood()
    {
        foodX = new Random().nextInt(20) * CELL_SIZE;
        foodY = new Random().nextInt(20) * CELL_SIZE;
    }

    public void loadImages()
    {
        ImageIcon iapp = new ImageIcon("../images/SnakeIconGame.png");
        iconApp = iapp.getImage();

        ImageIcon ibody = new ImageIcon("../images/Body.png");
        body = ibody.getImage();

        ImageIcon ifood = new ImageIcon("../images/RedDotFood.png");
        food = ifood.getImage();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (inGame)
        {
            g.drawImage(food, foodX, foodY, this);
            for (int i = 0; i < dots; i++)
                g.drawImage(body, x[i], y[i], this);
//            for (int x = 0; x <= 320; x += 10)
//                for (int y = 0; y <= 347; y += 10)
//                {
//                    g.setColor(Color.white);
//                    g.drawRect(x, y, 10, 10);
//                }
        }
        else
        {
            String str = "Game over";
           // Font f = new Font("Arial", 14, Font.BOLD);
            g.setColor(Color.white);
           // g.setFont(f);
            g.drawString(str, 125, SIZE / 2);
        }
    }

    public void move()
    {
        for (int i = dots; i > 0; i--)
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left)
            x[0] -= CELL_SIZE;
        if (right)
            x[0] += CELL_SIZE;
        if (up)
            y[0] -= CELL_SIZE;
        if (down)
            y[0] += CELL_SIZE;
    }

    public void checkFood()
    {
        if (x[0] == foodX && y[0] == foodY)
        {
            dots++;
            timePlus -= 3;
            timer.stop();
            timer = new Timer(timePlus, this);
            timer.start();
            createFood();
        }
    }

    public void checkCollisions()
    {
        for (int i = dots; i > 0; i--)
        {
            if (i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
            else if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0)
                inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (inGame)
        {
            checkFood();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right)
            {
                left = true;
                right = false;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left)
            {
                right = true;
                left = false;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down)
            {
                up = true;
                down = false;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up)
            {
                down = true;
                up = false;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_ESCAPE)
                System.exit(0);
        }
    }
}
