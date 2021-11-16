package it.unibo.oop.lab.mvcio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * This class implements a simple controller responsible of I/O access. It
 * considers a single file at a time, and it is able to serialize objects in it.
 */
public class Controller {

    private File currentFile;
    private static final String DEFAULT_PATH = System.getProperty("user.home") + File.separator + "output.txt";

    /**
     * Constructor that sets the current file to a default file with following path:
     *      <user.home>/output.txt.
     * */
    public Controller() {
        this.currentFile = new File(Controller.DEFAULT_PATH);
    }

    /**
     * Writes input string to current file.
     *
     * @param toWrite string to be write into current file
     * @throws FileNotFoundException
     *
     * */
    public void writeToCurrentFile(final String toWrite) throws FileNotFoundException {
        try (PrintStream ps = new PrintStream(this.currentFile)) {
            ps.print(toWrite);
        }
    }

    /**
     * Sets receiver's current file to input new current file.
     *
     * @param newCurrent {@link File} to be set as new current file
     * */
    public void setCurrentFile(final File newCurrent) {
        if (newCurrent.exists()) {
            this.currentFile = newCurrent;
        }
    }

    /**
     * Returns the receiver's current file.
     *
     * @return the current file as {@link File}
     * */
    public File getCurrentFile() {
        return new File(this.currentFile.getPath());
    }

    /**
     * Returns the current file's path.
     *
     * @return current file's path as {@link String}
     * */
    public String getCurrentFilePath() {
        return this.currentFile.getPath();
    }

}
