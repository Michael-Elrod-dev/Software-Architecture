/*
Name: Michael Elrod
Assignment: Project-2 (TicTacToe)
Class: CPSC 2150
Date: 10/7/2022

This program is designed to create a game board of any specified size that can
be used by two players to play a game of Tic-Tac-Toe
 */
package cpsc2150.extendedTicTacToe;
import cpsc2150.extendedTicTacToe.models.GameBoard;
import cpsc2150.extendedTicTacToe.models.BoardPosition;
import cpsc2150.extendedTicTacToe.models.GameBoardMem;
import cpsc2150.extendedTicTacToe.models.IGameBoard;

import java.util.*;

public class GameScreen {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        IGameBoard game = null;
        BoardPosition board = null;

        int row = 0;
        int column = 0;
        int numRows = 0;
        int numCols = 0;
        int numToWin = 0;
        int takeTurns = 0;
        int numPlayers = 0;
        char player = ' ';
        char gameType = ' ';
        boolean win = false;
        boolean playing = false;
        boolean startNewGame = false;
        String uInput = "";


        do {
            //Reset game attributes to play again
            takeTurns = 0;
            playing = true;
            startNewGame = false;

            // Get number of players
            while (true)
            {
                System.out.println("How many players?");
                numPlayers = Integer.parseInt(scnr.next());
                if (numPlayers > IGameBoard.MAX_PLAYERS){
                    System.out.println("Must be " + IGameBoard.MAX_PLAYERS + " players or fewer");
                    continue;
                }
                if (numPlayers < IGameBoard.MIN_PLAYERS){
                    System.out.println("Must be at least " + IGameBoard.MIN_PLAYERS + " players");
                    continue;
                }
                break;
            }

            // Get player characters
            char[] players = new char[numPlayers];
            for (int i=0;i<numPlayers;i++)
            {
                System.out.println("Enter the character to represent player " + (i+1));
                players[i] = Character.toUpperCase(scnr.next().charAt(0));
                for (int k=0;k<i;k++){
                    if (players[k] == players[i]) {
                        System.out.println(players[i] + " is already taken as a player token!");
                            i--;
                            break;
                    }
                }
            }

            // Get rows and columns of gameBoard
            while (true)
            {
                System.out.println("How many rows?");
                numRows = Integer.parseInt(scnr.next());
                if (numRows < IGameBoard.MIN_ROW || numRows > IGameBoard.MAX_ROW){
                    System.out.println("Rows must be between 3 and 100");
                    continue;
                }
                while (true)
                {
                    System.out.println("How many columns?");
                    numCols = Integer.parseInt(scnr.next());
                    if (numCols < IGameBoard.MIN_COL || numCols > IGameBoard.MAX_COL){
                        System.out.println("Columns must be between " + IGameBoard.MIN_COL + " and " + IGameBoard.MIN_COL);
                        continue;
                    }
                    break;
                }
                break;
            }

            // Get number in a row to win the game
            while (true)
            {
                System.out.println("How many in a row to win?");
                numToWin = Integer.parseInt(scnr.next());
                if(numToWin > numRows || numToWin > numCols){
                    System.out.println("Number in a row must be between " + IGameBoard.MIN_NUM_TO_WIN + " and " + IGameBoard.MAX_NUM_TO_WIN);
                    continue;
                }
                break;
            }

            // Get game type, fast or memory efficient
            while (true)
            {
                System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                gameType = Character.toUpperCase(scnr.next().charAt(0));
                if (gameType != 'F' && gameType != 'M'){
                    System.out.println("Please enter F or M");
                    continue;
                }
                break;
            }

            if (gameType == 'F')
                game = new GameBoard(numRows, numCols, numToWin);
            else
                game = new GameBoardMem(numRows, numCols, numToWin);


            // Game Loop!
            while (playing)
            {
                // Change turns every round
                if (takeTurns >= numPlayers)
                    takeTurns = 0;
                player = players[takeTurns];

                // Get row and column for player markers
                System.out.print(game);
                System.out.println("\nPlayer " + player + " Please enter your ROW");
                row = Integer.parseInt(scnr.next());
                System.out.println("Player " + player + " Please enter your COLUMN");
                column = Integer.parseInt(scnr.next());

                board = new BoardPosition(row, column);

                // If position is valid and available, place marker
                if (game.checkSpace(board))
                    game.placeMarker(board, player);
                else {
                    System.out.println("That space is unavailable, please pick again");
                    continue;
                }

                // Check for a winner
                if (game.checkForWinner(board)) {
                    playing = false;
                    win = true;
                    System.out.println("Player " + player + " wins!");
                    System.out.print(game);
                }

                // Check for a draw
                if (game.checkForDraw()) {
                    playing = false;
                    win = true;
                    System.out.println("It's a draw!");
                    System.out.print(game);
                }

                // Ask to play another game
                while (win)
                {
                    System.out.println("Would you like to play again? Y/N");
                    uInput = String.valueOf(scnr.next());

                    if (Objects.equals(uInput, "Y") || Objects.equals(uInput, "y"))
                    {
                        startNewGame = true;
                        win = false;
                        break;
                    }else if (Objects.equals(uInput, "N") || Objects.equals(uInput, "n"))
                    {
                        startNewGame = false;
                        win = false;
                        break;
                    }else
                    {
                        System.out.println("Please enter Y or N");
                    }
                }
            takeTurns++;
            }
        }while (startNewGame);
    }
}