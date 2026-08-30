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
        row--;
    }

    public void moveDown() {
        row++;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
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
