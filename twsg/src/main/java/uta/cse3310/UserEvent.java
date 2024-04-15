package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; // the game ID on the server
    PlayerType PlayerIdx; // either an bluePLAYER or redPLAYER
    int Button; // button number from 0 to 2499
    char[][] grid;

    UserEvent() {

    }

    UserEvent(int _GameId, PlayerType _PlayerIdx, int _Button) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button = _Button;
    }
}