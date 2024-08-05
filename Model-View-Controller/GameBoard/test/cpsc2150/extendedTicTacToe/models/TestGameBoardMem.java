package cpsc2150.extendedTicTacToe.models;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.String;

public class TestGameBoardMem
{
    private IGameBoard MakeABoard(int rows, int columns, int wins){
        return new GameBoardMem(rows, columns, wins);
    }

    private String toString(char[][] gb){
        String x = "   "; // 3 spaces
        // Prints top line of gameBoard
        for(int r=0;r<gb.length;r++) {
            if(r < 10)
                x = x.concat(" ");
            x = x.concat(String.valueOf(r));
            x = x.concat("|");
        }
        x = x.concat("\n");
        // Prints the rest of gameBoard
        for(int r=0;r<gb.length;r++) {
            for(int c=0;c<gb[0].length;c++) {
                if (c == 0) {
                    if (r < 10)
                        x = x.concat(" ");
                    x = x.concat(String.valueOf(r));
                }
                x = x.concat("|");
                x = x.concat(String.valueOf(gb[r][c]));
                x = x.concat(" ");
            }
            x = x.concat("|\n");
        }
        return x;
    }

    private char[][] expectedArray (int rows, int columns){
        char[][] gb = new char[rows][columns];
        for (int r=0;r<rows;r++){
            for (int c=0;c<columns;c++){
                gb[r][c] = ' ';
            }
        }
        return gb;
    }

    @Test
    public void testConstructor_size() {
        assertEquals(MakeABoard(2, 2, 2).toString(), toString(expectedArray(2, 2)));
    }

    @Test
    public void testConstructor_row_col() {
        assertTrue(MakeABoard(2, 2, 2).getNumRows() == 2 &&
                MakeABoard(2, 2, 2).getNumColumns() == 2
        );
    }

    @Test
    public void testConstructor_wins() {
        assertEquals(2, MakeABoard(2, 2, 2).getNumToWin());
    }

