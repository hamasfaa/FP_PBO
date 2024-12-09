package main;

//import brick.Game3Panel;
//import pacman.Game1Panel;
//import snake.Game1Panel;

import javax.swing.*;

public class GameWindow {
        private JFrame frame;
public GameWindow() {
    JFrame frame = new JFrame("Game Selection Menu");
    GamePanel gamePanel = new GamePanel(1); //Defaultnya 1, nanti bikin window default

    frame.add(gamePanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setLocationRelativeTo(null);

    JMenuBar menuBar = new JMenuBar();

    JMenu gameMenu = new JMenu("Games");
    JMenu helpMenu = new JMenu("Help");

    JMenuItem game1 = new JMenuItem("Game 1: ????");
    JMenuItem game2 = new JMenuItem("Game 2: ????");
    JMenuItem game3 = new JMenuItem("Game 3: ????");

    JMenuItem exitGame = new JMenuItem("Exit");
    JMenuItem about = new JMenuItem("About");

//    game1.addActionListener(e -> {
//        frame.getContentPane().removeAll();
//        frame.add(new Game1Panel());
//        frame.revalidate();
//        frame.repaint();
//    });

//        game2.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            frame.add(new Game2Panel());
//            frame.revalidate();
//            frame.repaint();
//        });
//
//        game3.addActionListener(e -> {
//            frame.getContentPane().removeAll();
//            frame.add(new Game3Panel());
//            frame.revalidate();
//            frame.repaint();
//        });

    exitGame.addActionListener(e -> System.exit(0));

    about.addActionListener(e -> JOptionPane.showMessageDialog(frame,
            "Game Selection Application\nChoose from 3 different games!",
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