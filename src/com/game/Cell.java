package com.game;

/**
 * Represents a single cell on the Minesweeper board.
 * A cell may contain a mine, track if it has been revealed,
 * and store the count of adjacent mines.
 */
public class Cell {

    /** True if this cell contains a mine. */
    private boolean mine = false;

    /** True if this cell has been revealed to the player. */
    private boolean revealed = false;

    /** Number of mines in adjacent cells. */
    private int adjacentMines = 0;

    /**
     * Checks if this cell contains a mine.
     *
     * @return true if this cell has a mine, false otherwise
     */
    public boolean isMine() { return mine; }

    /**
     * Sets whether this cell contains a mine.
     *
     * @param mine true to place a mine, false otherwise
     */
    public void setMine(boolean mine) { this.mine = mine; }

    /**
     * Checks if this cell has been revealed.
     *
     * @return true if the cell is revealed, false otherwise
     */
    public boolean isRevealed() { return revealed; }

    /**
     * Sets the revealed status of this cell.
     *
     * @param revealed true to reveal the cell, false to hide it
     */
    public void setRevealed(boolean revealed) { this.revealed = revealed; }

    /**
     * Gets the number of adjacent mines.
     *
     * @return number of mines in neighboring cells
     */
    public int getAdjacentMines() { return adjacentMines; }

    /**
     * Sets the number of adjacent mines for this cell.
     *
     * @param count number of adjacent mines
     */
    public void setAdjacentMines(int count) { this.adjacentMines = count; }

    /**
     * Returns a string representation of the cell for printing.
     * "_" for unrevealed, "*" for mine, or number of adjacent mines.
     *
     * @return string representing the cell
     */
    @Override
    public String toString() {
        if (!revealed) return "_";
        if (mine) return "*";
        return String.valueOf(adjacentMines);
    }
}
