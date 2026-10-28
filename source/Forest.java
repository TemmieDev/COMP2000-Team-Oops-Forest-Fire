import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Forest {

    // The species that can appear in a new forest.
    private static final List<Supplier<Tree>> SPECIES =
            List.of(Pine::new, Oak::new, Eucalyptus::new);

    private final Grid<Tree> trees; // grid of trees

    // Generic collection: keeps track of every tree that is currently on
    // fire, so the rest of the program doesn't need to scan the whole grid
    // to find them.
    private List<Tree> burningTrees;

    // Observers interested in what happens to the trees.
    private final List<ForestListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * @throws InvalidGridSizeException if rows or columns is less than 1
     */
    public Forest(int rows, int columns) throws InvalidGridSizeException {
        Random random = new Random();
        trees = new Grid<>(rows, columns,
                (row, col) -> SPECIES.get(random.nextInt(SPECIES.size())).get());
        burningTrees = new ArrayList<>();
    }

    /**
     * Returns the tree at (row, col).
     */
    public Tree getTree(int row, int col) throws InvalidGridPositionException {
        return trees.get(row, col);
    }

    public int getRows() {
        return trees.getRows();
    }

    public int getColumns() {
        return trees.getColumns();
    }

    /** Every tree with its position, for stream pipelines. */
    public Stream<GridCell<Tree>> cells() {
        return trees.cells();
    }

    /** The trees directly above/below/left/right of a position. */
    public Stream<GridCell<Tree>> neighbours(int row, int col) {
        return trees.neighbours(row, col);
    }

    /**
     * Counts trees grouped by any property
     */
    public <K extends Comparable<? super K>> Map<K, Long> countBy(
            Function<? super Tree, ? extends K> classifier) {
        return countBy(tree -> true, classifier);
    }

    /** Same as countBy(classifier) but only counts trees matching the filter. */
    public <K extends Comparable<? super K>> Map<K, Long> countBy(
            Predicate<? super Tree> filter,
            Function<? super Tree, ? extends K> classifier) {
        return trees.cells()
                .map(GridCell::value)
                .filter(filter)
                .collect(Collectors.groupingBy(classifier, TreeMap::new,
                        Collectors.counting()));
    }

    /**
     * Records that a tree has started burning, so it shows up in
     * getBurningTrees(). Safe to call more than once for the same tree.
     */
    public void reportBurning(Tree tree) {
        if (!burningTrees.contains(tree)) {
            burningTrees.add(tree);
            notifyListeners(ForestEvent.Type.IGNITED, tree);
        }
    }

    /**
     * Records that a tree is no longer burning (e.g. extinguished),
     * removing it from the tracked list.
     */
    public void reportExtinguished(Tree tree) {
        burningTrees.remove(tree);
        notifyListeners(ForestEvent.Type.EXTINGUISHED, tree);
    }

    /** Records that a tree finished burning without help from the player. */
    public void reportBurnedOut(Tree tree) {
        burningTrees.remove(tree);
        notifyListeners(ForestEvent.Type.BURNED_OUT, tree);
    }

    /** Records that a burned tree has grown back. */
    public void reportRegrown(Tree tree) {
        notifyListeners(ForestEvent.Type.REGROWN, tree);
    }

    public void addListener(ForestListener listener) {
        listeners.add(listener);
    }

    /** Observers should call this when they go away, or the Forest keeps them alive. */
    public void removeListener(ForestListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(ForestEvent.Type type, Tree tree) {
        ForestEvent event = new ForestEvent(type, tree);
        listeners.forEach(listener -> listener.onForestEvent(event));
    }

    public List<Tree> getBurningTrees() {
        return burningTrees;
    }

    //temporary method
    public void printTreeSpecies() {
        trees.cells()
                .collect(Collectors.groupingBy(GridCell::row, TreeMap::new,
                        Collectors.mapping(c -> c.value().getSpecies(),
                                Collectors.joining(" "))))
                .values()
                .forEach(System.out::println);
    }
}
