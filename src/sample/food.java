package sample;

import java.awt.*;

public class food extends objects{

    private boolean culture;
    private int MaxUsable;
    private boolean farmer;
    private int usable;
    private int ally;


    public food(Point coords, int durability, int id, int usable)
    {
        super(coords, durability, id);
        this.culture = false;
        this.MaxUsable = usable;
        this.usable = usable;
        this.farmer = false;
        this.ally = -1;
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
            return false;
        }
        else {
            this.usable = 0;
            return true;
        }
    }

    public void setUsable(int usable) {
        this.usable = usable;
    }

    public int getUsable() {
        return usable;
    }


    public boolean isCulture() {
        return culture;
    }

    public void setCulture(boolean culture) {
        this.culture = culture;
    }

    public int getMaxUsable() {
        return MaxUsable;
    }

    public void setAlly(int ally) {
        this.ally = ally;
    }

    public int getAlly() {
        return ally;
    }
}
