package cpsc2150.extendedTicTacToe.models;
import java.lang.*;
/*
 * Name: Michal Elrod
 * Class: 2150
 * Assignment: Project-1: Tic-Tac-Toe Board
 */


/**
 * </Description: This project includes (BoardPosition.java GameScreen.java cpsc2150.GameBoard.GameBoard.java)>
 * </These three files create a 5x8 tic-tac-toe board that two players can use to play>
 * @invariant (0 < column <= MAX_COLUMN)
 *             (0 < row <= MAX_ROW)
 * @correspondence self.row = row AND self.column = column
 */
public class BoardPosition {

    private int row;
    private int column;

    /**
     * </Description: The constructor sets the instance variables to appropriate values>
     * @param row The number of rows on the game board
     * @param column the number of columns on the game board
     * @post (row = #row)
     *       (column = #column)
     */
    public BoardPosition(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    /**
     * </Description: Provides the row number>
     * @return row
     * @post getRow = row AND
     *       row = #row AND
     *       column = #column
     *       [returns value of row]
     */
    public int getRow(){
        return row;
    }

    /**
     * </Description: Provides the column number>
     * @return column
     * @post getColumn = column AND
     *       column = #column AND
     *       row = #row
     *       [returns value of column]
     */
    public int getColumn(){
        return column;
    }
    /**
     * </Description: Compares two objects of BoardPosition and checks if they have the same row and column>
     * @return [returns true or false]
     * @param obj object class for comparison
     * @post [true iff both objects have the same row and column]
     *       one = #one
     *       two = #two
     */
    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != getClass())
            return false;

        BoardPosition x = (BoardPosition)obj;
        return (x.getRow() == getRow() && x.getColumn() == getColumn());
    }
    /**
     * </Description: Overrides the toString() method to format our information for printing>
     * @return newly formatted string for output EX: "3,5"
     * @post [returns a string that formats our data EX: "3,5"]
     */
    @Override
    public String toString()
    {
        return getRow() + "," + getColumn();
    }
}