package sample;

import javafx.util.Pair;

import java.awt.*;

public class objects {
    private int durability;
    private final int MaxDurability;
    private int id;
    private Point coords;

    public objects(Point coords, int max_durability, int id)
    {
        this.MaxDurability = max_durability;
        this.durability = max_durability;
        this.id = id;
        this.coords = coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
    }

    public void IncDurability(int durability) {
        if(this.durability + durability >= MaxDurability)
            this.durability = durability;
        else
            this.durability += durability;
    }

    public boolean DecDurability(int durability)
    {
        if(this.durability - durability <= 0) {
            this.durability = 0;
            return true;
        }
        else {
            this.durability -= durability;
            return false;
        }
    }

    public int getDurability() {
        return durability;
    }
}
