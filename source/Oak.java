public class Oak extends Tree {
    @Override
    public String getSpecies() {
        return "Oak";
    }

    @Override
    public double getIgnitionMultiplier() {
        return 0.6; // Oak trees are less flammable than generic trees
    }
}
