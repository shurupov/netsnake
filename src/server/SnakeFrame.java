package server;

import codes.Codes;

import javax.swing.*;

/**
 * Developer: Eugene Shurupov
 * Date:      17.07.13
 * Time:      0:47
 */
public class SnakeFrame extends JFrame {

    private static final String WINDOW_TITLE = "Snakes";

    private Canvas canvas;

    public SnakeFrame() {
        super(WINDOW_TITLE);
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        createGUI();
    }

    private void createGUI() {
        canvas = new Canvas();
        getContentPane().add(canvas);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
