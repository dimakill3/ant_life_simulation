package sample;

import javafx.util.Pair;

public class ant {

    private final int HP_buff = 15;
    private int MaxHealth;
    private int health;
    private int strength;
    private int intellect;
    private int agility;
    private boolean worker;
    private boolean angry;
    private boolean hungry;
    private String action;
    private Pair<Integer, Integer> purpose;
    private Pair<Integer, Integer> coords;
    private boolean dead = false;

    public ant (int health, int strength, int intellect, Pair<Integer, Integer> coords)
    {
        this.strength = strength;
        this.intellect = intellect;
        this.coords = coords;
        this.hungry = false;

        if(intellect > strength)
        {
            this.worker = true;
            health -= HP_buff;

            this.angry = false;
        }
        else
        {
            if(agility > 5)
                this.angry = true;
            else
                this.angry = false;

            worker= false;
            health += HP_buff;
        }

        this.health = health;
        this.MaxHealth = health;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public void setHealth(int health) {
        this.health = health;

        if (this.health > MaxHealth)
            this.health = MaxHealth;

        if(this.health < 0)
            dead = true;
    }

    public int getHealth() {
        return health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getAgility() {
        return agility;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void setHungry(boolean hungry) {
        this.hungry = hungry;
    }

    public boolean isHungry() {
        return hungry;
    }

    public void setPurpose(Pair<Integer, Integer> purpose) {
        this.purpose = purpose;
    }

    public Pair<Integer, Integer> getPurpose() {
        return purpose;
    }
}
