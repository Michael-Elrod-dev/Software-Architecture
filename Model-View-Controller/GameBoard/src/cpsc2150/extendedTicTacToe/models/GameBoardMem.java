/*
 * Name: Michal Elrod
 * Class: 2150
 * Assignment: Project: Tic-Tac-Toe Board
 */
package cpsc2150.extendedTicTacToe.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardMem extends AbsGameBoard implements IGameBoard
{
    /**
     * </Description: This project creates a list of positions on a Tic-Tac-Toe board of a specified size>
     * </and supports multiple players>
     * @invariant (IGameBoard.MIN_ROW <= numRow <= IGameBoard.MAX_ROW)
     *             (IGameBoard.MIN_COL <= numColumn <= IGameBoard.MAX_COL)
     *             (IGameBoard.MIN_NUM_TO_WIN <= numToWin <= IGameBoard.MAX_NUM_TO_WIN)
     * @correspondence self = [a Map<Character, List<BoardPosition>> that hold a
     *                          players positions on a game board]
     */
    private int numRow;
    private int numColumn;
    private int numToWin;
    private Map<Character, List<BoardPosition>> gameBoard;

    /**
     * </Description: Class constructor assigns variables>
     * @pre MIN_ROW <= row <= MAX_ROW
     *      MIN_COL <= column <= MAX_COL
     *      MIN_NUM_TO_WIN <= wins <= MAX_NUM_TO_WIN
     *      wins <= MIN_ROW
     *      wins <= MIN_COLUMN
     * @post [gameBoard is a key/value map collection that contains a Character as
     *       key and list of BoardPositions as the values]
     *       numRow = #row
     *       numColumn = #column
     *       numToWin = #wins
     */
    public GameBoardMem(int row, int column, int wins) {
        numRow = row;
        numColumn = column;
        numToWin = wins;
        gameBoard = new HashMap<>();
    }

    /**
     * </Description: Places the character in marker on the position specified by
     * marker and should not be called if the space is unavailable>
     * @param pos position on the game board
     * @param player token for the current player
     * @pre checkSpace(position) = true
     * @post [player's token is placed in list of positions for specified player]
     */
    public void placeMarker(BoardPosition pos, char player)
    {
        // If key doesn't exist, create it
        if (!gameBoard.containsKey(player))
            gameBoard.put(player, new ArrayList<>());

        // Add passed value to player's key
        gameBoard.get(player).add(pos);
    }

    /**
     * </Description: Checks what is at the given position on the board>
     * @param pos position to be checked
     * @return character at the given position
     * @pre [pos is a valid position on the game board]
     * @post whatsAtPos() = [current player's token OR and empty space]
     */
    public char whatsAtPos(BoardPosition pos)
    {
        for (HashMap.Entry<Character, List<BoardPosition>> map : gameBoard.entrySet())
            if (isPlayerAtPos(pos, map.getKey()))
                return map.getKey();

        return ' ';
    }

    /**
     * </Description: Checks if player is at the given position>
     * @param pos position on the board
     * @param player player being checked for
     * @return true OR false
     * @pre [pos is a valid position on the game board]
     * @post true iff whatsAtPos(pos) = player
     */
    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        return gameBoard.get(player).contains(pos);
    }

    /**
     * </Description: Returns the number of rows in the GameBoard>
     * @return numRow
     * @post [number of rows is returned]
     */
    public int getNumRows() { return numRow; }

    /**
     * </Description: Returns the number of columns in the GameBoard>
     * @return numColumn
     * @post [number of columns is returned]
     */
    public int getNumColumns() { return numColumn; }

    /**
     * </Description: Returns the number of tokens in a row needed to win the game>
     * @return numToWin
     * @post [number of connected tokens to win is returned]
     */
    public int getNumToWin() { return numToWin; }
}