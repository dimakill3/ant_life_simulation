package sample;

import java.awt.*;
import java.util.Vector;

public class ant extends insect{

    private final int HP_buff = 15;
    private int intellect;
    private int grab;
    private final boolean worker;
    private boolean hungry;
    private boolean dehydration;

    public ant (int health, int strength, int intellect, Point coords)
    {
        super(health, strength, coords);

        this.intellect = intellect;
        this.grab = 0;
        this.hungry = false;

        this.way = new Vector<>();

        if(this.intellect > this.getStrength())
        {
            this.worker = true;
            this.DecMaxHealth(HP_buff);
        }
        else
        {
            worker= false;
            this.IncMaxHealth(HP_buff);
        }
        this.setHealth(this.getMaxHealth());
    }

    public int getIntellect() {
        return intellect;
    }


    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setDehydration(boolean dehydration) {
        this.dehydration = dehydration;
    }

    public boolean isDehydration() {
        return dehydration;
    }

    public boolean isWorker() {
        return worker;
    }

    public void setGrab(int grab) {
        this.grab = grab;
    }

    public int getGrab() {
        return grab;
    }

}
