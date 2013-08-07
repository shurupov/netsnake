package client;

import java.io.IOException;

/**
 * Developer: Eugene Shurupov
 * Date:      07.08.13
 * Time:      23:24
 */
public class LeftSnake extends RightSnake {

    public LeftSnake() throws IOException {
        super();
    }

    @Override
    protected int makeStep(int[] field) {
        for (int i = direction; i >= 0; i--) {
            if (field[i] == 0) {
                return setDirection(i);
            }
        }
        for (int i = field.length - 1; i > direction; i--) {
            if (field[i] == 0) {
                return setDirection(i);
            }
        }
        return -1;
    }
}
