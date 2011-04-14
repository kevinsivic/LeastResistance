/*
 * Unit tests for the Grid container for the Least Resistance Problem.
 *
 * @author Kevin Sivic
 */

package leastresistance;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridBeanTest {
    /**
     * Test of getGrid method, of class GridBean
     */
    @Test
    public void testGetGrid() {
        GridBean instance = new GridBean();
        Integer[][] defaultRows = new Integer[10][100];
        Integer[][] result = instance.getGrid();
        assertEquals(defaultRows, result);
    }

    /**
     * Test of addRows method with empty string.
     * @throws Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddRowsEmptyString() {
        String row = "";
        GridBean instance = new GridBean();
        instance.addRows(row);
    }

    /**
     * Test of addRows method with non-integer input.
     */
    @Test(expected = NumberFormatException.class)
    public void testAddRowInvalidInput() {
        String row = "1 3 a b d";
        GridBean instance = new GridBean();
        instance.addRows(row);
    }

    /**
     * Test of addRows method with valid input.
     */
    @Test
    public void testAddRowValidInput() {
        Integer[][] expectedRows = new Integer[10][100];
        Integer[] row = {1,3,2,4,6};
        expectedRows[0] = row;

        GridBean instance = new GridBean();
        instance.addRows("1 3 2 4 6");
        assertEquals(expectedRows, instance.getGrid());
    }
    
    /**
     * Test addRows with multiple rows.
     */
    @Test
    public void testAddMultipleRows() {
        Integer[][] expectedRows = new Integer[10][100];
        Integer[] row = {1,3,2,4,5};
        expectedRows[0] = row;
        Integer[] row2 = {1,4,7,9,10};
        expectedRows[1] =  row2;

        GridBean instance = new GridBean();
        instance.addRows("1 3 2 4 5\n1 4 7 9 10");
        assertEquals(expectedRows, instance.getGrid());
    }
}