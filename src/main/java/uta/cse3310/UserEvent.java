package uta.cse3310;

// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; // the game ID on the server
    PlayerType PlayerIdx; // either an bluePLAYER or redPLAYER
    int Button; // button number from 0 to 2499
    char[][] grid;
    String userName;
    int buttonRowE;
    int buttonColE;
    int buttonRow;
    int buttonCol;
    String wordHighlighted;
    String type;


    UserEvent() {

    }

    UserEvent(int _GameId, PlayerType _PlayerIdx, int _Button, String _userName, int _buttonRow, int _buttonCol, String _type, String _wordHighlighted, int _buttonRowE, int _buttonColE){
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
        userName = _userName;
        buttonRow = _buttonRow;
        buttonCol = _buttonCol;
        type = _type;
        wordHighlighted = _wordHighlighted;
        buttonRowE = _buttonRowE;
        buttonColE = _buttonColE;
    }
}