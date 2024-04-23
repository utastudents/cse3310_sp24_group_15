package uta.cse3310;

class Word {
    private String word;
    private int startRow;
    private int startCol;
    private String direction; // Change direction field type to String

    public Word(String word, int startRow, int startCol, String direction) { // Adjust constructor parameter type
        this.word = word;
        this.startRow = startRow;
        this.startCol = startCol;
        this.direction = direction;
    }

    public String getWord() {
        return word;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public String getDirection() { // Adjust getter return type
        return direction;
    }
}
