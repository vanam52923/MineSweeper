package test;

import com.game.Board;
import com.game.Game;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private String runGameWithInput(Game game, String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));
        game.play(new Scanner(in));
        System.setOut(originalOut);

        return outContent.toString();
    }

    @Test
    void testSquareBoardWinScenario() {
        List<int[]> mines = Arrays.asList(new int[]{1, 1});
        Board board = new Board(2, 2, mines);
        Game game = new Game(board);

        String input = "A1\nA2\nB1\n";
        String output = runGameWithInput(game, input);

        assertTrue(output.contains("Congratulations"),
                "Expected game to be won after revealing all safe cells.");
    }

    @Test
    void testRectangularBoardWinScenario() {

        List<int[]> mines = Arrays.asList(new int[]{1, 2});
        Board board = new Board(2, 3, mines);
        Game game = new Game(board);

        String input = "A1\nA2\nA3\nB1\nB2\n";
        String output = runGameWithInput(game, input);

        assertTrue(output.contains("Congratulations"),
                "Expected rectangular board win after revealing all safe cells.");
    }

    @Test
    void testGameOverOnMineClick() {

        List<int[]> mines = Arrays.asList(new int[]{0, 0});
        Board board = new Board(2, 2, mines);
        Game game = new Game(board);
        String input = "A1\n";
        String output = runGameWithInput(game, input);

        assertTrue(output.contains("Game over"),
                "Expected game over when mine is clicked.");
    }

    @Test
    void testInvalidCellInput() {
        Board board = new Board(2, 2, 0);
        Game game = new Game(board);
        String input = "Z9\nA0\nA1\n";
        String output = runGameWithInput(game, input);

        assertTrue(output.contains("Invalid input: Cell out of board range") ||
                        output.contains("Invalid input: Input too short"),
                "Expected validation message for invalid cell input.");
    }

    @Test
    void testRevealAllCellsWithMineAtCorner() {
        List<int[]> mines = Arrays.asList(new int[]{2, 2});
        Board board = new Board(3, 3, mines);
        Game game = new Game(board);

        String input = "A1\nA2\nA3\nB1\nB2\nB3\nC1\nC2\n";
        String output = runGameWithInput(game, input);

        assertTrue(output.contains("Congratulations"),
                "Expected game to be won after revealing all safe cells except the mine.");
    }
}
