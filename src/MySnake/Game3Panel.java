package MySnake;

import inputs.KeyboardInputs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game3Panel extends JFrame {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private ArrayList<Point> snake = new ArrayList<>();
    private Point food;
    private String direction = "RIGHT";
    private boolean gameOver = false;
    private Timer timer;
    private KeyboardInputs input;

    public Game3Panel() {
        setTitle("MySnake");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        input = KeyboardInputs.getInstance();
        addKeyListener(input);
        setFocusable(true);

        snake.add(new Point(5, 5));
        deployFood();

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    playerMovement();
                    collisionHandling();
                    repaint();
                } else if (input.isKeyPressed(KeyEvent.VK_R)) {
                    restartGame();
                }
            }
        });
        timer.start();

        add(new GamePanel());
    }

    private void playerMovement() {
        if (input.isKeyPressed(KeyEvent.VK_UP) && !direction.equals("DOWN")) {
            direction = "UP";
        }
        if (input.isKeyPressed(KeyEvent.VK_DOWN) && !direction.equals("UP")) {
            direction = "DOWN";
        }
        if (input.isKeyPressed(KeyEvent.VK_LEFT) && !direction.equals("RIGHT")) {
            direction = "LEFT";
        }
        if (input.isKeyPressed(KeyEvent.VK_RIGHT) && !direction.equals("LEFT")) {
            direction = "RIGHT";
        }

        Point head = snake.get(0);
        Point newHead = null;
        switch (direction) {
            case "UP":
                newHead = new Point(head.x, head.y - 1);
                break;
            case "DOWN":
                newHead = new Point(head.x, head.y + 1);
                break;
            case "LEFT":
                newHead = new Point(head.x - 1, head.y);
                break;
            case "RIGHT":
                newHead = new Point(head.x + 1, head.y);
                break;
        }

        if (newHead != null) {
            if (newHead.equals(food)) {
                snake.add(0, newHead);
                deployFood();
            } else {
                snake.add(0, newHead);
                snake.remove(snake.size() - 1);
            }
        }
    }

    private void collisionHandling() {
        Point head = snake.get(0);

        if (head.x < 0 || head.x >= WIDTH / TILE_SIZE || head.y < 0 || head.y >= HEIGHT / TILE_SIZE) {
            gameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }

    private void deployFood() {
        int gridWidth = WIDTH / TILE_SIZE;
        int gridHeight = HEIGHT / TILE_SIZE;

        int x = (int) (Math.random() * gridWidth);
        int y = (int) (Math.random() * gridHeight);

        food = new Point(x, y);

        if (snake.contains(food)) {
            deployFood();
        }
    }

    private void restartGame() {
        snake.clear();
        snake.add(new Point(5, 5));
        direction = "RIGHT";
        gameOver = false;
        deployFood();
        repaint();
        System.out.println("Game restarted!");
    }

    private class GamePanel extends JPanel {
        public GamePanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics canvas) {
            super.paintComponent(canvas);

            canvas.setColor(Color.GREEN);
            for (Point p : snake) {
                canvas.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

            canvas.setColor(Color.RED);
            canvas.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            if (gameOver) {
                canvas.setColor(Color.WHITE);
                FontMetrics metrics = canvas.getFontMetrics(canvas.getFont());
                String gameOverText = "Game Over! Press R to restart.";

                int x = (WIDTH - metrics.stringWidth(gameOverText)) / 2;
                int y = (HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent();

                canvas.drawString(gameOverText, x, y);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game3Panel game = new Game3Panel();
            game.setVisible(true);
        });
    }
}