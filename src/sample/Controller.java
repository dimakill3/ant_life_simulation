package sample;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;


public class Controller {

    public static int image_width = 45;
    public static int image_height = 45;
    private String img_url = "/sample/images/grass.jpg";
    private Image filling_picture = new Image(img_url, image_width, image_height, true, false);
    private InnerShadow night = new InnerShadow();
    private InnerShadow rain = new InnerShadow();
    private Thread auto_step;
    private Runnable r;

    public final static Integer Scene_blocks = 20;

    private boolean is_building_in_process = false;
    private boolean is_simulation_in_process = false;
    private boolean auto_mod = false;


    private int filling_block = blocks.grass.number;
    private int[][] main_array = new int[Scene_blocks][Scene_blocks];
    private int[][] matrixWays = new int[Scene_blocks][Scene_blocks];

    Vector<Point> Empty_place = new Vector<>();

    private Vector<anthill> anthills = new Vector<>();
    private Vector<food> culture = new Vector<>();
    private Vector<food> neutral_food = new Vector<>();
    private Vector<objects> materials = new Vector<>();
    private Vector<objects> water = new Vector<>();
    private Vector<insect> enemy = new Vector<>();

    private final int empty_place_in_wave_algorithm_id = -1;
    private final int barrier_in_wave_algorithm_id = 999;
    private final int food_in_wave_algorithm_id = 1000;
    private final int water_in_wave_algorithm_id = 1001;
    private final int material_in_wave_algorithm_id = 1002;
    private final int anthill_in_wave_algorithm_id = 1003;
    private final int ant_in_wave_algorithm_id = 1004;
    private final int enemy_in_wave_algorithm_id = 1005;

    private final int min_material_durability = 9; //30
    private final int max_material_durability = 9; //40

    private final int min_water_durability = 9;    //80
    private final int max_water_durability = 9;   //100

    private final int min_food_durability = 2;      //6
    private final int max_food_durability = 2;     //12

    private final int min_food_usable = 9;     //30
    private final int max_food_usable = 9;     //30

    private final int how_step_to_born = 80;

    private final int anthill_durability = 100;

    private final int min_characteristic = 2;
    private final int max_characteristic = 10;
    private final int min_ant_health = 25;
    private final int max_ant_health = 50;

    private int anthill_count = 0;
    private final int MaxAnthillCount = 2;

    private final int day_step = 80;
    private int day = 1;
    private int step = 1;

    private int[] anthill_levelUp_food = {30, 60};
    private int[] anthill_levelUp_materials = {40, 80};
    private int[] anthill_levelUp_water = {20, 40};

    private int begin_count_of_ants = 6;

    private final int Dec_anthill_durability = 5;
    private final int how_materials_need_to_repair = 1;
    private final int Dec_objects_durability = 1;

    private int chance_to_spawn_food = 2;
    private int chance_to_spawn_material = 2;
    private int chance_to_spawn_enemy = 2;
    private int chance_to_spawn_water = 2;

    private final int how_eat_egg_worker = 1;
    private final int how_eat_egg_fighter = 2;

    private boolean is_night;
    private int is_rain;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane main_window;

    @FXML
    private Button start_simulation_button;

    @FXML
    private Button build_map_button;

    @FXML
    private ImageView image_apple;

    @FXML
    private ImageView image_water;

    @FXML
    private ImageView image_grass;

    @FXML
    private ImageView image_anthill;

    @FXML
    private ImageView image_mushrooms;

    @FXML
    private ImageView image_stick;

    @FXML
    private ImageView image_insect_flower;

    @FXML
    private Button apply_map_button;

    @FXML
    private ImageView image_flower;

    @FXML
    private GridPane main_scene;

    @FXML
    private Button exit_simulation_button;

    @FXML
    private Button auto_mode_button;

    @FXML
    private Button step_button;

    @FXML
    private TextArea main_log;

    @FXML
    private ImageView first_print_image;

    @FXML
    private TextArea first_print_log;

    @FXML
    private TextArea second_print_log;

    @FXML
    private ImageView second_print_image;

    @FXML
    private Label day_and_step;

    @FXML
    private ImageView third_print_image;

    @FXML
    private ImageView fourth_print_image;

    @FXML
    private TextArea third_print_log;

    @FXML
    private TextArea fourth_print_log;

    @FXML
    void ChooseApple(MouseEvent event) {
        filling_picture = blocks.apple.picture;
        filling_block = blocks.apple.number;
    }

    @FXML
    void ChooseWater(MouseEvent event) {
        filling_picture = blocks.water.picture;
        filling_block = blocks.water.number;
    }

    @FXML
    void ChooseAnthill(MouseEvent event) {
        filling_picture = blocks.anthill.picture;
        filling_block = blocks.anthill.number;
    }

    @FXML
    void ChooseFlower(MouseEvent event) {
        filling_picture = blocks.plant.picture;
        filling_block = blocks.plant.number;
    }

    @FXML
    void ChooseGrass(MouseEvent event) {
        filling_picture = blocks.grass.picture;
        filling_block = blocks.grass.number;
    }

    @FXML
    void ChooseStick(MouseEvent event) {
        filling_picture = blocks.stick.picture;
        filling_block = blocks.stick.number;
    }

    @FXML
    void ChooseMushrooms(MouseEvent event) {
        filling_picture = blocks.mushrooms.picture;
        filling_block = blocks.mushrooms.number;
    }

    @FXML
    void ChooseInsectFlower(MouseEvent event) {
        filling_picture = blocks.infected_plant.picture;
        filling_block = blocks.infected_plant.number;
    }

    @FXML
    void CellClick(MouseEvent event) {
        if(is_building_in_process) {
            change_picture();
        }
        else
        {
            if(is_simulation_in_process)
            {
                show_block_info();
            }
        }
    }

    @FXML
    void Begin_Changes_Button_Click(ActionEvent event) {
        start_simulation_button.setVisible(false);
        build_map_button.setVisible(false);

        image_stick.setVisible(true);
        image_anthill.setVisible(true);
        image_apple.setVisible(true);
        image_flower.setVisible(true);
        image_grass.setVisible(true);
        image_insect_flower.setVisible(true);
        image_water.setVisible(true);
        image_mushrooms.setVisible(true);
        apply_map_button.setVisible(true);

        is_building_in_process = true;

    }

    @FXML
    void Apply_Changes_Button_Click(ActionEvent event) {
        start_simulation_button.setVisible(true);
        build_map_button.setVisible(true);

        image_stick.setVisible(false);
        image_anthill.setVisible(false);
        image_apple.setVisible(false);
        image_flower.setVisible(false);
        image_grass.setVisible(false);
        image_insect_flower.setVisible(false);
        image_water.setVisible(false);
        image_mushrooms.setVisible(false);
        apply_map_button.setVisible(false);

        is_building_in_process = false;
    }

    @FXML
    void Start_Simulation_Button_Click(ActionEvent event) {
        boolean got_anthill = false;
        for(int i = 0; i < Scene_blocks; i++)
            for(int j = 0; j < Scene_blocks; j++)
                if(main_array[i][j] == blocks.anthill.number)
                {
                    got_anthill = true;
                    break;
                }

        if(!got_anthill)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Симуляция невозможна без муравейника");
            error.showAndWait();
            return;
        }

        start_simulation_button.setVisible(false);
        build_map_button.setVisible(false);

        exit_simulation_button.setVisible(true);
        step_button.setVisible(true);
        auto_mode_button.setVisible(true);

        is_simulation_in_process = true;
        int anthill_index = 0;

        for(int i = 0; i < Scene_blocks; i++)
            for(int j = 0; j < Scene_blocks; j++)
            {
                if(main_array[i][j] == blocks.anthill.number)
                {
                        anthills.add(new anthill(new Point(i, j), anthill_durability, main_array[i][j], anthill_index));

                        for(int k = 0; k < begin_count_of_ants; k++) {
                            add_new_ant(anthills.get(anthill_index));
                        }

                        matrixWays[i][j] = anthill_in_wave_algorithm_id;
                    anthill_index++;
                }
                if(main_array[i][j] == blocks.grass.number) {
                    Empty_place.add(new Point(i, j));
                    matrixWays[i][j] = empty_place_in_wave_algorithm_id;
                }

                if(main_array[i][j] == blocks.apple.number || main_array[i][j] == blocks.infected_plant.number || main_array[i][j] == blocks.mushrooms.number)
                {
                    neutral_food.add(new food(new Point(i, j), randomize(min_food_durability, max_food_durability),
                            main_array[i][j], randomize(min_food_usable, max_food_usable)));
                    matrixWays[i][j] = food_in_wave_algorithm_id;
                }
                if(main_array[i][j] == blocks.water.number)
                {
                    water.add(new objects(new Point(i, j), randomize(min_water_durability, max_water_durability), main_array[i][j]));
                    matrixWays[i][j] = water_in_wave_algorithm_id;
                }
                if(main_array[i][j] == blocks.plant.number || main_array[i][j] == blocks.stick.number)
                {
                    materials.add(new objects(new Point(i, j), randomize(min_material_durability, max_material_durability), main_array[i][j]));
                    matrixWays[i][j] = material_in_wave_algorithm_id;
                }
            }

