package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    public char[][] grid = new char[20][20];
    private List<Word> embeddedWords;

    // Constructor
    public Grid(List<Word> embeddedWords) {
        this.embeddedWords = embeddedWords;
        
    }
    // Constructor and other necessary methods
}