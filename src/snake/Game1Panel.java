package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game1Panel extends JPanel implements ActionListener {
    private float x = 100, y = 100;
    private float xVel = 10f, yVel = 2f;
    private Timer timer;

    public Game1Panel() {
        setBackground(Color.BLACK);
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval((int) x, (int) y, 20, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateBall();
        repaint();
    }

    private void updateBall() {
        x += xVel;
        y += yVel;

        if (x > getWidth() - 20 || x < 0) {
            xVel *= -1;
        }
        if (y > getHeight() - 20 || y < 0) {
            yVel *= -1;
        }}
}