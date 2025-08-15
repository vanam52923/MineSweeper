package test;

import com.game.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void testMineGetterSetter() {
        Cell cell = new Cell();
        assertFalse(cell.isMine(), "Default mine should be false");
        cell.setMine(true);
        assertTrue(cell.isMine(), "Mine should be set to true");
    }

    @Test
    void testRevealedGetterSetter() {
        Cell cell = new Cell();
        assertFalse(cell.isRevealed(), "Default revealed should be false");
        cell.setRevealed(true);
        assertTrue(cell.isRevealed(), "Revealed should be set to true");
    }

    @Test
    void testAdjacentMinesGetterSetter() {
        Cell cell = new Cell();
        assertEquals(0, cell.getAdjacentMines(), "Default adjacent mines should be 0");
        cell.setAdjacentMines(3);
        assertEquals(3, cell.getAdjacentMines(), "Adjacent mines should be 3");
    }

    @Test
    void testToStringHiddenCell() {
        Cell cell = new Cell();
        assertEquals("_", cell.toString(), "Hidden cell should display _");
    }

    @Test
    void testToStringMineCell() {
        Cell cell = new Cell();
        cell.setMine(true);
        cell.setRevealed(true);
        assertEquals("*", cell.toString(), "Revealed mine cell should display *");
    }

    @Test
    void testToStringSafeCell() {
        Cell cell = new Cell();
        cell.setAdjacentMines(2);
        cell.setRevealed(true);
        assertEquals("2", cell.toString(), "Revealed safe cell should display adjacent mines count");
    }
}
