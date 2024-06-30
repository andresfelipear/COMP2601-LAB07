
/**
 * Game
 *
 * @author Andres Arevalo
 * @version 1.0
 */
public interface Game
{
    /**
     * Plays the game with the given input.
     *
     * @param input The input for playing the game.
     * @return The result of the play.
     */
    String play(final int input);

    /**
     * Retrieves the details of the game's history.
     *
     * @return A string representing the game's history.
     */
    String getHistoryDetails();

    /**
     * Gets the current score of the game.
     *
     * @return The current score.
     */
    int getScore();

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver();

    /**
     * Saves feedback for the game.
     *
     * @param feedback The feedback to be saved.
     * @throws InvalidFeedbackException if the feedback is invalid.
     */
    void saveFeedback(final String feedback) throws InvalidFeedbackException;

    /**
     * Restarts the game.
     */
    void restartGame();
}
