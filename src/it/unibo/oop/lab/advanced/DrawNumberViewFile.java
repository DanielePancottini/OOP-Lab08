package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Implementation of {@link DrawNumberView} which prints game status into a log file.
 * */
public class DrawNumberViewFile extends AbstractViewToFile {

    private static final File LOG_FILE = new File(System.getProperty("user.home") + File.separator + "log.txt");

    /**
     *{@inheritDoc}
     * */
    @Override
    protected void printMessage(final String message) {
        try (PrintStream ps = new PrintStream(DrawNumberViewFile.LOG_FILE)) {
            ps.println(message);
        } catch (FileNotFoundException e) {
            this.displayError(e.getMessage());
        }
    }

}
