package client;

import codes.Codes;

import java.io.IOException;

/**
 * Developer: Eugene Shurupov
 * Date:      07.08.13
 * Time:      23:19
 */
public class RandomSnake extends SnakeIntelligence {

    public RandomSnake() throws IOException {
        super();
    }

    @Override
    protected int makeStep(int[] field) {
        int nextStep;
        do {
            nextStep = (int) Math.floor(Math.random() * Codes.STEP_VARIANT_COUNT);
        } while (field[nextStep] > 0); //field is filled
        return nextStep;
    }

}
