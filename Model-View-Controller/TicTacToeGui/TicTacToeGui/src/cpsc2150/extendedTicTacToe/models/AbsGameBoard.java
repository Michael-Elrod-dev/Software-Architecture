package cpsc2150.extendedTicTacToe.models;

public abstract class AbsGameBoard implements IGameBoard {
    /**
     * </Description: Overrides the toString() method to format our information for printing>
     * @return myString
     * @post [returns a string that formats our data as a game board]
     */
    @Override
    public String toString()
    {
        String x = "   "; // 3 spaces
        // Prints top line of gameBoard
        for(int i=0;i<getNumColumns();i++) {
            if(i < 10)
                x = x.concat(" ");

            x = x.concat(String.valueOf(i));
            x = x.concat("|");
        }
        x = x.concat("\n");
        // Prints the rest of gameBoard
        for(int i = 0; i < getNumRows(); i++) {
            for(int k = 0; k < getNumColumns(); k++) {
                BoardPosition pos = new BoardPosition(i, k);
                if (k == 0) {
                    if (i < 10)
                        x = x.concat(" ");
                    x = x.concat(String.valueOf(i));
                }
                x = x.concat("|");
                x = x.concat(String.valueOf(whatsAtPos(pos)));
                x = x.concat(" ");
            }
            x = x.concat("|\n");
        }
        return x;
    }
}