            day = 1;
            step = 1;
            day_and_step.setText("День " + day + " Шаг " + step);
            is_rain = 0;
            is_night = false;
    }

    @FXML
    void ExitSimulationButtonClick(MouseEvent event) {
        start_simulation_button.setVisible(true);
        build_map_button.setVisible(true);

        exit_simulation_button.setVisible(false);
        step_button.setVisible(false);
        auto_mode_button.setVisible(false);
        main_log.clear();
        clear_logs();
        day_and_step.setText("");
    }

    @FXML
    void SetSimulationAutoMod(MouseEvent event) {
        /*if(auto_mod) {
            auto_mod = false;
        }
        else {
            auto_mod = true;
            auto_step = new Thread(t, "MyThread");
            auto_step.start();
        }*/
    }

    @FXML
    void StepButtonClick(MouseEvent event) {
        Step();
        clear_logs();
        if(anthills.size() == 0)
        {
            Alert error = new Alert(Alert.AlertType.ERROR, "Муравейников больше нет. Симуляция закончена!");
            error.showAndWait();
            ExitSimulationButtonClick(event);
        }
    }

    @FXML
    void initialize() {
        night.setChoke(0);
        night.setRadius(100);
        night.setBlurType(BlurType.THREE_PASS_BOX);
        night.setColor(Color.BLACK);

        rain.setColor(Color.BLUE);
        rain.setChoke(0);
        rain.setRadius(60);
        rain.setBlurType(BlurType.THREE_PASS_BOX);

        for (int x = 0; x < Scene_blocks; x++) {
            for (int y = 0; y < Scene_blocks; y++) {
                main_scene.add(new ImageView(filling_picture), y, x);
            }
        }



       /* r = ()->{
    do
    {
        if(auto_mod)	//Проверка на необходимость завершения
        {
            Step();
        }
        else
            return;		//Завершение потока

        try{
            Thread.sleep(1000);		//Приостановка потока на 1 сек.
        }catch(InterruptedException e){}
    }
    while(true);
};*/

    }

    void change_picture()
    {
            main_scene.getChildren().forEach(element -> {
                element.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(is_building_in_process) {
                            int matrixX = GridPane.getRowIndex(element);
                            int matrixY = GridPane.getColumnIndex(element);

                            if(main_array[matrixX][matrixY] == blocks.anthill.number)
                                anthill_count--;

                            if(filling_block == blocks.anthill.number) {
                                if(anthill_count + 1 <= MaxAnthillCount)
                                        anthill_count++;
                                else{
                                        Alert error = new Alert(Alert.AlertType.ERROR, "Нельзя поставить более двух муравейников");
                                        error.showAndWait();
                                        return;
                                 }
                            }

                            main_scene.getChildren().remove(element);

                            main_array[matrixX][matrixY] = filling_block;
                            main_scene.add(new ImageView(filling_picture), matrixY, matrixX);
                        }
                    }
                });
            });
    }

    void show_block_info()
    {
        main_scene.getChildren().forEach(element -> {
            element.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(is_simulation_in_process) {
                        int matrixX = GridPane.getRowIndex(element);
                        int matrixY = GridPane.getColumnIndex(element);

                        clear_logs();

                        for(anthill anthill : anthills)
                        {
                            if(anthill.getCoords().x == matrixX && anthill.getCoords().y == matrixY)
                            {
                                print_out_anthill(anthill, first_print_log, first_print_image);
                                return;
                            }

                            for(ant ant : anthill.ants)
                                if(ant.getCoords().x == matrixX && ant.getCoords().y == matrixY) {
                                    print_out_ant(ant, first_print_log, first_print_image);
                                    return;
                                }

                                for(food food : anthill.farms)
                                    if(food.getCoords().x == matrixX && food.getCoords().y == matrixY) {
                                        print_out_food(food, first_print_log, first_print_image);
                                        return;
                                    }

                        }

                        for(objects object : water)
                            if(object.getCoords().x == matrixX && object.getCoords().y == matrixY) {
                                print_out_objects(object, first_print_log, first_print_image);
                                return;
                            }

                        for(objects object : materials)
                            if(object.getCoords().x == matrixX && object.getCoords().y == matrixY) {
                                print_out_objects(object, first_print_log, first_print_image);
                                return;
                            }

                        for(food food : neutral_food)
                            if(food.getCoords().x == matrixX && food.getCoords().y == matrixY) {
                                print_out_food(food, first_print_log, first_print_image);
                                return;
                            }

                        for(Point point : Empty_place)
                            if(point.x == matrixX && point.y == matrixY)
                            {
                                first_print_log.setVisible(true);
                                first_print_image.setVisible(true);
                                first_print_image.setImage(blocks.grass.picture);
                                first_print_image.setViewport(null);
                                first_print_log.appendText("Трава\n");
                                return;
                            }

                    }
                }
            });
        });
    }

    private void clear_logs() {
        first_print_image.setVisible(false);
        second_print_image.setVisible(false);
        third_print_image.setVisible(false);
        fourth_print_image.setVisible(false);
        first_print_image.setImage(null);
        second_print_image.setImage(null);
        third_print_image.setImage(null);
        fourth_print_image.setImage(null);

        first_print_log.setVisible(false);
        second_print_log.setVisible(false);
        third_print_log.setVisible(false);
        fourth_print_log.setVisible(false);
        first_print_log.clear();
        second_print_log.clear();
        third_print_log.clear();
        fourth_print_log.clear();
    }

    void Step()
    {
        int for_random = 0;

        //Для каждого объекта, который может заспавниться, есть шанс спавна
        //яблоко
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < -1) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
                //matrixWays[coords.x][coords.y] = food_in_wave_algorithm_id;
                neutral_food.add(new food(coords, randomize(min_food_durability, max_food_durability), blocks.apple.number, randomize(min_food_usable, max_food_usable)));
                filling_block = blocks.apple.number;
                filling_picture = blocks.apple.picture;
                //main_array[coords.x][coords.y] = filling_block;

                set_image(coords, new Point(0,0), filling_block, food_in_wave_algorithm_id);
            }
        }

        //гриб
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < -1) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
                //matrixWays[coords.x][coords.y] = food_in_wave_algorithm_id;
                neutral_food.add(new food(coords, randomize(min_food_durability, max_food_durability), blocks.mushrooms.number, randomize(min_food_usable, max_food_usable)));
                filling_block = blocks.mushrooms.number;
                filling_picture = blocks.mushrooms.picture;

                set_image(coords, new Point(0,0), filling_block, food_in_wave_algorithm_id);
            }
        }

        //растение
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < -1) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
               // matrixWays[coords.x][coords.y] = material_in_wave_algorithm_id;
                materials.add(new objects(coords, randomize(min_material_durability, max_material_durability) , blocks.plant.number));
                filling_block = blocks.plant.number;
                filling_picture = blocks.plant.picture;

                set_image(coords, new Point(0,0), filling_block, material_in_wave_algorithm_id);
            }
        }

        //палочка
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < -1) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
                //matrixWays[coords.x][coords.y] = material_in_wave_algorithm_id;
                materials.add(new objects(coords, randomize(min_material_durability, max_material_durability) , blocks.stick.number));
                filling_block = blocks.stick.number;
                filling_picture = blocks.stick.picture;

                set_image(coords, new Point(0,0), filling_block, material_in_wave_algorithm_id);
            }
        }

        //лужа
        if(is_rain != 0 && Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < -1) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
                //matrixWays[coords.x][coords.y] = water_in_wave_algorithm_id;
                water.add(new objects(coords, randomize(min_water_durability, max_water_durability) , blocks.water.number));
                filling_block = blocks.water.number;
                filling_picture = blocks.water.picture;

                set_image(coords, new Point(0,0), filling_block, water_in_wave_algorithm_id);
            }
        }

        for (anthill anthill : anthills) {
            //+1 Матка
            int ant_count = anthill.getHow_ant() + 1;

            //Считаем, сколько определённых ресурсов нужно на данный момент, чтобы муравейник функционировал нормально
            //Еда нужна, чтобы прокормить всех муравьёв в конце дня, и два раза за день накормить яйца (иначе они умрут)
            int day_food_need = anthill.ants.size() + 1;
            //Вода нужна, чтобы напоить всех муравьёв и для починки муравейника
            int day_water_need = anthill.ants.size() + 1 + (anthill_durability - anthill.getDurability()) / Dec_anthill_durability + how_materials_need_to_repair * 2;
            //Материалы нужны для починки муравейника
            int day_materials_need = (anthill_durability - anthill.getDurability()) / Dec_anthill_durability + how_materials_need_to_repair * 2;

            for (Point point : anthill.eggs) {
                day_food_need += point.y * 2;
            }


            //Если достаточно материалов, еды и воды, при этом другое улучшение закончено и муравейник не максимального уровня, то начинается улучшение
            //При этом улучшение требует муравья, который будет заниматься строительством (сам процесс строительства описан при распределении работы между муравьями)
            if ((anthill.getBuild_step() == 0) && (anthill.getAnthill_level() != 3) &&
                    (anthill.getCount_food() >= anthill_levelUp_food[anthill.getAnthill_level() - 1] + day_food_need * 3) &&
                    (anthill.getCount_water() >= anthill_levelUp_water[anthill.getAnthill_level() - 1] + day_water_need * 3) &&
                    (anthill.getCount_materials() >= anthill_levelUp_materials[anthill.getAnthill_level() - 1] + day_materials_need * 3)) {
                anthill.DecCount_food(anthill_levelUp_food[anthill.getAnthill_level() - 1]);
                anthill.DecCount_water(anthill_levelUp_food[anthill.getAnthill_level() - 1]);
                anthill.DecCount_materials(anthill_levelUp_food[anthill.getAnthill_level() - 1]);
                anthill.setBuild_step();
            }

            //Если есть место для ещё одного муравья и еды хватит всем муравьям(учитывая матку и новорождённое яйцо) на 2 дня, то матка может родить яйцо
            //Иначе, если еды не хватает, то матка создаёт яйцо, чтобы его съесть, при этом матка должна быть сытая. Так она может делать каждые пол дня (если сытая)
            //Так как это считается канибаллизмом, то это делается только при вынужденных мерах
            if (!(anthill.isQueen_hungry() || anthill.isQueen_dehydration()))
            {
                    if(ant_count - 1 < anthill.getAnt_capacity() && anthill.getCount_food() >= (day_food_need * 2 + 2) &&
                    anthill.getCount_water() >= day_water_need) {

                if(anthill.getHow_ant_workers() < 4 + (anthill.getAnthill_level() - 1) * 2) {
                    anthill.eggs.add(new Point(how_step_to_born, how_eat_egg_worker));
                    anthill.IncHow_ant_workers();
                }
                else
                    anthill.eggs.add(new Point(how_step_to_born, how_eat_egg_fighter));

                anthill.IncHow_ant();
                anthill.setQueen_hungry(true);
            } else {
                if (anthill.getCount_food() < day_food_need || anthill.getCount_water() < day_water_need) {
                    anthill.IncCount_food(2);
                    anthill.IncCount_water(2);
                    anthill.setQueen_hungry(true);

                }
            }
            }


            //Узнаём, какой муравей чем занят, чтобы свободные муравьи делали полезную работу(чтобы не было так, что все муравьи, например, носят еду)
            Point[] works = new Point[5];
            int ant_on_food_id = 1;
            int ant_on_water_id = 2;
            int ant_on_material_id = 3;
            int ant_on_build_id = 4;
            int ant_on_repair_id = 5;
            int how_food_grab = 0;
            int how_water_grab = 0;
            int how_material_grab = 0;
            boolean is_anyone_guardian = false;
            works[0] = new Point(ant_on_food_id, 0);
            works[1] = new Point(ant_on_water_id, 0);
            works[2] = new Point(ant_on_material_id, 0);
            works[3] = new Point(ant_on_build_id, 0);
            works[4] = new Point(ant_on_repair_id, 0);

            //Узнаём кто чем занимается
            for (int ant = 0; ant < anthill.ants.size(); ant++) {
                if (anthill.ants.get(ant).getAction().equals(Actions.GoToFood.toString()) || anthill.ants.get(ant).getAction().equals(Actions.CarryFood.toString())) {
                    works[0].y++;
                    how_food_grab += ant_grab_object(anthill.ants.get(ant));
                }
                if (anthill.ants.get(ant).getAction().equals(Actions.GoToWater.toString()) || anthill.ants.get(ant).getAction().equals(Actions.CarryWater.toString())) {
                    works[1].y++;
                    how_water_grab += ant_grab_object(anthill.ants.get(ant));
                }
                if (anthill.ants.get(ant).getAction().equals(Actions.GoToMaterial.toString()) || anthill.ants.get(ant).getAction().equals(Actions.CarryMaterial.toString())) {
                    works[2].y++;
                    how_material_grab += ant_grab_object(anthill.ants.get(ant));
                }
                if (anthill.ants.get(ant).getAction().equals(Actions.Build.toString())) {
                    works[3].y++;
                    how_food_grab += ant_grab_object(anthill.ants.get(ant));
                }
                if (anthill.ants.get(ant).getAction().equals(Actions.Repair.toString())) {
                    works[4].y++;

                }
                if (anthill.ants.get(ant).getAction().equals(Actions.PatrolEggs.toString()))
                    is_anyone_guardian = true;
            }

            //Планируем действия муравьёв
            for (int ant = 0; ant < anthill.ants.size(); ant++) {

                //Если муравей - рабочий, то он будет либо таскать пищу, воду, материалы, разводить тлю, или строить и ремонтировать муравейник
                if (anthill.ants.get(ant).isWorker()) {
                    //Если муравей ожидает, то даём ему работу
                    if (anthill.ants.get(ant).getAction().equals(Actions.Wait.toString())) {

                        //Сначала проверяем, есть ли вообще чем заняться муравью
                        if (neutral_food.size() == 0 && culture.size() == 0 && water.size() == 0 &&
                                materials.size() == 0 && (anthill.getDurability() == anthill_durability || works[4].y == 1)
                                && (anthill.getBuild_step() == 0 || works[3].y == 1))
                            continue;

                        if(anthill.getCount_food() < day_food_need - how_food_grab && neutral_food.size() != 0 || culture.size() != 0)
                        {
                            anthill.ants.get(ant).setAction(Actions.GoToFood.toString());
                            how_food_grab += ant_grab_object(anthill.ants.get(ant));
                        }
                        else
                            if(anthill.getCount_water() < day_water_need - how_water_grab && water.size() != 0)
                            {
                                anthill.ants.get(ant).setAction(Actions.GoToWater.toString());
                                how_water_grab += ant_grab_object(anthill.ants.get(ant));
                            }
                            else
                                if(anthill.getCount_materials() < day_materials_need - how_material_grab && materials.size() != 0) {
                                    anthill.ants.get(ant).setAction(Actions.GoToMaterial.toString());
                                    how_material_grab += ant_grab_object(anthill.ants.get(ant));
                                }
                                else
                                    if(anthill.getDurability() < 15 && anthill.getCount_materials() != 0 && anthill.getCount_water() != 0) {
                                        for (Point work : works) {
                                            if(work.x == ant_on_repair_id && work.y == 0) {
                                                anthill.ants.get(ant).setAction(Actions.Repair.toString());
                                                work.y++;
                                            }
                                        }
                                    }

                                    if(anthill.ants.get(ant).getAction().equals(Actions.Wait.toString())) {
                                        bubbleSort(works);

                                        //Проверяем работу, на которой меньше всего муравьёв
                                        for (Point work : works) {
                                            //Если это поиск еды...
                                            if (work.x == ant_on_food_id) {
                                                //Проверяем есть ли вообще какая-нибудь еда на карте
                                                if (neutral_food.size() != 0 || culture.size() != 0) {
                                                    anthill.ants.get(ant).setAction(Actions.GoToFood.toString());
                                                    work.y++;
                                                    //Мы дали муравью работу, можно дальше не искать
                                                    break;
                                                } else
                                                    //Если еды нет и мы не вышли из поиска работы, значит работа для муравья есть, продолжаем искать
                                                    continue;
                                            }
                                            //Если это поиск воды...
                                            if (work.x == ant_on_water_id) {
                                                //Если вода на карте есть, то отправляем муравья добывать воду
                                                if (water.size() != 0) {
                                                    anthill.ants.get(ant).setAction(Actions.GoToWater.toString());
                                                    work.y++;
                                                    //Если отправили муравья на работу, то завершаем поиск работы
                                                    break;
                                                } else
                                                    //Иначе смотрим следующую работу
                                                    continue;
                                            }
                                            //Если это поиск материалов...
                                            if (work.x == ant_on_material_id) {
                                                //Если на карте есть материалы, то отправляем муравья добывать их
                                                if (materials.size() != 0) {
                                                    anthill.ants.get(ant).setAction(Actions.GoToMaterial.toString());
                                                    work.y++;
                                                    //Если отправили муравья на работу, то завершаем поиск работы
                                                    break;
                                                } else
                                                    //Иначе смотрим следующую работу
                                                    continue;
                                            }
                                            //Если это строительство...
                                            if (work.x == ant_on_build_id) {
                                                //Если муравейник нуждается в улучшении и его ещё никто не улучшает, то отправляем муравья работать
                                                if (anthill.getBuild_step() != 0 && work.y == 0) {
                                                    anthill.ants.get(ant).setAction(Actions.Build.toString());
                                                    work.y++;
                                                    //Если отправили муравья на работу, то завершаем поиск работы
                                                    break;
                                                } else
                                                    //Иначе смотрим следующую работу
                                                    continue;
                                            }
                                            //Если это починка...
                                            if (work.x == ant_on_repair_id) {
                                                //Если муравейник нуждается в починке и его ещё никто не чинит, то отправляем муравья работать
                                                if (anthill.getDurability() != anthill_durability && work.y == 0 &&
                                                        anthill.getCount_materials() != 0 && anthill.getCount_water() != 0) {
                                                    anthill.ants.get(ant).setAction(Actions.Repair.toString());
                                                    work.y++;
                                                    //Если отправили муравья на работу, то завершаем поиск работы
                                                    break;
                                                } else
                                                    continue;
                                            }
                                        }
                                    }


                    if(anthill.ants.get(ant).getAction().equals(Actions.GoToFood.toString()))
                    {
                        //Муравей идёт собирать культуру, если они есть и есть урожай. Если нет, то ищем близжайшую еду
                        if (anthill.farms.size() != 0) {
                            //Для начала выбирается культура с наибольшимколичеством еды. Сделано это для того, чтобы достичь эффективности культур
                            //То есть чтобы все они производили еду (если бы муравей собирал только одну культуру,
                            // то другая была бы переполнена урожаем и не могла производить еду)
                            int[] sort_cult = new int[4];

                            for (int k = 0; k < anthill.farms.size(); k++) {
                                sort_cult[k] = anthill.farms.get(k).getUsable();
                            }

                            Arrays.sort(sort_cult);
                            int index = -1;
                            for (int k = 0; k < anthill.farms.size(); k++) {
                                if (sort_cult[0] == anthill.farms.get(k).getUsable()) {
                                    index = k;
                                    break;
                                }
                            }

                            if (index != -1) {
                                //Если культура скоро перестанет плодоносить и исчезнет, то нужно собрать весь оставшийся урожай
                                if (anthill.farms.get(index).getDurability() == 1) {
                                    if (anthill.farms.get(index).getUsable() != 0) {
                                        //Отправляем муравья собирать последние урожаи с культуры
                                        anthill.ants.get(ant).setPurpose(anthill.farms.get(index).getCoords());
                                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                                        anthill.ants.get(ant).WavePropagation(anthill.farms.get(index).getCoords());
                                        anthill.ants.get(ant).setAction(Actions.GoToFarmFood.toString());

                                    }
                                } else {
                                    //Если количество еды, которое может унести муравей больше или равно количеству урожая, то муравей идёт собирать
                                    if (anthill.farms.get(index).getUsable() - ant_grab_object(anthill.ants.get(ant)) >= 0) {
                                        //Отправляем собирать урожай с культуры
                                        anthill.ants.get(ant).setPurpose(anthill.farms.get(index).getCoords());
                                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                                        anthill.ants.get(ant).WavePropagation(anthill.farms.get(index).getCoords());
                                        anthill.ants.get(ant).setAction(Actions.GoToFarmFood.toString());
                                    }
                                }
                            }
                        }

                        //Если культур нет или нет урожая, то муравей идёт собирать "нейтральную еду"
                        if (anthill.ants.get(ant).getAction().equals(Actions.GoToFood.toString())) {
                            anthill.ants.get(ant).setMatrixWay(matrixWays);
                            anthill.ants.get(ant).WavePropagation(food_in_wave_algorithm_id);
                            anthill.ants.get(ant).setPurpose(anthill.ants.get(ant).way.lastElement());
                        }
                    }
                    //Если м идёт за водой - Строим путь до ближайшей воды
                    if(anthill.ants.get(ant).getAction().equals(Actions.GoToWater.toString()))
                    {
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(water_in_wave_algorithm_id);
                        anthill.ants.get(ant).setPurpose(anthill.ants.get(ant).way.lastElement());
                    }
                    //Если м идёт за материалами - Строим путь до ближайших материалов
                    if(anthill.ants.get(ant).getAction().equals(Actions.GoToMaterial.toString()))
                    {
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(material_in_wave_algorithm_id);
                        anthill.ants.get(ant).setPurpose(anthill.ants.get(ant).way.lastElement());
                    }
                    //Если м идёт строить муравейник - Строим путь до муравейника
                    if(anthill.ants.get(ant).getAction().equals(Actions.Build.toString()))
                    {
                        anthill.ants.get(ant).setPurpose(anthill.getCoords());
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(anthill.getCoords());
                        if (anthill.ants.get(ant).way.size() == 0)
                            anthill.ants.get(ant).way.add(anthill.getCoords());
                    }
                    //Если м идёт чинить муравейник - Строим путь до муравейника
                    if(anthill.ants.get(ant).getAction().equals(Actions.Repair.toString()))
                    {
                        anthill.ants.get(ant).setPurpose(anthill.getCoords());
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(anthill.getCoords());
                        if (anthill.ants.get(ant).way.size() == 0)
                            anthill.ants.get(ant).way.add(anthill.getCoords());
                    }
                    //Если м не нашлось работы - Строим путь до муравейника (идёт отдыхать в муравейник до появления работы)
                    if(anthill.ants.get(ant).getAction().equals(Actions.Wait.toString()))
                    {
                        anthill.ants.get(ant).setPurpose(anthill.getCoords());
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(anthill.getCoords());
                    }

                        //Ход муравья
                        ant_worker_step(anthill.ants.get(ant), anthill);
                    continue;
                    }

                    if (!anthill.ants.get(ant).getAction().equals(Actions.Wait.toString())) {

                        //Если то, что муравей шёл делать уже сделано, или вещь, к которой он шёл, сломалась, то ему заново ищется работа
                        if(main_array[anthill.ants.get(ant).getPurpose().x][anthill.ants.get(ant).getPurpose().y] == blocks.grass.number)
                        {
                            anthill.ants.get(ant).setAction(Actions.Wait.toString());
                            anthill.ants.get(ant).way.clear();
                            anthill.ants.get(ant).setPurpose(new Point(-1, -1));
                            anthill.ants.add(anthill.ants.get(ant));
                            anthill.ants.remove(ant);
                            ant--;
                            continue;
                        }

                            anthill.ants.get(ant).setMatrixWay(matrixWays);
                        //Если муравей шёл за ресурсами, и в это время появился ресурс того же типа, который располагается к нему ближе, чем тот, к которому он шёл
                        //то строится новый путь до него. А так же если на пути появляется препятствие, то муравей прокладывает новый путь
                        if (true) {
                            if (anthill.ants.get(ant).getAction().equals(Actions.GoToFood.toString()))
                                anthill.ants.get(ant).WavePropagation(food_in_wave_algorithm_id);

                            if (anthill.ants.get(ant).getAction().equals(Actions.GoToWater.toString()))
                                anthill.ants.get(ant).WavePropagation(water_in_wave_algorithm_id);

                            if (anthill.ants.get(ant).getAction().equals(Actions.GoToMaterial.toString()))
                                anthill.ants.get(ant).WavePropagation(material_in_wave_algorithm_id);

                            if(anthill.ants.get(ant).getAction().equals(Actions.GoToFarmFood.toString()) ||
                                    anthill.ants.get(ant).getAction().equals(Actions.CarryMushrooms.toString()) ||
                                    anthill.ants.get(ant).getAction().equals(Actions.CarryInfectedPlant.toString()))
                                anthill.ants.get(ant).WavePropagation(anthill.ants.get(ant).getPurpose());

                            anthill.ants.get(ant).setPurpose(anthill.ants.get(ant).way.lastElement());
                        }

                        //Если на пути к муравейнику появляется препятствие, то муравей прокладывает новый путь
                        if (anthill.ants.get(ant).getAction().equals(Actions.Repair.toString()) ||
                                anthill.ants.get(ant).getAction().equals(Actions.Build.toString()) ||
                                anthill.ants.get(ant).getAction().equals(Actions.CarryFood.toString()) ||
                                anthill.ants.get(ant).getAction().equals(Actions.CarryWater.toString()) ||
                                anthill.ants.get(ant).getAction().equals(Actions.CarryMaterial.toString())) {
                            anthill.ants.get(ant).WavePropagation(anthill.getCoords());
                        }

                        if((anthill.ants.get(ant).getAction().equals(Actions.Repair.toString()) ||
                                anthill.ants.get(ant).getAction().equals(Actions.Build.toString())) && anthill.ants.get(ant).way.size() == 0)
                        {
                            anthill.ants.get(ant).way.add(anthill.getCoords());
                        }
                        //Ход муравья
                        ant_worker_step(anthill.ants.get(ant), anthill);
                    }
                } else {
                    //Если муравей - воин, то он будет либо охранять яйца, либо патрулировать муравейник, защищая его от вражеских насекомых
                    if (enemy.size() != 0) {
                        anthill.ants.get(ant).setMatrixWay(matrixWays);
                        anthill.ants.get(ant).WavePropagation(enemy_in_wave_algorithm_id);
                        //если враг в радиусе 3-х клеток от воина, то он идёт в нападение
                        if (anthill.ants.get(ant).way.size() <= 3) {
                            if (anthill.ants.get(ant).getAction().equals(Actions.PatrolEggs.toString()))
                                anthill.setGuard(false);

                            anthill.ants.get(ant).setAction(Actions.Attack.toString());
                            anthill.ants.get(ant).setPurpose(anthill.ants.get(ant).way.lastElement());
                        } else {
                            anthill.ants.get(ant).setAction(Actions.Wait.toString());
                            for (insect enemy : enemy)
                                if (enemy.getAction().equals(Actions.BreakAnthill.toString())) {
                                    anthill.ants.get(ant).setAction(Actions.Attack.toString());
                                    anthill.ants.get(ant).setMatrixWay(matrixWays);
                                    anthill.ants.get(ant).setPurpose(enemy.getCoords());
                                    anthill.ants.get(ant).WavePropagation(anthill.ants.get(ant).getPurpose());
                                }

                        }
                    }

                    //Если меравей ожидает, то если нужен охранник яиц, то муравей идёт защищать яйца
                    //Иначе он идёт патрулировать около муравейника
                    if (anthill.ants.get(ant).getAction().equals(Actions.Wait.toString())) {
                        if (!anthill.isGuard() && !is_anyone_guardian) {
                            anthill.ants.get(ant).setMatrixWay(matrixWays);
                            anthill.ants.get(ant).setPurpose(anthill.getCoords());
                            anthill.ants.get(ant).WavePropagation(anthill.getCoords());
                            anthill.ants.get(ant).setAction(Actions.PatrolEggs.toString());
                            is_anyone_guardian = true;
                        } else {
                            anthill.ants.get(ant).setMatrixWay(matrixWays);
                            Vector<Point> where_to_step = new Vector<>();
                            for (int t = 0; t < Scene_blocks; t++)
                                for (int k = 0; k < Scene_blocks; k++)
                                    if (main_array[t][k] == blocks.grass.number &&
                                            ((t == anthill.ants.get(ant).getCoords().x + 1) || (t == anthill.ants.get(ant).getCoords().x - 1)) &&
                                            ((k == anthill.ants.get(ant).getCoords().y + 1) || (k == anthill.ants.get(ant).getCoords().y - 1)) &&
                                            ((t >= anthill.getCoords().x - 3) && (t <= anthill.getCoords().x + 3)) &&
                                            ((k >= anthill.getCoords().y - 3) || (k <= anthill.getCoords().y + 3)))
                                        where_to_step.add(new Point(t, k));

                            anthill.ants.get(ant).setPurpose(where_to_step.get(randomize(0, where_to_step.size())));
                            anthill.ants.get(ant).WavePropagation(anthill.ants.get(ant).getPurpose());
                            anthill.ants.get(ant).setAction(Actions.PatrolAnthill.toString());
                        }
                    }
                    ant_fighter_step(anthill.ants.get(ant), anthill);
                }
            }

        }
        //Прибавляем шаг
        step++;
        for (anthill anthill : anthills) {
            for (int egg = 0; egg < anthill.eggs.size(); egg++) {
                anthill.eggs.get(egg).x--;
                if(anthill.eggs.get(egg).x == 0)
                {
                    main_log.appendText("Шаг " + step + ": " + "Родился муравей у команды " + anthill.getAlly() + "\n");
                    int intellect;
                    int strength;
                    //Рождается рабочий
                    if(anthill.eggs.get(egg).y == 1)
                    {
                        do {
                            intellect = randomize(min_characteristic, max_characteristic);
                            strength = randomize(min_characteristic, max_characteristic);

                        }while(intellect <= strength);

                    }
                    //Рождается воин
                    else
                    {
                        do {
                            intellect = randomize(min_characteristic, max_characteristic);
                            strength = randomize(min_characteristic, max_characteristic);

                        }while(intellect > strength);

                    }

                    anthill.ants.add(new ant(randomize(min_ant_health, max_ant_health), strength, intellect,
                            randomize(min_characteristic, max_characteristic), anthill.getCoords()));

                    anthill.eggs.remove(egg);
                    egg--;
                }
            }

            for (sample.food food : anthill.farms) food.IncUsable(1);
        }

        //Если прошла треть дня, то наступает ночь
        if(step == (day_step - (day_step / 3) + 1)) {
            is_night = true;
            main_log.appendText("Шаг " + step + ": " + "Началась ночь\n");

                for(Node node: main_scene.getChildren())
                {
                    node.setEffect(night);
                }

            day_night_change(anthills, neutral_food, water, materials);
        }

        //Если прошёл день, наступает утро, все муравьи едят, все объекты повреждаются
        if(step == day_step + 1)
        {
            main_log.clear();
            //День меняется(прибавляется)
            step = 1;
            day++;
            //Наступает утро
            is_night = false;

            main_log.appendText("Шаг " + step + ": " + "Наступило утро\n");
                for(Node node: main_scene.getChildren())
                {
                    node.setEffect(null);
                }

                //В конце дня все муравьи(включая матку и яйца) едят и пьют, если кому-то не хватило, то они голодают или обезвожены
            for (sample.anthill anthill : anthills) {
                anthill.setQueen_hungry(!anthill.DecCount_food(1));

                anthill.setQueen_dehydration(!anthill.DecCount_water(1));

                //яйца едят. Если не хватает еды для яйца, то оно погибает
                for (int egg = 0; egg < anthill.eggs.size(); egg++) {
                    if (!anthill.DecCount_food(anthill.eggs.get(egg).y)) {
                        anthill.eggs.removeElement(anthill.eggs.get(egg));
                        egg--;
                    }
                }

                for (int ant = 0; ant < anthill.ants.size(); ant++) {

                    anthill.ants.get(ant).setHungry(!anthill.DecCount_food(1));

                    anthill.ants.get(ant).setDehydration(!anthill.DecCount_water(1));
                }
            }

            day_night_change(anthills, neutral_food, water, materials);
        }

        //Если дождь идёт, то сокращается количество шагов, которые он идёт на 1
        if(is_rain != 0) {
            is_rain--;
            for (sample.objects objects : water) objects.IncDurability(1);
            if(is_rain == 0) {
                    main_scene.setEffect(null);
                    main_log.appendText("Шаг " + step + ": " + "Дождь закончился\n");
            }
        }

        //Если дождь не идёт, то есть шанс его наступления
        if(is_rain == 0)
        {
            for_random = randomize(0, 140);

            if(for_random == 0) {
                is_rain = randomize(40, 60);
                    main_scene.setEffect(rain);
                main_log.appendText("Шаг " + step + ": " + "Дождь начался\n");
            }
        }

        //Если муравей голоден или обезвожен, он теряет здоровье. Если здоровье на 0, то он погибает
        for(anthill anthill : anthills)
        {
            for(int ant = 0; ant < anthill.ants.size(); ant++)
            {
                if(anthill.ants.get(ant).isHungry())
                    if(anthill.ants.get(ant).DecHealth(1))
                    {
                        main_log.appendText("Шаг " + step + ": " + "У " + anthill.getAlly() + " команды умер муравей\n");
                        delete_object_image(anthill.ants.get(ant).getCoords());
                        if(anthill.ants.get(ant).isWorker())
                            anthill.DecHow_ant_workers();

                        anthill.ants.remove(ant);
                        ant--;
                        anthill.DecHow_ant();
                    }

                if(anthill.ants.get(ant).isDehydration())
                    if(anthill.ants.get(ant).DecHealth(1))
                    {
                        main_log.appendText("Шаг " + step + ": " + "У " + anthill.getAlly() + " команды умер муравей\n");
                        delete_object_image(anthill.ants.get(ant).getCoords());
                        if(anthill.ants.get(ant).isWorker())
                            anthill.DecHow_ant_workers();
                        anthill.ants.remove(ant);
                        ant--;
                        anthill.DecHow_ant();
                    }
            }
        }


        day_and_step.setText("День " + day + " Шаг " + step);
    }

    public int ant_grab_object(ant ant)
    {
        return (ant.getStrength() / 2) + (ant.getStrength() % 2);
    }

    public static void bubbleSort(Point @NotNull [] arr){
        for(int i = arr.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
            if( arr[j].y > arr[j+1].y ){
                Point tmp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = tmp;
            }
        }
    }
}

    int randomize(int min, int max)
    {
        if(min == max)
            return min;
        else
            if(min == 0)
                return (int) (Math.random() * max);
            else
                return min + (int) (Math.random() * (max - min + 1));
    }

    //Ход муравья рабочего
    private void ant_worker_step(ant ant, anthill anthill) {
            //Если перед муравьём его цель, то он собирает её
            if (ant.getPurpose().x == ant.way.firstElement().x && ant.getPurpose().y == ant.way.firstElement().y) {
                if(ant.getAction().equals(Actions.GoToFarmFood.toString()))
                {
                    grab_food(ant, anthill.farms);
                    for(int i = 0; i < anthill.farms.size(); i++)
                        if(anthill.farms.get(i).getId() == blocks.infected_plant.number) {
                            ant.setAction(Actions.CarryWater.toString());
                            filling_picture = blocks.ant_worker_with_water.picture;
                        }
                        else {
                            ant.setAction(Actions.CarryFood.toString());
                            filling_picture = blocks.ant_worker_with_food.picture;
                        }
                }
                else
                //Если муравей шёл за едой, то он её собирает
                if (ant.getAction().equals(Actions.GoToFood.toString())) {
                    if(anthill.getCount_mushrooms_farms() < anthill.getMax_mushrooms_farms())
                    {
                        for(food food : neutral_food)
                            if(food.getId() == blocks.mushrooms.number) {
                                ant.setAction(Actions.CarryMushrooms.toString());
                                break;
                            }
                    }
                    if(ant.getAction().equals(Actions.GoToFood.toString()))
                    ant.setAction(Actions.CarryFood.toString());

                    filling_picture = blocks.ant_worker_with_food.picture;

                    //Проверяем нейтральную еду. Если муравей идёт не за нейтральной едой, то проверяем культуры
                    grab_food(ant, neutral_food);

                }
                else
                //Если муравей шёл за водой, то он её собирает
                if (ant.getAction().equals(Actions.GoToWater.toString())) {
                    ant.setAction(Actions.CarryWater.toString());
                    filling_picture = blocks.ant_worker_with_water.picture;

                    grab_materials_or_water(ant, water);
                }
                else
                //Если муравей шёл за материалами, то он их собирает
                if (ant.getAction().equals(Actions.GoToMaterial.toString())) {
                    if(anthill.getCount_milk_farms() < anthill.getMax_milk_farms())
                    {
                        for(objects object : materials)
                            if(object.getId() == blocks.plant.number) {
                                ant.setAction(Actions.CarryInfectedPlant.toString());
                                break;
                            }
                    }
                    if(ant.getAction().equals(Actions.GoToMaterial.toString()))
                        ant.setAction(Actions.CarryMaterial.toString());

                    filling_picture = blocks.ant_worker_with_material.picture;

                    grab_materials_or_water(ant, materials);
                }
                else
                //Если муравей нёс в муравейнк еду, то он кладёт её в запасы
                if (ant.getAction().equals(Actions.CarryFood.toString())) {
                    anthill.IncCount_food(ant.getGrab());
                    ant.setAction(Actions.Wait.toString());
                    filling_picture = blocks.ant_worker.picture;
                }
                else
                //Если муравей нёс в муравейнк воду, то он кладёт её в запасы
                if (ant.getAction().equals(Actions.CarryWater.toString())) {
                    anthill.IncCount_water(ant.getGrab());
                    ant.setAction(Actions.Wait.toString());
                    filling_picture = blocks.ant_worker.picture;
                }
                else
                //Если муравей нёс в муравейнк материалы, то он кладёт их в запасы
                if (ant.getAction().equals(Actions.CarryMaterial.toString())) {
                    anthill.IncCount_materials(ant.getGrab());
                    ant.setAction(Actions.Wait.toString());
                    filling_picture = blocks.ant_worker.picture;
                }
                else
                //Если мурвей чинил муравейник, то муравейник чинится на 1 единицу
                //Если при этом муравейник починился полность. то муравейник освобождается от работы
                //Если не хватает материалов на починку, то муравей так же освобождается от работы
                    if(ant.getAction().equals(Actions.Repair.toString())) {
                        //Если муравей уже в муравейнике, то он чинит его
                        if(ant.getCoords().x == anthill.getCoords().x && ant.getCoords().y == anthill.getCoords().y) {

                            if(anthill.getCount_water() == 0 || anthill.getCount_materials() == 0) {
                                anthill.IncDurability(1);
                                if (anthill.getDurability() % Dec_anthill_durability == 0) {
                                    anthill.DecCount_water(how_materials_need_to_repair);
                                    anthill.DecCount_materials(how_materials_need_to_repair);
                                }
                            }
                            else
                                ant.setAction(Actions.Wait.toString());

                            if (anthill.getDurability() == anthill_durability || anthill.getCount_water() == 0 || anthill.getCount_materials() == 0) {
                                ant.setAction(Actions.Wait.toString());
                            }
                            filling_picture = blocks.ant_worker.picture;
                        }
                        //Иначе он заходит внутрь муравейника
                        else
                        {
                            delete_object_image(ant.getCoords());
                            ant.setCoords(anthill.getCoords());
                        }
                    }
                    else
                    if(ant.getAction().equals(Actions.Build.toString())) {
                        //Если муравей в муравейнике, то он начинает его строить
                        if(ant.getCoords().x == anthill.getCoords().x && ant.getCoords().y == anthill.getCoords().y) {
                            anthill.DecBuild_step();
                            if (anthill.getBuild_step() == 0) {
                                ant.setAction(Actions.Wait.toString());
                                if(anthill.getAnthill_level() == 1)
                                    filling_picture = blocks.anthill_lvl2.picture;
                                else
                                    filling_picture = blocks.anthill_lvl3.picture;
                                delete_object_image(anthill.getCoords());
                                set_image(anthill.getCoords(), new Point(0, 0), blocks.anthill.number, anthill_in_wave_algorithm_id);
                                anthill.LevelUp();
                            }
                            filling_picture = blocks.ant_worker.picture;
                        }
                        //Иначе он заходит внутрь муравейника
                        else{
                            delete_object_image(ant.getCoords());
                            ant.setCoords(anthill.getCoords());
                        }
                    }

                //Если муравей в муравейнике и ресурс прямо перед муравейником, то количество еды сразу прибавляется к запасам
                if (ant.getCoords().x == anthill.getCoords().x && ant.getCoords().y == anthill.getCoords().y) {
                    if (ant.getAction().equals(Actions.CarryFood.toString())) {
                        anthill.IncCount_food(ant.getGrab());
                        ant.setAction(Actions.Wait.toString());
                    }
                    if (ant.getAction().equals(Actions.CarryWater.toString())) {
                        anthill.IncCount_water(ant.getGrab());
                        ant.setAction(Actions.Wait.toString());
                    }
                    if (ant.getAction().equals(Actions.CarryMaterial.toString())) {
                        anthill.IncCount_materials(ant.getGrab());
                        ant.setAction(Actions.Wait.toString());
                    }
                } else {
                    ant.setSprite_cut(rotation(ant.getCoords().x, ant.getCoords().y, ant.getPurpose().x, ant.getPurpose().y));
                    delete_object_image(ant.getCoords());
                    set_image(ant.getCoords(), ant.getSprite_cut(), blocks.ant_worker.number, ant_in_wave_algorithm_id);
                    //Иначе у муравья строится маршрут до муравейника(если он несёт ресурсы)
                    if (!ant.getAction().equals(Actions.Wait.toString())) {
                        ant.setMatrixWay(matrixWays);
                        if(ant.getAction().equals(Actions.CarryMushrooms.toString()) || ant.getAction().equals(Actions.CarryInfectedPlant.toString()))
                        {
                            Vector<Point> where_place_farm = new Vector<>();
                            int radius = 3;

                            //Если муравейник близок к краям карты, то расширяем радиус возможного установления фермы, чтобы было просторнее
                            if((anthill.getCoords().x == 0 || anthill.getCoords().x == Scene_blocks || anthill.getCoords().x == 1 || anthill.getCoords().x == Scene_blocks - 1) || 
                                    (anthill.getCoords().y == 0 || anthill.getCoords().y == Scene_blocks || anthill.getCoords().y == 1 || anthill.getCoords().y == Scene_blocks - 1))
                                radius = 4;

                            for(int t = 0; t < Scene_blocks; t++)
                                for(int k = 0; k < Scene_blocks; k++)
                                    if(main_array[t][k] == blocks.grass.number &&
                                            ((t <= ant.getCoords().x + radius) || (t >= ant.getCoords().x - radius)) ||
                                            ((t >= ant.getCoords().x + 2) || (t <= ant.getCoords().x - 2)) ||
                                            ((k <= ant.getCoords().y + radius) || (k >= ant.getCoords().y - radius)) ||
                                            ((k >= ant.getCoords().y + 2) || (k <= ant.getCoords().y - 2)))
                                        where_place_farm.add(new Point(t, k));

                                    Point coords = new Point(where_place_farm.get(randomize(0, where_place_farm.size())));

                                    ant.setPurpose(coords);
                                    ant.WavePropagation(coords);


                            if(ant.getAction().equals(Actions.CarryMushrooms.toString()))
                                anthill.IncCount_mushrooms_farms();
                            else
                                anthill.IncCount_milk_farms();
                        }
                        else {
                            ant.setPurpose(anthill.getCoords());
                            ant.WavePropagation(anthill.getCoords());
                        }
                    }
                }
            } else {
                //Если муравей ещё не дошёл до цели, то он продвигается к ней
                Point sprite_cut = rotation(ant.getCoords().x, ant.getCoords().y, ant.way.firstElement().x, ant.way.firstElement().y);
                if(ant.getCoords().x != anthill.getCoords().x || ant.getCoords().y != anthill.getCoords().y) {
                    delete_object_image(ant.getCoords());
                }

                ant.setCoords(ant.way.firstElement());
                ant.way.remove(0);

                if (ant.getAction().equals(Actions.GoToFood.toString()) || ant.getAction().equals(Actions.GoToWater.toString()) ||
                        ant.getAction().equals(Actions.GoToMaterial.toString()) || ant.getAction().equals(Actions.GoToFarmFood.toString()) ||
                        ant.getAction().equals(Actions.Repair.toString()) || ant.getAction().equals(Actions.Build.toString())) {
                    filling_picture = blocks.ant_worker.picture;
                }

                if (ant.getAction().equals(Actions.CarryFood.toString()) || ant.getAction().equals(Actions.CarryMushrooms.toString())) {
                    filling_picture = blocks.ant_worker_with_food.picture;
                }

                if (ant.getAction().equals(Actions.CarryWater.toString())) {
                    filling_picture = blocks.ant_worker_with_water.picture;
                }

                if (ant.getAction().equals(Actions.CarryMaterial.toString()) || ant.getAction().equals(Actions.CarryInfectedPlant.toString())) {
                    filling_picture = blocks.ant_worker_with_material.picture;
                }

                set_image(ant.getCoords(), sprite_cut, blocks.ant_worker.number, ant_in_wave_algorithm_id);
                ant.setSprite_cut(sprite_cut);
            }
}

    //Ход муравья воина
    private void ant_fighter_step(ant ant, anthill anthill)
    {
        //Если на карте есть враги, то проверяем, не находятся ли они рядом с воинами
            if(ant.getAction().equals(Actions.Attack.toString()))
            {
                    Point sprite_cut;

                    //если он уже перед врагом, то он атакует его
                    if(ant.way.size() == 1) {
                        sprite_cut = rotation(ant.getCoords().x, ant.getCoords().y, ant.getPurpose().x, ant.getPurpose().y);
                        filling_picture = blocks.ant_fighter.picture;
                        if(ant.getSprite_cut().x != sprite_cut.x || ant.getSprite_cut().y != sprite_cut.y)
                        {
                            delete_object_image(ant.getCoords());
                            set_image(ant.getCoords(), sprite_cut, blocks.ant_fighter.number, ant_in_wave_algorithm_id);
                            ant.setSprite_cut(sprite_cut);
                        }
                        for(insect enemy : enemy)
                        {
                            if(enemy.getCoords().x == ant.getPurpose().x && enemy.getCoords().y == ant.getPurpose().y)
                                enemy.DecHealth(ant.getStrength());
                        }
                    }
                    else {
                        sprite_cut = rotation(ant.getCoords().x, ant.getCoords().y, ant.way.firstElement().x, ant.way.firstElement().y);
                        filling_picture = blocks.ant_fighter.picture;
                        delete_object_image(ant.getCoords());
                        ant.setCoords(ant.way.firstElement());
                        ant.way.remove(0);
                        set_image(ant.getCoords(), sprite_cut, blocks.ant_fighter.number, ant_in_wave_algorithm_id);
                        ant.setSprite_cut(sprite_cut);
                    }
                    return;

            }


            if (ant.getAction().equals(Actions.PatrolEggs.toString())) {
                if(ant.getCoords().x != anthill.getCoords().x || ant.getCoords().y != anthill.getCoords().y)
                {
                    if(ant.way.firstElement().x == anthill.getCoords().x && ant.way.firstElement().y == anthill.getCoords().y)
                    {
                        delete_object_image(ant.getCoords());
                        ant.setCoords(anthill.getCoords());
                        return;
                    }
                }
                else
                return;
            }

            if(ant.getCoords().x != anthill.getCoords().x || ant.getCoords().y != anthill.getCoords().y)
                delete_object_image(ant.getCoords());

                Point sprite_cut = rotation(ant.getCoords().x, ant.getCoords().y, ant.way.firstElement().x, ant.way.firstElement().y);
                ant.setCoords(ant.way.firstElement());
                filling_picture = blocks.ant_fighter.picture;
                ant.way.remove(0);
                set_image(ant.getCoords(), sprite_cut, blocks.ant_fighter.number, ant_in_wave_algorithm_id);
                ant.setSprite_cut(sprite_cut);

                if(ant.getAction().equals(Actions.PatrolAnthill.toString()))
                {
                   ant.setMatrixWay(matrixWays);
                    Vector<Point> where_to_step = new Vector<>();
                    for(int t = 0; t < Scene_blocks; t++)
                        for(int k = 0; k < Scene_blocks; k++)
                            if(main_array[t][k] == blocks.grass.number &&
                                    ((t == ant.getCoords().x + 1) || (t == ant.getCoords().x - 1)) &&
                                    ((k == ant.getCoords().y + 1) || (k == ant.getCoords().y - 1)) &&
                                    ((t >= anthill.getCoords().x - 3) && (t <= anthill.getCoords().x + 3)) &&
                                    ((k >= anthill.getCoords().y - 3) || (k <= anthill.getCoords().y + 3)))
                                where_to_step.add(new Point(t, k));

                   ant.setPurpose(where_to_step.get(randomize(0, where_to_step.size())));
                    ant.WavePropagation(ant.getPurpose());
                }
    }

    //Муравей берёт воду или материалы
    private void grab_materials_or_water(ant ant, Vector<objects> materials_or_water) {
        for(int i = 0; i < materials_or_water.size(); i++)
            if(ant.getPurpose().x == materials_or_water.get(i).getCoords().x && ant.getPurpose().y == materials_or_water.get(i).getCoords().y)
            {
                if(materials_or_water.get(i).getDurability() - (ant_grab_object(ant)) > 0) {
                    materials_or_water.get(i).DecDurability(ant_grab_object(ant));
                    ant.setGrab(ant_grab_object(ant));
                }
                else{
                    int index = i;
                    ant.setGrab(materials_or_water.get(i).getDurability());
                    materials_or_water.get(i).DecDurability(materials_or_water.get(i).getDurability());
                    delete_object_image(materials_or_water.get(index).getCoords());
                    Empty_place.add(materials_or_water.get(i).getCoords());
                    matrixWays[materials_or_water.get(i).getCoords().x][materials_or_water.get(i).getCoords().y] = empty_place_in_wave_algorithm_id;
                    materials_or_water.remove(i);
                }
            }
    }

    //Муравей берёт еду
    private void grab_food(ant ant, Vector<food> food) {
        for(int i = 0; i < food.size(); i++)
            if(ant.getPurpose().x == food.get(i).getCoords().x && ant.getPurpose().y == food.get(i).getCoords().y)
            {
                if(food.get(i).isCulture()) {
                    if (food.get(i).getUsable() - (ant_grab_object(ant)) > 0) {
                        food.get(i).DecUsable(ant_grab_object(ant));
                        ant.setGrab(ant_grab_object(ant));
                    } else {
                        ant.setGrab(food.get(i).getUsable() - 1);
                        food.get(i).DecUsable(food.get(i).getUsable() - 1);
                    }
                }
                else
                {
                    if (food.get(i).getUsable() - (ant_grab_object(ant)) > 0) {
                        food.get(i).DecUsable(ant_grab_object(ant));
                        ant.setGrab(ant_grab_object(ant));
                    } else {
                        ant.setGrab(food.get(i).getUsable());
                        food.get(i).DecUsable(food.get(i).getUsable());
                        delete_object_image(food.get(i).getCoords());
                        food.remove(i);
                    }
                }
                return;
            }
    }

    //Поворот спрайта
    public Point rotation(int x, int y, int go_x, int go_y) {
    if(x == go_x + 1 && y == go_y)
    {
        return new Point(0, 0);
    }

    if(x == go_x + 1 && y == go_y - 1)
    {
        return new Point(45, 0);
    }

    if(x == go_x && y == go_y - 1)
    {
        return new Point(0 , 45);
    }

    if(x == go_x - 1 && y == go_y - 1)
    {
        return new Point(45, 45);
    }

    if(x == go_x - 1 && y == go_y)
    {
        return new Point(90, 0);
    }

    if(x == go_x - 1 && y == go_y + 1)
    {
        return new Point(90, 45);
    }

    if(x == go_x && y == go_y + 1)
    {
        return new Point(0, 90);
    }

    if(x == go_x + 1 && y == go_y + 1)
    {
        return new Point(45, 90);
    }
return new Point(0, 0);
}

    //удалить картинку
    private void delete_object_image(Point coords) {
    ObservableList<Node> observableList = main_scene.getChildren();
    for (int i = 0; i < observableList.size(); i++)
        if (GridPane.getRowIndex(observableList.get(i)) == coords.x && GridPane.getColumnIndex(observableList.get(i)) == coords.y) {

                main_scene.getChildren().remove(observableList.get(i));
               
                main_scene.add(new ImageView(blocks.grass.picture), coords.y, coords.x);
                if (is_night)
                    main_scene.getChildren().get(main_scene.getChildren().size() - 1).setEffect(night);

            main_array[coords.x][coords.y] = blocks.grass.number;
            matrixWays[coords.x][coords.y] = empty_place_in_wave_algorithm_id;
            Empty_place.add(new Point(coords.x, coords.y));

            break;
        }
}

    //установить картинку
    private void set_image(Point coords, Point sprite_cut, int block_id, int wave_id) {
    ObservableList<Node> observableList = main_scene.getChildren();
    for (int i = 0; i < observableList.size(); i++)
        if (GridPane.getRowIndex(observableList.get(i)) == coords.x && GridPane.getColumnIndex(observableList.get(i)) == coords.y) {
            ImageView imageView = new ImageView(filling_picture);

                main_scene.getChildren().remove(observableList.get(i));

                imageView.setViewport(new Rectangle2D(sprite_cut.x, sprite_cut.y, image_width, image_height));
                main_scene.add(imageView, coords.y, coords.x);

            main_array[coords.x][coords.y] = block_id;
            matrixWays[coords.x][coords.y] = wave_id;
            Empty_place.removeElement(coords);

                if (is_night)
                    main_scene.getChildren().get(main_scene.getChildren().size() - 1).setEffect(night);

            break;
        }
}

