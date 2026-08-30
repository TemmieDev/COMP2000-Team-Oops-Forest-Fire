import java.awt.*;

import javax.swing.JFrame;

public class ForestSimulation{

    private Forest forest;
    private Fire fire;
    private Water water;
    private Wind wind;

    private ForestPanel forestPanel;

    public ForestSimulation(){
        //forest
        forest = new Forest(6, 6);
        //objects
        fire = new Fire();
        water = new Water(0, 0);
        wind = new Wind();

        //GUI
        forestPanel = new ForestPanel(forest, water);
        JFrame frame = new JFrame("Forest Simulation");

        frame.add(forestPanel);
        frame.pack();
        
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

    }
}