//Creating the states for tree
public class Tree {
    private String state;
    public boolean isBurning;

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
}