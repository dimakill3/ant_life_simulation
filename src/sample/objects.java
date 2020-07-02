package sample;

import javafx.util.Pair;

public class objects {
    private int durability;
    private final int MaxDurability;
    private int id;
    private Pair<Integer, Integer> coords;

    public objects(Pair<Integer, Integer> coords, int max_durability, int id)
    {
        this.MaxDurability = max_durability;
        this.durability = max_durability;
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
        if(this.durability + durability >= MaxDurability)
            this.durability = durability;
        else
            this.durability += durability;
    }

    public void DecDurability(int durability)
    {
        this.durability -= durability;
    }

    public int getDurability() {
        return durability;
    }
}
