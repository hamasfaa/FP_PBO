package BricksBreaker;

import inputs.KeyboardInputs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Game2Panel extends JPanel implements ActionListener {
    private int ballX, ballY, ballXDir, ballYDir;
    private int paddleX = 310;
    private int[][] bricks = new int[3][7];
    private boolean play = false;
    private Timer timer;
    private int score = 0;
    private Random random = new Random();
    private KeyboardInputs input;

    public Game2Panel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(700, 600));
        input = KeyboardInputs.getInstance();
        addKeyListener(input);
        requestFocusInWindow();
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        play = true;
        initializeBricks();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            if (input.isKeyPressed(KeyEvent.VK_LEFT) && paddleX > 0) {
                paddleX -= 5;
            }
            if (input.isKeyPressed(KeyEvent.VK_RIGHT) && paddleX < 600) {
                paddleX += 5;
            }

            ballX += ballXDir;
            ballY += ballYDir;

            if (ballX < 0 || ballX > 680) ballXDir = -ballXDir;
            if (ballY < 0) ballYDir = -ballYDir;

            Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
            Rectangle paddleRect = new Rectangle(paddleX, 550, 100, 10);

            if (ballRect.intersects(paddleRect)) {
                ballYDir = -ballYDir;
            }

            outer: for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[0].length; j++) {
                    if (bricks[i][j] > 0) {
                        int brickX = j * 100 + 50;
                        int brickY = i * 30 + 50;
                        Rectangle brickRect = new Rectangle(brickX, brickY, 80, 20);

                        if (ballRect.intersects(brickRect)) {
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

        if (input.isKeyPressed(KeyEvent.VK_SPACE) && !play) {
            restartGame();
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bricks Breaker");
        Game2Panel game = new Game2Panel();
        frame.setBounds(10, 10, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }
}
