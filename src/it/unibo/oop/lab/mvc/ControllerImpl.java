package it.unibo.oop.lab.mvc;

import java.util.LinkedList;
import java.util.List;

public class ControllerImpl implements Controller {

    private final List<String> history;
    private String stringToPrint;

    /**
     * Builds a new {@link ControllerImpl}.
     * */
    public ControllerImpl() {
        this.history = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void printNext() {
        if (this.isUnset(this.stringToPrint)) {
            System.out.println(this.stringToPrint);
            this.history.add(this.stringToPrint);
            this.unsetString();
        } else {
            throw new IllegalStateException("String to be printed unset");
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public void setNextString(final String toPrint) {
        this.stringToPrint = toPrint;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public String getNextString() {
        return this.stringToPrint;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<String> getPrintedHistory() {
        return List.copyOf(this.history);
    }

    private boolean isUnset(final String s) {
        return s == null || s.isEmpty();
    }

    private void unsetString() {
        this.stringToPrint = null;
    }

}
