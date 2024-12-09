package MySnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game3Panel extends JFrame {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private ArrayList<Point> snake = new ArrayList<>();
    private Point food;
    private String direction = "RIGHT";
    private boolean gameOver = false;

    public Game3Panel() {
        setTitle("MySnake");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        snake.add(new Point(5, 5));
        deployFood();

        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    playerMovement();
                    collisionHandling();
                    repaint();
                }
            }
        });
        timer.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (!direction.equals("DOWN")) direction = "UP";
                            break;
                        case KeyEvent.VK_DOWN:
                            if (!direction.equals("UP")) direction = "DOWN";
                            break;
                        case KeyEvent.VK_LEFT:
                            if (!direction.equals("RIGHT")) direction = "LEFT";
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (!direction.equals("LEFT")) direction = "RIGHT";
                            break;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyTyped(KeyEvent e) {}
        });

        add(new GamePanel());
    }

    private void playerMovement() {
        Point body = snake.get(0);
        Point newHead = switch (direction) {
            case "UP" -> new Point(body.x, body.y - 1);
            case "DOWN" -> new Point(body.x, body.y + 1);
            case "LEFT" -> new Point(body.x - 1, body.y);
            default -> new Point(body.x + 1, body.y);
        };

        if (newHead.equals(food)) {
            snake.add(0, newHead);
            deployFood();
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
        }
    }

    private void collisionHandling() {
        Point body = snake.get(0);

        if (body.x < 0 || body.x >= WIDTH / TILE_SIZE || body.y < 0 || body.y >= HEIGHT / TILE_SIZE) {
            gameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (body.equals(snake.get(i))) {
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
