package sample;

import javafx.scene.image.Image;

public enum blocks {
    grass(0, "/sample/images/grass.jpg"), anthill(1, "/sample/images/anthill.png"), water(2, "/sample/images/water.jpg"),
    apple(3, "/sample/images/apple_on_grass.png"), plant(4, "/sample/images/plant.png"),
    infected_plant(5, "/sample/images/infected_plant.png"), stick(6, "/sample/images/stick.png"),
    mushrooms(7, "/sample/images/mushrooms.png"), ant_worker(8, "/sample/images/ant_worker_sprite.png"),
    ant_worker_with_food(9, "/sample/images/ant_worker_with_food_sprite.png"), ant_worker_with_water(10, "/sample/images/ant_worker_with_water_sprite.png"),
    ant_worker_with_material(11, "/sample/images/ant_worker_with_material_sprite.png"), ant_fighter(12, "/sample/images/ant_fighter_sprite.png"),
    anthill_lvl2(13, "/sample/images/anthill_lv2.1.png"), anthill_lvl3(14, "/sample/images/anthill_lv3.1.png"),
    spider(15, "/sample/images/spider_sprite.png");


    public final int number;
    public final Image picture;


    blocks(int number, String url){
        this.number = number;
        if(number == 8 || number == 9 || number == 10 || number == 11 || number == 12 || number == 15)
            this.picture = new Image(url);
        else
        this.picture = new Image(url, Controller.image_width, Controller.image_height, true, false);
    }
}