package uta.cse3310;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WordBank {
    private static final ArrayList<String> WORDS = new ArrayList<>();

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/stockton/pro1/cse3310_sp24_group_15-main/twsg/src/main/java/uta/cse3310/wordbank.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                WORDS.add(line.trim().toLowerCase());
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading word bank file: " + e.getMessage());
        }
    }

    public static String getRandomWord() {
        Random rand = new Random();
        int index = rand.nextInt(WORDS.size());
        return WORDS.get(index);
    }

    public static void main(String[] args) {
        String randomWord = getRandomWord();
        System.out.println("Random word from the word bank: " + randomWord);
    }
}
