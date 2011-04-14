/*
 * Unit tests for the Least Resistance problem.
 *
 * @author Kevin Sivic
 */

package leastresistance;

import org.junit.Test;
import static org.junit.Assert.*;

public class LeastResistanceResourceTest {

    /**
     * Test of postText method, of class LeastResistanceResource.
     */
    @Test
    public void testPostTextAcceptsValidInput() {
        String content = "1 3 5 4\n1 2 4 5";
        LeastResistanceResource instance =  new LeastResistanceResource();
        instance.postText(content);
    }

    /**
     * Test of postText method passing invalid input.
     */
    @Test
    public void testPostTextFailsOnInvalidInput() {
        String content = "";
        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains error message: " + response,
                response.contains("An error occurred while processing.  Please check your input and try again."));
    }

    /**
     * Test postText returns correct response with no wrap.
     */
    @Test
    public void testPostTextHandlesValidDataWithNoWrap() {
        String content = "3 4 1 2 8 6\n" +
                         "6 1 8 2 7 4\n" +
                         "5 9 3 9 9 5\n" +
                         "8 4 1 3 2 6\n" +
                         "3 7 2 8 6 4";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("16"));
        assertTrue("Response contains correct path: " + response, response.contains("1 2 3 4 4 5"));
    }

    /**
     * Test postText returns correct response with wrap.
     */
    @Test
    public void testPostTextHandlesValidDataWithWrap() {
        String content = "3 4 1 2 8 6\n" +
                         "6 1 8 2 7 4\n" +
                         "5 9 3 9 9 5\n" +
                         "8 4 1 3 2 6\n" +
                         "3 7 2 1 2 3";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("11"));
        assertTrue("Response contains correct path: " + response, response.contains("1 2 1 5 4 5"));
    }

    /**
     * Test postText returns correct response with too much resistance.
     */
    @Test
    public void testPostTextHandlesValidDataWithTooMuchResistance() {
        String content = "19 10 19 10 19\n" +
                         "21 23 20 19 12\n" +
                         "20 12 20 11 10";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains no: " + response, response.contains("No"));
    }

    /**
     * Test postText returns correct response with negative resistances.
     */
    @Test
    public void testPostTextHandlesValidDataWithNegativeResistances() {
        String content = "19 -10 19 10 19\n" +
                         "21 23 -20 -19 -12\n" +
                         "20 -12 20 11 -10";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("-44"));
        assertTrue("Response contains correct path: " + response, response.contains("1 3 2 2 2"));
    }

    /**
     * Test postText returns correct response with optimal starting point
     * somewhere other than 0,0.
     */
    @Test
    public void testPostTextHandlesValidDataWithNonOriginStart() {
        String content = "19 -10 19 10 19\n" +
                         "-100 23 -20 -19 -12\n" +
                         "20 -12 20 11 -10";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("-163"));
        assertTrue("Response contains correct path: " + response, response.contains("2 3 2 2 2"));
    }

    /**
     * Test postText returns correct response with two rows.
     */
    @Test
    public void testPostTextHandlesValidDataWithTwoRows() {
        String content = "1 3 4 5 2\n" +
                         "5 4 3 2 3";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("11"));
        assertTrue("Response contains correct path: " + response, response.contains("1 1 2 2 1"));
    }

    /**
     * Test postText returns correct response with one row.
     */
    @Test
    public void testPostTextHandlesValidDataWithOneRow() {
        String content = "1 3 4 5 2";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("15"));
        assertTrue("Response contains correct path: " + response, response.contains("1 1 1 1 1"));
    }

    /**
     * Test postText returns correct response with optimal solution beginning
     * at last row.
     */
    @Test
    public void testPostTextHandlesValidDataWithOptimalSolutionBeginningAtLastRow() {
        String content = "19 -10 19 10 19\n" +
                         "23 23 -20 -19 -12\n" +
                         "-100 -12 20 11 -10";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);
        assertTrue("Response contains yes: " + response, response.contains("Yes"));
        assertTrue("Response contains correct resistance: " + response, response.contains("-163"));
        assertTrue("Response contains correct path: " + response, response.contains("3 3 2 2 2"));
    }

    /**
     * Test postText returns failure when input contains fewer than five columns.
     */
    @Test
    public void testPostTextFailsWhenDataIncludesLessThanFiveColumns() {
        String content = "19 29 1 3\n" +
                         "10 34 3 1\n" +
                         "100 21 3 2";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);

        assertTrue("Response contains error message: " + response,
                   response.contains("An error occurred while processing.  Please check your input and try again."));
    }

    /**
     * Test postText returns failure when input contains greater than 100 columns
     */
    @Test
    public void testPostTextFailsWhenDataIncludesMoreThanHundredColumns() {
        String content = "1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5\n" +
                         "1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5\n" +
                         "1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);

        assertTrue("Response contains error message: " + response,
                   response.contains("An error occurred while processing.  Please check your input and try again."));
    }

    /**
     * Test postText returns failure when input contains greater than ten rows
     */
    @Test
    public void testPostTextFailsWhenDataIncludesMoreThanTenRows() {
        String content = "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5\n" +
                         "1 2 3 4 5";

        LeastResistanceResource instance = new LeastResistanceResource();
        String response = instance.postText(content);

        assertTrue("Response contains error message: " + response,
                   response.contains("An error occurred while processing.  Please check your input and try again."));
    }
}