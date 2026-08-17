import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;

public class ForestSimulation{

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