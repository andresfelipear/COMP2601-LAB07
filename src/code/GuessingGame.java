import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GuessingGame
 *
 * @author Andres Arevalo
 * @version 1.0
 */
public class GuessingGame
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

    public GuessingGame(final int min,
                        final int max)
    {
        this.min = min;
        this.max = max;
    }

    private void newGuessNumber()
    {
        guess = (int) (Math.random() * (max - min)) + min;
        System.out.println(guess);
    }

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

    public List<String> getHistory()
    {
        return history;
    }

    public int getScore()
    {
        return score;
    }

    public String getHistoryDetails()
    {
        return String.join("\n", history);
    }

    public boolean isGuessed()
    {
        return guessed;
    }

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
            fw.write(feedback + System.lineSeparator());
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
