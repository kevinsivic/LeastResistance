/*
 * This file contains the solution to the Least Resistance problem.
 *
 * @author Kevin Sivic
 */

package leastresistance;

import javax.ejb.Stateless;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * This class solves the least resistance problem as defined in the LeanDog
 * programming challenges document.
 *
 * While the algorithm will solve for any size grid with at least 1 row and
 * 1 column the input has been limited per the spec.
 */
@Stateless
@Path("/LeastResistance")
public class LeastResistanceResource {
    
    private Integer currentOptimalResistance = 51;
    private String currentOptimalPath = "";
    private Boolean completedPath = Boolean.FALSE;

    /**
     * POST method for processing an input grid and returning the least resistance
     * result.
     *
     * Error handling is somewhat lacking here given that this is not production code.
     * 
     * @return a plain text representation of the path of least resistance.
     */
    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String postText(String content) {
        try {
            // Create GridBean based on input, this does most of the input validation.
            GridBean grid = new GridBean();
            grid.addRows(content);

            // Begin recursion to find path of least resistance.
            this.traverseGrid(grid, -1, -1, 0, "");

            // These member variables are set by traverseGrid and contain the
            // relevant information regarding the path of least resistance.
            return (this.completedPath ? "Yes" : "No") + "\n" +
                    String.valueOf(this.currentOptimalResistance) + "\n" +
                    this.currentOptimalPath;
        } catch (IllegalArgumentException e) {
            return "An error occurred while processing.  Please check your input and try again.";
        }
    }

    /**
     * This is a recursive function which traverses the provided grid and stores
     * the optimal solution in member variables of this class.
     *
     * If row and col are -1 it will be assumed that this is the first iteration.
     */
    private void traverseGrid(GridBean grid, Integer row, Integer col, Integer currentResistance, String currentPath) {
        // Retrieve the grid as an array to allow easy access to grid.  This is
        // probably not very efficient.
        Integer[][] gridArray = grid.getGrid();

        // Initialize variable which will hold the next options.
        Integer[][] nextOptions = null;

        // If this is the first time around we want to set each of the items
        // in the first column as the next options.  Otherwise we want to set
        // the three adjacent items.
        if (row == -1 && col == -1) {
            nextOptions = new Integer[grid.getRowCount()+1][2];
            for (int i = 0; i <= grid.getRowCount(); i++) {
                nextOptions[i] = new Integer[] {i, 0};
            }
        } else {
            nextOptions = new Integer[3][2];
            nextOptions[0] = new Integer[] {row-1, col+1};
            nextOptions[1] = new Integer[] {row, col+1};
            nextOptions[2] = new Integer[] {row+1, col+1};
        }

        for (Integer[] nextOption : nextOptions) {
            // If the nextOption is either before the top row or after the bottom
            // row then wrap.
            if (nextOption[0] == -1) {
                if (grid.getRowCount() == 0) { continue; } // One row grid.
                nextOption[0] = grid.getRowCount();
            } else if (nextOption[0] > grid.getRowCount() ) {
                if (grid.getRowCount() == 0) { continue; } // One row grid.
                nextOption[0] = 0;
            }

            // If the next option is passed the last column then we're finished!
            // Store the details (if relevant) and return.
            if (nextOption[1] >= grid.getColumnCount()) {
                if (this.currentOptimalResistance > currentResistance) {
                    this.completedPath = Boolean.TRUE;
                    this.currentOptimalResistance = currentResistance;
                    this.currentOptimalPath = currentPath;
                }
                return;
            }

            // If the new resistance is greater than 50 then it's too expensive.
            // Store the results (if relevant) and return.
            int newResistance = currentResistance + gridArray[nextOption[0]][nextOption[1]];
            if (newResistance > 50) {
                if (this.currentOptimalPath.isEmpty()) {
                    this.currentOptimalResistance = currentResistance;
                    this.currentOptimalPath = currentPath;
                }
                return;
            }

            // If we got here, time to go another round.
            this.traverseGrid(grid, nextOption[0], nextOption[1], newResistance, currentPath + String.valueOf(nextOption[0] + 1) + " ");
        }
    }
}