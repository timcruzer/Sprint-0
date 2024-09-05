package defaultPackage;

import java.util.ArrayList;
import java.util.List;

// board class
public class Board {
    private char[][] grid;
    private int size;
    private String currentPlayer;
    private int blueScore;
    private int redScore;
    
    // includes player, board size, and scores

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        this.currentPlayer = "Blue";
        this.blueScore = 0;
        this.redScore = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    // see which player needs to make their move and make the letter their color
    public boolean makeMove(int row, int col, char letter) {
        if (grid[row][col] == ' ') {
            grid[row][col] = letter;
            if (checkSOS(row, col, letter)) {
                if (currentPlayer.equals("Blue")) {
                    blueScore++;
                } else {
                    redScore++;
                }
            }
            switchPlayer();
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("Blue") ? "Red" : "Blue";
    }

    public String getCurrentPlayer() {
        return currentPlayer; // switch back to other player after move
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public List<int[]> getSOSSequence(int row, int col, char letter) {
        List<int[]> sosSequence = new ArrayList<>();
        // implement logic to find SOS sequences

        // horizontal Check
        if (col > 1 && grid[row][col - 2] == 'S' && grid[row][col - 1] == 'O' && letter == 'S') {
            sosSequence.add(new int[]{row, col - 2});
            sosSequence.add(new int[]{row, col});
        }
        // vertical Check
        if (row > 1 && grid[row - 2][col] == 'S' && grid[row - 1][col] == 'O' && letter == 'S') {
            sosSequence.add(new int[]{row - 2, col});
            sosSequence.add(new int[]{row, col});
        }
        // diagonal Check (top-left to bottom-right)
        if (row > 1 && col > 1 && grid[row - 2][col - 2] == 'S' && grid[row - 1][col - 1] == 'O' && letter == 'S') {
            sosSequence.add(new int[]{row - 2, col - 2});
            sosSequence.add(new int[]{row, col});
        }
        // diagonal Check (top-right to bottom-left)
        if (row > 1 && col < size - 2 && grid[row - 2][col + 2] == 'S' && grid[row - 1][col + 1] == 'O' && letter == 'S') {
            sosSequence.add(new int[]{row - 2, col + 2});
            sosSequence.add(new int[]{row, col});
        }

        return sosSequence;
    }

    private boolean checkSOS(int row, int col, char letter) {
        List<int[]> sosSequence = getSOSSequence(row, col, letter);
        return !sosSequence.isEmpty();
    }

    public boolean isFull() { // continue game as long as board isn't full (general game)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
