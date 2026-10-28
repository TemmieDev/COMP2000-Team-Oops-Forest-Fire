import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ForestSimulation implements KeyListener {

    private static final Logger LOGGER = Logger.getLogger(ForestSimulation.class.getName());
    private static final int DEFAULT_SIZE = 6;

    private Forest forest;
    private Fire fire;
    private Water water;
    // private Wind wind;

    private ForestPanel panel;
    private StatsPanel stats;
    private JFrame frame;
    private Timer fireTimer;
    private Timer waterTimer;
    private Timer regrowthTimer;

    // How often the fire advances, in milliseconds. Slower than a redraw
    // tick so the spread is watchable rather than instant.
    private static final int FIRE_STEP_MS = 2000;

    private static final int WATER_RECHARGE_MS = 2000; // 1 second

    private Map<Tree, Long> regrowingTrees = new HashMap<>();
    public static final int REGROWTH_CHECK_MS = 100;
    
    public ForestSimulation() {
        this(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public ForestSimulation(int rows, int columns) {
        // forest
        forest = createForest(rows, columns);
        // objects
        fire = new Fire();
        water = new Water(0, 0);
        // wind = new Wind();

        // GUI
        panel = new ForestPanel(forest, water);
        stats = new StatsPanel(forest, () -> "");
        frame = new JFrame("Forest Simulation");

        frame.add(stats, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);

        // Advance the fire on a timer rather than only when a key is
        // pressed, so it spreads on its own while the player is moving
        // water around. fire.spread() is currently a stub
        fireTimer = new Timer(FIRE_STEP_MS, e -> {
            fire.spread(forest);
            panel.repaint();
        });
        fireTimer.start();
        startRegrowthTimer();
        startRandomFire();

        forest.printTreeSpecies(); // Print the species of each tree in the forest

    }

    /**
     * Builds the forest, recovering from a bad size by falling back to the
     * default one so the simulation still starts.
     */
    private static Forest createForest(int rows, int columns) {
        try {
            return new Forest(rows, columns);
        } catch (InvalidGridSizeException e) {
            LOGGER.warning(e.getMessage() + " - using " + DEFAULT_SIZE + " x "
                    + DEFAULT_SIZE + " instead");
            try {
                return new Forest(DEFAULT_SIZE, DEFAULT_SIZE);
            } catch (InvalidGridSizeException impossible) {
                // The default is a compile-time constant that is valid, so
                // reaching here means the code itself is broken.
                throw new IllegalStateException("Default forest size is invalid", impossible);
            }
        }
    }

    private void startRegrowthTimer(){
        regrowthTimer = new Timer(REGROWTH_CHECK_MS, e ->{
            long currentTime = System.nanoTime();

                regrowingTrees.entrySet().removeIf(entry -> {
                    Tree tree = entry.getKey();
                    long regrowthDeadLine = entry.getValue();

                    if(currentTime >= regrowthDeadLine){
                        if (tree.isBurned()){
                            tree.regrow();
                        }
                        return true; 
                    }
                    return false;
                });
                panel.repaint();
            });
            regrowthTimer.start();
    }

    public void startRandomFire() {
        // Start a random fire in the forest
        int row = (int) (Math.random() * forest.getRows());
        int col = (int) (Math.random() * forest.getColumns());

        try {
            Tree tree = forest.getTree(row, col);
            fire.startFire(tree);
            forest.reportBurning(tree);
            System.out.println("Random fire started at (" + row + ", " + col + ")");
        } catch (InvalidGridPositionException e) {
            // Should not happen since row/col are generated from the
            // forest's own dimensions, but handled defensively in case
            // that generation logic ever changes.
            System.out.println("Could not start fire: " + e.getMessage());
        }

        panel.repaint();
    }

    // WATER
    public void useWater() {
        try {
            Tree tree = forest.getTree(
                    water.getRow(),
                    water.getCol());

            if (water.extinguish(tree)) {
                forest.reportExtinguished(tree);

                long regrowthTimeNs = tree.getRegrowthTime() * 1_000_000_000L; // Convert seconds to nanoseconds
                long deadline = System.nanoTime() + regrowthTimeNs;
                regrowingTrees.put(tree, deadline);


                startWaterRecharge();
            }

        } catch (InvalidGridPositionException e) {
            System.out.println("Could not use water: " + e.getMessage());
        }

        panel.repaint();
    }

    public void moveWater(String direction) {
        if (direction.equals("UP")) {
            water.moveUp();
        } else if (direction.equals("DOWN")) {
            water.moveDown(forest.getRows());
        } else if (direction.equals("LEFT")) {
            water.moveLeft();
        } else if (direction.equals("RIGHT")) {
            water.moveRight(forest.getColumns());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveWater("UP");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveWater("DOWN");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveWater("LEFT");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveWater("RIGHT");
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            useWater();
        }

        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    private void startWaterRecharge() {
        if (waterTimer != null && waterTimer.isRunning()) {
            return;
        }

        final long startTime = System.nanoTime();

        waterTimer = new Timer(100, e -> {

            long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;

            int progress = (int) Math.min(
                    100,
                    elapsedMs * 100 / WATER_RECHARGE_MS);

            water.updateRecharge(progress);
            panel.repaint();

            if (progress >= 100) {
                waterTimer.stop();
            }
        });

        waterTimer.start();

    }




    /**
     * Optional arguments: [rows] [columns], e.g. "java ForestSimulation 8 10".
     * Anything unusable falls back to the default size.
     */
    public static void main(String[] args) {
        int rows = DEFAULT_SIZE;
        int columns = DEFAULT_SIZE;
        try {
            if (args.length >= 2) {
                rows = Integer.parseInt(args[0]);
                columns = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("Grid size arguments must be whole numbers ("
                    + e.getMessage() + ") - using the default size");
            rows = DEFAULT_SIZE;
            columns = DEFAULT_SIZE;
        }
        new ForestSimulation(rows, columns);
    }
}
