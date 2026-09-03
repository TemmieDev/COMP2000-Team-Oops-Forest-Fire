import javax.swing.*;
import java.awt.*;

public class ForestPanel extends JPanel{
    private Forest forest;
    private Water water;

    private final int CELL_SIZE = 80; // Size of each cell in the grid

        // Normal state palette
    private static final Color[] TREE_GREEN = {
        Color.decode("#355432"), Color.decode("#64945f"), Color.decode("#1b3618")
    };
    private static final Color GROUND_GREEN = Color.decode("#306b2a");
    private static final Color GROUND_BROWN = Color.decode("#302016");
 
    // Burning / burned share the same ground palette
    private static final Color SCORCHED_DARK = Color.decode("#211f1d");
    private static final Color SCORCHED_ASH = Color.decode("#7a7978");
    private static final Color SCORCHED_EMBER = Color.decode("#911d10");
 
    private static final Color FIRE_COLOR = Color.decode("#911d10");
    private static final Color BURNED_TREE_COLOR = Color.decode("#211f1d");
    private static final Color WATER_COLOR = Color.decode("#1551a1");
    
    public ForestPanel(Forest forest, Water water) {
        this.forest = forest;
        this.water = water;

        setPreferredSize(new Dimension(
            forest.getColumns() * CELL_SIZE, 
            forest.getRows() * CELL_SIZE + 40
        ));
    }

    @Override
    protected void paintComponent(Graphics g0) {
    super.paintComponent(g0);
    Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawForest(g);
        drawWater(g);
    }
    
    private void drawForest(Graphics2D g) {
        for (int row = 0; row < forest.getRows(); row++) {
            for (int col = 0; col < forest.getColumns(); col++) {
                Tree tree = forest.getTree(row, col);
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                int cx = x + CELL_SIZE / 2;
                int cy = y + CELL_SIZE / 2;

                int seed = (row * 31 + col * 17) % 100;
 
                String state = tree.getState();
 
                if (state.equals("GREEN")) {
                    // Ground: 80% green / 20% brown
                    g.setColor(seed < 80 ? GROUND_GREEN : GROUND_BROWN);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
 
                    // Tree: one of 3 greens, picked randomly per tree
                    int treeSeed = (row * 13 + col * 7) % TREE_GREEN.length;
                    g.setColor(TREE_GREEN[treeSeed]);
                    int r = CELL_SIZE / 2 - 14;
                    g.fillOval(cx - r, cy - r, r * 2, r * 2);
 
                } else if (state.equals("BURNING")) {
                    drawScorchedGround(g, x, y, seed);
                    g.setColor(FIRE_COLOR);
                    int r = CELL_SIZE / 2 - 14;
                    g.fillOval(cx - r, cy - r, r * 2, r * 2);
 
                } else if (state.equals("BURNED")) {
                    drawScorchedGround(g, x, y, seed);
                    int r = CELL_SIZE / 5;
                    g.setColor(BURNED_TREE_COLOR);
                    g.fillOval(cx - r, cy - r, r * 2, r * 2);
                }
 
                g.setColor(new Color(0, 0, 0, 40));
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }
 
    // Shared by BURNING and BURNED: 10% dark / 80% ash / 10% ember
    private void drawScorchedGround(Graphics2D g, int x, int y, int seed) {
        if (seed < 10) {
            g.setColor(SCORCHED_DARK);
        } else if (seed < 90) {
            g.setColor(SCORCHED_ASH);
        } else {
            g.setColor(SCORCHED_EMBER);
        }
        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
    }

    private void drawWater(Graphics g) {

        int x = water.getCol() * CELL_SIZE;
        int y = water.getRow() * CELL_SIZE;
        int cx = x + CELL_SIZE / 2;
        int cy = y + CELL_SIZE / 2;

        int diameter = (int) ((CELL_SIZE - 40) * (2.0 / 3.0));
        g.setColor(WATER_COLOR);
        g.fillOval(cx - diameter / 2, cy - diameter / 2, diameter, diameter);
    }
 
}
