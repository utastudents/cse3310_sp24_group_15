package test.java.uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uta.cse3310.Game;

/**
 * Unit test for the Game class.
 */
public class GameTest extends TestCase {
    private Game game;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameTest(String testName) {
        super(testName);
        game = new Game(); // Assume Statistics is correctly set up for testing
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(GameTest.class);
    }

    
    public void testFillEmptySlotsWithRandomLetters() {
        game.initializeGrid(); 
        game.fillEmptySlotsWithRandomLetters();

        boolean allFilled = true;
        char[][] grid = game.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ' ' || !(grid[i][j] >= 'A' && grid[i][j] <= 'Z')) {
                    allFilled = false;
                    break;
                }
            }
            if (!allFilled) {
                break;
            }
        }

        assertTrue("all empty slots should be filled with random uppercase letters", allFilled);
    }
    public void testPlaceWordSuccess() {
        String testWord = "TEST";
        game.initializeGrid();  // Ensure grid is initialized and empty
        game.placeWord(testWord);
    
        boolean wordFound = false;
        for (int i = 0; i < game.getGrid().length; i++) {
            for (int j = 0; j < game.getGrid()[0].length; j++) {
                if (findWordInGrid(testWord, i, j)) {
                    wordFound = true;
                    break;
                }
            }
            if (wordFound) break;
        }
    
        assertTrue("The word should be placed on the grid.", wordFound);
    }
    


    private boolean findWordInGrid(String word, int startRow, int startCol) {
        
        for (int dir = 0; dir < 8; dir++) {
            boolean matches = true;
            int row = startRow;
            int col = startCol;

            for (char c : word.toCharArray()) {
                if (row < 0 || row >= game.getGrid().length || col < 0 || col >= game.getGrid()[0].length ||
                    game.getGrid()[row][col] != c) {
                    matches = false;
                    break;
                }
                row += game.dx[dir];
                col += game.dy[dir];
            }

            if (matches) return true;
        }
        return false;
    }
    public void testPopulateGridWithWords() {
        game.initializeGrid();  // Ensure the grid is reset before population
        game.populateGridWithWords();
    
        boolean allFilled = true;
        char[][] grid = game.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ' ') {
                    allFilled = false;
                    break;
                }
            }
            if (!allFilled) break;
        }
    
        assertTrue("All slots in the grid should be filled", allFilled);
        assertTrue("Words should be unique", game.uniqueWords.size() == 20);
    }
    
    public void testInitializeGrid() {
        game.initializeGrid(); 

        
        char[][] grid = game.getGrid();
        boolean allSpaces = true;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != ' ') {
                    allSpaces = false;
                    break;
                }
            }
            if (!allSpaces) break;
        }

        assertTrue("all grid cells should be initialized to spaces", allSpaces);
    }
}
