import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class ForestSimulation{

    private Forest forest;
    private Fire fire;
    private Water water;
    private Wind wind;

    public ForestSimulation(){
        //forest
        forest = new Forest(6, 6);
        //objects
        fire = new Fire();
        water = new Water(0, 0);
        wind = new Wind();
    }

    public void startRandomFire() {
        // Start a random fire in the forest
        int row = (int) (Math.random() * forest.getRows());
        int col = (int) (Math.random() * forest.getColumns());
        Tree tree = forest.getTree(row, col);
        fire.setFire(tree);

        System.out.println("Random fire started at (" + row + ", " + col + ")");
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

    //MAIN SIMULATION
    public void runSimulation() {
        System.out.println("Forest Simulation Started");
        forest.displayForest();

    }
    public static void main(String[] args){

        ForestSimulation simulation = new ForestSimulation();
        simulation.runSimulation();

        /*Tree tree = new Tree(50, 50, 20);
        JFrame frame = new JFrame("Test");
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                tree.draw(g);
            }
        };
        frame.add(panel);
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/

    }
}