package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUIWithFileChooser {

    /*
     * TODO: Starting from the application in mvcio:
     *
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     *
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     *
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     *
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    private final JFrame frame = new JFrame();
    private final Controller controller = new Controller();

    /**
     * builds a new {@link SimpleGUIWithFileChooser}.
     */
    public SimpleGUIWithFileChooser() {
        final JPanel canvas = new JPanel(new BorderLayout());

        final JTextArea txtArea = new JTextArea();
        canvas.add(txtArea, BorderLayout.CENTER);

        final JButton saveBtn = new JButton("Save");
        canvas.add(saveBtn, BorderLayout.SOUTH);

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    SimpleGUIWithFileChooser.this.controller.writeToCurrentFile(txtArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        /*
         * Browse Section
         * */
        final JPanel browsePanel = new JPanel();
        browsePanel.setLayout(new BorderLayout());

        final JTextField txtBrowse = new JTextField(this.controller.getCurrentFile().getName());
        txtBrowse.setEditable(false);
        browsePanel.add(txtBrowse, BorderLayout.CENTER);

        final JButton browseBtn = new JButton("Browse...");
        browsePanel.add(browseBtn, BorderLayout.LINE_END);

        canvas.add(browsePanel, BorderLayout.NORTH);

        /*
         * Browse Button Handler
         * */
        browseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser currentFileChooser = new JFileChooser();
                final int result = currentFileChooser.showSaveDialog(null);

                switch (result) {
                    case JFileChooser.APPROVE_OPTION:
                        SimpleGUIWithFileChooser.this.controller.setCurrentFile(currentFileChooser.getSelectedFile());
                        txtBrowse.setText(SimpleGUIWithFileChooser.this.controller.getCurrentFile().getName());
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Error During File Choosing");
                        break;
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
     * Method that makes the {@link SimpleGUIWithFileChooser} visible.
     * */
    public void display() {
        this.frame.setVisible(true);
    }

    public static void main(final String[] args) {
        new SimpleGUIWithFileChooser().display();
    }


}
