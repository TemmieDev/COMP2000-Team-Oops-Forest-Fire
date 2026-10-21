public class Pine extends Tree {
    @Override
    public String getSpecies() {
        return "Pine";
    }

    @Override
    public double getIgnitionMultiplier() {
        return 1.0; // Pine trees are more flammable than generic trees
    }
    
}
