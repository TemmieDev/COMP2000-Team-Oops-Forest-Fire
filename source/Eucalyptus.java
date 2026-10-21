public class Eucalyptus extends Tree {
    @Override
    public String getSpecies() {
        return "Eucalyptus";
    }

    @Override
    public double getIgnitionMultiplier() {
        return 1.5; // Eucalyptus trees are more flammable than generic trees
    }
}
