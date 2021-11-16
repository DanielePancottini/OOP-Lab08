package it.unibo.oop.lab.advanced;

import java.util.Map;
import java.util.Random;

/**
 *Implementation of {@link DrawNumber} which reprensents the model class.
 */
public final class DrawNumberImpl implements DrawNumber {

    private int choice;
    private final int min;
    private final int max;
    private final int attempts;
    private int remainingAttempts;
    private final Random random = new Random();

    /**
     * Builds a new {@link DrawNumberImpl}.
     *
     * @param propsMap properties of config file, default values if io errors
     *
     * @throws IOException
     */
    public DrawNumberImpl(final Map<String, Integer> propsMap) {

        this.max = propsMap.get("maximum");
        this.min = propsMap.get("minimum");
        this.attempts = propsMap.get("attempts");

        this.reset();
    }

    @Override
    public void reset() {
        this.remainingAttempts = this.attempts;
        this.choice = this.min + random.nextInt(this.max - this.min + 1);
    }

    @Override
    public DrawResult attempt(final int n) throws AttemptsLimitReachedException {
        if (this.remainingAttempts <= 0) {
            throw new AttemptsLimitReachedException();
        }
        if (n < this.min || n > this.max) {
            throw new IllegalArgumentException("The number is outside boundaries");
        }
        remainingAttempts--;
        if (n > this.choice) {
            return DrawResult.YOURS_HIGH;
        }
        if (n < this.choice) {
            return DrawResult.YOURS_LOW;
        }
        return DrawResult.YOU_WON;
    }

}
