package it.unibo.oop.lab.mvcio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();
    private final Controller controller = new Controller();

    /**
     * builds a new {@link SimpleGUI}.
     */
    public SimpleGUI() {
        final JPanel canvas = new JPanel(new BorderLayout());

        final JTextArea txtArea = new JTextArea();
        canvas.add(txtArea, BorderLayout.CENTER);

        final JButton saveBtn = new JButton("Save");
        canvas.add(saveBtn, BorderLayout.SOUTH);

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    SimpleGUI.this.controller.writeToCurrentFile(txtArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.frame.add(canvas);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         *
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
    }

    /**
     * Method that makes the {@link SimpleGUI} visible.
     * */
    public void display() {
        this.frame.setVisible(true);
    }

    public static void main(final String[] args) {
        new SimpleGUI().display();
    }

}