private void print_out_ant(ant ant, TextArea area, ImageView imageView)
{
    imageView.setVisible(true);
    area.setVisible(true);

    area.appendText("   Муравей   \n");

    if(ant.isWorker())
    {
        area.appendText("Роль: рабочий\n");
        if(ant.getAction().equals(Actions.CarryFood.toString()))
        {
            imageView.setImage(blocks.ant_worker_with_food.picture);
            area.appendText("Несёт " + ant.getGrab() + "ед. еды\n");
        }
        else
            if(ant.getAction().equals(Actions.CarryWater.toString()))
            {
                imageView.setImage(blocks.ant_worker_with_water.picture);
                area.appendText("Несёт " + ant.getGrab() + "ед. воды\n");
            }
            else
                if(ant.getAction().equals(Actions.CarryMaterial.toString()))
                {
                    imageView.setImage(blocks.ant_worker_with_material.picture);
                    area.appendText("Несёт " + ant.getGrab() + "ед. материалов\n");
                }
                else
                {
                    imageView.setImage(blocks.ant_worker.picture);

                    if(ant.getAction().equals(Actions.GoToFood.toString()))
                        area.appendText("Идёт за едой: " + "x: " + ant.getPurpose().x + " y: " + ant.getPurpose().y + "\n");
                    else
                    if(ant.getAction().equals(Actions.GoToWater.toString()))
                        area.appendText("Идёт за водой: " + "x: " + ant.getPurpose().x + " y: " + ant.getPurpose().y + "\n");
                    else
                    if(ant.getAction().equals(Actions.GoToMaterial.toString()))
                        area.appendText("Идёт за материалами: " + "x: " + ant.getPurpose().x + " y: " + ant.getPurpose().y + "\n");
                    else
                    if(ant.getAction().equals(Actions.Repair.toString()))
                        area.appendText("Чинит муравейник\n");
                    else
                    if(ant.getAction().equals(Actions.Build.toString()))
                        area.appendText("Строит муравейник\n");
                }
    }
    else
    {
        imageView.setImage(blocks.ant_fighter.picture);
        area.appendText("Роль: воин\n");

        if(ant.getAction().equals(Actions.Attack.toString()))
            area.appendText("Идёт в атаку\n");

        if(ant.getAction().equals(Actions.PatrolEggs.toString()))
            area.appendText("Охраняет яйца\n");

        if(ant.getAction().equals(Actions.PatrolAnthill.toString()))
            area.appendText("Патрулирует вокруг муравейника\n");
    }

    imageView.setViewport(new Rectangle2D(ant.getSprite_cut().x, ant.getSprite_cut().y, image_width, image_height));

    area.appendText("Координаты: " + "x: " + ant.getCoords().x + " y: " + ant.getCoords().y + "\n");
    area.appendText("Здоровье: " + ant.getHealth() + "/" + ant.getMaxHealth() + "\n");
    area.appendText("Сила: " + ant.getStrength() + "\n");
    area.appendText("Интеллект: " + ant.getIntellect() + "\n");
    area.appendText("Муравей: ");
    if(ant.isHungry())
        area.appendText("голоден, ");
    else
        area.appendText("сыт, ");

    if(ant.isDehydration())
        area.appendText("обезвожен\n");
    else
        area.appendText("напоен\n");
}

