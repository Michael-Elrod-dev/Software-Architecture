package cpsc2150.extendedTicTacToe.models;
import java.lang.*;

/**
 * GameBoard is abstractly a 2 dimensional grid of characters used to play Tic-Tac-Toe
 *
 * Defines: numRow: Z - The number of rows in the game board
 *          numColumn: Z - The number of columns in the game board
 *          numToWin: Z - The number of markers in a row needed to win
 *          MAX_ROW: Z - The max number of rows the game board can have
 *          MIN_ROW: Z - The minimum number of rows the game board can have
 *          MAX_COL: Z - The max number of columns the game board can have
 *          MIN_COL: Z - The minimum number of columns the game board can have
 *          MAX_NUM_TO_WIN: Z -  The max number of connected markers needed to win
 *          MIN_NUM_TO_WIN: Z -  The minimum number of connected markers needed to win
 *
 * Initialization Ensures: The game board will be of size
 *          getNumRows() x getNumColumns() and filled with empty spaces
 *          game board will be at most MAX_ROW x MAX_COL and at least MIN_ROW x MIN_COL
 *
 * @constraints MIN_ROW <= numRow <= MAX_ROW
 *              MIN_COL <= numColumn <= MAX_COL
 *              MIN_NUM_TO_WIN <= numToWin <= MAX_NUM_TO_WIN
 *              numToWin <= numRow
 *              numToWin <= numColumn
 */
public interface IGameBoard
{
    public static final int MAX_ROW = 100;
    public static final int MIN_ROW = 3;
    public static final int MAX_COL = 100;
    public static final int MIN_COL = 3;
    public static final int MAX_NUM_TO_WIN = 25;
    public static final int MIN_NUM_TO_WIN = 3;
    public static final int MAX_PLAYERS = 10;
    public static final int MIN_PLAYERS = 2;
    /**
     * </Description: Checks a given space on the board to see if it is free>
     * @param pos the position to be checked
     * @return true OR false
     * @post [true iff position is available for a player to place a marker]
     */
    public default boolean checkSpace(BoardPosition pos)
    {
        // Cannot call whatsAtPos() with invalid bounds, so we check that first
        if (pos.getRow() < getNumRows() &&
            pos.getRow() >= 0 &&
            pos.getColumn() < getNumColumns() &&
            pos.getColumn() >= 0)
            return (whatsAtPos(pos) == ' ');
        return false;
    }

    /**
     * </Description: Checks if player is at the given position>
     * @param pos position on the board
     * @param player player being checked for
     * @return true OR false
     * @pre [pos is a valid position on the game board]
     * @post true iff whatsAtPos(pos) = player
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player)
    {
        return (whatsAtPos(pos) == player);
    }

    /**
     * </Description: Checks the board for a win, 5 consecutive markers>
     * @param lastPos the position of the last marker that was placed
     * @return true OR false
     * @pre lastPos != NULL
     * @post true iff checkHorizontalWin(lastPos, player) = true OR
     *                checkVerticalWin(lastPos, player)   = true OR
     *                checkDiagonalWin(lastPos, player)   = true
     */
    public default boolean checkForWinner(BoardPosition lastPos)
    {
        char player = whatsAtPos(lastPos);

        return  (checkHorizontalWin(lastPos, player) ||
                checkVerticalWin(lastPos, player)   ||
                checkDiagonalWin(lastPos, player));
    }

