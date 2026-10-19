public class Water {
    //water is controled by the user to put out the fire
    private int row;
    private int col;
    private boolean available;
    private int rechargeProgress;

    public Water(int row, int col) {
        this.row = row;
        this.col = col;
        this.available = true;
        this.rechargeProgress = 100;
    }

    //move the water to a new position
    public void moveUp() {
        if (row > 0) {
            row--;
        }
    }

    public void moveDown(int maxRows) {
        if (row < maxRows - 1) {
            row++;
        }
    }

    public void moveLeft() {
        if (col > 0) {
            col--;
        }
    }

    public void moveRight(int maxCols) {
        if (col < maxCols - 1) {
            col++;
        }
    }

    public boolean extinguish(Tree tree) {
       // Water cannot be used while charging
    if (!available) {
        return false;
    }

    // Only extinguish burning trees
    if (tree == null || !tree.isBurning()) {
        return false;
    }

    tree.extinguish();

    // Disable water and begin recharge
    available = false;
    rechargeProgress = 0;

    return true;
    }

    //called by forestsimulation
    public void updateRecharge(int progress) {
        if(available){
            return;
        }

        rechargeProgress = Math.max(0, Math.min(100, progress));

        if (rechargeProgress == 100) {
            available = true;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public int getRechargeProgress() {
        return rechargeProgress;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
