package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Developer: Eugene Shurupov
 * Date:      07.08.13
 * Time:      22:43
 */
public class RightSnake extends SnakeIntelligence {

    protected static Logger logger = LoggerFactory.getLogger(RightSnake.class);

    int direction = 0;

    public RightSnake() throws IOException {
        super();
    }

    @Override
    protected int makeStep(int[] field) {
        for (int i = direction; i < field.length; i++) {
            if (field[i] == 0) {
                return setDirection(i);
            }
        }
        for (int i = 0; i < direction; i++) {
            if (field[i] == 0) {
                return setDirection(i);
            }
        }
        return -1;
    }

    protected int setDirection(int direction) {
        logger.info("direction {}", direction);
        this.direction = direction;
        return direction;

    }
}
