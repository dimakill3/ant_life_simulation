package sample;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
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

    //private Vector<Vector<ant>> ants = new Vector<>();
    private Vector<anthill> anthills = new Vector<>();
    //private Vector<Vector<Point>> eggs = new Vector<>();
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

    private final int anthill_durability = 20;

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

    private int begin_count_of_ants = 1;

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
                //Обработка вывода информации
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
                            //ants.get(anthill_index).add(new ant(randomize(min_ant_health, max_ant_health), randomize(min_characteristic, max_characteristic),
                            //        randomize(min_characteristic, max_characteristic), randomize(min_characteristic, max_characteristic),
                            //        new Point(i, j)));

                            anthills.get(anthill_index).ants.add(new ant(30, 5,
                                    7, 3,
                                    new Point(i, j)));
                            anthills.get(anthill_index).IncHow_ant();
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

            for(int i = 0; i < anthills.size(); i++)
                for(int j = 0; j < anthills.get(i).ants.size(); j++)
                    anthills.get(i).ants.get(j).setMatrixWay(matrixWays);

            day = 1;
            step = 1;
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

    void Step()
    {
        int for_random = 0;

        //Для каждого объекта, который может заспавниться, есть шанс спавна
        //яблоко
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < chance_to_spawn_food) {
                for_random = randomize(0, Empty_place.size());
                Point coords = Empty_place.get(for_random);
                //Empty_place.remove(for_random);
                //matrixWays[coords.x][coords.y] = food_in_wave_algorithm_id;
                neutral_food.add(new food(coords, randomize(min_food_durability, max_food_durability), blocks.apple.number, randomize(min_food_usable, max_food_usable)));
                filling_block = blocks.apple.number;
                filling_picture = blocks.apple.picture;
                main_array[coords.x][coords.y] = filling_block;

                set_image(coords, new Point(0,0), filling_block, food_in_wave_algorithm_id);
            }
        }

        //гриб
        if(Empty_place.size() != 0) {
            for_random = randomize(0, 100);
            if (for_random >= 0 && for_random < chance_to_spawn_food) {
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
            if (for_random >= 0 && for_random < chance_to_spawn_material) {
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
            if (for_random >= 0 && for_random < chance_to_spawn_material) {
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
            if (for_random >= 0 && for_random < chance_to_spawn_water) {
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

        for(int i = 0; i < anthills.size(); i++)
        {
            //+1 Матка
            int ant_count = anthills.get(i).getHow_ant() + 1;

            //Если достаточно материалов, еды и воды, при этом другое улучшение закончено и муравейник не максимального уровня, то начинается улучшение
            //При этом улучшение требует муравья, который будет заниматься строительством (сам процесс строительства описан при распределении работы между муравьями)
            if((anthills.get(i).getBuild_step() == 0) && (anthills.get(i).getAnthill_level() != 3) &&
                    (anthills.get(i).getCount_food() >= anthill_levelUp_food[anthills.get(i).getAnthill_level() - 1] + ant_count * 3) &&
                    (anthills.get(i).getCount_water() >= anthill_levelUp_water[anthills.get(i).getAnthill_level() - 1] + ant_count * 3 +
                            (((anthill_durability - anthills.get(i).getDurability()) / Dec_anthill_durability) + how_materials_need_to_repair) * 4) &&
                    (anthills.get(i).getCount_materials() >= anthill_levelUp_materials[anthills.get(i).getAnthill_level() - 1] +
                            (((anthill_durability - anthills.get(i).getDurability()) / Dec_anthill_durability) + how_materials_need_to_repair) * 4))
            {
                anthills.get(i).setBuild_step();
            }

            //Если есть место для ещё одного муравья и еды хватит всем муравьям(учитывая матку и новорождённое яйцо) на 2 дня, то матка может родить яйцо
            //Иначе, если еды не хватает, то матка создаёт яйцо, чтобы его съесть, при этом матка должна быть сытая. Так она может делать каждые пол дня (если сытая)
            //Так как это считается канибаллизмом, то это делается только при вынужденных мерах
            if(!(anthills.get(i).isQueen_hungry() || anthills.get(i).isQueen_dehydration()) && ant_count - 1 < anthills.get(i).getAnt_capacity() && anthills.get(i).getCount_food() >= ((ant_count + 1) * 2) &&
            anthills.get(i).getCount_water() >= (((ant_count + 1) * 2) +
            ((((anthill_durability - anthills.get(i).getDurability()) / Dec_anthill_durability) + how_materials_need_to_repair) * 4)))
            {
                anthills.get(i).eggs.add(new Point(how_step_to_born, how_eat_egg_worker));
                anthills.get(i).IncCount_of_eggs();
                anthills.get(i).setQueen_hungry(true);
            }
            else
            {
                if(anthills.get(i).getCount_food() < ant_count || (anthills.get(i).getCount_water() < (ant_count + how_materials_need_to_repair)))
                {
                    anthills.get(i).IncCount_food(2);
                    anthills.get(i).IncCount_water(2);
                    anthills.get(i).setQueen_hungry(true);
                }
            }


            //Узнаём, какой муравей чем занят, чтобы свободные муравьи делали полезную работу(чтобы не было так, что все муравьи, например, носят еду)
            Point[] works = new Point[5];
            int ant_on_food_id = 1;
            int ant_on_water_id = 2;
            int ant_on_material_id = 3;
            int ant_on_build_id = 4;
            int ant_on_repair_id = 5;
            boolean is_anyone_guardian = false;
            works[0] = new Point(ant_on_food_id, 0);
            works[1] = new Point(ant_on_water_id, 0);
            works[2] = new Point(ant_on_material_id, 0);
            works[3] = new Point(ant_on_build_id, 0);
            works[4] = new Point(ant_on_repair_id, 0);

            //Узнаём кто чем занимается
            for(int ant = 0; ant < anthills.get(i).ants.size(); ant++) {
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToFood.toString()) || anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryFood.toString()))
                    works[0].y++;
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToWater.toString()) || anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryWater.toString()))
                    works[1].y++;
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToMaterial.toString()) || anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryMaterial.toString()))
                    works[2].y++;
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.Build.toString()))
                    works[3].y++;
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.Repair.toString()))
                    works[4].y++;
                if(anthills.get(i).ants.get(ant).getAction().equals(Actions.PatrolEggs.toString()))
                    is_anyone_guardian = true;
            }

            //Планируем действия муравьёв
            for(int ant = 0; ant < anthills.get(i).ants.size(); ant++)
            {

                //Если муравей - рабочий, то он будет либо таскать пищу, воду, материалы, разводить тлю, или строить и ремонтировать муравейник
                if(anthills.get(i).ants.get(ant).isWorker())
                {
                    //Если муравей ожидает, то даём ему работу
                    if(anthills.get(i).ants.get(ant).getAction().equals(Actions.Wait.toString())) {
                        //Сначала проверяем, если вообще чем заняться муравью
                        if(neutral_food.size() == 0 && culture.size() == 0 && water.size() == 0 &&
                                materials.size() == 0 && (anthills.get(i).getDurability() == anthill_durability || works[4].y == 1)
                                && (anthills.get(i).getBuild_step() == 0 || works[3].y == 1))
                            continue;

                        bubbleSort(works);

                        //Проверяем работу, на которой меньше всего муравьёв
                        for (Point work : works) {
                            //Если это поиск еды...
                            if (work.x == ant_on_food_id) {
                                //Проверяем есть ли вообще какая-нибудь еда на карте
                                if (neutral_food.size() != 0 || culture.size() != 0) {
                                    //Муравей идёт собирать культуру, если они есть и есть урожай. Если нет, то ищем близжайшую еду
                                    if (anthills.get(i).getMilk_farm() > 0 || anthills.get(i).getMushrooms_farm() > 0) {
                                        //Для начала выбирается культура с наибольшимколичеством еды. Сделано это для того, чтобы достичь эффективности культур
                                        //То есть чтобы все они производили еду (если бы муравей собирал только одну культуру,
                                        // то другая была бы переполнена урожаем и не могла производить еду)
                                        int[] sort_cult = new int[4];
                                        for (int k = 0; k < culture.size(); k++) {
                                            sort_cult[k] = culture.get(k).getUsable();
                                        }

                                        Arrays.sort(sort_cult);
                                        int index = -1;
                                        for (int k = 0; k < culture.size(); k++)
                                            if (sort_cult[0] == culture.get(k).getUsable())
                                                index = k;

                                        if (index != -1) {
                                            //Если культура скоро перестанет плодоносить и исчезнет, то нужно собрать весь оставшийся урожай
                                            if (culture.get(index).getDurability() == 1) {
                                                if (culture.get(index).getUsable() != 0) {
                                                    //Отправляем муравья собирать последние урожаи с культуры
                                                    anthills.get(i).ants.get(ant).setAction(Actions.GoToFood.toString());
                                                    work.y++;
                                                    anthills.get(i).ants.get(ant).setPurpose(culture.get(index).getCoords());
                                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                                    anthills.get(i).ants.get(ant).WavePropagation(culture.get(index).getCoords());
                                                }
                                            } else {
                                                //Если количество еды, которое может унести муравей больше или равно количеству урожая, то муравей идёт собирать
                                                if (culture.get(index).getUsable() - ant_grab_object(anthills.get(i).ants.get(ant)) >= 0) {
                                                    //Отправляем собирать урожай с культуры
                                                    anthills.get(i).ants.get(ant).setAction(Actions.GoToFood.toString());
                                                    work.y++;
                                                    anthills.get(i).ants.get(ant).setPurpose(culture.get(index).getCoords());
                                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                                    anthills.get(i).ants.get(ant).WavePropagation(culture.get(index).getCoords());
                                                }
                                            }
                                        }
                                    }

                                    //Если культур нет или нет урожая, то муравей идёт собирать "нейтральную еду"
                                    if (anthills.get(i).ants.get(ant).getAction().equals(Actions.Wait.toString())) {
                                        anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                        anthills.get(i).ants.get(ant).WavePropagation(food_in_wave_algorithm_id);
                                        anthills.get(i).ants.get(ant).setAction(Actions.GoToFood.toString());
                                        anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).ants.get(ant).way.lastElement());
                                        work.y++;
                                    }
                                    //Мы дали муравью работу, можно дальше не искать
                                    break;
                                } else
                                    //Если еды нет и мы не вышли из поиска работы, значит работа для муравья есть, продолжаем искать
                                    continue;
                            }

                            if (work.x == ant_on_water_id) {
                                //Если вода на карте есть, то отправляем муравья добывать воду
                                if (water.size() != 0) {
                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                    anthills.get(i).ants.get(ant).WavePropagation(water_in_wave_algorithm_id);
                                    anthills.get(i).ants.get(ant).setAction(Actions.GoToWater.toString());
                                    anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).ants.get(ant).way.lastElement());
                                    work.y++;
                                    //Если отправили муравья на работу, то завершаем поиск работы
                                    break;
                                } else
                                    //Иначе смотрим следующую работу
                                    continue;
                            }

                            if (work.x == ant_on_material_id) {
                                //Если на карте есть материалы, то отправляем муравья добывать их
                                if (materials.size() != 0) {
                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                    anthills.get(i).ants.get(ant).WavePropagation(material_in_wave_algorithm_id);
                                    anthills.get(i).ants.get(ant).setAction(Actions.GoToMaterial.toString());
                                    anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).ants.get(ant).way.lastElement());
                                    work.y++;
                                    //Если отправили муравья на работу, то завершаем поиск работы
                                    break;
                                } else
                                    //Иначе смотрим следующую работу
                                    continue;
                            }

                            if (work.x == ant_on_build_id) {
                                //Если муравейник нуждается в улучшении и его ещё никто не улучшает, то отправляем муравья работать
                                if (anthills.get(i).getBuild_step() != 0 && work.y == 0) {
                                    anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).getCoords());
                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                    anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).getCoords());
                                    if(anthills.get(i).ants.get(ant).way.size() == 0)
                                        anthills.get(i).ants.get(ant).way.add(anthills.get(i).getCoords());
                                    anthills.get(i).ants.get(ant).setAction(Actions.Build.toString());
                                    work.y++;
                                    //Если отправили муравья на работу, то завершаем поиск работы
                                    break;
                                } else
                                    //Иначе смотрим следующую работу
                                    continue;
                            }

                            if (work.x == ant_on_repair_id) {
                                //Если муравейник нуждается в починке и его ещё никто не чинит, то отправляем муравья работать
                                if (anthills.get(i).getDurability() != anthill_durability && work.y == 0) {
                                    anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).getCoords());
                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                    anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).getCoords());
                                    if(anthills.get(i).ants.get(ant).way.size() == 0)
                                        anthills.get(i).ants.get(ant).way.add(anthills.get(i).getCoords());
                                    anthills.get(i).ants.get(ant).setAction(Actions.Repair.toString());
                                    work.y++;
                                    //Если отправили муравья на работу, то завершаем поиск работы
                                    break;
                                }else
                                    continue;
                            }
                        }
                    }
                    if(!anthills.get(i).ants.get(ant).getAction().equals(Actions.Wait.toString()))
                    {
                        anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);

                        //Если муравей шёл за ресурсами, и в это время появился ресурс того же типа, который располагается к нему ближе, чем тот, к которому он шёл
                        //то строится новый путь до него. А так же если на пути появляется препятствие, то муравей прокладывает новый путь
                        if(true) {
                            if (anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToFood.toString()))
                                anthills.get(i).ants.get(ant).WavePropagation(food_in_wave_algorithm_id);

                            if (anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToWater.toString()))
                                anthills.get(i).ants.get(ant).WavePropagation(water_in_wave_algorithm_id);

                            if (anthills.get(i).ants.get(ant).getAction().equals(Actions.GoToMaterial.toString()))
                                anthills.get(i).ants.get(ant).WavePropagation(material_in_wave_algorithm_id);

                            anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).ants.get(ant).way.lastElement());
                        }

                        //Если на пути к муравейнику появляется препятствие, то муравей прокладывает новый путь
                        if(anthills.get(i).ants.get(ant).getAction().equals(Actions.Repair.toString()) &&
                                anthills.get(i).ants.get(ant).getAction().equals(Actions.Build.toString()) &&
                                anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryFood.toString()) &&
                                anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryWater.toString()) &&
                                anthills.get(i).ants.get(ant).getAction().equals(Actions.CarryMaterial.toString())) {
                            anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).getCoords());
                        }

                        //Ход муравья
                        ant_worker_step(anthills.get(i).ants.get(ant), anthills.get(i));
                    }
                }
                else
                {
                    //Если муравей - воин, то он будет либо охранять яйца, либо патрулировать муравейник, защищая его от вражеских насекомых
                    if(enemy.size() != 0)
                    {
                        anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                        anthills.get(i).ants.get(ant).WavePropagation(enemy_in_wave_algorithm_id);
                        //если враг в радиусе 3-х клеток от воина, то он идёт в нападение
                        if(anthills.get(i).ants.get(ant).way.size() <= 3)
                        {
                            if(anthills.get(i).ants.get(ant).getAction().equals(Actions.PatrolEggs.toString()))
                                anthills.get(i).setGuard(false);

                            anthills.get(i).ants.get(ant).setAction(Actions.Attack.toString());
                            anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).ants.get(ant).way.lastElement());
                        }
                        else
                        {
                            for(insect enemy : enemy)
                                if(enemy.getAction().equals(Actions.BreakAnthill.toString()))
                                {
                                    anthills.get(i).ants.get(ant).setAction(Actions.Attack.toString());
                                    anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                                    anthills.get(i).ants.get(ant).setPurpose(enemy.getCoords());
                                    anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).ants.get(ant).getPurpose());

                                }
                        }
                    }

                    //Если меравей ожидает, то если нужен охранник яиц, то муравей идёт в защищать яйца
                    //Иначе он идёт патрулировать около муравейника
                    if(anthills.get(i).ants.get(ant).getAction().equals(Actions.Wait.toString())) {
                        if (!anthills.get(i).isGuard() && !is_anyone_guardian) {
                            anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                            anthills.get(i).ants.get(ant).setPurpose(anthills.get(i).getCoords());
                            anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).getCoords());
                            anthills.get(i).ants.get(ant).setAction(Actions.PatrolEggs.toString());
                        } else {
                            anthills.get(i).ants.get(ant).setMatrixWay(matrixWays);
                            Vector<Point> where_to_step = new Vector<>();
                            for(int t = 0; t < Scene_blocks; t++)
                                for(int k = 0; k < Scene_blocks; k++)
                                    if(main_array[t][k] == blocks.grass.number &&
                                            ((t == anthills.get(i).ants.get(ant).getCoords().x + 1) || (t == anthills.get(i).ants.get(ant).getCoords().x - 1)) &&
                                            ((k == anthills.get(i).ants.get(ant).getCoords().y + 1) || (k == anthills.get(i).ants.get(ant).getCoords().y - 1)) &&
                                            ((t >= anthills.get(i).getCoords().x - 3) && (t <= anthills.get(i).ants.get(ant).getCoords().x + 3)) &&
                                            ((k >= anthills.get(i).ants.get(ant).getCoords().y - 3) || (k <= anthills.get(i).ants.get(ant).getCoords().y + 3)))
                                        where_to_step.add(new Point(t, k));

                            anthills.get(i).ants.get(ant).setPurpose(where_to_step.get(randomize(0, where_to_step.size())));
                            anthills.get(i).ants.get(ant).WavePropagation(anthills.get(i).ants.get(ant).getPurpose());
                            anthills.get(i).ants.get(ant).setAction(Actions.PatrolAnthill.toString());
                        }
                    }
                    ant_fighter_step(anthills.get(i).ants.get(ant), anthills.get(i));
                }
            }

        }
        //Прибавляем шаг
        step++;
        for (sample.food food : culture) food.IncUsable(1);
        for (anthill anthill : anthills) {
            for (int egg = 0; egg < anthill.eggs.size(); egg++) {
                anthill.eggs.get(egg).x--;
                if(anthill.eggs.get(egg).x == 0)
                {
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
                }
            }
        }

        //Если прошла треть дня, то наступает ночь
        if(step == (day_step - (day_step / 3) + 1)) {
            is_night = true;

                for(Node node: main_scene.getChildren())
                {
                    node.setEffect(night);
                }

            for (sample.anthill anthill : anthills) anthill.DecDurability(Dec_anthill_durability);
            for (sample.food food : culture) food.DecDurability(Dec_objects_durability);
            for (sample.food food : neutral_food) food.DecDurability(Dec_objects_durability);
            for (sample.objects objects : materials) objects.DecDurability(Dec_objects_durability);
            for (sample.objects objects : water) objects.DecDurability(Dec_objects_durability);

        }

        //Если прошёл день, наступает утро, все муравьи едят, все объекты повреждаются
        if(step == day_step + 1)
        {
            //День меняется(прибавляется)
            step = 1;
            day++;
            //Наступает утро
            is_night = false;
                for(Node node: main_scene.getChildren())
                {
                    node.setEffect(null);
                }

                //В конце дня все муравьи(включая матку и яйца) едят и пьют, если кому-то не хватило, то они голодают или обезвожены
                for(int i = 0; i < anthills.size(); i++) {
                    anthills.get(i).setQueen_hungry(!anthills.get(i).DecCount_food(1));

                    anthills.get(i).setQueen_dehydration(!anthills.get(i).DecCount_water(1));

                    anthills.get(i).DecDurability(Dec_anthill_durability);

                    //яйца едят. Если не хватает еды или воды для яйца, то оно погибает
                    for(int egg = 0; egg < anthills.get(i).eggs.size(); egg++)
                    {
                        if(!anthills.get(i).DecCount_food(anthills.get(i).eggs.get(egg).y))
                        {
                            anthills.get(i).eggs.removeElement(anthills.get(i).eggs.get(egg));
                            egg--;
                            continue;
                        }

                        if(!anthills.get(i).DecCount_water(anthills.get(i).eggs.get(egg).y))
                        {
                            anthills.get(i).eggs.removeElement(anthills.get(i).eggs.get(egg));
                            egg--;
                        }
                    }

                    for (int ant = 0; ant < anthills.get(i).ants.size(); ant++) {

                        anthills.get(i).ants.get(ant).setHungry(!anthills.get(i).DecCount_food(1));

                        anthills.get(i).ants.get(ant).setDehydration(!anthills.get(i).DecCount_water(1));
                    }
                }

            for (sample.food food : culture) food.DecDurability(Dec_objects_durability);
            for (sample.food food : neutral_food) food.DecDurability(Dec_objects_durability);
            for (sample.objects objects : materials) objects.DecDurability(Dec_objects_durability);
            for (sample.objects objects : water) objects.DecDurability(Dec_objects_durability);

        }

        //Если дождь идёт, то сокращается количество шагов, которые он идёт на 1
        if(is_rain != 0) {
            is_rain--;
            for (sample.objects objects : water) objects.IncDurability(1);
            if(is_rain == 0) {
                    main_scene.setEffect(null);
            }
        }

        //Если дождь не идёт, то есть шанс его наступления
        if(is_rain == 0)
        {
            for_random = randomize(0, 140);

            if(for_random == 0) {
                is_rain = randomize(40, 60);
                    main_scene.setEffect(rain);
            }
        }
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

    private void ant_worker_step(ant ant, anthill anthill) {
            //Если перед муравьём его цель, то он собирает её
            if (ant.getPurpose().x == ant.way.firstElement().x && ant.getPurpose().y == ant.way.firstElement().y) {
                //Если муравей шёл за едой, то он её собирает
                if (ant.getAction().equals(Actions.GoToFood.toString())) {
                    ant.setAction(Actions.CarryFood.toString());
                    filling_picture = blocks.ant_worker_with_food.picture;

                    //Проверяем нейтральную еду. Если муравей идёт не за нейтральной едой, то проверяем культуры
                    if (!grab_food(ant, neutral_food))
                        //Проверяем культуры
                        grab_food(ant, culture);

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
                    if(ant.getAction().equals(Actions.Repair.toString())) {
                        //Если муравей уже в муравейнике, то он чинит его
                        if(ant.getCoords().x == anthill.getCoords().x && ant.getCoords().y == anthill.getCoords().y) {
                            anthill.IncDurability(1);
                            if (anthill.getDurability() == anthill_durability) {
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
                    if (ant.getAction().equals(Actions.GoToFood.toString())) {
                        anthill.IncCount_food(ant.getGrab());
                        ant.setAction(Actions.Wait.toString());
                    }
                    if (ant.getAction().equals(Actions.GoToWater.toString())) {
                        anthill.IncCount_water(ant.getGrab());
                        ant.setAction(Actions.Wait.toString());
                    }
                    if (ant.getAction().equals(Actions.GoToMaterial.toString())) {
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
                        ant.setPurpose(anthill.getCoords());
                        ant.WavePropagation(anthill.getCoords());
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
                        ant.getAction().equals(Actions.GoToMaterial.toString())) {
                    filling_picture = blocks.ant_worker.picture;
                }

                if (ant.getAction().equals(Actions.CarryFood.toString())) {
                    filling_picture = blocks.ant_worker_with_food.picture;
                }

                if (ant.getAction().equals(Actions.CarryWater.toString())) {
                    filling_picture = blocks.ant_worker_with_water.picture;
                }

                if (ant.getAction().equals(Actions.CarryMaterial.toString())) {
                    filling_picture = blocks.ant_worker_with_material.picture;
                }

                set_image(ant.getCoords(), sprite_cut, blocks.ant_worker.number, ant_in_wave_algorithm_id);
                ant.setSprite_cut(sprite_cut);
            }
}


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
                    }
                    else
                    {
                        Point sprite_cut = rotation(ant.getCoords().x, ant.getCoords().y, ant.way.firstElement().x, ant.way.firstElement().y);
                        delete_object_image(ant.getCoords());
                        ant.setCoords(ant.way.firstElement());
                        filling_picture = blocks.ant_fighter.picture;
                        ant.way.remove(0);
                        set_image(ant.getCoords(), sprite_cut, blocks.ant_fighter.number, ant_in_wave_algorithm_id);
                    }
                }
            }

            if(ant.getAction().equals(Actions.PatrolAnthill.toString()))
            {

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
    private boolean grab_food(ant ant, Vector<food> food) {
        for(int i = 0; i < food.size(); i++)
            if(ant.getPurpose().x == food.get(i).getCoords().x && ant.getPurpose().y == food.get(i).getCoords().y)
            {
                if(food.get(i).getUsable() - (ant_grab_object(ant)) > 0) {
                    food.get(i).DecUsable(ant_grab_object(ant));
                    ant.setGrab(ant_grab_object(ant));
                }
                else {
                    ant.setGrab(food.get(i).getUsable());
                    food.get(i).DecUsable(food.get(i).getUsable());
                    delete_object_image(food.get(i).getCoords());
                    Empty_place.add(food.get(i).getCoords());
                    matrixWays[food.get(i).getCoords().x][food.get(i).getCoords().y] = empty_place_in_wave_algorithm_id;
                    food.remove(i);
                }

                return true;
            }
        return false;
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

}
