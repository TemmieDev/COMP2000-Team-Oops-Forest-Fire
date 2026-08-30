import java.awt.Color;
import java.awt.Graphics;

//Creating the states for tree
public class Tree {
    private String state;
    private int burnTime;
    private int restoreTime;
    private int x;
    private int y;
    private int size;

    public Tree(int x, int y, int size){
        this.x = x;
        this.y = y;
        this.size = size;
        state = "GREEN";
    }

    public void draw(Graphics g){
        if(state.equals("GREEN")){
            g.setColor(Color.GREEN);
        } else if(state.equals("BURNING")){
            g.setColor(Color.RED);
        } else if(state.equals("BURNED")){
            g.setColor(Color.BLACK);
        }
        g.fillRect(x, y, size, size);
    }

    public Tree (){
        state = "GREEN"; //green
    }

    public void burn(){
        state = "BURNING"; //red
    }

    public void extinguish(){
        state = "BURNED"; //black
    }

    public String getState(){
        return state;
    }

    public int getBurnTime(){
        return burnTime;
    }

    public int getRestoreTime(){
        return restoreTime;
    }
}