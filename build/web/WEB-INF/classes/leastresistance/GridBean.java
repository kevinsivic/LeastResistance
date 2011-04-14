/*
 * This file contains a grid container for the LeastResistance problem.
 *
 * @author Kevin Sivic
 */

package leastresistance;

import javax.ejb.Singleton;

/**
 * This class holds a representation of a Grid for the LeastResistance project.
 */
@Singleton
public class GridBean {

    private Integer columnCount;
    protected Integer[][] grid = new Integer[10][100];
    private int rowPointer = -1;

    /**
     * Get the value of grid
     */
    public Integer[][] getGrid() {
        return this.grid;
    }

    /**
     * Get the number of rows in this grid.
     */
    public Integer getRowCount() {
        return this.rowPointer;
    }

    /**
     * Get the number of columns in this grid.
     */
    public Integer getColumnCount() {
        return this.columnCount;
    }

    /**
     * This function accepts a string formatted as a space separated list of
     * grid items with each grid row separated by a newline.  It validates that
     * input and stores it in a two-dimensional array.
     */
    public void addRows(String stringRows)
    {
        // Split stringRows into array of string grid.
        String[] arrayRows = stringRows.split("\n");

        // If there are more than 10 grid, error.
        if (arrayRows.length > 10) {
            throw new IllegalArgumentException("Invalid input detected.");
        }

        // Add each of the grid to the 2-d array.
        for (String row : arrayRows) {
            // Split stringRow into individual items.
            String[] arrayOfRowItems = row.split(" ");

            // If columnCount has not yet been set (meaning this is the first
            // row that has been processed) then verify that it's between 5 and
            // 100 columns and store the count.  If it is not valid, throw an
            // exception.
            if (this.columnCount == null) {
                if (arrayOfRowItems.length >= 5 && arrayOfRowItems.length < 100) {
                    this.columnCount = arrayOfRowItems.length;
                } else {
                    throw new IllegalArgumentException("Invalid input detected.");
                }
            }

            // If this items length is different then the previous items length
            // then the input is invalid and an exception is thrown.
            if (arrayOfRowItems.length != this.columnCount.intValue()) {
                throw new IllegalArgumentException("Invalid input detected.");
            }

            // This is valid, convert each item to an Integer and store in an array.
            Integer[] intRow = new Integer[this.columnCount];
            for (int i = 0; i < this.columnCount; i++) {
                intRow[i] = Integer.decode(arrayOfRowItems[i]);
            }

            // Store this row in the 2-d array.
            this.grid[++this.rowPointer] = intRow;
        }
    }
}
