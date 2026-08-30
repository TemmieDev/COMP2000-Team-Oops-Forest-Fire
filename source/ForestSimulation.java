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
        forest = new Forest(6, 6);
        fire = new Fire();
        water = new Water();
        wind = new Wind();
    }
    public static void main(String[] args){
        Tree tree = new Tree(50, 50, 20);
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
        frame.setVisible(true);

    }
}