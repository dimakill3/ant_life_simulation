package sample;

import javafx.util.Pair;

public class objects {
    private int durability;
    private int id;
    private Pair<Integer, Integer> coords;

    public objects(int durability, int id, Pair<Integer, Integer> coords)
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
}
