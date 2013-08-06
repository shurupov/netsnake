package server;

import codes.Codes;

import javax.swing.*;
import java.awt.*;

/**
 * Developer: Eugene Shurupov
 * Date:      17.07.13
 * Time:      1:24
 */
public class Canvas extends JPanel {


    private int[][] cells = new int[Codes.FIELD_HEIGHT][Codes.FIELD_WIDTH];

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.black);

        for (int y = 0; y < Codes.FIELD_HEIGHT; y++) {
            for (int x = 0; x < Codes.FIELD_WIDTH; x++) {
                if (cells[y][x] > 0) {
                    g.setColor(new Color(cells[y][x]));
                    g.fillRect(x * Codes.CELL_SIZE + 1, y * Codes.CELL_SIZE + 1,
                            Codes.CELL_SIZE -2, Codes.CELL_SIZE -2);
                }
            }
        }

    }

    public int[][] getCells() {
        return cells;
    }

    synchronized public void move(int newX, int newY, int lastX, int lastY, int color) {
        cells[lastY][lastX] = 0;
        cells[newY][newX] = color;
    }
}
