import javax.swing.*;
import java.awt.*;

/**
 * Main
 *
 * @author Andres Arevalo
 * @version 1.0
 */
public class Main
{
    private static final String APP_NAME;
    private static final String MENU_TITLE;
    private static final String OPTION1;
    private static final String OPTION2;
    private static final String OPTION3;
    private static final String EXIT;
    private static final String SUBMIT_BUTTON;
    private static final int    WIDTH;
    private static final int    HEIGHT;
    private static final int    MIN_NUMBER;
    private static final int    MAX_NUMBER;
    private static final int    ROWS_MAIN_PANEL;
    private static final int    COLS_MAIN_PANEL;
    private static final int    GAP_MAIN_PANEL;
    private static final int    MEDIUM_WIDTH;
    private static final int    SMALL_HEIGHT;


    static
    {
        APP_NAME         = "Guess the Number Game";
        MENU_TITLE       = "OPTIONS";
        OPTION1          = "How To Play";
        OPTION2          = "Send Feedback";
        OPTION3          = "Restart Game";
        EXIT             = "Exit";
        SUBMIT_BUTTON    = "Submit Guess";
        WIDTH            = 800;
        HEIGHT           = 600;
        MIN_NUMBER       = 1;
        MAX_NUMBER       = 100;
        MEDIUM_WIDTH     = 120;
        SMALL_HEIGHT     = 30;
        ROWS_MAIN_PANEL  = 3;
        COLS_MAIN_PANEL  = 1;
        GAP_MAIN_PANEL   = 10;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI()
    {
        final JFrame   frame;
        final JMenuBar menuBar;
        final JMenu    menu;
        final JPanel   panel;
        final JPanel   userPanel;

        final JMenuItem option1;
        final JMenuItem option2;
        final JMenuItem option3;
        final JMenuItem exit;

        final JLabel headerLabel;
        final JLabel resultLabel;

        final JTextField userInput;

        final JButton submitGuess;

        frame       = new JFrame(APP_NAME);
        panel       = new JPanel();
        userPanel   = new JPanel();
        menuBar     = new JMenuBar();
        menu        = new JMenu(MENU_TITLE);

        option1 = new JMenuItem(OPTION1);
        option2 = new JMenuItem(OPTION2);
        option3 = new JMenuItem(OPTION3);
        exit    = new JMenuItem(EXIT);

        headerLabel =
                new JLabel(String.format("Guess a number between %d and %d",
                            MIN_NUMBER,
                            MAX_NUMBER), JLabel.CENTER);
        submitGuess = new JButton(SUBMIT_BUTTON);
        userInput   = new JTextField();

        userInput.setPreferredSize(new Dimension(MEDIUM_WIDTH, SMALL_HEIGHT));
        submitGuess.setPreferredSize(new Dimension(MEDIUM_WIDTH, SMALL_HEIGHT));
        userPanel.add(userInput);
        userPanel.add(submitGuess);

        panel.setLayout(new GridLayout(ROWS_MAIN_PANEL, COLS_MAIN_PANEL, GAP_MAIN_PANEL, GAP_MAIN_PANEL));
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(userPanel, BorderLayout.CENTER);

        option1.addActionListener(
                e ->
                  {
                      final String instructions;
                      instructions = getGameInstructions();
                      JOptionPane.showMessageDialog(frame, getGameInstructions());
                  });

        menu.add(option1);
        menu.add(option2);
        menu.add(option3);
        menu.add(exit);

        menuBar.add(menu);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setJMenuBar(menuBar);
        frame.add(panel);
        frame.setVisible(true);

    }

    private static String getGameInstructions()
    {
        return "Instructions:\n" +
                "1. Enter a number between 1 and 100 in the text field.\n" +
                "2. Click \"Submit Guess\" to check your guess.\n" +
                "3. If your guess is too low or to high, you will receive feedback.\n" +
                "4. Keep guessing until you find the correct number.\n" +
                "5. Your score will be displayed and updated after each guess.\n" +
                "6. You can restar the game or exit from the \"Options\" menu.";
    }
}
