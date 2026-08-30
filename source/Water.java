public class Water {
    //water is controled by the user to put out the fire
    private int row;
    private int col;

    public Water(int row, int col) {
        this.row = row;
        this.col = col;
    }

    //move the water to a new position
    public void moveUp() {
        if (row > 0) {
            row--;
        }
    }

    public void moveDown() {
        if (row < 5) { // Assuming a 6x6 grid
            row++;
        }
    }

    public void moveLeft() {
        if (col > 0) {
            col--;
        }
    }

    public void moveRight() {
        if (col < 5) { // Assuming a 6x6 grid
            col++;
        }
    }

    public void extinguish(Tree tree) {
        if (tree.getState().equals("BURNING")) {
            tree.extinguish();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
