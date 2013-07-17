package server;

import codes.Codes;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Developer: Eugene Shurupov
 * Date:      16.07.13
 * Time:      0:18
 */
public class SnakesField {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(Codes.APPLICATION_PORT);

        SnakeFrame frame = new SnakeFrame();

        while (true) {
            new Thread(new SnakeProcessor(serverSocket.accept(), frame.getCanvas())).start();
        }

    }

}
