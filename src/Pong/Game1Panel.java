package Pong;

import inputs.KeyboardInputs;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game1Panel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running;
    private Paddle paddle1, paddle2;
    private Ball ball;
    private int score1, score2;
    private KeyboardInputs input;

    public Game1Panel() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(700, 600));
        input = KeyboardInputs.getInstance();
        addKeyListener(input);
        requestFocusInWindow(); // Ensure the panel gets focus
        paddle1 = new Paddle(10, 250, KeyEvent.VK_W, KeyEvent.VK_S);
        paddle2 = new Paddle(670, 250, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        ball = new Ball(350, 300);
        score1 = 0;
        score2 = 0;
        start();
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1){
                tick();
                delta--;
            }
            repaint();
        }
    }

    public void tick() {
        paddle1.update(input);
        paddle2.update(input);
        ball.tick(paddle1, paddle2);

        if(ball.getX() < 0){
            score2++;
            ball.reset();
        }
        if(ball.getX() > 700){
            score1++;
            ball.reset();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paddle1.render(g);
        paddle2.render(g);
        ball.render(g);

        g.setColor(Color.WHITE);
        for(int i=0; i < 600; i+=15){
            g.fillRect(350, i, 2, 10);
        }

        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString(String.valueOf(score1), 300, 50);
        g.drawString(String.valueOf(score2), 400, 50);
    }
}

class Paddle {
    private int x, y;
    private int width = 10, height = 100;
    private int speed = 5;
    private int upKey, downKey;
    private boolean up, down;

    public Paddle(int x, int y, int upKey, int downKey){
        this.x = x;
        this.y = y;
        this.upKey = upKey;
        this.downKey = downKey;
    }

    public void update(KeyboardInputs input){
        up = input.isKeyPressed(upKey);
        down = input.isKeyPressed(downKey);
        tick();
    }

    private void tick(){
        if(up && y > 0){
            y -= speed;
        }
        if(down && y < 600 - height){
            y += speed;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}

class Ball {
    private int x, y;
    private int size = 20;
    private int xVelocity = 4, yVelocity = 4;

    public Ball(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void tick(Paddle p1, Paddle p2){
        x += xVelocity;
        y += yVelocity;

        if(y <= 0 || y >= 580){
            yVelocity = -yVelocity;
        }

        if(x <= p1.getX() + p1.getWidth()){
            if(y + size >= p1.getY() && y <= p1.getY() + p1.getHeight()){
                xVelocity = -xVelocity;
            }
        }

        if(x + size >= p2.getX()){
            if(y + size >= p2.getY() && y <= p2.getY() + p2.getHeight()){
                xVelocity = -xVelocity;
            }
        }
    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }

    public void reset(){
        x = 350;
        y = 300;
        xVelocity = -xVelocity;
        yVelocity = 4;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
