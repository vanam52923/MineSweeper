package test;

import com.game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

class BoardTest {

    @Test
    void testBoardCreationNoMines() {
        Board board = new Board(3, 3, Collections.emptyList());
        Assertions.assertEquals(3, board.getRows());
        Assertions.assertEquals(3, board.getCols());
        Assertions.assertFalse(board.allSafeRevealed()); // not revealed yet
    }

    @Test
    void testBoardWithMinesAdjacencyCalculation() {
        Board board = new Board(3, 3, Collections.singletonList(new int[]{0, 0}));
        board.revealCell(0, 1);
        Assertions.assertFalse(board.allSafeRevealed());
    }

    @Test
    void testRevealMineReturnsTrue() {
        Board board = new Board(2, 2, Collections.singletonList(new int[]{0, 0}));
        Assertions.assertTrue(board.revealCell(0, 0), "Clicking on a mine should return true (game over)");
    }

    @Test
    void testRevealNumberedCell() {
        Board board = new Board(2, 2, Collections.singletonList(new int[]{0, 0}));
        Assertions.assertFalse(board.revealCell(0, 1));
        Assertions.assertFalse(board.allSafeRevealed());
    }

    @Test
    void testRevealEmptyCellFloodFill() {
        Board board = new Board(3, 3, Collections.singletonList(new int[]{0, 0}));
        Assertions.assertFalse(board.revealCell(2, 2)); // Far from mine
    }

    @Test
    void testAllSafeRevealed() {
        Board board = new Board(3, 3, Collections.singletonList(new int[]{0, 0}));
        Assertions.assertFalse(board.allSafeRevealed());
    }

    @Test
    void testAllSafeCellsRevealedTrue() {
        Board board = new Board(2, 2, Collections.singletonList(new int[]{0, 0}));
        Assertions.assertFalse(board.revealCell(0, 1));
        Assertions.assertFalse(board.revealCell(1, 0));
        Assertions.assertFalse(board.revealCell(1, 1));
        Assertions.assertTrue(board.allSafeRevealed());
    }

    @Test
    void testPrintBoardWithoutReveal() {
        Board board = new Board(2, 2, Collections.singletonList(new int[]{0, 0}));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        board.printBoard(false);
        String result = output.toString();
        Assertions.assertTrue(result.contains("A"), "Should print row labels");
        Assertions.assertTrue(result.contains("1"), "Should print column labels");
    }

    @Test
    void testPrintBoardWithRevealAll() {
        Board board = new Board(2, 2, Collections.singletonList(new int[]{0, 0}));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        board.printBoard(true);
        String result = output.toString();
        Assertions.assertTrue(result.contains("*"), "Should reveal mines when revealAll is true");
    }

    @Test
    void testZeroMinesBoard() {
        Board board = new Board(2, 2, Collections.emptyList());
        Assertions.assertFalse(board.revealCell(0, 0));
        Assertions.assertFalse(board.revealCell(0, 1));
        Assertions.assertFalse(board.revealCell(1, 0));
        Assertions.assertFalse(board.revealCell(1, 1));
        Assertions.assertTrue(board.allSafeRevealed(), "Board with zero mines should be winnable immediately after all cells are clicked");
    }

    @Test
    void testAlmostAllMines() {
        Board board = new Board(2, 2, Arrays.asList(
                new int[]{0, 0}, new int[]{0, 1}, new int[]{1, 0}
        ));
        Assertions.assertFalse(board.revealCell(1, 1));
        Assertions.assertTrue(board.allSafeRevealed(), "Only one safe cell, revealing it should win the game");
    }
}
