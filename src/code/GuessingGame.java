import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GuessingGame
 * This class represents a guessing game where a user tries to guess a randomly generated number
 * between a specified range. It keeps track of the user's guesses, score, and allows for feedback
 * submission which is saved to a file.
 * @author Andres Arevalo
 * @version 1.0
 */
public class GuessingGame implements Game
{
    private int                 guess;
    private int                 score;
    private boolean             guessed;
    private FileWriter          fw;
    private final int           min;
    private final int           max;
    private final List<String>  history;
    private final List<String>  feedbacks;

    private static final String ERROR_CLOSING_FILE;
    private static final String FILE_NAME;
    private static final String ERROR_WRITING_FILE;
    private static final int    MIN_FEEDBACK_LENGTH;
    private static final int    INITIAL_SCORE;

    {
        history     = new ArrayList<>();
        feedbacks   = new ArrayList<>();
        newGuessNumber();
        fw          = null;
    }

    static
    {
        FILE_NAME           = "feedbacks.txt";
        ERROR_CLOSING_FILE  = "Error closing the file";
        ERROR_WRITING_FILE  = "Error writing to file";
        INITIAL_SCORE       = 0;
        MIN_FEEDBACK_LENGTH = 10;
    }

    /**
     * Constructs a GuessingGame with specified minimum and maximum number for guessing.
     *
     * @param min The minimum number in the guessing range.
     * @param max The maximum number in the guessing range.
     */
    public GuessingGame(final int min,
                        final int max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * Generates a new random number to be guessed within the range.
     */
    private void newGuessNumber()
    {
        guess = (int) (Math.random() * (max - min)) + min;
        System.out.println(guess);
    }

    /**
     * Plays the guessing game by comparing the user's guess with the actual number.
     *
     * @param guess The number guessed by the user.
     * @return A string message indicating the result of the guess.
     */
    @Override
    public String play(final int guess)
    {
        if(guess < min || guess > max)
        {
            return String.format("Please enter a number between %d and %d.", min, max);
        }
        else
        {
            if(guess > this.guess)
            {
                guessed = false;
                history.add("Guessed too high: " + guess);
                return "Too High! Try Again.";
            }
            else if(guess < this.guess)
            {
                guessed = false;
                history.add("Guessed too low: " + guess);
                return "Too Low! Try Again.";
            }
            else
            {
                final String result;
                result = "Correct! the number was " + this.guess;

                guessed = true;
                history.add("Guessed correctly: " + guess);
                score ++;
                newGuessNumber();

                return result;
            }
        }
    }

    /**
     * Returns the current score of the user.
     *
     * @return The current score.
     */
    @Override
    public int getScore()
    {
        return score;
    }

    /**
     * Checks if the user has guessed the number correctly.
     *
     * @return {@code true} if the user has guessed correctly, otherwise {@code false}.
     */
    @Override
    public boolean isGameOver()
    {
        return guessed;
    }

    /**
     * Returns the history of all guesses made by the user.
     *
     * @return A string containing the history of guesses.
     */
    @Override
    public String getHistoryDetails()
    {
        return String.join("\n", history);
    }



    /**
     * Saves the user's feedback to a file.
     *
     * @param feedback The feedback provided by the user.
     * @throws InvalidFeedbackException If the feedback is invalid.
     */
    public void saveFeedback(String feedback) throws InvalidFeedbackException
    {
        if(feedback == null || feedback.isBlank() || feedback.length() < MIN_FEEDBACK_LENGTH)
        {
            throw new InvalidFeedbackException(
                    String.format("Invalid Feedback. It should be at least %s characters long",
                                  MIN_FEEDBACK_LENGTH));
        }
        feedbacks.add(feedback);

        try
        {
            fw = new FileWriter(FILE_NAME, true);
            fw.write(feedbacks.getLast() + System.lineSeparator());
        }
        catch(IOException e)
        {
            System.out.println(ERROR_WRITING_FILE);
            System.out.println(e.getMessage());
        }
        finally
        {
            if(fw != null)
            {
                try
                {
                    fw.close();
                }
                catch(IOException e)
                {
                    System.out.println(ERROR_CLOSING_FILE);
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Restarts the game by resetting the score and clearing the history and feedbacks.
     */
    public void restartGame()
    {
        guessed = false;
        score   = INITIAL_SCORE;
        try
        {
            fw      = new FileWriter(FILE_NAME);
            fw.write("");
        }
        catch(IOException e)
        {
            System.out.println(ERROR_WRITING_FILE);
            System.out.println(e.getMessage());
        }
        finally
        {
            if(fw != null)
            {
                try
                {
                    fw.close();
                }
                catch(IOException e)
                {
                    System.out.println(ERROR_CLOSING_FILE);
                    System.out.println(e.getMessage());
                }
            }
        }

        history.clear();
        feedbacks.clear();
        newGuessNumber();
    }

}
