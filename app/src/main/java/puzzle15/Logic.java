package puzzle15;

import java.util.ArrayList;
import java.util.Collections;

public class Logic {
    private ArrayList<ArrayList<Integer>> grid;
    private int blankX;
    private int blankY;
    private int dimension;

    public Logic(int dimension) {
        this.dimension = dimension;
        this.grid = new ArrayList<ArrayList<Integer>>(this.dimension);
        this.createGrid();
        this.shuffle();
    }

    public void createGrid() {
        for (int i = 0; i < this.dimension; i++) {
            ArrayList<Integer> temp = new ArrayList<Integer>(this.dimension);
            for (int j = 0; j < this.dimension; j++) {
                temp.add((i * this.dimension) + j);
            }
            this.grid.add(temp);
        }
        this.blankX = 0;
        this.blankY = 0;
    }

    public void shuffle() {
        ArrayList<Integer> arr = new ArrayList<Integer>(this.dimension * this.dimension);
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                arr.add(this.grid.get(i).get(j));
            }
        }
        do {
            Collections.shuffle(arr);
            this.blankX = arr.indexOf(0) / this.dimension;
            this.blankY = arr.indexOf(0) % this.dimension;
        } while (!this.isSolvable(arr));
        this.grid.clear();
        for (int i = 0; i < this.dimension; i++) {
            ArrayList<Integer> temp = new ArrayList<Integer>(this.dimension);
            for (int j = 0; j < this.dimension; j++) {
                temp.add(arr.get((i * this.dimension) + j));
            }
            this.grid.add(temp);
        }
    }

    public void moveUp() {
        this.grid.get(this.blankX).set(this.blankY, this.grid.get(this.blankX - 1).get(this.blankY));
        this.grid.get(this.blankX - 1).set(this.blankY, 0);
        this.blankX--;
    }

    public void moveDown() {
        this.grid.get(this.blankX).set(this.blankY, this.grid.get(this.blankX + 1).get(this.blankY));
        this.grid.get(this.blankX + 1).set(this.blankY, 0);
        this.blankX++;
    }

    public void moveRight() {
        this.grid.get(this.blankX).set(this.blankY, this.grid.get(this.blankX).get(this.blankY + 1));
        this.grid.get(this.blankX).set(this.blankY + 1, 0);
        this.blankY++;
    }

    public void moveLeft() {
        this.grid.get(this.blankX).set(this.blankY, this.grid.get(this.blankX).get(this.blankY - 1));
        this.grid.get(this.blankX).set(this.blankY - 1, 0);
        this.blankY--;
    }

    public Integer getGridElem(int x, int y) {
        return this.grid.get(x).get(y);
    }

    public Integer getBlankX() {
        return this.blankX;
    }

    public Integer getBlankY() {
        return this.blankY;
    }

    public boolean isSolved() {
        if (this.grid.get(this.dimension - 1).get(this.dimension - 1).intValue() != 0) {
            return false;
        }
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.grid.get(i).get(j).intValue() != (i * this.dimension) + j) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSolvable(ArrayList<Integer> arr) {
        int countInversions = 0;
        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i).intValue() > arr.get(i + 1).intValue()) {
                countInversions++;
            }
        }
        if (this.dimension % 2 != 0 && countInversions % 2 == 0) {
            return true;
        } else if (this.dimension % 2 == 0) {
            if (this.dimension - this.blankX + 1 % 2 == 0 && countInversions % 2 != 0) {
                return true;
            } else if ((this.dimension - this.blankX + 1 % 2 != 0) && countInversions % 2 == 0) {
                return true;
            }
        }
        return false;
    }

}