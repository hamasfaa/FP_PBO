package main;

import Pong.Game1Panel;
import BricksBreaker.Game2Panel;
import MySnake.Game3Panel;
import javax.swing.*;

public class GameWindow {
    private JFrame frame;

    public GameWindow() {
        frame = new JFrame("Game Selection Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Games");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem game1 = new JMenuItem("Game 1: Pong");
        JMenuItem game2 = new JMenuItem("Game 2: Bricks Breaker");
        JMenuItem game3 = new JMenuItem("Game 3: Snake");

        JMenuItem exitGame = new JMenuItem("Exit");
        JMenuItem about = new JMenuItem("About");

        game1.addActionListener(e -> {
            frame.getContentPane().removeAll();
            Game1Panel gamePanel = new Game1Panel();
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });

        game2.addActionListener(e -> {
            frame.getContentPane().removeAll();
            Game2Panel gamePanel = new Game2Panel();
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });

        game3.addActionListener(e -> {
            frame.getContentPane().removeAll();
            Game3Panel gamePanel = new Game3Panel();
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocusInWindow();
        });

        exitGame.addActionListener(e -> System.exit(0));

        about.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                "Hamasah Fatiy Dakhilullah(5025231139)\nRafael Asa Edginius Krisdina(5025231143)\nAmadeo Yesa(5025231160)\nGame Selection Application\nChoose from 3 different games!",
                "About", JOptionPane.INFORMATION_MESSAGE));

        gameMenu.add(game1);
        gameMenu.add(game2);
        gameMenu.add(game3);
        gameMenu.addSeparator();
        gameMenu.add(exitGame);

        helpMenu.add(about);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }
}