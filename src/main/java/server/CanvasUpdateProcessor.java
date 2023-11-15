package server;

import codes.Codes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CanvasUpdateProcessor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CanvasUpdateProcessor.class);

    private final Canvas canvas;
    private final Object locker;

    public CanvasUpdateProcessor(Canvas canvas, Object locker) {
        this.canvas = canvas;
        this.locker = locker;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            logger.debug("Process is broken", e);
        }
    }

    private void process() throws InterruptedException {
        while (true) {
            synchronized (locker) {
                canvas.repaint();
            }
            Thread.sleep(Codes.SLEEP_TIME);
        }
    }
}
