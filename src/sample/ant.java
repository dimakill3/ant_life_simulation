package sample;

import javafx.util.Pair;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Vector;

public class ant {

    private final int HP_buff = 15;
    private int MaxHealth;
    private int health;
    private int strength;
    private int intellect;
    private int agility;
    private int grab;
    private final boolean worker;
    private final boolean angry;
    private boolean hungry;
    private boolean dehydration;
    private String action;
    private Point purpose;
    private Point coords;
    private boolean dead = false;
    public Vector<Point> way;
    private int[][] matrixWay;
    private Point sprite_cut;

    public ant (int health, int strength, int intellect, int agility, Point coords)
    {
        this.strength = strength;
        this.intellect = intellect;
        this.agility = agility;
        this.coords = coords;
        this.grab = 0;
        this.purpose = new Point(-1, -1);
        this.hungry = false;
        this.action = Actions.Wait.toString();
        this.sprite_cut = new Point(0, 0);

        this.way = new Vector<>();
        this.matrixWay = new int[Controller.Scene_blocks][Controller.Scene_blocks];

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

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public Point getCoords() {
        return coords;
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

    public void setPurpose(Point purpose) { this.purpose = purpose; }

    public Point getPurpose() {
        return purpose;
    }

    public boolean isWorker() {
        return worker;
    }

    public boolean isAngry() {
        return angry;
    }

    public void setGrab(int grab) {
        this.grab = grab;
    }

    public int getGrab() {
        return grab;
    }

    public void setSprite_cut(Point sprite_cut) {
        this.sprite_cut = sprite_cut;
    }

    public Point getSprite_cut() {
        return sprite_cut;
    }

    public void setMatrixWay(int[][] matrixWay) {
        this.matrixWay = matrixWay.clone();
        for(int i = 0; i < this.matrixWay.length; i++)
            this.matrixWay[i] = matrixWay[i].clone();
    }

    public void WavePropagation(int item_id) {  // распространение волны
        int markNumber = 0;
        boolean finished = false;

        matrixWay[this.coords.x][this.coords.y] = 0;

        for(int i = 0; i < Controller.Scene_blocks; i++)
            for(int j = 0; j < Controller.Scene_blocks; j++)
                if(matrixWay[i][j] > 999 && matrixWay[i][j] <= 1003 && matrixWay[i][j] != item_id)
                    matrixWay[i][j] = 999;

        do {
            for (int i = 0; i < Controller.Scene_blocks; i++) {
                for (int j = 0; j < Controller.Scene_blocks; j++) {
                    if (matrixWay[i][j] == markNumber) {
                        if (i != Controller.Scene_blocks - 1)
                            if (matrixWay[i + 1][j] == -1) matrixWay[i + 1][j] = markNumber + 1;
                        if (j != Controller.Scene_blocks - 1)
                            if (matrixWay[i][j + 1] == -1) matrixWay[i][j + 1] = markNumber + 1;
                        if (i != 0)
                            if (matrixWay[i - 1][j] == -1) matrixWay[i - 1][j] = markNumber + 1;
                        if (j != 0)
                            if (matrixWay[i][j - 1] == -1) matrixWay[i][j - 1] = markNumber + 1;
                        //по углам
                        if ((i != Controller.Scene_blocks - 1) && (j != Controller.Scene_blocks - 1) && matrixWay[i + 1][j] != 999  && matrixWay[i][j + 1] != 999) //справа снизу
                            if (matrixWay[i + 1][j + 1] == -1) matrixWay[i + 1][j + 1] = markNumber + 1;

                        if (j != Controller.Scene_blocks - 1 && i != 0  && matrixWay[i - 1][j] != 999 && matrixWay[i][j + 1] != 999) //справа сверху
                            if (matrixWay[i - 1][j + 1] == -1) matrixWay[i - 1][j + 1] = markNumber + 1;

                        if (i != 0 && j != 0 && matrixWay[i - 1][j] != 999 && matrixWay[i][j - 1] != 999) //слева сверху
                            if (matrixWay[i - 1][j - 1] == -1) matrixWay[i - 1][j - 1] = markNumber + 1;

                        if (i != Controller.Scene_blocks - 1 && j != 0 &&
                                matrixWay[i + 1][j] != 999 && matrixWay[i][j - 1] != 999) //слева снизу
                            if (matrixWay[i + 1][j - 1] == -1) matrixWay[i + 1][j - 1] = markNumber + 1;
                    }

                    if(matrixWay[i][j] == item_id &&
                            ((i != Controller.Scene_blocks - 1 && matrixWay[i + 1][j] >= 0 && matrixWay[i + 1][j] < 90) ||
                            (j != Controller.Scene_blocks - 1 && matrixWay[i][j + 1] >= 0 && matrixWay[i][j + 1] < 90) ||
                            (i != 0 && matrixWay[i - 1][j] >= 0 && matrixWay[i - 1][j] < 90) ||
                            (j != 0 && matrixWay[i][j - 1] >= 0 && matrixWay[i][j - 1] < 90) ||
                            (i != Controller.Scene_blocks - 1 && j != Controller.Scene_blocks - 1 && matrixWay[i + 1][j + 1] >= 0 && matrixWay[i + 1][j + 1] < 90) ||
                            (i != 0 && j != Controller.Scene_blocks - 1 && matrixWay[i - 1][j + 1] >= 0 && matrixWay[i - 1][j + 1] < 90) ||
                            (i != Controller.Scene_blocks - 1 && j != 0 && matrixWay[i + 1][j - 1] >= 0 && matrixWay[i + 1][j - 1] < 90) ||
                            (i != 0 && j != 0 && matrixWay[i - 1][j - 1] >= 0 && matrixWay[i - 1][j - 1] < 90)))
                    {
                        this.matrixWay[i][j] = markNumber + 2;
                        finished = true;
                        way.clear();
                        Add_Way(i, j, markNumber + 2);
                        break;
                    }
                }
            }
            markNumber++;
        }while (!finished && markNumber < Controller.Scene_blocks* Controller.Scene_blocks);
    }

    public void WavePropagation(Point place) {  // распространение волны
        int markNumber = 0;
        boolean finished = false;

        matrixWay[this.coords.x][this.coords.y] = 0;

        for(int i = 0; i < Controller.Scene_blocks; i++)
            for(int j = 0; j < Controller.Scene_blocks; j++)
                if(place.x != i && place.y != j && matrixWay[i][j] > 999 && matrixWay[i][j] <= 1003)
                    matrixWay[i][j] = 999;

        matrixWay[purpose.x][purpose.y] = -1;

        do {
            for (int i = 0; i < Controller.Scene_blocks; i++) {
                for (int j = 0; j < Controller.Scene_blocks; j++) {
                    if (matrixWay[i][j] == markNumber) {
                        if (i != Controller.Scene_blocks - 1)
                            if (matrixWay[i + 1][j] == -1) matrixWay[i + 1][j] = markNumber + 1;
                        if (j != Controller.Scene_blocks - 1)
                            if (matrixWay[i][j + 1] == -1) matrixWay[i][j + 1] = markNumber + 1;
                        if (i != 0)
                            if (matrixWay[i - 1][j] == -1) matrixWay[i - 1][j] = markNumber + 1;
                        if (j != 0)
                            if (matrixWay[i][j - 1] == -1) matrixWay[i][j - 1] = markNumber + 1;
                        //по углам
                        if ((i != Controller.Scene_blocks - 1) && (j != Controller.Scene_blocks - 1) && matrixWay[i + 1][j] != 999 && matrixWay[i][j + 1] != 999) //справа снизу
                            if (matrixWay[i + 1][j + 1] == -1) matrixWay[i + 1][j + 1] = markNumber + 1;

                        if (j != Controller.Scene_blocks - 1 && i != 0  && matrixWay[i - 1][j] != 999 && matrixWay[i][j + 1] != 999) //справа сверху
                            if (matrixWay[i - 1][j + 1] == -1) matrixWay[i - 1][j + 1] = markNumber + 1;

                        if (i != 0 && j != 0 && matrixWay[i - 1][j] != 999 && matrixWay[i][j - 1] != 999) //слева сверху
                            if (matrixWay[i - 1][j - 1] == -1) matrixWay[i - 1][j - 1] = markNumber + 1;

                        if (i != Controller.Scene_blocks - 1 && j != 0 && matrixWay[i + 1][j] != 999 && matrixWay[i][j - 1] != 999) //слева снизу
                            if (matrixWay[i + 1][j - 1] == -1) matrixWay[i + 1][j - 1] = markNumber + 1;

                       if (matrixWay[this.purpose.x][this.purpose.y] >= 0 && matrixWay[this.purpose.x][this.purpose.y] < 999){
                            finished = true;
                            this.way.clear();
                            Add_Way(this.purpose.x, this.purpose.y, markNumber + 1);
                        }
                    }
                }
            }
            markNumber++;
        }while (!finished && markNumber < Controller.Scene_blocks* Controller.Scene_blocks);
    }

    void Add_Way(int _target_X, int _target_Y, int _markNumber){

        if(matrixWay[_target_X][_target_Y] != 0 && way.size() < _markNumber) {
            way.add(0, new Point(_target_X, _target_Y));

            if (_target_Y < Controller.Scene_blocks - 1) //Справа
                if (matrixWay[_target_X][_target_Y + 1] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X, _target_Y + 1, _markNumber);
                }
            if (_target_Y > 0)//Слева
                if (matrixWay[_target_X][_target_Y - 1] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X, _target_Y - 1, _markNumber);
                }
            if (_target_X < Controller.Scene_blocks - 1)//Сннизу
                if (matrixWay[_target_X + 1][_target_Y] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X + 1, _target_Y, _markNumber);
                }
            if (_target_X > 0) //Сверху
                if (matrixWay[_target_X - 1][_target_Y] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X - 1, _target_Y, _markNumber);
                }
            //по углам
            if (_target_Y < Controller.Scene_blocks - 1 && _target_X < Controller.Scene_blocks - 1 && matrixWay[_target_X + 1][_target_Y] != 999 && matrixWay[_target_X][_target_Y + 1] != 999) //Справа снизу
                if (matrixWay[_target_X + 1][_target_Y + 1] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X + 1, _target_Y + 1, _markNumber);
                }
            if (_target_Y > 0 && _target_X < Controller.Scene_blocks - 1  && matrixWay[_target_X + 1][_target_Y] != 999 && matrixWay[_target_X][_target_Y - 1] != 999)//Слева снизу
                if (matrixWay[_target_X + 1][_target_Y - 1] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X + 1, _target_Y - 1, _markNumber);
                }
            if (_target_Y < Controller.Scene_blocks - 1 && _target_X > 0  && matrixWay[_target_X - 1][_target_Y] != 999 && matrixWay[_target_X][_target_Y + 1] != 999)//Сверху справа
                if (matrixWay[_target_X - 1][_target_Y + 1] == matrixWay[_target_X][_target_Y] - 1) {
                    Add_Way(_target_X - 1, _target_Y + 1, _markNumber);
                }
            if (_target_X > 0 && _target_Y > 0  && matrixWay[_target_X - 1][_target_Y] != 999 && matrixWay[_target_X][_target_Y - 1] != 999) //Сверху слева
                if (matrixWay[_target_X - 1][_target_Y - 1] == matrixWay[_target_X][_target_Y] - 1 ) {
                    Add_Way(_target_X - 1, _target_Y - 1, _markNumber);
                }
        }
    }

    void print_wave(){
        for (int i = 0; i < Controller.Scene_blocks; i++){
            for (int j = 0; j < Controller.Scene_blocks; j++){
                System.out.printf("%3d", matrixWay[i][j]);
            }
            System.out.println();
        }
    }

    void matrix_search(int x, int y)
    {

    }
}
