import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class ForestSimulation implements KeyListener {

    private Forest forest;
    private Fire fire;
    private Water water;
    //private Wind wind;

    private ForestPanel panel;
    private JFrame frame;

    public ForestSimulation(){
        //forest
        forest = new Forest(6, 6);
        //objects
        fire = new Fire();
        water = new Water(0, 0);
        //wind = new Wind();

        //GUI
        panel = new ForestPanel(forest, water);
        frame = new JFrame("Forest Simulation");

        frame.add(panel);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public void startRandomFire() {
        // Start a random fire in the forest
        int row = (int) (Math.random() * forest.getRows());
        int col = (int) (Math.random() * forest.getColumns());
        Tree tree = forest.getTree(row, col);
        fire.startFire(tree);

        System.out.println("Random fire started at (" + row + ", " + col + ")");

        panel.repaint();
    }

    //WATER
    public void useWater(){
        int row = water.getRow();
        int col = water.getCol();

        Tree tree = forest.getTree(row, col);
        water.extinguish(tree);
    }

    public void moveWater(String direction){
        if(direction.equals("UP")){
            water.moveUp();
        } else if(direction.equals("DOWN")){
            water.moveDown(forest.getRows());
        } else if(direction.equals("LEFT")){
            water.moveLeft();
        } else if(direction.equals("RIGHT")){
            water.moveRight(forest.getColumns());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_UP) {
            moveWater("UP");
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveWater("DOWN");
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveWater("LEFT");
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveWater("RIGHT");
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Tree tree = forest.getTree(water.getRow(), water.getCol());
            if (tree.getState().equals("BURNING")) {
                water.extinguish(tree);
            }
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

    //MAIN SIMULATION
    public void runSimulation() {
        System.out.println("Forest Simulation Started");
        forest.displayForest();

        startRandomFire();
        forest.displayForest();

    }
    public static void main(String[] args){

        ForestSimulation simulation = new ForestSimulation();
        simulation.runSimulation();

    }
}