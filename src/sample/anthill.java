package sample;

import java.awt.*;
import java.util.Vector;

public class anthill extends objects{
    public Vector<ant> ants;
    public Vector<Point> eggs;
    private boolean queen_hungry;
    private boolean queen_dehydration;
    private int ant_capacity;
    private int how_ant;
    private int count_of_eggs;
    private int anthill_level;
    private boolean guard;
    private int Max_milk_farms;
    private int Max_mushrooms_farms;
    public Vector<Point> milk_farms;
    public Vector<Point> mushrooms_farms;
    private int count_food;
    private int count_water;
    private int count_materials;
    private final int ally;
    private int build_step;
    private final int lvlUp_steps = 20;

    public anthill(Point coords, int durability, int id, int ally)
    {
        super(coords, durability, id);
        this.ants = new Vector<>();
        this.eggs = new Vector<>();
        this.anthill_level = 1;
        this.ant_capacity = 7;
        this.how_ant = 0;
        this.count_of_eggs = 0;
        this.guard = false;
        this.count_food = 10;
        this.count_water = 10;
        this.count_materials = 10;
        this.ally = ally;
        this.build_step = 0;
        this.queen_hungry = false;
        this.queen_dehydration = false;
        this.Max_milk_farms = 1;
        this.Max_mushrooms_farms = 1;
        this.milk_farms = new Vector<>();
        this.mushrooms_farms = new Vector<>();
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

    public void IncCount_materials(int count_materials) {
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

    public void DecBuild_step() {
        this.build_step--;
    }

    public void setBuild_step() {
        this.build_step = lvlUp_steps;
    }

    public int getBuild_step() {
        return build_step;
    }

    public int getAnthill_level() {
        return anthill_level;
    }

    public void LevelUp()
    {
        this.anthill_level++;
        ant_capacity += 4;
        if(this.anthill_level == 3) {
            Max_milk_farms++;
            Max_mushrooms_farms++;
        }
    }

    public int getAlly() {
        return ally;
    }

    public int getAnt_capacity() {
        return ant_capacity;
    }

    public boolean isQueen_hungry() {
        return queen_hungry;
    }

    public void setQueen_hungry(boolean queen_hungry) {
        this.queen_hungry = queen_hungry;
    }

    public boolean isQueen_dehydration() {
        return queen_dehydration;
    }

    public void setQueen_dehydration(boolean queen_dehydration) {
        this.queen_dehydration = queen_dehydration;
    }

    public int getHow_ant() {
        return how_ant;
    }

    public void IncHow_ant() {
        this.how_ant++;
    }

    public void DecHow_ant() {
        this.how_ant--;
    }

    public int getMax_milk_farms() {
        return Max_milk_farms;
    }

    public int getMax_mushrooms_farms() {
        return Max_mushrooms_farms;
    }

    public void IncMax_milk_farms() {
        Max_milk_farms++;
    }

    public void IncMax_mushrooms_farms() {
        Max_mushrooms_farms++;
    }
}

