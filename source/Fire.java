import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fire {

    // private static final int BURN_DURATION_TICKS = 10;
    private static final double SPREAD_CHANCE = 0.25;
    private final Random random = new Random();

    // private final Map<Tree, Integer> burnTimers = new HashMap<>();
    // private final Random random = new Random();

    public void startFire(Tree tree) {

        if (tree != null && tree.isGreen()) {
            tree.burn();
        }
    }

    public void spread(Forest forest) {
        List<Tree> burningAtStart =
                new ArrayList<>(forest.getBurningTrees());

        for (int row = 0; row < forest.getRows(); row++) {
            for (int col = 0; col < forest.getColumns(); col++) {

                Tree tree;

                try {
                    tree = forest.getTree(row, col);
                } catch (InvalidGridPositionException e) {
                    continue;
                }

                if (!tree.isBurning()
                        || !burningAtStart.contains(tree)) {
                    continue;
                }

                // Try spreading in four directions
                tryIgnite(forest, row - 1, col);
                tryIgnite(forest, row + 1, col);
                tryIgnite(forest, row, col - 1);
                tryIgnite(forest, row, col + 1);
            }
        }
    }



    private void tryIgnite(Forest forest, int row, int col) {
        if (row < 0 || row >= forest.getRows()
                || col < 0 || col >= forest.getColumns()) {
            return;
        }

        try {
            Tree neighbour = forest.getTree(row, col);

            if (neighbour.isGreen()
                    && random.nextDouble() < SPREAD_CHANCE) {

                startFire(neighbour);
                forest.reportBurning(neighbour);
                System.out.println("Fire spread to (" + row + ", " + col + ")");
            }

        } catch (InvalidGridPositionException e) {
            System.out.println(e.getMessage());
        }
    }
}
