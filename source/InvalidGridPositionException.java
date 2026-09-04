/**
 * Thrown when code tries to access a (row, col) position that does not
 * exist in the Forest's grid.
 */
public class InvalidGridPositionException extends Exception {

    private final int row;
    private final int col;

    public InvalidGridPositionException(int row, int col) {
        super("Invalid grid position: (" + row + ", " + col + ")");
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
