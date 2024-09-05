package defaultPackage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testMakeMove() {
        Board board = new Board(3);
        boolean moveMade = board.makeMove(0, 0, 'S');
        assertTrue(moveMade, "The move should be made successfully.");
        assertEquals('S', board.getCell(0, 0), "The cell should contain 'S'.");
    }

    @Test
    void testCheckSOS() {
        Board board = new Board(3);
        board.makeMove(0, 0, 'S');
        board.makeMove(0, 1, 'O');
        boolean sosFormed = board.makeMove(0, 2, 'S');
        assertTrue(sosFormed, "SOS should be formed horizontally.");
        assertEquals(1, board.getBlueScore(), "Blue player should have 1 point.");
    }
}
