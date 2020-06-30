package sample;

import javafx.util.Pair;

public class food extends objects{

    private final boolean culture;
    private int MaxUsable;
    private boolean farmer;
    private int usable;


    public food(int durability, int id, Pair<Integer, Integer> coords, boolean culture, int usable)
    {
        super(durability, id, coords);
        this.culture = culture;
        this.MaxUsable = usable;
        this.usable = usable;
        this.farmer = false;
    }

    public void IncUsable(int usable) {
        if(this.usable + usable > MaxUsable)
            this.usable = MaxUsable;
        else
            this.usable += usable;
    }

    public boolean DecUsable(int usable) {
        if(this.usable - usable > 0) {
            this.usable -= usable;
            return true;
        }
        else {
            this.usable = 0;
            return false;
        }
    }

    public int getUsable() {
        return usable;
    }


    public boolean isCulture() {
        return culture;
    }

    public int getMaxUsable() {
        return MaxUsable;
    }

    public void setFarmer(boolean farmer) {
        this.farmer = farmer;
    }

    public boolean isFarmer() {
        return farmer;
    }
}
