package it.unibo.oop.lab.advanced;

public class DrawNumberViewConsole extends AbstractViewToFile {

    /**
     * {@inheritDoc}
     * */
    @Override
    protected void printMessage(final String message) {
        System.out.println(message);
    }

}
