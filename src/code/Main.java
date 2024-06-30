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
    private static final String INVALID_INPUT;
    private static final String FEEDBACK_TITLE;
    private static final int    WIDTH;
    private static final int    HEIGHT;
    private static final int    MIN_NUMBER;
    private static final int    MAX_NUMBER;
    private static final int    ROWS_MAIN_PANEL;
    private static final int    COLS_MAIN_PANEL;
    private static final int    GAP_MAIN_PANEL;
    private static final int    MEDIUM_WIDTH;
    private static final int    SMALL_HEIGHT;
    private static final String GAME_DESCRIPTION;
    private static final String ERROR_FEEDBACK_TITLE;
    private static final String GUESS_HISTORY_TITLE;


    static
    {
        APP_NAME             = "Guess the Number Game";
        MENU_TITLE           = "OPTIONS";
        OPTION1              = "How To Play";
        OPTION2              = "Send Feedback";
        OPTION3              = "Restart Game";
        EXIT                 = "Exit";
        SUBMIT_BUTTON        = "Submit Guess";
        FEEDBACK_TITLE       = "Enter your feedback:";
        ERROR_FEEDBACK_TITLE = "Feedback Error";
        GUESS_HISTORY_TITLE  = "Guess History";
        WIDTH                = 800;
        HEIGHT               = 600;
        MIN_NUMBER           = 1;
        MAX_NUMBER           = 100;
        MEDIUM_WIDTH         = 120;
        SMALL_HEIGHT         = 30;
        ROWS_MAIN_PANEL      = 4;
        COLS_MAIN_PANEL      = 1;
        GAP_MAIN_PANEL       = 10;

        INVALID_INPUT    = String.format("Please enter a number between %d and %d.", MIN_NUMBER, MAX_NUMBER);
        GAME_DESCRIPTION = String.format("Guess a number between %d and %d", MIN_NUMBER, MAX_NUMBER);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    /**
     * Creates and displays the GUI for the Guess the Number Game.
     * This method initializes all components and sets up event listeners.
     */
    private static void createAndShowGUI()
    {
        final Game game;
        final JFrame   frame;
        final JMenuBar menuBar;
        final JMenu    menu;
        final JPanel   panel;
        final JPanel   userPanel;

        final JMenuItem gameInstructions;
        final JMenuItem sendFeedback;
        final JMenuItem restartGame;
        final JMenuItem exit;

        final JLabel headerLabel;
        final JLabel scoreLabel;
        final JLabel resultLabel;

        final JTextField userInput;

        final JButton submitGuess;

        game        = new GuessingGame(MIN_NUMBER, MAX_NUMBER);
        frame       = new JFrame(APP_NAME);
        panel       = new JPanel();
        userPanel   = new JPanel();
        menuBar     = new JMenuBar();
        menu        = new JMenu(MENU_TITLE);

        gameInstructions = new JMenuItem(OPTION1);
        sendFeedback    = new JMenuItem(OPTION2);
        restartGame         = new JMenuItem(OPTION3);
        exit            = new JMenuItem(EXIT);

        headerLabel = new JLabel(GAME_DESCRIPTION, JLabel.CENTER);
        resultLabel = new JLabel("", JLabel.CENTER);
        scoreLabel  = new JLabel("Score: 0", JLabel.CENTER);

        submitGuess = new JButton(SUBMIT_BUTTON);
        userInput   = new JTextField();

        userInput.setPreferredSize(new Dimension(MEDIUM_WIDTH, SMALL_HEIGHT));
        submitGuess.setPreferredSize(new Dimension(MEDIUM_WIDTH, SMALL_HEIGHT));
        userPanel.add(userInput);
        userPanel.add(submitGuess);

        panel.setLayout(new GridLayout(ROWS_MAIN_PANEL, COLS_MAIN_PANEL, GAP_MAIN_PANEL, GAP_MAIN_PANEL));
        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(userPanel, BorderLayout.CENTER);
        panel.add(scoreLabel, BorderLayout.CENTER);
        panel.add(resultLabel, BorderLayout.SOUTH);

        submitGuess.addActionListener(e ->{
            final int guess;
            String response;

            try
            {
                guess    = Integer.parseInt(userInput.getText());
                response = game.play(guess);
            }
            catch(NumberFormatException ex)
            {
                response = INVALID_INPUT;
            }

            scoreLabel.setText("Score: " + game.getScore());
            resultLabel.setText(response);
            userInput.setText("");

            if(game.isGameOver())
            {
                JOptionPane.showMessageDialog(frame, game.getHistoryDetails(), GUESS_HISTORY_TITLE, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gameInstructions.addActionListener(
                e -> JOptionPane.showMessageDialog(frame, getGameInstructions()));

        sendFeedback.addActionListener(
                e ->
                {
                    final String feedback;
                    feedback = JOptionPane.showInputDialog(frame, null, FEEDBACK_TITLE, JOptionPane.QUESTION_MESSAGE);

                    try
                    {
                        game.saveFeedback(feedback);
                    }
                    catch(InvalidFeedbackException ex)
                    {
                        JOptionPane.showMessageDialog(frame, ex.getMessage(), ERROR_FEEDBACK_TITLE, JOptionPane.ERROR_MESSAGE);
                    }
                });

        restartGame.addActionListener(e ->
          {
              game.restartGame();
              scoreLabel.setText("Score: " + game.getScore());
              userInput.setText("");
              resultLabel.setText("");
          });

        exit.addActionListener(e -> frame.dispose());

        menu.add(gameInstructions);
        menu.add(sendFeedback);
        menu.add(restartGame);
        menu.add(exit);

        menuBar.add(menu);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setJMenuBar(menuBar);
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Returns the game instructions.
     *
     * @return A string containing the game instructions.
     */
    private static String getGameInstructions()
    {
        return """
                Instructions:
                1. Enter a number between 1 and 100 in the text field.
                2. Click "Submit Guess" to check your guess.
                3. If your guess is too low or to high, you will receive feedback.
                4. Keep guessing until you find the correct number.
                5. Your score will be displayed and updated after each guess.
                6. You can restart the game or exit from the "Options" menu.""";
    }
}
