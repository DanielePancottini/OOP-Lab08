package it.unibo.oop.lab.advanced;

/**
 * Abstract class to model a view which needs to write messages into file.
 * Used to avoid repetitions.
 * */
public abstract class AbstractViewToFile implements DrawNumberView {

    private DrawNumberViewObserver observer;

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setObserver(final DrawNumberViewObserver observer) {
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void start() {
        this.printMessage("Game Started");
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void numberIncorrect() {
        this.printMessage("Number Incorrect, try again...");
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
        case YOURS_HIGH:
        case YOURS_LOW:
            this.printMessage(res.getDescription());
            return;
        case YOU_WON:
            this.printMessage(res.getDescription() + " New game starts");
            break;
        default:
            throw new IllegalStateException("Unexpected result: " + res);
        }
        observer.resetGame();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void limitsReached() {
        this.printMessage("You lost" + " New game starts");
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void displayError(final String message) {
        this.printMessage(message);
    }

    protected abstract void printMessage(String message);


}
