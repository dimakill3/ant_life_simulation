package sample;

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

    public Point getCoords() {
        return coords;
    }

    public void IncDurability(int durability) {
        if(this.durability + durability >= MaxDurability)
            this.durability = MaxDurability;
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

    public int getMaxDurability() {
        return MaxDurability;
    }

    public int getDurability() {
        return durability;
    }

    public int getId() {
        return id;
    }
}
