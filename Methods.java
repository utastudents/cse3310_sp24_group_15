package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

/*
 * LIST OF METHODS BEFORE SORTING INTO USED .JAVA SOURCE FILES
 * THESE ARE NOT THE METHODS USED, JUST THE EARLY ITERATIONS TO KEEP
 * TRACK OF WHAT WE NEED TO IMPLEMENT
*/
public class Methods{

    // Method to display the letter grid to both players
    public void displayLetterGrid(char[][] grid) {
        // Implementation to display the grid
    }

    // Method to provide a word list to each user
    public List<String> generateWordList() {
        // Implementation to generate and return a word list
        return null;
    }

    // Method to update the score when a player locates a word
    public int updateScore(int currentScore) {
        // Implementation to update and return the new score
        return 0;
    }

    // Method to change the color of selected letters
    public void changeLetterColor(int startX, int startY, int endX, int endY, String color) {
        // Implementation to change the color of letters
    }

    // Method to check if a word is already found
    public boolean isWordFound(String word) {
        // Implementation to check if a word is already found
        return false;
    }

    // Method to register a new user
    public boolean registerUser(String nickname) {
        // Implementation to register a user and return success status
        return false;
    }

    // Method to sign in a user
    public boolean signInUser(String nickname) {
        // Implementation to sign in a user and return success status
        return false;
    }

    // Method to add a user to the Player Waiting List
    public void addUserToWaitingList(String nickname) {
        // Implementation to add a user to the waiting list
    }

    // Method to start the game with two players
    public void startGame() {
        // Implementation to start the game
    }

    // Method to display the winner
    public void displayWinner(String winnerNickname) {
        // Implementation to display the winner
    }

    // Method to provide a hint by showing the start letter of a random word
    public char provideHint(List<String> wordList) {
        // Implementation to provide a hint
        return ' ';
    }

    // Method to allow players to play again or return to the lobby
    public void postGameOptions(boolean playAgain) {
        // Implementation for post-game options
    }

    // Method to validate a user's nickname
    public boolean validateNickname(String nickname) {
        // Implementation to validate a nickname
        return false;
    }

    // Method to handle a player's disconnection
    public void handleDisconnection(String nickname) {
        // Implementation to handle disconnection
    }

    // Method to wait for a disconnected player to reconnect
    public boolean waitForReconnection(String nickname) {
        // Implementation to wait for reconnection
        return false;
    }
}