private void print_out_anthill(anthill anthill, TextArea area, ImageView imageView)
{
    first_print_image.setViewport(null);
    area.setVisible(true);
    imageView.setVisible(true);

    int where_to_print = 2;
    if(anthill.getAnthill_level() == 1)
        imageView.setImage(blocks.anthill.picture);
    else
    if(anthill.getAnthill_level() == 2)
        imageView.setImage(blocks.anthill_lvl2.picture);
    else
        imageView.setImage(blocks.anthill_lvl3.picture);


    area.appendText("...Муравейник...\n");
    area.appendText("Команда: " + anthill.getAlly() + "\n");
    area.appendText("Прочность: " + anthill.getDurability() + "/" + anthill_durability + "\n");
    area.appendText("Запасы еды: " + anthill.getCount_food() + "\n");
    area.appendText("Запасы воды: " + anthill.getCount_water() + "\n");
    area.appendText("Запасы материалов: " + anthill.getCount_materials() + "\n");
    area.appendText("Вместимость: " + anthill.getHow_ant() + "/" + anthill.getAnt_capacity() + "\n");
    area.appendText("Матка: ");
    if(anthill.isQueen_hungry())
        area.appendText("голодна, ");
    else
        area.appendText("сыта, ");

    if(anthill.isQueen_dehydration())
        area.appendText("обезвожена\n");
    else
        area.appendText("напоена\n");

    area.appendText("Количество яиц: " + anthill.eggs.size() + "\n");
    area.appendText("Пастбища тли: ");
    int farms = 0;
    boolean flag = true;
    if(anthill.getCount_milk_farms() != 0) {
        for (int i = 0; i < anthill.farms.size(); i++) {
            if(anthill.farms.get(i).getId() == blocks.infected_plant.number) {
                area.appendText("x: " + anthill.farms.get(i).getCoords().x + " y: " + anthill.farms.get(i).getCoords().y);
                farms++;
            }

            if (farms == anthill.getCount_milk_farms()) {
                area.appendText("\n");
                break;
            }
            else {
                if(flag)
                area.appendText(" ; ");
                flag = false;
            }
        }
    }
    else
        area.appendText("---\n");


    area.appendText("Ферма грибов: ");
    farms = 0;
    flag = true;
    if(anthill.farms.size() != 0) {
        for (int i = 0; i < anthill.farms.size(); i++) {
            if(anthill.farms.get(i).getId() == blocks.mushrooms.number) {
                area.appendText("x: " + anthill.farms.get(i).getCoords().x + " y: " + anthill.farms.get(i).getCoords().y);
                farms++;
            }

            if (farms == anthill.farms.size()) {
                area.appendText("\n");
                break;
            }
            else {
                if(flag) {
                    area.appendText(" ; ");
                    flag = false;
                }
            }
        }
    }
    else
        area.appendText("---\n");

    area.appendText("Уровень: " + anthill.getAnthill_level() + "\n");


    if(anthill.getBuild_step() == 0)
        area.appendText("Строительство: ---\n");
    else {
        area.appendText("Строительство: " + anthill.getBuild_step() + " шагов\n");

    }
        for(ant ant : anthill.ants)
        {
            if(ant.getAction().equals(Actions.Repair.toString()) ||
                    ant.getAction().equals(Actions.Build.toString()) ||
                    ant.getAction().equals(Actions.PatrolEggs.toString()))
            {
                if(where_to_print == 2) {
                    second_print_log.setVisible(true);
                    second_print_image.setVisible(true);

                    print_out_ant(ant, second_print_log, second_print_image);
                    where_to_print++;
                }
                else
                if(where_to_print == 3) {
                    third_print_log.setVisible(true);
                    third_print_image.setVisible(true);

                    print_out_ant(ant, third_print_log, third_print_image);
                    where_to_print++;
                }
                else {
                    if(where_to_print == 4) {
                        fourth_print_log.setVisible(true);
                        fourth_print_image.setVisible(true);
                        print_out_ant(ant, fourth_print_log, fourth_print_image);
                        where_to_print++;
                    }
                }
            }

        }

        area.appendText("Охрана яиц: ");
        if(anthill.isGuard())
            area.appendText("есть\n");
        else
            area.appendText("нет\n");

    area.appendText("Координаты: " + "x: " + anthill.getCoords().x + " y: " + anthill.getCoords().y + "\n");
}

