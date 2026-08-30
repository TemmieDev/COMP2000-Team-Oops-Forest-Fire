public class Fire {

    public void setFire(Tree tree) {
        
        if (tree.getState().equals("GREEN")) {
            tree.burn();
        }
    }
    
}
