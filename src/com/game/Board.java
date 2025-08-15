package com.game;

import java.util.*;

/**
 * Represents the Minesweeper game board.
 * Stores a 2D array of cells, places mines, calculates adjacent mine counts,
 * and handles revealing cells.
 */
public class Board {

    /** 2D array representing the board cells */
    private final Cell[][] board;

    /** Directions for all 8 neighbors including diagonals */
    private static final int[][] possibleDirections = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    /**
     * Constructor for normal gameplay with random mines.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param numMines number of mines to place randomly
     */
    public Board(int rows, int cols, int numMines) {
        board = new Cell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                board[r][c] = new Cell();

        placeMines(numMines);
        calculateAdjacents();
    }

    /**
     * Constructor for testing with predefined mine positions.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param minePositions list of mine positions as int[]{row, col}
     */
    public Board(int rows, int cols, List<int[]> minePositions) {
        board = new Cell[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                board[r][c] = new Cell();

        for (int[] pos : minePositions) {
            int r = pos[0], c = pos[1];
            board[r][c].setMine(true);
        }

        calculateAdjacents();
    }

    /**
     * Places the given number of mines randomly on the board.
     *
     * @param numMines number of mines to place
     */
    private void placeMines(int numMines) {
        Random rand = new Random();
        Set<String> used = new HashSet<>();
        int rows = getRows();

        while (used.size() < numMines) {
            int r = rand.nextInt(rows);
            while (board[r].length == 0) r = rand.nextInt(rows);
            int c = rand.nextInt(board[r].length);
            String key = r + "," + c;
            if (used.add(key)) {
                board[r][c].setMine(true);
            }
        }
    }

    /**
     * Calculates and sets the adjacent mine counts for all cells.
     */
    private void calculateAdjacents() {
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (!board[r][c].isMine()) continue;
                for (int[] dir : possibleDirections) {
                    int nr = r + dir[0], nc = c + dir[1];
                    if (nr >= 0 && nr < getRows() && nc >= 0 && nc < board[nr].length) {
                        if (!board[nr][nc].isMine()) {
                            board[nr][nc].setAdjacentMines(board[nr][nc].getAdjacentMines() + 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reveals a cell at the given position.
     * If it is a mine, returns true (game over).
     * Otherwise, reveals the cell and neighbors if zero adjacent.
     *
     * @param row row index
     * @param col column index
     * @return true if mine was revealed (game over), false otherwise
     */
    public boolean revealCell(int row, int col) {
        Cell cell = board[row][col];
        if (cell.isMine()) {
            System.out.println("Game over, stepped on mine");
            return true;
        }
        if (cell.getAdjacentMines() > 0) cell.setRevealed(true);
        else revealNeighbours(row, col);
        return false;
    }

    /**
     * Reveals a cell and its neighbors for zero-adjacent cells.
     *
     * @param startR row index
     * @param startC column index
     */
    private void revealNeighbours(int startR, int startC) {
        Queue<int[]> queue = new LinkedList<>();
        revealCellAndAddToQueue(startR, startC, queue);
        processQueue(queue);
    }

    /**
     * Reveals a cell and adds it to the queue.
     *
     * @param r row index
     * @param c column index
     * @param queue queue
     */
    private void revealCellAndAddToQueue(int r, int c, Queue<int[]> queue) {
        board[r][c].setRevealed(true);
        queue.add(new int[]{r, c});
    }

    /**
     * Processes the queue to reveal all zero adjacent neighbor cells.
     *
     * @param queue queue
     */
    private void processQueue(Queue<int[]> queue) {
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0], c = pos[1];
            Cell cell = board[r][c];

            if (cell.getAdjacentMines() == 0) {
                for (int[] dir : possibleDirections) {
                    int nr = r + dir[0], nc = c + dir[1];
                    if (nr >= 0 && nr < getRows() && nc >= 0 && nc < board[nr].length) {
                        Cell neighbor = board[nr][nc];
                        if (!neighbor.isRevealed() && !neighbor.isMine()) {
                            revealCellAndAddToQueue(nr, nc, queue);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if all safe (non-mine) cells have been revealed.
     *
     * @return true if all safe cells revealed, false otherwise
     */
    public boolean allSafeRevealed() {
        for (Cell[] row : board)
            for (Cell cell : row)
                if (!cell.isMine() && !cell.isRevealed()) return false;
        return true;
    }

    /**
     * Prints the board to the console.
     * Optionally reveals all cells.
     *
     * @param revealAll true to reveal all cells, false to show hidden cells
     */
    public void printBoard(boolean revealAll) {
        int rows = getRows();
        int cols = getCols();

        System.out.print("   ");
        for (int c = 1; c <= cols; c++) System.out.print(c + " ");
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.print((char)('A' + r) + "  ");
            for (int c = 0; c < cols; c++) {
                Cell cell = board[r][c];
                if (revealAll) System.out.print(cell.isMine() ? "* " : cell.getAdjacentMines() + " ");
                else System.out.print(cell.toString() + " ");
            }
            System.out.println();
        }
    }

    /** @return number of rows */
    public int getRows() { return board.length; }

    /** @return number of columns  */
    public int getCols() {
        for (Cell[] row : board) {
            if (row != null && row.length > 0) return row.length;
        }
        return 0;
    }
}
