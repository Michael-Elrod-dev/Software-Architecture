/*
 * Name: Michal Elrod
 * Class: 2150
 * Assignment: Project: Tic-Tac-Toe Board
 */
package cpsc2150.extendedTicTacToe.models;

public class GameBoard extends AbsGameBoard implements IGameBoard {
    /**
     * </Description: This project creates a Tic-Tac-Toe board of a specified size and supports>
     * </multiple players>
     * @invariant (IGameBoard.MIN_ROW <= numRow <= IGameBoard.MAX_ROW)
     *             (IGameBoard.MIN_COL <= numColumn <= IGameBoard.MAX_COL)
     *             (IGameBoard.MIN_NUM_TO_WIN <= numToWin <= IGameBoard.MAX_NUM_TO_WIN)
     * @correspondence self = gameBoard[0..MAX_ROW-1][0..MAX_COL-1]
     *                 self.numRow = #row
     *                 self.numColumn = #column
     *                 self.numToWin = #win
     */
    private int numRow;
    private int numColumn;
    private int numToWin;
    private char[][] gameBoard;

    /**
     * </Description: Class constructor assigns variables>
     * @pre MIN_ROW <= row <= MAX_ROW
     *      MIN_COL <= column <= MAX_COL
     *      MIN_NUM_TO_WIN <= wins <= MAX_NUM_TO_WIN
     *      wins <= MIN_ROW
     *      wins <= MIN_COLUMN
     * @post gameboard = #gameBoard[numRow][numColumn]
     *       numRow = #row
     *       numColumn = #column
     *       numToWIn = #wins
     *       gameBoard[0..MAX_ROW-1][0..MAX_COL-1] = ' '
     */
    public GameBoard(int row, int column, int wins){
        numRow = row;
        numColumn = column;
        numToWin = wins;
        gameBoard = new char [numRow][numColumn];

        // Creat board with empty spaces
        for (int i=0;i<numColumn;i++)
            for (int k=0;k<numRow;k++)
                gameBoard[k][i] = ' ';
    }

    /**
     * </Description: Places the character in marker on the position specified by
     * marker and should not be called if the space is unavailable>
     * @param pos position on the game board
     * @param player token for the current player
     * @pre checkSpace(position) = true
     * @post [player's token is placed in specified position]
     */
    public void placeMarker(BoardPosition pos, char player)
    {
        gameBoard[pos.getRow()][pos.getColumn()] = player;
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
        return (gameBoard[pos.getRow()][pos.getColumn()]);
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