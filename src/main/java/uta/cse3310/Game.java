package uta.cse3310;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import uta.cse3310.WordBank;
import uta.cse3310.Grid;

public class Game {

    PlayerType Players;
    public PlayerType[] Button;
    // Buttons are indexed 0 to 2499 in the code
    // 0    1   2   .. 49
    // 50   51  52  .. 99
    // 100  101 102 .. 149
    // ..   ..  ..  .. ..
    // 2450 ..  ..  .. 2499

    public String[] Msg;
    public int GameId;
    public Statistics Stats;
    private int scorePlayer1;
    private int scorePlayer2;


    Game(Statistics s) {
        scorePlayer1 = 0;
        scorePlayer2 = 0;

        Stats = s;
        Button = new PlayerType[2500];
        // initialize it
        initializeGrid();

        Players = PlayerType.bluePLAYER;
        // Shown to the user, 0 is bluePLAYER
        // 1 is redPLAYER
        Msg = new String[2];
        Msg[0] = "Waiting for other player to join";
        Msg[1] = "";
    }
    public  Set<String> uniqueWords = new HashSet<>();
    private final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1}; // Changes in row indices for 8 directions
    private final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1}; // Changes in column indices for 8 directions
 //gh repo clone utastudents/cse3310_sp24_group_15
    
    private List<Word> wordsu = new ArrayList<>();
        // Add words to the list if needed

    Grid gridg = new Grid(wordsu);
    public char[][] grid = gridg.grid;
    public char randomLetter() { // Method name should start with lowercase
        String chars = "abcdefghijklmnopqrstuvwxyz"; // test, use abcdefghijklmnopqrstuvwxyz
        Random rnd = new Random();
        char c = chars.charAt(rnd.nextInt(chars.length()));
        return Character.toUpperCase(c);//upper is for test
    }
    
    // Initialize the grid with random letters
    public void initializeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ' '; // Call the randomLetter method with parentheses
            }
        }
    }
    

    // Populate the grid with words and random letters
    public void populateGridWithWords() {
        // Get random words from the WordBank
        WordBank wordBank = new WordBank(); // Rename the variable to start with lowercase
       
        ArrayList<String> words = new ArrayList<>();
        while (words.size() < 20) {
            String word = wordBank.getRandomWord();
                if (!uniqueWords.contains(word)) {
                    words.add(word);
                    uniqueWords.add(word);
                }
            }


        // Place words in the grid
        for (String word : words) {
            placeWord(word);
        }

        // Fill empty slots with random letters
        fillEmptySlotsWithRandomLetters();
    }

    // Place a word in the grid
    private void placeWord(String word) {
        Random rand = new Random();
        int row = rand.nextInt(grid.length);
        int col = rand.nextInt(grid[0].length);
        int direction = rand.nextInt(8); // 8 possible directions (horizontal, vertical, diagonal)
    
        // Check if the word can fit in the chosen direction
        boolean canPlace = checkPlaceWord(word, row, col, direction);
    
        if (canPlace) {
            // Place the word in the grid
            int len = word.length();
            for (int i = 0; i < len; i++) {
                grid[row][col] = word.charAt(i);
                row += dx[direction];//move to the next position based on the chosen direction
                col += dy[direction];
            }
        } else {
            placeWord(word); //retry placing the word
        }
    }
    public Game() {
        // Constructor code here
        // You can initialize the Game object without any parameters
        rPlayers = new HashSet<>();
        wPlayers = new ArrayList<>();
    }
    // Helper method to check if a word can be placed at a certain position and direction
    private boolean checkPlaceWord(String word, int row, int col, int direction) {
        int len = word.length();
        int newRow = row + len * dx[direction];
        int newCol = col + len * dy[direction];
        if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length) {
            return false; 
        }
        for (int i = 0; i < len; i++) {
            if (grid[row][col] != ' ' && grid[row][col] != word.charAt(i)) {
                return false; 
            }
            
            row += dx[direction];
            col += dy[direction];
        }
    
        return true; 
    }
    
    

    // Fill empty slots with random letters
    private void fillEmptySlotsWithRandomLetters() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ' ') { 
                    grid[i][j] = randomLetter(); 
                }
            }
        }
        
    }

    public char[][] getGrid() {
        return grid;
    }
    public void PrintGame(int on) {

        if(on == 1){
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println(); 
            }
            System.out.println("Used Words:");
            for (String word : uniqueWords) {
                System.out.println(word);
            }
        }
    }
    
    /*  THIS IS HOW TO test
    public static void main(String[] args) {
        Game game = new Game();
        game.initializeGrid();
        game.populateGridWithWords();
        char[][] grid = game.getGrid();
        
        game.PrintGame(0); // //1 for print, 0 for none
    }*/
    
    /*
    public void PrintGame() {
        // this method is used for debugging only
        // sometimes you want to see a picture of what is going on
        // NEEDS TO BE UPDATED FOR THE 50X50 GRID
        System.out.println(Button[0].toString() + " " + Button[1].toString() + " " + Button[2].toString());
        System.out.println(Button[3].toString() + " " + Button[4].toString() + " " + Button[5].toString());
        System.out.println(Button[6].toString() + " " + Button[7].toString() + " " + Button[8].toString());
    }*/

    public void SetBoard(PlayerType p, int[] b) {
        // this method is only used for testing purposes
        // p is the player to give the square to, and b
        // is an array of button numbers
        for (int i : b) {
            Button[i] = p;
        }

    }

    public void StartGame() {
        // will start the game and give players their color for the game
        Msg[0] = "You are color [player1_color].";
        Msg[1] = "You are color [player2_color].";
        Stats.setGamesInProgress(Stats.getGamesInProgress() + 1);
    }

    private boolean CheckLine(int[] a, int[] b) {
        // Uses the checks to see if the word is from the word list
        // Takes in the two button arrays the player selects
        // CheckHorizontal();
        // CheckDiagonal();
        // CheckVertical();
        // will return boolean from the check
        // If the two button arrays do not meet the horizontal
        // diagonal or vertical check it will return false
        return true;
    }

    private boolean CheckHorizontal(int[] a, int[] b) {
        //checks horizontal word with word bank
        //returns boolean
        return true;
    }

    private boolean CheckVertical(int[] a, int[] b) {
        //checks vertical word with word bank
        //returns boolean
        return true;
    }

    private boolean CheckDiagonal(int[] a, int[] b) {
        //checks diagonal word with word bank
        //returns boolean
        return true;
    }


    // This function returns an index for each player
    // It does not depend on the representation of Enums
    public int PlayerToIdx(PlayerType P) {
        int retval = 0;
        if (P == PlayerType.bluePLAYER) {
            retval = 0;
        } else {
            retval = 1;
        }
        return retval;
    }

    // Method to add a player to the waiting list
    // F: void, UREQ023
    public void addWaitingPlayer(String playerNickname) {
        //add the player on the waiting list
    }
     // Method to start the game when there are two waiting players
    // F: void, UREQ026
    public void twoPlayerStartGame() {
     //check if there is two player in the waiting list 
     // if there two player in the waiting list is set the game to start
    }



    public void Update(UserEvent U) {

        //check valid positions for the two clicks (valid move check)
        //check if word valid if passes test above (valid move check)
        //if word is valid do the apporopriate functions
        //if there are no words in the word list calculate winner

        if (U.isValidWord()) {
            if (U.getPlayerId().equals("Player1")) {
                scorePlayer1 += U.getWord().length();
            } else if (U.getPlayerId().equals("Player2")) {
                scorePlayer2 += U.getWord().length();
            }
        }
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public void Tick() {
        // this function can be called periodically if a
        // timer is needed.

    }
     // Method to register a new player with a nickname
    // NF: boolean, UREQ018
    public boolean registerPlayer(String nickname) {
      //if the existing user namer contain nickname 
      //print that the name is already been taken and return false
      //if the name is not yet being picked then we add the name nickname and return the true

      if (rPlayers.contains(nickname)) {
        System.out.println("Error: " + nickname + " is taken. Try something else!");
        return false;
      } else {
        rPlayers.add(nickname);
        System.out.println("Successful registration for " + nickname);
        return true;
      }
    }
      // Method to sign in a player with a nickname
    // F: boolean, UREQ021
    public boolean signInPlayer(String nickname) {
         
         //if the exist username contain the nickname
         // Add the player to the waiting list upon successful sign-in
         // Successful login or in another way return true
         //else print not found and return false

        if (rPlayers.contains(nickname)) {
            System.out.println("Success: Player " + nickname + " signed in!");
            return true;
        } else {
        rPlayers.add(nickname);
            System.out.println("Error: Player " + nickname + " not found.");
            return false;
        }
    
    }

     public void startWordSelection(int row, int col, String playerId) {
        // Logic to start selecting a word
    }
    
     public void selectLetter(int row, int col, String playerId) {
        // Logic to handle letter selection and word highlighting
    }
    
   public void endWordSelection(int row, int col, String playerId) {
        // Logic to end word selection and determine the winner
    }
     public void promptStartLetter() {
        // Logic to prompt players of the start letter
    }
       // Method to print the winner of the game
    // F: void
    public void printWinner(String winner) {
       //implement the one that win 
    }

        // Method to provide a word list to each user
    public List<String> generateWordList() {
        // Implementation to generate and return a word list
        return null;
    }

}
// In windows, shift-alt-F formats the source code
// In linux, it is ctrl-shift-I