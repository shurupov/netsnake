package client;

import codes.Codes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Arrays;

/**
 * Developer: Eugene Shurupov
 * Date:      17.07.13
 * Time:      22:30
 */
abstract public class SnakeIntelligence {

    protected static Logger logger = LoggerFactory.getLogger(SnakeIntelligence.class);

    private final Socket socket;
    private final Writer writer;
    private final InputStream inputStream;

    public SnakeIntelligence() throws IOException {

        socket = new Socket("localhost", Codes.APPLICATION_PORT);
        writer = new OutputStreamWriter(socket.getOutputStream());
        inputStream = socket.getInputStream();

    }

    public void process() throws IOException {

        if (!handshake()) {
            return;
        }

        int [] field = new int[Codes.STEP_VARIANT_COUNT];
        int nextStep;

        while (read(field)) { //we have response
            logger.debug("SnakeIntelligence.process visible field received is {}", Arrays.toString(field));

            if (hasEmptyCell(field)) {
                go();
            } else {
                bye();
                return;
            }

            nextStep = makeStep(field);

            logger.info("Next step is " + nextStep);
            writer.write(nextStep);
            writer.flush();
        }

    }

    abstract protected int makeStep(int[] field);

    private boolean hasEmptyCell(int [] cells) {
        for (int cell : cells) {
            if (cell == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean read(int [] field) throws IOException {
        for (int i = 0; i < field.length; i++) {
            int readInt = inputStream.read();
            if (readInt == -1) {
                return false;
            }
            field[i] = readInt;
        }
        return true;
    }

    private boolean handshake() throws IOException {

        writer.write(Codes.HANDSHAKE_REQUEST_CODE);
        writer.flush();
        int response = inputStream.read();
        logger.debug("Client.handshake response={}", response);
        if (response == Codes.HANDSHAKE_RESPONSE_OK) {
            logger.info("Handshake is successfully committed");
            return true;
        }
        socket.close();
        logger.info("Handshake is broken");
        return false;

    }

    private void bye() throws IOException {
        logger.info("SnakeIntelligence.bye");
        writer.write(Codes.BYE_REQUEST);
        writer.flush();
        socket.close();
    }

    private void go() throws IOException {
        logger.info("SnakeIntelligence.go");
        writer.write(Codes.GO_REQUEST);
        writer.flush();
    }

}
