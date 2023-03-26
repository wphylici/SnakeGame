import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.io.*;
import java.util.Scanner;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int CELL_SIZE = 20;
    private final int ALL_DOTS = 400;
    private Image iconApp;
    private Image body;
    private Image food;
    private int score = 0;
    private int foodX;
    private int foodY;
    private int record;
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

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(food, foodX, foodY, this);
            for (int i = 0; i < dots; i++)
                g.drawImage(body, x[i], y[i], this);

            String strScore = Integer.toString(score);
            String str = "Score: ";

            g.setColor(Color.white);
            g.drawString(str + strScore, 5, 10);

            for (int x = 0; x <= 640; x += 20)
                for (int y = 0; y <= 667; y += 20) {
                    g.setColor(Color.gray);
                    g.drawRect(x, y, 20, 20);
                }
        } else {
            FileReader rec;
            try {
                rec = new FileReader("../save/best_score");
                Scanner scan = new Scanner(rec);
                if (scan.hasNextInt())
                    record = scan.nextInt();
                rec.close();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            if (record < score) {
                record = score;
                FileWriter w;
                try {
                    w = new FileWriter("../save/best_score");
                    String strScore = Integer.toString(score);
                    w.write(strScore);
                    w.flush();
                    w.close();
                }
                catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            String GameOver = "Game over";
            String BestResult = "best result: ";
            String strRecord= Integer.toString(record);

            Font go = new Font("Arial", Font.BOLD, 45);
            g.setColor(Color.white);
            g.setFont(go);
            g.drawString(GameOver, 200, SIZE / 2);

            Font br = new Font("Arial", Font.BOLD, 20);
            g.setFont(br);
            g.drawString(BestResult + strRecord, 245, 400);

            JPanel panel = new JPanel();
            JButton retry = new JButton("Retry");
            panel.add(retry);
            panel.setVisible(true);
            timer.stop();
        }
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 40 - i * CELL_SIZE;
            y[i] = 40;
        }
        timer = new Timer(timePlus, this);
        timer.start();
    }

    public void createFood() {
        foodX = new Random().nextInt(20) * CELL_SIZE;
        foodY = new Random().nextInt(20) * CELL_SIZE;
    }

    public void loadImages() {
        ImageIcon iapp = new ImageIcon("../images/SnakeIconGame.png");
        iconApp = iapp.getImage();

        ImageIcon ibody = new ImageIcon("../images/Body.png");
        body = ibody.getImage();

        ImageIcon ifood = new ImageIcon("../images/RedDotFood.png");
        food = ifood.getImage();
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
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

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            dots++;
            score += 10;
            timePlus -= 2;
            timer.stop();
            timer = new Timer(timePlus, this);
            timer.start();
            createFood();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i])
                inGame = false;
            else if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0)
                inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkFood();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                right = false;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                left = false;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                down = false;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
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
