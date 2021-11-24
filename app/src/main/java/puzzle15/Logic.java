package puzzle15;

import java.awt.Color;
import java.util.Random;

public class Logic {
    private int size;
    private int dimension;
    private static final Color FOREGROUND_COLOR = new Color(239, 83, 80);
    private static final Random RANDOM = new Random();
    private int[] tiles;
    private int tileSize;
    private int nbTiles;
    private int blankPos;
    private int margin;
    private int gridSize;
    private boolean gameOver;

    public Logic() {
        this.size = 0;
        this.dimension = 0;
        this.tiles = new int[size * size];
        this.tileSize = 0;
        this.blankPos = 0;
        this.margin = 0;
        this.gridSize = 0;
        this.gameOver = false;

    }

    private void newGame() {
        do {
            reset();
            shuffle();
        } while (!isSolvable());

        gameOver = false;
    }

    private void reset() {
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (i + 1) % tiles.length;
        }

        blankPos = tiles.length - 1;
    }

    private void shuffle() {

        int n = nbTiles;

        while (n > 1) {
            int r = RANDOM.nextInt(n--);
            int tmp = tiles[r];
            tiles[r] = tiles[n];
            tiles[n] = tmp;
        }
    }

    private boolean isSolvable() {
        int countInversions = 0;

        for (int i = 0; i < nbTiles; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j] > tiles[i])
                    countInversions++;
            }
        }

        return countInversions % 2 == 0;
    }

    private boolean isSolved() {
        if (tiles[tiles.length - 1] != 0)
            return false;

        for (int i = nbTiles - 1; i >= 0; i--) {
            if (tiles[i] != i + 1)
                return false;
        }

        return true;
    }
}