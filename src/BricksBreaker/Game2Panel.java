package BricksBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game2Panel extends JPanel implements ActionListener, KeyListener {
    private int ballX, ballY, ballXDir, ballYDir;
    private int paddleX = 310;
    private int[][] bricks = new int[3][7];
    private boolean play = false;
    private Timer timer;
    private int score = 0;
    private Random random = new Random();

    public Game2Panel() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(5, this);
        timer.start();
        restartGame();
    }

    private void initializeBricks() {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = random.nextBoolean() ? 1 : 0;
            }
        }
    }

    private void randomizeBallPosition() {
        ballX = random.nextInt(600) + 50;
        ballY = 200;
        ballXDir = random.nextBoolean() ? -1 : 1;
        ballYDir = 2;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 700, 600);

        g.setColor(Color.RED);
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g.fillRect(j * 100 + 50, i * 30 + 50, 80, 20);
                }
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect(paddleX, 550, 100, 10);

        g.setColor(Color.YELLOW);
        g.fillOval(ballX, ballY, 20, 20);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 10);

        if (checkWin()) {
            g.setColor(Color.GREEN);
            g.drawString("You Win! Press Space to Restart", 250, 300);
            play = false;
        }

        if (ballY > 570) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Press Space to Restart", 250, 300);
            play = false;
        }

        g.dispose();
    }

    private boolean checkWin() {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void restartGame() {
        randomizeBallPosition();
        paddleX = 310;
        score = 0;
        play = false;
        initializeBricks();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            ballX += ballXDir;
            ballY += ballYDir;

            if (ballX < 0 || ballX > 680) ballXDir = -ballXDir;
            if (ballY < 0) ballYDir = -ballYDir;

            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX, 550, 100, 10))) {
                ballYDir = -ballYDir;
            }

            outer: for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[0].length; j++) {
                    if (bricks[i][j] > 0) {
                        int brickX = j * 100 + 50;
                        int brickY = i * 30 + 50;
                        Rectangle brickRect = new Rectangle(brickX, brickY, 80, 20);

                        if (new Rectangle(ballX, ballY, 20, 20).intersects(brickRect)) {
                            bricks[i][j] = 0;
                            ballYDir = -ballYDir;
                            score += 10;
                            break outer;
                        }
                    }
                }
            }

            if (ballY > 570) {
                play = false;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int paddleSpeed = 30;

        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < 600) {
            paddleX += paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!play) {
                restartGame();
                play = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bricks Breaker");
        Game2Panel game = new Game2Panel();
        frame.setBounds(10, 10, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }
}
