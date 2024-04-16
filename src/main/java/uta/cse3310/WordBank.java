package uta.cse3310;
import java.util.ArrayList;
import java.util.Random;


public class WordBank {
    private static final String[] WORDS = {
            "Elephant", "Sunshine", "Galaxy", "Bicycle", "Whisper", "Ocean", "Guitar", "Chocolate", "Adventure",
            "Puzzle", "Castle", "Dragon", "Rainbow", "Symphony", "Moonlight", "Fireworks", "Treasure", "Thunder",
            "Butterfly", "Telescope", "Enigma", "Carnival", "Serenade", "Lighthouse", "Unicorn", "Sushi",
            "Harmony", "Whisper", "Bamboo", "Mirage", "Infinity", "Aurora", "Saffron", "Tornado", "whisper",
            "gondola", "avalanche", "mirage", "eclipse", "whisper"
    };
    static {
        for (int i = 0; i < WORDS.length; i++) {
            WORDS[i] = WORDS[i].toUpperCase();
        }
    }



    public static String getRandomWord() {
        Random rand = new Random();
        int index = rand.nextInt(WORDS.length);
        return WORDS[index];
    }
}