    @Test
    public void testCheckSpace_X() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 2), 'X');

        assertFalse(gb.checkSpace(new BoardPosition(2, 2)));
    }

    @Test
    public void testCheckSpace_empty() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 2), 'X');

        assertFalse(gb.checkSpace(new BoardPosition(2, 2)));
    }

    @Test
    public void testCheckSpace_outOfBounds() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 2), 'X');

        assertFalse(gb.checkSpace(new BoardPosition(10, 10)));
    }
    @Test
    public void testCheckHorizontalWin_noWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');

        assertFalse(gb.checkHorizontalWin(new BoardPosition(2, 3), 'X'));
    }

    @Test
    public void testCheckHorizontalWin_middleWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 0), 'O');
        gb.placeMarker(new BoardPosition(2, 2), 'X');
        gb.placeMarker(new BoardPosition(1, 3), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(2, 5), 'O');
        gb.placeMarker(new BoardPosition(2, 4), 'X');

        assertTrue(gb.checkHorizontalWin(new BoardPosition(2, 2), 'X'));
    }

    @Test
    public void testCheckHorizontalWin_bottomRightWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(3, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'O');
        gb.placeMarker(new BoardPosition(3, 5), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');
        gb.placeMarker(new BoardPosition(6, 4), 'X');
        gb.placeMarker(new BoardPosition(6, 5), 'X');
        gb.placeMarker(new BoardPosition(6, 6), 'X');

        assertTrue(gb.checkHorizontalWin(new BoardPosition(6, 6), 'X'));
    }

    @Test
    public void testCheckHorizontalWin_topLeftWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(3, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'O');
        gb.placeMarker(new BoardPosition(3, 5), 'O');
        gb.placeMarker(new BoardPosition(3, 1), 'O');
        gb.placeMarker(new BoardPosition(0, 0), 'X');
        gb.placeMarker(new BoardPosition(0, 1), 'X');
        gb.placeMarker(new BoardPosition(0, 2), 'X');
        gb.placeMarker(new BoardPosition(0, 3), 'X');

        assertTrue(gb.checkHorizontalWin(new BoardPosition(0, 3), 'X'));
    }

    @Test
    public void testCheckVerticalWin_middleWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(2, 4), 'O');
        gb.placeMarker(new BoardPosition(1, 3), 'X');
        gb.placeMarker(new BoardPosition(3, 3), 'X');
        gb.placeMarker(new BoardPosition(5, 3), 'O');
        gb.placeMarker(new BoardPosition(5, 4), 'O');
        gb.placeMarker(new BoardPosition(4, 3), 'X');

        assertTrue(gb.checkVerticalWin(new BoardPosition(4, 3), 'X'));
    }

    @Test
    public void testCheckVerticalWin_noWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');

        assertFalse(gb.checkVerticalWin(new BoardPosition(2, 3), 'X'));
    }

    @Test
    public void testCheckVerticalWin_bottomRightWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(3, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'O');
        gb.placeMarker(new BoardPosition(3, 5), 'O');
        gb.placeMarker(new BoardPosition(3, 6), 'X');
        gb.placeMarker(new BoardPosition(4, 3), 'O');
        gb.placeMarker(new BoardPosition(4, 6), 'X');
        gb.placeMarker(new BoardPosition(5, 6), 'X');
        gb.placeMarker(new BoardPosition(6, 6), 'X');

        assertTrue(gb.checkVerticalWin(new BoardPosition(6, 6), 'X'));
    }

    @Test
    public void testCheckVerticalWin_topLeftWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(2, 2), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(3, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'O');
        gb.placeMarker(new BoardPosition(3, 5), 'O');
        gb.placeMarker(new BoardPosition(4, 3), 'O');
        gb.placeMarker(new BoardPosition(0, 0), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'X');
        gb.placeMarker(new BoardPosition(2, 0), 'X');
        gb.placeMarker(new BoardPosition(3, 0), 'X');

        assertTrue(gb.checkVerticalWin(new BoardPosition(3, 0), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_leftDownWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(1, 0), 'X');
        gb.placeMarker(new BoardPosition(4, 0), 'O');
        gb.placeMarker(new BoardPosition(2, 1), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 2), 'X');
        gb.placeMarker(new BoardPosition(2, 3), 'O');
        gb.placeMarker(new BoardPosition(4, 3), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(4, 3), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_leftUpWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(4, 0), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(3, 1), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(2, 2), 'X');
        gb.placeMarker(new BoardPosition(2, 3), 'O');
        gb.placeMarker(new BoardPosition(1, 3), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(1, 3), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_topDownWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(0, 1), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(1, 2), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(4, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(3, 4), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_topUpWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(0, 5), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(1, 4), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(4, 3), 'O');
        gb.placeMarker(new BoardPosition(3, 2), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(3, 2), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_rightUpWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(1, 6), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(2, 5), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(4, 3), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(4, 3), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_rightDownWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(4, 5), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(5, 6), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(5, 6), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_bottomUpWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(3, 5), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(4, 4), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(5, 3), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(6, 2), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_bottomDownWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(3, 1), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(4, 2), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(5, 3), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 4), 'X');

        assertTrue(gb.checkDiagonalWin(new BoardPosition(6, 4), 'X'));
    }

    @Test
    public void testCheckDiagonalWin_noWin() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(2, 3), 'X');
        gb.placeMarker(new BoardPosition(1, 0), 'O');
        gb.placeMarker(new BoardPosition(4, 5), 'X');
        gb.placeMarker(new BoardPosition(2, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 4), 'X');
        gb.placeMarker(new BoardPosition(4, 1), 'O');

        assertFalse(gb.checkDiagonalWin(new BoardPosition(4, 1), 'O'));
    }

    @Test
    public void testCheckForDraw_full() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        for (int r=0;r<gb.getNumRows();r++){
            for (int c=0;c<gb.getNumColumns();c++){
                if (c % 2 == 0)
                    gb.placeMarker(new BoardPosition(r, c), 'X');
                gb.placeMarker(new BoardPosition(r, c), 'O');
            }
        }

        assertTrue(gb.checkForDraw());
    }

    @Test
    public void testCheckForDraw_notFull() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        for (int r=0;r<gb.getNumRows();r++){
            for (int c=0;c<gb.getNumColumns()-1;c++){
                if (c % 2 == 0)
                    gb.placeMarker(new BoardPosition(r, c), 'X');
                gb.placeMarker(new BoardPosition(r, c), 'O');
            }
        }

        assertFalse(gb.checkForDraw());
    }

    @Test
    public void testCheckForDraw_empty() {
        IGameBoard gb = MakeABoard(7, 7, 4);

        assertFalse(gb.checkForDraw());
    }

    @Test
    public void testCheckForDraw_win() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(4, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(3, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');
        gb.placeMarker(new BoardPosition(2, 0), 'O');

        assertFalse(gb.checkForDraw());
    }

    @Test
    public void testWhatsAtPos_inBounds_X() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals("O", String.valueOf(gb.whatsAtPos(new BoardPosition(5, 0))));
    }

    @Test
    public void testWhatsAtPos_inBounds_empty() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(" ", String.valueOf(gb.whatsAtPos(new BoardPosition(2, 0))));
    }

    @Test
    public void testWhatsAtPos_full() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        for (int r=0;r<gb.getNumRows();r++){
            for (int c=0;c<gb.getNumColumns();c++){
                if (c % 2 == 0)
                    gb.placeMarker(new BoardPosition(r, c), 'X');
                gb.placeMarker(new BoardPosition(r, c), 'O');
            }
        }

        assertEquals("O", String.valueOf(gb.whatsAtPos(new BoardPosition(5, 5))));
    }

    @Test
    public void testWhatsAtPos_manyPlayers() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        gb.placeMarker(new BoardPosition(1, 1), 'B');
        gb.placeMarker(new BoardPosition(2, 1), 'B');
        gb.placeMarker(new BoardPosition(4, 1), 'B');

        gb.placeMarker(new BoardPosition(3, 2), 'A');
        gb.placeMarker(new BoardPosition(3, 3), 'A');
        gb.placeMarker(new BoardPosition(2, 3), 'A');

        gb.placeMarker(new BoardPosition(4, 4), 'D');
        gb.placeMarker(new BoardPosition(3, 5), 'D');
        gb.placeMarker(new BoardPosition(2, 5), 'D');

        assertEquals("D", String.valueOf(gb.whatsAtPos(new BoardPosition(4, 4))));
    }

    @Test
    public void testWhatsAtPos_empty() {
        IGameBoard gb = MakeABoard(7, 7, 4);

        assertEquals(" ", String.valueOf(gb.whatsAtPos(new BoardPosition(4, 4))));
    }

    @Test
    public void testIsPlayerAtPos_correct() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertTrue(gb.isPlayerAtPos(new BoardPosition(5, 0), 'O'));
    }

    @Test
    public void testIsPlayerAtPos_incorrect() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertFalse(gb.isPlayerAtPos(new BoardPosition(2, 0), 'O'));
    }

    @Test
    public void testIsPlayerAtPos_full() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        for (int r=0;r<gb.getNumRows();r++){
            for (int c=0;c<gb.getNumColumns();c++){
                if (c % 2 == 0)
                    gb.placeMarker(new BoardPosition(r, c), 'X');
                gb.placeMarker(new BoardPosition(r, c), 'O');
            }
        }

        assertTrue(gb.isPlayerAtPos(new BoardPosition(6, 6), 'O'));
    }

    @Test
    public void testIsPlayerAtPos_manyPlayers() {
        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        gb.placeMarker(new BoardPosition(1, 1), 'B');
        gb.placeMarker(new BoardPosition(2, 1), 'B');
        gb.placeMarker(new BoardPosition(4, 1), 'B');

        gb.placeMarker(new BoardPosition(3, 2), 'A');
        gb.placeMarker(new BoardPosition(3, 3), 'A');
        gb.placeMarker(new BoardPosition(2, 3), 'A');

        gb.placeMarker(new BoardPosition(4, 4), 'D');
        gb.placeMarker(new BoardPosition(3, 5), 'D');
        gb.placeMarker(new BoardPosition(2, 5), 'D');

        assertTrue(gb.isPlayerAtPos(new BoardPosition(3, 2), 'A'));
    }

    @Test
    public void testIsPlayerAtPos_empty() {
        IGameBoard gb = MakeABoard(7, 7, 4);

        assertFalse(gb.isPlayerAtPos(new BoardPosition(4, 4), 'X'));
    }

    @Test
    public void testPlaceMarker_middle() {
        char[][] expected = expectedArray(7, 7);
        expected[6][0] = 'X';
        expected[5][0] = 'O';
        expected[6][1] = 'X';
        expected[5][1] = 'O';
        expected[3][2] = 'X';
        expected[5][2] = 'O';
        expected[6][3] = 'X';

        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 2), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(toString(expected), gb.toString());
    }

    @Test
    public void testPlaceMarker_rightBorder() {
        char[][] expected = expectedArray(7, 7);
        expected[6][0] = 'X';
        expected[5][0] = 'O';
        expected[6][1] = 'X';
        expected[5][1] = 'O';
        expected[3][6] = 'X';
        expected[5][2] = 'O';
        expected[6][3] = 'X';

        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(3, 6), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(toString(expected), gb.toString());
    }

    @Test
    public void testPlaceMarker_topBorder() {
        char[][] expected = expectedArray(7, 7);
        expected[6][0] = 'X';
        expected[5][0] = 'O';
        expected[6][1] = 'X';
        expected[5][1] = 'O';
        expected[0][3] = 'X';
        expected[5][2] = 'O';
        expected[6][3] = 'X';

        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(0, 3), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(toString(expected), gb.toString());
    }

    @Test
    public void testPlaceMarker_leftBorder() {
        char[][] expected = expectedArray(7, 7);
        expected[6][0] = 'X';
        expected[5][0] = 'O';
        expected[6][1] = 'X';
        expected[5][1] = 'O';
        expected[1][0] = 'X';
        expected[5][2] = 'O';
        expected[6][3] = 'X';

        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(1, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(toString(expected), gb.toString());
    }

    @Test
    public void testPlaceMarker_bottomBorder() {
        char[][] expected = expectedArray(7, 7);
        expected[6][0] = 'X';
        expected[5][0] = 'O';
        expected[6][1] = 'X';
        expected[5][1] = 'O';
        expected[6][5] = 'X';
        expected[5][2] = 'O';
        expected[6][3] = 'X';

        IGameBoard gb = MakeABoard(7, 7, 4);
        gb.placeMarker(new BoardPosition(6, 0), 'X');
        gb.placeMarker(new BoardPosition(5, 0), 'O');
        gb.placeMarker(new BoardPosition(6, 1), 'X');
        gb.placeMarker(new BoardPosition(5, 1), 'O');
        gb.placeMarker(new BoardPosition(6, 5), 'X');
        gb.placeMarker(new BoardPosition(5, 2), 'O');
        gb.placeMarker(new BoardPosition(6, 3), 'X');

        assertEquals(toString(expected), gb.toString());
    }
};