package sample;

import javafx.util.Pair;

public class objects {
    private int durability;
    private int id;
    private Pair<Integer, Integer> coords;

    public objects(Pair<Integer, Integer> coords, int durability, int id)
    {
        this.durability = durability;
        this.id = id;
        this.coords = coords;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void IncDurability(int durability) {
        this.durability += durability;
    }
}
