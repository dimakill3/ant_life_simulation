package sample;

import javafx.scene.image.Image;

public enum blocks {
grass(0, "/sample/images/grass.jpg"), anthill(1, "/sample/images/anthill.png"), water(2, "/sample/images/water.jpg"),
    apple(3, "/sample/images/apple_on_grass.png"), plant(4, "/sample/images/plant.png"),
    infected_plant(5, "/sample/images/infected_plant.png"), stick(6, "/sample/images/stick.png"),
    mushrooms(7, "/sample/images/mushrooms.png"),
    ant_worker(8, "/sample/images/ant_worker.png"), ant_worker_with_food(9, "/sample/images/ant_worker_with_food.png"),
    ant_worker_with_material(10, "/sample/images/ant_worker_with_material.png"), ant_fighter(11, "/sample/images/ant_fighter.png");


    public final int number;
    public final Image picture;
    public final int count_spawn_food = 3;
    public final int count_spawn_material = 2;


    blocks(int number, String url){
        this.number = number;
        this.picture = new Image(url, 45, 45, true, false);
    }

}