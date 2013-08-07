package client;

import java.io.IOException;

/**
 * Developer: Eugene Shurupov
 * Date:      07.08.13
 * Time:      23:21
 */
public class Snake {

    public static void main(String[] args) throws IOException {

        if (args.length > 0) {

            if ("-random".equals(args[0])) {
                new RandomSnake().process();
                return;
            }
            if ("-right".equals(args[0])) {
                new RightSnake().process();
                return;
            }if ("-left".equals(args[0])) {
                new LeftSnake().process();
                return;
            }

        }

    }

}
