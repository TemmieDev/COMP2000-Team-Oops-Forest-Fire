public class Fire {

    public void startFire(Tree tree) {
        
        if (tree.getState().equals("GREEN")) {
            tree.burn();
        }
    }
    
}