    /**
     * </Description: Checks to see if all board positions are taken>
     * @return true OR false
     * @post true iff checkSpace() = false for all positions
     */
    public default boolean checkForDraw()
    {
        // Makes call to whatsAtPos for every position on the board
        BoardPosition board;
        for(int i = 0; i < getNumColumns(); i++) {
            for(int j = 0; j < getNumRows(); j++) {
                board = new BoardPosition(j, i);
                if(whatsAtPos(board) == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * </Description: Checks for 5 horizontally consecutive markers in respect to the last marker placed>
     * @param lastPos position of last marker placed
     * @param player 'X' OR 'O' for the player whose turn it was
     * @return true OR false
     * @pre lastPos != NULL
     *      player = 'X' OR
     *      player = 'O'
     * @post [true iff 5 consecutive markers are found in the given row]
     */
    public default boolean checkHorizontalWin(BoardPosition lastPos, char player)
    {
        // Makes calls to isPlayerAtPos() for the whole row
        int connects = 0;
        int row = lastPos.getRow();
        // Check left positions
        for(int i=0;i<getNumColumns();i++) {
            lastPos = new BoardPosition(row, i);
            if(whatsAtPos(lastPos) == player) {
                connects++;
                if (connects >= getNumToWin())
                    return true;
            }
            else {connects = 0;}
        }
        return false;
    }

    /**
     * </Description: Checks for 5 vertically consecutive markers in respect to the last marker placed>
     * @param lastPos position of last marker placed
     * @param player 'X' OR 'O' for the player whose turn it was
     * @return true OR false
     * @pre lastPos != NULL
     *      player = 'X' OR
     *      player = 'O'
     * @post [true iff 5 consecutive markers are found in the given column]
     */
    public default boolean checkVerticalWin(BoardPosition lastPos, char player)
    {
        // Makes calls to isPlayerAtPos() for the while column
        int connects = 0;
        int column = lastPos.getColumn();
        // Check left positions
        for(int i=0;i<getNumRows();i++) {
            lastPos = new BoardPosition(i, column);
            if(whatsAtPos(lastPos) == player) {
                connects++;
                if (connects >= getNumToWin())
                    return true;
            }
            else {connects = 0;}
        }
        return false;
    }

    /**
     * </Description: Checks for 5 diagonally consecutive markers in respect to the last marker placed>
     * @param lastPos position of last marker placed
     * @param player 'X' OR 'O' for the player whose turn it was
     * @return true OR false
     * @pre lastPos != NULL
     *      player = 'X' OR
     *      player = 'O'
     * @post [true iff 5 consecutive markers are found in a diagonal line]
     */
    public default boolean checkDiagonalWin(BoardPosition lastPos, char player)
    {
        BoardPosition temp1 = lastPos;
        BoardPosition temp2 = lastPos;
        int connects = 0;
        int row = lastPos.getRow();
        int column = lastPos.getColumn();

        // Find the position at the top of the downhill diagonal \
        while (row > 0 && column > 0) {
            row--;
            column--;
        }

        // Now check all positions from the upper left to lower right \
        for (int r=row,c=column;r<getNumRows() && c<getNumColumns();r++,c++){
            temp1 = new BoardPosition(r, c);
            if(whatsAtPos(temp1) == player) {
                connects++;
                if (connects >= getNumToWin())
                    return true;
            }
            // If break in connections we reset count
            else {connects = 0;}
        }

        row = lastPos.getRow();
        column = lastPos.getColumn();
        // Find the position at the top of the uphill diagonal /
        while (row > 0 && column < getNumColumns()-1) {
            row--;
            column++;
        }
        // Now check all positions from the upper right to the lower left /
        for (int r=row,c=column;r<getNumRows() && c>=0;r++,c--){
            temp2 = new BoardPosition(r, c);
            if(whatsAtPos(temp2) == player) {
                connects++;
                if (connects >= getNumToWin())
                    return true;
            }
            // If break in connections we reset count
            else {connects = 0;}
        }
        return false;
    }

    /**
     * </Description: Checks what is at the given position on the board>
     * @param pos position to be checked
     * @return character at the given position
     * @pre [pos is a valid position on the game board]
     * @post whatsAtPos() = 'X' OR
     *       whatsAtPos() = 'O' OR
     *       whatsAtPos() = ' '
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * </Description: Places the character in marker on the position specified by
     * marker and should not be called if the space is unavailable>
     * @param marker object soring the markers for the specified player
     * @param player either 'X' or 'O' for either player
     * @pre checkSpace(position) = true
     * @post [player's token is placed in specified position]
     */
    public void placeMarker(BoardPosition marker, char player);

    /**
     * </Description: Returns the number of rows in the GameBoard>
     * @return [number of rows in gameBoard]
     * @post [number of rows is returned]
     */
    public int getNumRows();

    /**
     * </Description: Returns the number of columns in the GameBoard>
     * @return [number of columns in gameBoard]
     * @post [number of columns is returned]
     */
    public int getNumColumns();

    /**
     * </Description: Returns the number of tokens in a row needed to win the game>
     * @return [number of connected tokens on gameBoard to win]
     * @post [number of connected tokens to win is returned]
     */
    public int getNumToWin();
}