package uta.cse3310;

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

    Game(Statistics s) {
        Stats = s;
        Button = new PlayerType[2500];
        // initialize it
        initializeBoard();

        Players = PlayerType.bluePLAYER;
        // Shown to the user, 0 is bluePLAYER
        // 1 is redPLAYER
        Msg = new String[2];
        Msg[0] = "Waiting for other player to join";
        Msg[1] = "";
    }

    public void initializeBoard() {
        // initializes the board to NOPLAYER in all spots
        // then sets the words in the grid
        // then fills the rest with random letters
    }

    public void PrintGame() {
        // this method is used for debugging only
        // sometimes you want to see a picture of what is going on
        // NEEDS TO BE UPDATED FOR THE 50X50 GRID
        System.out.println(Button[0].toString() + " " + Button[1].toString() + " " + Button[2].toString());
        System.out.println(Button[3].toString() + " " + Button[4].toString() + " " + Button[5].toString());
        System.out.println(Button[6].toString() + " " + Button[7].toString() + " " + Button[8].toString());
    }

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

    public void Update(UserEvent U) {

        //check valid positions for the two clicks (valid move check)
        //check if word valid if passes test above (valid move check)
        //if word is valid do the apporopriate functions
        //if there are no words in the word list calculate winner

    }

    public void Tick() {
        // this function can be called periodically if a
        // timer is needed.

    }
}
// In windows, shift-alt-F formats the source code
// In linux, it is ctrl-shift-I