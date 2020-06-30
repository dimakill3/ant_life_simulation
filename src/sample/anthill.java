package sample;

import javafx.util.Pair;

public class anthill extends objects{
    private int ant_capacity;
    private int how_ant;
    private int count_of_eggs;
    private int anthill_level;
    private boolean guard;
    private int count_food;
    private int count_water;
    private int count_materials;
    private int ally;

    public anthill(Pair<Integer, Integer> coords, int durability, int id, int ally)
    {
        super(durability, id, coords);
        this.anthill_level = 1;
        this.ant_capacity = 7;
        this.how_ant = 0;
        this.count_of_eggs = 0;
        this.guard = false;
        this.count_food = 10;
        this.count_water = 10;
        this.count_materials = 10;
        this.ally = ally;
    }

    public void IncCount_of_eggs() {
        if(this.how_ant + this.count_of_eggs < this.ant_capacity)
        this.count_of_eggs++;
    }

    public void DecCount_of_eggs() {
       if(this.count_of_eggs != 0)
        this.count_of_eggs--;
    }

    public int getCount_of_eggs() {
        return count_of_eggs;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }

    public boolean isGuard() {
        return guard;
    }

    public void IncCount_food(int count_food) {
            this.count_food += count_food;
    }

    public boolean DecCount_food(int count_food) {
        if(this.count_food - count_food >= 0) {
            this.count_food -= count_food;
            return true;
        }
        else
            return false;
    }

    public int getCount_food() {
        return count_food;
    }

    public void IncCount_water(int count_water) {
            this.count_water += count_water;
    }

    public boolean DecCount_water(int count_water) {
        if(this.count_water - count_water >= 0) {
            this.count_water -= count_water;
            return true;
        }
        else
            return false;
    }

    public int getCount_water() {
        return count_water;
    }

    public void setCount_materials(int count_materials) {
            this.count_materials += count_materials;
    }

    public boolean DecCount_materials(int count_materials) {
        if(this.count_materials - count_materials >= 0) {
            this.count_materials -= count_materials;
            return true;
        }
        else
            return false;
    }

    public int getCount_materials() {
        return count_materials;
    }

    public void LevelUp()
    {
        this.anthill_level++;
        ant_capacity += 4;
    }
}

