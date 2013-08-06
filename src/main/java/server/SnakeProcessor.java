package server;

import codes.Codes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.Random;

/**
 * Developer: Eugene Shurupov
 * Date:      16.07.13
 * Time:      0:22
 */
public class SnakeProcessor implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SnakeProcessor.class);

    private static final Object locker = new Object();

    private static final Random RAND = new Random(System.currentTimeMillis());

    private final Socket socket;
    private final Reader reader;
    private final OutputStream outputStream;
    private final Canvas canvas;

    private int color;

    Point[] snake = new Point[Codes.SNAKE_LENGTH];


    public SnakeProcessor(Socket socket, Canvas canvas) throws IOException {
        this.socket = socket;
        this.canvas = canvas;
        reader = new InputStreamReader(socket.getInputStream());
        outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {

        try {
            process();
        } catch (Exception e) {
            logger.debug("Process is broken", e);
        }

    }

    private void process() throws IOException, InterruptedException {
        if (!handshake()) {
            return;
        }

        int x = (int) (Math.random() * Codes.FIELD_WIDTH);
        int y = (int) (Math.random() * Codes.FIELD_HEIGHT);

        color = RAND.nextInt() & 0x00FFFFFF;

        logger.info("Color is {}", Integer.toHexString(color));

        for (int i = 0; i < snake.length; i++) {
            snake[i] = new Point(x, y);
        }

        canvas.getCells()[y][x] = color;

        while (true) {

            canvas.repaint();

            Thread.sleep(Codes.SLEEP_TIME);

            synchronized (locker) {

                x = snake[0].x;
                y = snake[0].y;

                int[] visibleCells = new int[]{
                        getCell(x, y - 1),
                        getCell(x + 1, y),
                        getCell(x, y + 1),
                        getCell(x - 1, y)
                };
                for (int cell : visibleCells)
                outputStream.write(cell);
                outputStream.flush();


                if (bye()) {
                    return;
                }

                int nextStep = reader.read();

                logger.debug("SnakeProcessor.process nextStep equals {}", nextStep);

                switch (nextStep) {
                    case 0 : step(x, y - 1);
                        break;
                    case 1 : step(x + 1, y);
                        break;
                    case 2 : step(x, y + 1);
                        break;
                    case 3 : step(x - 1, y);
                        break;
                    default: return;
                }

            }

        }

    }

    private void step(int x, int y) {
        canvas.move(x, y, snake[snake.length - 1].x, snake[snake.length - 1].y, color);

        for (int i = snake.length - 1; i > 0; i--) {
            snake[i] = snake[i - 1];
        }

        snake[0] = new Point(x, y);

        logger.info("New coordinates are {} {}", x, y);
    }

    private int getCell(int x, int y) {
        logger.debug("SnakeProcessor.getCell {} {}", x, y);
        if (x < 0 || x >= Codes.FIELD_WIDTH || y < 0 || y >= Codes.FIELD_HEIGHT) {
            return 100;
        }
        return canvas.getCells()[y][x];
    }

    private boolean handshake() throws IOException {
        if (reader.read() == Codes.HANDSHAKE_REQUEST_CODE) {
            outputStream.write(Codes.HANDSHAKE_RESPONSE_OK);
            outputStream.flush();
            logger.info("Handshake is successfully committed");
            return true;
        }
        socket.close();
        logger.info("Handshake is broken");
        return false;
    }

    private boolean bye() throws IOException {
        int response = reader.read();
        logger.debug("SnakeProcessor.bye received code {}", response);
        if (response == Codes.BYE_REQUEST) {
            socket.close();
            logger.info("SnakeProcessor.bye bye");
            return true;
        }
        return false;
    }

    private class Point {

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x;
        public int y;

    }
}
