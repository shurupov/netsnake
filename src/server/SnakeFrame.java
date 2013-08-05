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
        this.setSize(Codes.CELL_SIZE * Codes.FIELD_WIDTH, Codes.CELL_SIZE * Codes.FIELD_HEIGHT + 15);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
