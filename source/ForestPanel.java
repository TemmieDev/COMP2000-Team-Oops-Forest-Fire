import java.awt.*;
import javax.swing.*;

public class ForestPanel extends JPanel{
    private Forest forest;
    private Water water;

    private final int CELL_SIZE = 80; // Size of each cell in the grid

    public ForestPanel(Forest forest, Water water) {
        this.forest = forest;
        this.water = water;

        setPreferredSize(new Dimension(
            forest.getColumns() * CELL_SIZE, 
            forest.getRows() * CELL_SIZE + 40
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawForest(g);
        drawWater(g);
    }
    
    private void drawForest(Graphics g) {
        for (int row = 0; row < forest.getRows(); row++) {
            for (int col = 0; col < forest.getColumns(); col++) {
                try {
                    Tree tree = forest.getTree(row, col);
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;

                    switch (tree.getState()) {
                        case GREEN -> g.setColor(Color.GREEN);
                        case BURNING -> g.setColor(Color.RED);
                        case BURNED -> g.setColor(Color.BLACK);
                    }

                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    g.setColor(Color.GRAY);
                    g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                } catch (InvalidGridPositionException e) {
                    // row/col come from forest.getRows()/getColumns() so this
                    // is not expected to happen, but still have to satisfy
                    // the checked exception rather than ignore it silently.
                    System.out.println("Skipped drawing cell: " + e.getMessage());
                }
            }
        }
    }

    private void drawWater(Graphics g) {

        int x = water.getCol() * CELL_SIZE;
        int y = water.getRow() * CELL_SIZE;

        g.setColor(Color.BLUE);
        g.fillOval(x + 20, y + 20, CELL_SIZE - 40, CELL_SIZE - 40);
    }
}
