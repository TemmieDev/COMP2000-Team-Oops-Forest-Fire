import java.util.ArrayList;
import java.util.List;

public class Forest {

    private Tree[][] trees; // grid of trees

    // Generic collection: keeps track of every tree that is currently on
    // fire, so the rest of the program (e.g. a future "spread fire to
    // neighbours" step) doesn't need to scan the whole grid to find them.
    private List<Tree> burningTrees;

    public Forest(int rows, int columns) {
        trees = new Tree[rows][columns];
        burningTrees = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                trees[row][col] = new Tree();
            }
        }
    }

    /**
     * Returns the tree at (row, col).
     */
    public Tree getTree(int row, int col) throws InvalidGridPositionException {
        if (row < 0 || row >= trees.length || col < 0 || col >= trees[0].length) {
            throw new InvalidGridPositionException(row, col);
        }
        return trees[row][col];
    }

    public int getRows() {
        return trees.length;
    }

    public int getColumns() {
        return trees[0].length;
    }

    /**
     * Records that a tree has started burning, so it shows up in
     * getBurningTrees(). Safe to call more than once for the same tree.
     */
    public void reportBurning(Tree tree) {
        if (!burningTrees.contains(tree)) {
            burningTrees.add(tree);
        }
    }

    /**
     * Records that a tree is no longer burning (e.g. extinguished),
     * removing it from the tracked list.
     */
    public void reportExtinguished(Tree tree) {
        burningTrees.remove(tree);
    }

    public List<Tree> getBurningTrees() {
        return burningTrees;
    }

    public void displayForest() {

    }
}