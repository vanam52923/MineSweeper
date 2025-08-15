package com.game;

import java.util.Scanner;

/**
 * Represents a Minesweeper game. Handles board creation, user input,
 * and game looping logic. Can be run normally with user input or tested
 * with a preset Board.
 */
public class Game {

    /** The game board. This can be injected for testing purposes. */
    private Board board;

    /**
     * Default constructor for normal gameplay.
     * The board will be created based on user input in the play() method.
     */
    public Game() {}

    /**
     * Constructor for testing purposes with a pre-set board.
     *
     * @param board  Board instance to use for the game
     */
    public Game(Board board) {
        this.board = board;
    }

    /**
     * Main method to start the Minesweeper game.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play(new Scanner(System.in));
    }

    /**
     * Starts the game with the scanner for input.
     * If no board was preset, prompts the user for board size and number of mines.
     *
     * @param sc Scanner instance to read user input
     */
    public void play(Scanner sc) {
        if (board == null) {
            int[] size = getBoardSize(sc);
            int numMines = getNumMines(sc, size[0], size[1]);
            board = new Board(size[0], size[1], numMines);
        }

        runGameLoop(sc);
        board.printBoard(true);
        sc.close();
    }

    /**
     * Prompts user for board type and size.
     *
     * @param sc scanner for input
     * @return an array of two integers: {rows, cols}
     */
    private int[] getBoardSize(Scanner sc) {
        while (true) {
            String boardType = askBoardType(sc);
            try {
                if (boardType.equals("S")) return getSquareBoardSize(sc);
                else if (boardType.equals("R")) return getRectBoardSize(sc);
                else System.out.println("Invalid choice. Enter S or R.");
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    /**
     * Asks user whether the board is square or rectangular.
     *
     * @param sc Scanner for input
     * @return "S" for square or "R" for rectangular
     */
    private String askBoardType(Scanner sc) {
        System.out.print("Square or rectangular board? (S/R): ");
        return sc.nextLine().trim().toUpperCase();
    }

    /**
     * Prompts for size of a square board.
     *
     * @param sc Scanner for input
     * @return an array {size, size}
     */
    private int[] getSquareBoardSize(Scanner sc) {
        int size = getPositiveInt(sc, "Enter size for square board: ");
        return new int[]{size, size};
    }

    /**
     * Prompts for size of a rectangular board.
     *
     * @param sc Scanner for input
     * @return an array {rows, cols}
     */
    private int[] getRectBoardSize(Scanner sc) {
        System.out.print("Enter board size (rows,cols): ");
        String[] rc = sc.nextLine().split(",");
        if (rc.length != 2) throw new IllegalArgumentException("Enter two numbers separated by comma.");
        int rows = Integer.parseInt(rc[0].trim());
        int cols = Integer.parseInt(rc[1].trim());
        if (rows <= 0 || cols <= 0) throw new IllegalArgumentException("Rows and columns must be positive.");
        return new int[]{rows, cols};
    }

    /**
     * Prompts for number of mines within allowed limits.
     *
     * @param sc Scanner for input
     * @param rows number of board rows
     * @param cols number of board columns
     * @return number of mines
     */
    private int getNumMines(Scanner sc, int rows, int cols) {
        int maxMines = (int) Math.floor(0.35 * rows * cols);
        while (true) {
            try {
                int numMines = getPositiveInt(sc, "Enter number of mines (max 35% of board size i.e." + maxMines + "): ");
                if (numMines > maxMines) throw new Exception("Mines cannot exceed " + maxMines);
                return numMines;
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }

    /**
     * Reads a positive integer from the user.
     *
     * @param sc Scanner for input
     * @param message prompt message
     * @return positive integer entered by the user
     */
    private int getPositiveInt(Scanner sc, String message) {
        System.out.print(message);
        int value = Integer.parseInt(sc.nextLine().trim());
        if (value <= 0) throw new IllegalArgumentException("Value must be positive.");
        return value;
    }

    /**
     * Runs the main game loop until the game ends.
     *
     * @param sc Scanner for input
     */
    private void runGameLoop(Scanner sc) {
        boolean gameOver = false;
        while (!gameOver) {
            board.printBoard(false);

            if (board.allSafeRevealed()) {
                System.out.println("***Congratulations*** You won! All safe cells revealed!");
                break;
            }

            int[] cell = getCellClick(sc);
            gameOver = board.revealCell(cell[0], cell[1]);
        }
    }

    /**
     * Prompts the user to select a cell to reveal.
     *
     * @param sc Scanner for input
     * @return array {row, col} of selected cell
     */
    private int[] getCellClick(Scanner sc) {
        while (true) {
            System.out.print("Click a cell (e.g., A1): ");
            try {
                String input = sc.nextLine().toUpperCase().trim();
                if (input.length() < 2) throw new Exception("Input too short");
                int row = input.charAt(0) - 'A';
                int col = Integer.parseInt(input.substring(1)) - 1;
                if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols())
                    throw new Exception("Cell out of board range");
                return new int[]{row, col};
            } catch (Exception e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        }
    }
}
