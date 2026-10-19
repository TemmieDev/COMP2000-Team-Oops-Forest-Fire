import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fire {

    private static final int BURN_DURATION_TICKS = 10;
    private static final double SPREAD_CHANCE = 0.10;


    private final Map<Tree, Integer> burnTimers = new HashMap<>();
    private final Random random = new Random();
    
    public void startFire(Tree tree) {
        
        if (tree.getState() == TreeState.GREEN) {
            tree.burn();
            burnTimers.put(tree, BURN_DURATION_TICKS);
        }
    }

    public void spread(Forest forest) {
 for (int row = 0; row < forest.getRows(); row++) {
            for (int col = 0; col < forest.getColumns(); col++) {
                Tree tree;
                try {
                    tree = forest.getTree(row, col);
                } catch (InvalidGridPositionException e) {
                    continue; 
                }
 
                if (tree.getState() == TreeState.BURNING) {
                    burnTimers.putIfAbsent(tree, BURN_DURATION_TICKS);
 
                    tryIgnite(forest, row - 1, col);
                    tryIgnite(forest, row + 1, col);
                    tryIgnite(forest, row, col - 1);
                    tryIgnite(forest, row, col + 1);
                }
            }
        }
 
        List<Tree> finished = new ArrayList<>();
        for (Map.Entry<Tree, Integer> entry : burnTimers.entrySet()) {
            Tree tree = entry.getKey();
 
            if (tree.getState() != TreeState.BURNING) {
                
                finished.add(tree);
                continue;
            }
 
            int remaining = entry.getValue() - 1;
            if (remaining <= 0) {
                tree.extinguish();
                forest.reportExtinguished(tree);
                finished.add(tree);
            } else {
                entry.setValue(remaining);
            }
        }
        for (Tree tree : finished) {
            burnTimers.remove(tree);
        }
    }
 
    private void tryIgnite(Forest forest, int row, int col) {
        if (row < 0 || row >= forest.getRows() || col < 0 || col >= forest.getColumns()) {
            return;
        }
        Tree neighbour;
        try {
            neighbour = forest.getTree(row, col);
        } catch (InvalidGridPositionException e) {
            return;
        }
        if (neighbour.getState() == TreeState.GREEN && random.nextDouble() < SPREAD_CHANCE) {
            neighbour.burn();
            burnTimers.put(neighbour, BURN_DURATION_TICKS);
            forest.reportBurning(neighbour);
        }
    }
}