private void print_out_objects(objects object, TextArea area, ImageView imageView)
{
    first_print_image.setViewport(null);
    area.setVisible(true);
    imageView.setVisible(true);

    if(object.getId() == blocks.water.number)
        area.appendText("   Лужа   \n");
    else
        if(object.getId() == blocks.plant.number)
            area.appendText("Материал: цветочек   \n");
        else
            if(object.getId() == blocks.stick.number)
                area.appendText("Материал: палочка   \n");

    area.appendText("Запасы: " + object.getDurability() + "/" + object.getMaxDurability() + "\n");
    area.appendText("Координаты: " + "x: " + object.getCoords().x + " y: " + object.getCoords().y + "\n");

    for(blocks b : blocks.values())
        if(b.number == object.getId())
            imageView.setImage(b.picture);

}

private void print_out_food(food food, TextArea area, ImageView imageView)
{
    first_print_image.setViewport(null);
    area.setVisible(true);
    imageView.setVisible(true);

    if(food.getId() == blocks.apple.number)
    area.appendText("Еда: яблоко\n");
    else
        if(food.getId() == blocks.mushrooms.number)
            area.appendText("Еда: гриб\n");
        else
            if(food.getId() == blocks.infected_plant.number)
                area.appendText("Еда: молоко тли\n");

    area.appendText("Свежесть: " + food.getDurability() + "/" + food.getMaxDurability() + "\n");
        area.appendText("Запасы: " + food.getUsable() + "/" + food.getMaxUsable() + "\n");

        if(food.isCulture())
            area.appendText("Окультуренное\n");
        else
        area.appendText("Дикое\n");


        area.appendText("Координаты: " + "x: " + food.getCoords().x + " y: " + food.getCoords().y + "\n");

        for(blocks b : blocks.values())
            if(b.number == food.getId())
                imageView.setImage(b.picture);

    }

    private void add_new_ant(anthill anthill)
    {
        int intellect;
        int strength;

        if(anthill.getHow_ant_workers() < 4 + (anthill.getAnthill_level() - 1) * 2 &&
                anthill.getHow_ant() != 2 && anthill.getHow_ant() != 5) {

            do {
                intellect = randomize(min_characteristic, max_characteristic);
                strength = randomize(min_characteristic, max_characteristic);

            }while(intellect <= strength);

            anthill.IncHow_ant_workers();
        }
        else {
            do {
                intellect = randomize(min_characteristic, max_characteristic);
                strength = randomize(min_characteristic, max_characteristic);

            }while(intellect > strength);
        }

        anthill.ants.add(new ant(randomize(min_ant_health, max_ant_health), strength, intellect,
                randomize(min_characteristic, max_characteristic), anthill.getCoords()));
        anthill.IncHow_ant();
    }

    private void day_night_change(Vector<anthill> anthills, Vector<food> neutral_food, Vector<objects> water, Vector<objects> materials)
    {
        //Муравейник немного ломается, если его прочность равна 0, то он уничтожается
        for (int i = 0; i < anthills.size(); i++) {
            if(anthills.get(i).DecDurability(Dec_anthill_durability))
            {
                main_log.appendText("Шаг " + step + ": " + "Муравейник сломался у комaнды " + anthills.get(i).getAlly() + "\n");
                for(int ant = 0; ant < anthills.get(i).ants.size();)
                {
                    delete_object_image(anthills.get(i).ants.get(ant).getCoords());
                    anthills.get(i).ants.remove(ant);
                }
                for (int j = 0; j < anthills.get(i).farms.size();)
                {
                    delete_object_image(anthills.get(i).farms.get(j).getCoords());
                    anthills.get(i).farms.remove(j);
                }
                delete_object_image(anthills.get(i).getCoords());
                anthills.remove(i);
                i--;
                continue;
            }

            for (int j = 0; j < anthills.get(i).farms.size(); j++) {
                if(anthills.get(i).farms.get(j).DecDurability(Dec_objects_durability))
                {
                    delete_object_image(anthills.get(i).farms.get(j).getCoords());
                    anthills.get(i).farms.removeElement(anthills.get(i).farms.get(j));
                    j--;
                }
            }
        }
        //Еда гниёт, если она полностью сгниёт, то она уничтожается
        for (int i = 0; i < neutral_food.size(); i++) {
            if(neutral_food.get(i).DecDurability(Dec_objects_durability))
            {
                delete_object_image(neutral_food.get(i).getCoords());
                neutral_food.removeElement(neutral_food.get(i));
                i--;
            }
        }
        //Вода испаряется, если она полностью испарилась, то она пропадает с карты
        for (int i = 0; i < water.size(); i++) {
            if(water.get(i).DecDurability(Dec_objects_durability))
            {
                delete_object_image(water.get(i).getCoords());
                water.removeElement(water.get(i));
                i--;
            }
        }
        //Материалы портятся, если они полностью испортились, то они пропадают
        for (int i = 0; i < materials.size(); i++) {
            if(materials.get(i).DecDurability(Dec_objects_durability))
            {
                delete_object_image(materials.get(i).getCoords());
                materials.removeElement(materials.get(i));
                i--;
            }
        }
    }
}
