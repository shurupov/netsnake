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


    private byte[][] cells = new byte[Codes.FIELD_WIDTH][Codes.FIELD_HEIGHT];

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setBackground(Color.black);

        g.setColor(Color.white);

        for (int y = 0; y < Codes.FIELD_HEIGHT; y++) {
            for (int x = 0; x < Codes.FIELD_WIDTH; x++) {
                if (cells[y][x] > 0) {
                    g.fillRect(x * 50 + 1, y * 50 + 1, 48, 48);
                }
            }
        }

    }

    public byte[][] getCells() {
        return cells;
    }

    synchronized public void move(int newX, int newY, int lastX, int lastY) {
        cells[newY][newX] = 1;
        cells[lastY][lastX] = 0;
    }
}
