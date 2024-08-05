package cpsc2150.extendedTicTacToe.controllers;

import cpsc2150.extendedTicTacToe.models.*;
import cpsc2150.extendedTicTacToe.views.*;

/**
 * <p>
 * The {@link TicTacToeController} class will handle communication between our {@link TicTacToeView}
 * and our model ({@link IGameBoard} and {@link BoardPosition})
 * </p>
 *
 * <p>
 * This is where you will write code
 * <p>
 *
 * You will need to include your {@link BoardPosition} class, the {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class TicTacToeController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private TicTacToeView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;
    /**
     * <p>
     * The token of the current player whose turn it is
     * </p>
     */
    private char currPlayer;
    /**
     * <p>
     * The list of possible player tokens
     * </p>
     */
    private char[] playerChar;
    /**
     * <p>
     * Boolean checking for if the game is over
     * </p>
     */
    private boolean end;

    /**
     * <p>
     * This creates a controller for running the Extended TicTacToe game
     * </p>
     *
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     *
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public TicTacToeController(IGameBoard model, TicTacToeView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;

        // Some code is needed here.
        playerChar = new char[]{'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        currPlayer = 0;
        end = false;
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     *
     * @param row
     *      The row of the activated button
     * @param col
     *      The column of the activated button
     *
     * @post [ will allow the player to place a marker in the position if it is a valid space, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int row, int col) {
        // If the game is over and the players have clicked again we restart
        if (end) {
            newGame();
            return;
        }
        // Show whose turn it is
        screen.setMessage("It is player " + playerChar[(currPlayer + 1) % numPlayers] + "'s turn. ");

        // CHeck if space is available, then place marker
        if (curGame.checkSpace(new BoardPosition(row, col))) {
            curGame.placeMarker(new BoardPosition(row, col), playerChar[currPlayer % numPlayers]);
            screen.setMarker(row, col, playerChar[currPlayer % numPlayers]);
        } else {
            // If not available, request another try
            screen.setMessage("That space is unavailable! \nPlayer " + playerChar[(currPlayer) % numPlayers] + ", please try again.");
            return;
        }

        // Check for win conditions
        if (curGame.checkForWinner(new BoardPosition(row, col))) {
            screen.setMessage("Player " + playerChar[(currPlayer) % numPlayers] + " has won! \nClick any button to play again.");
            end = true;
        }
        // Check for draw
        else if (curGame.checkForDraw()){
            screen.setMessage("It's a draw! Click any button to play again.");
            end = true;
        }
        currPlayer++;
    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     *
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();

        //start back at the set-up menu
        GameSetupScreen screen = new GameSetupScreen();
        GameSetupController controller = new GameSetupController(screen);
        screen.registerObserver(controller);
    }
}