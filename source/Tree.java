//Creating the states for tree
public class Tree {
    private TreeState state;
    private int burnTime;
    private int restoreTime;

    public Tree() {
        state = TreeState.GREEN;
    }

    public void burn() {
        state = TreeState.BURNING;
    }

    public void extinguish() {
        state = TreeState.BURNED;
    }

    public TreeState getState() {
        return state;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public int getRestoreTime() {
        return restoreTime;
    }
}