package sample;

import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import com.sun.deploy.security.SelectableSecurityManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class Controller {


    private String img_url = "/sample/images/grass.jpg";
    private Image filling_picture = new Image(img_url, 45, 45, true, false);
    private InnerShadow night = new InnerShadow();
    private InnerShadow rain = new InnerShadow();

    public final static Integer Scene_blocks = 20;

    private boolean is_building_in_process = false;
    private boolean is_simulation_in_process = false;
    private boolean auto_mod = false;

    private int filling_block = blocks.grass.number;
    private int[][] main_array = new int[Scene_blocks][Scene_blocks];
    private int[][] matrixWays = new int[Scene_blocks][Scene_blocks];

    Vector<Pair<Integer, Integer>> Empty_place = new Vector<>();

    private Vector<Vector<ant>> ants = new Vector<>();
    private Vector<anthill> anthills = new Vector<>();
    private Vector<Vector<Integer>> eggs = new Vector<>();
    private Vector<food> culture = new Vector<>();
    private Vector<food> neutral_food = new Vector<>();
    private Vector<objects> materials = new Vector<>();
    private Vector<objects> water = new Vector<>();

    private int food_in_wave_algorithm_id = 100;
    private int water_in_wave_algorithm_id = 101;
    private int material_in_wave_algorithm_id = 102;

    private final int min_material_durability = 30;
    private final int max_material_durability = 40;

    private final int min_water_durability = 80;
    private final int max_water_durability = 100;

    private final int min_food_durability = 6;
    private final int max_food_durability = 12;

    private final int min_food_usable = 30;
    private final int max_food_usable = 40;

    private final int how_step_to_born = 80;

    private final int anthill_durability = 20;

    private final int min_characteristic = 2;
    private final int max_characteristic = 10;
    private final int min_ant_health = 25;
    private final int max_ant_health = 50;

    private int anthill_count = 0;
    private final int MaxAnthillCount = 2;

    private int day;
    private int step;

    private int[] anthill_levelUp_food = {30, 60};
    private int[] anthill_levelUp_materials = {40, 80};
    private int[] anthill_levelUp_water = {20, 40};

    private int begin_count_of_ants = 1;

    private final int Dec_object_durability = 1;

    private int chance_to_spawn_food;
    private int chance_to_spawn_material;
    private int chance_to_spawn_enemy;
    private int chance_to_spawn_water;

    private boolean is_night;
    private boolean is_rain;

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
                        anthills.add(new anthill(new Pair<>(i, j), anthill_durability, main_array[i][j], anthill_index));
                        ants.add(new Vector<>());
                        eggs.add(new Vector<>());

                        for(int k = 0; k < begin_count_of_ants; k++) {
                            ants.get(anthill_index).add(new ant(randomize(min_ant_health, max_ant_health), randomize(min_characteristic, max_characteristic),
                                    randomize(min_characteristic, max_characteristic), randomize(min_characteristic, max_characteristic),
                                    new Pair<>(i, j)));
                        }

                        matrixWays[i][j] = 99;
                    anthill_index++;
                }
                if(main_array[i][j] == blocks.grass.number) {
                    Empty_place.add(new Pair<>(i, j));
                    matrixWays[i][j] = -1;
                }

                if(main_array[i][j] == blocks.apple.number || main_array[i][j] == blocks.infected_plant.number || main_array[i][j] == blocks.mushrooms.number)
                {
                    neutral_food.add(new food(new Pair<>(i, j), randomize(min_food_durability, max_food_durability),
                            main_array[i][j], randomize(min_food_usable, max_food_usable)));
                    matrixWays[i][j] = food_in_wave_algorithm_id;
                }
                if(main_array[i][j] == blocks.water.number)
                {
                    water.add(new objects(new Pair<>(i, j), randomize(min_water_durability, max_water_durability), main_array[i][j]));
                    matrixWays[i][j] = water_in_wave_algorithm_id;
                }
                if(main_array[i][j] == blocks.plant.number || main_array[i][j] == blocks.stick.number)
                {
                    materials.add(new objects(new Pair<>(i, j), randomize(min_material_durability, max_material_durability), main_array[i][j]));
                    matrixWays[i][j] = material_in_wave_algorithm_id;
                }
            }

            for(int i = 0; i < anthills.size(); i++)
                for(int j = 0; j < begin_count_of_ants; j++)
                    ants.get(i).get(j).setMatrixWay(matrixWays);

            ants.get(0).get(0).WavePropagation(material_in_wave_algorithm_id);

            day = 1;
            step = 1;
            is_rain = false;
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
        if(auto_mod)
            auto_mod = false;
        else
            auto_mod = true;

        while(auto_mod)
        {
        }
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
                main_scene.add(new ImageView(filling_picture), x, y);
            }
        }



// Установить ночь
        ObservableList<Node> observableList = main_scene.getChildren();

/*
        for(int i = 0; i < observableList.size(); i++)
        {
            observableList.get(i).effectProperty().setValue(night);
        }*/

        //Установить дождь
       /* main_scene.setEffect(rain);*/
    }

    void change_picture()
    {
            main_scene.getChildren().forEach(element -> {
                element.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(is_building_in_process) {
                            int matrixX = main_scene.getColumnIndex(element);
                            int matrixY = main_scene.getRowIndex(element);

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
                            main_scene.add(new ImageView(filling_picture), matrixX, matrixY);
                        }
                    }
                });
            });
    }

    int randomize(int min, int max)
    {
        return min + (int) (Math.random() * max);
    }

    void Step()
    {
        for(int i = 0; i < anthills.size(); i++)
        {
            //+1 Матка
            int ant_count = anthills.get(i).getHow_ant() + 1;

            //Если достаточно материалов, еды и воды, при этом другое улучшение закончено и муравейник не максимального уровня, то начинается улучшение
            //При этом улучшение требует муравья, который будет заниматься строительством (сам процесс строительства описан при распределении работы между муравьями)
            if((anthills.get(i).getBuild_step() == 0) && (anthills.get(i).getAnthill_level() != 3) &&
                    (anthills.get(i).getCount_food() >= anthill_levelUp_food[anthills.get(i).getAnthill_level() - 1] + ant_count * 3) &&
                    (anthills.get(i).getCount_water() >= anthill_levelUp_water[anthills.get(i).getAnthill_level() - 1] + ant_count * 3 +
                            (anthill_durability - anthills.get(i).getDurability()) + Dec_object_durability * 4) &&
                    (anthills.get(i).getCount_materials() >= anthill_levelUp_materials[anthills.get(i).getAnthill_level() - 1] +
                            (anthill_durability - anthills.get(i).getDurability()) + Dec_object_durability * 4) )
            {
                anthills.get(i).setBuild_step();
            }

            //Если есть место для ещё одного муравья и еды хватит всем муравьям(учитывая матку и новорождённое яйцо) на 2 дня, то матка может родить яйцо
            //Иначе, если еды не хватает, то матка создаёт яйцо, чтобы его съесть, при этом матка должна быть сытая. Так она может делать каждые пол дня (если сытая)
            //Так как это считается канибаллизмом, то это делается только при вынужденных мерах
            if(ant_count - 1 < anthills.get(i).getAnt_capacity() && anthills.get(i).getCount_food() >= ((ant_count + 1) * 2))
            {
                eggs.get(i).add(how_step_to_born);
                anthills.get(i).IncCount_of_eggs();
                anthills.get(i).setQueen_hungry(true);
            }
            else
            {
                if(anthills.get(i).getCount_food() < ant_count)
                {
                    anthills.get(i).IncCount_food(2);
                    anthills.get(i).IncCount_water(2);
                    anthills.get(i).setQueen_hungry(true);
                }
            }

            //Для каждого объекта, который может заспавниться, есть шанс спавна

            //яблоко
            if(randomize(0, 100) >= 0 || randomize(0, 100) < chance_to_spawn_food)
            {
                int random_empty_place = randomize(0, Empty_place.size() - 1);
                Pair<Integer, Integer> coords = Empty_place.get(random_empty_place);

                main_scene.getChildren().forEach(element -> {
                    if(coords.getKey() == main_scene.getColumnIndex(element) && coords.getValue() == main_scene.getRowIndex(element))
                    {
                        main_scene.getChildren().remove(element);

                        filling_block = blocks.apple.number;
                        filling_picture = blocks.apple.picture;

                        main_array[coords.getKey()][coords.getValue()] = filling_block;
                        main_scene.add(new ImageView(filling_picture), coords.getKey(), coords.getValue());

                        Empty_place.removeElementAt(random_empty_place);
                    }
                });
            }

            //гриб
            if(randomize(0, 100) >= 0 || randomize(0, 100) < chance_to_spawn_food)
            {
                int random_empty_place = randomize(0, Empty_place.size() - 1);
                Pair<Integer, Integer> coords = Empty_place.get(random_empty_place);

                main_scene.getChildren().forEach(element -> {
                    if(coords.getKey() == main_scene.getColumnIndex(element) && coords.getValue() == main_scene.getRowIndex(element))
                    {
                        main_scene.getChildren().remove(element);

                        filling_block = blocks.mushrooms.number;
                        filling_picture = blocks.mushrooms.picture;

                        main_array[coords.getKey()][coords.getValue()] = filling_block;
                        main_scene.add(new ImageView(filling_picture), coords.getKey(), coords.getValue());

                        Empty_place.removeElementAt(random_empty_place);
                    }
                });
            }

            //растение
            if(randomize(0, 100) >= 0 || randomize(0, 100) < chance_to_spawn_material)
            {
                int random_empty_place = randomize(0, Empty_place.size() - 1);
                Pair<Integer, Integer> coords = Empty_place.get(random_empty_place);

                main_scene.getChildren().forEach(element -> {
                    if(coords.getKey() == main_scene.getColumnIndex(element) && coords.getValue() == main_scene.getRowIndex(element))
                    {
                        main_scene.getChildren().remove(element);

                        filling_block = blocks.plant.number;
                        filling_picture = blocks.plant.picture;

                        main_array[coords.getKey()][coords.getValue()] = filling_block;
                        main_scene.add(new ImageView(filling_picture), coords.getKey(), coords.getValue());

                        Empty_place.removeElementAt(random_empty_place);
                    }
                });
            }

            //палочка
            if(randomize(0, 100) >= 0 || randomize(0, 100) < chance_to_spawn_material)
            {
                int random_empty_place = randomize(0, Empty_place.size() - 1);
                Pair<Integer, Integer> coords = Empty_place.get(random_empty_place);

                main_scene.getChildren().forEach(element -> {
                    if(coords.getKey() == main_scene.getColumnIndex(element) && coords.getValue() == main_scene.getRowIndex(element))
                    {
                        main_scene.getChildren().remove(element);

                        filling_block = blocks.stick.number;
                        filling_picture = blocks.stick.picture;

                        main_array[coords.getKey()][coords.getValue()] = filling_block;
                        main_scene.add(new ImageView(filling_picture), coords.getKey(), coords.getValue());

                        Empty_place.removeElementAt(random_empty_place);
                    }
                });
            }

            //Узнаём, какой муравей чем занят, чтобы свободные муравьи делали полезную работу(чтобы не было так, что все муравьи, например, носят еду)
            Point[] works = new Point[4];
            int ant_on_food_id = 1;
            int ant_on_water_id = 2;
            int ant_on_material_id = 3;
            int ant_on_build_or_repair_id = 4;
            works[0] = new Point(ant_on_food_id, 0);
            works[1] = new Point(ant_on_water_id, 0);
            works[2] = new Point(ant_on_material_id, 0);
            works[3] = new Point(ant_on_build_or_repair_id, 0);

            //Узнаём кто чем занимается
            for(int ant = 0; ant < ants.get(i).size() - 1; ant++) {
                if(ants.get(i).get(ant).getAction() == Actions.GoToFood.toString() || ants.get(i).get(ant).getAction() == Actions.CarryFood.toString())
                    works[0].y++;
                if(ants.get(i).get(ant).getAction() == Actions.GoToWater.toString() || ants.get(i).get(ant).getAction() == Actions.CarryWater.toString())
                    works[1].y++;
                if(ants.get(i).get(ant).getAction() == Actions.GoToMaterial.toString() || ants.get(i).get(ant).getAction() == Actions.CarryMaterial.toString())
                    works[2].y++;
                if(ants.get(i).get(ant).getAction() == Actions.Build.toString() || ants.get(i).get(ant).getAction() == Actions.Repair.toString())
                    works[3].y++;
            }

            bubbleSort(works);

            //Планируем действия муравьёв
            for(int ant = 0; ant < ants.get(i).size() - 1; ant++)
            {

                //Если муравей - рабочий, то он будет либо таскать пищу, воду, материалы, разводить тлю, или строить и ремонтировать муравейник
                if(ants.get(i).get(ant).isWorker())
                {
                    //Если муравей ожидает, то даём ему работу
                    if(ants.get(i).get(ant).getAction() == Actions.Wait.toString()) {
                        //Сначала проверяем, если вообще чем заняться муравью
                        if(neutral_food.size() == 0 || culture.size() == 0 || water.size() == 0 ||
                                materials.size() == 0 || anthills.get(i).getDurability() == anthill_durability || anthills.get(i).getBuild_step() == 0)
                            continue;

                        //Проверяем работу, на которой меньше всего муравьёв
                        for (int d = 0; d < works.length; d++) {
                             //Если это поиск еды...
                            if (works[d].x == ant_on_food_id) {
                                //Проверяем есть ли вообще какая-нибудь еда на карте
                                if(neutral_food.size() != 0 || culture.size() != 0)
                                {
                                    //Муравей идёт собирать культуру, если они есть и есть урожай. Если нет, то ищем близжайшую еду
                                    if(anthills.get(i).getMilk_farm() > 0 || anthills.get(i).getMushrooms_farm() > 0) {
                                        //Для начала выбирается культура с наибольшимколичеством еды. Сделано это для того, чтобы достичь эффективности культур
                                        //То есть чтобы все они производили еду (если бы муравей собирал только одну культуру,
                                        // то другая была бы переполнена урожаем и не могла производить еду)
                                        int[] sort_cult = new int[4];
                                        for(int k = 0; k < culture.size(); k++)
                                        {
                                            sort_cult[k] = culture.get(k).getUsable();
                                        }

                                        Arrays.sort(sort_cult);
                                        int index = -1;
                                        for(int k = 0; k < culture.size(); k++)
                                            if(sort_cult[0] == culture.get(k).getUsable())
                                                index = k;

                                            if(index != -1) {
                                                //Если культура скоро перестанет плодоносить и исчезнет, то нужно собрать весь оставшийся урожай
                                                if (culture.get(index).getDurability() == 1) {
                                                    if (culture.get(index).getUsable() != 0) {
                                                        //Отправляем муравья собирать последние урожаи с культуры
                                                        ants.get(i).get(ant).setAction(Actions.GoToFood.toString());
                                                        works[d].y++;
                                                        ants.get(i).get(ant).setPurpose(culture.get(index).getCoords());
                                                        ants.get(i).get(ant).WavePropagation();
                                                    }
                                                } else {
                                                    //Если количество еды, которое может унести муравей больше или равно количеству урожая, то муравей идёт собирать
                                                    if (culture.get(index).getUsable() - ant_grab_object(ants.get(i).get(ant)) >= 0) {
                                                        //Отправляем собирать урожай с культуры
                                                        ants.get(i).get(ant).setAction(Actions.GoToFood.toString());
                                                        works[d].y++;
                                                        ants.get(i).get(ant).setPurpose(culture.get(index).getCoords());
                                                        ants.get(i).get(ant).WavePropagation();
                                                    }
                                                }
                                            }
                                    }

                                    //Если культур нет или нет урожая, то муравей идёт собирать "нейтральную еду"
                                    if(ants.get(i).get(ant).getAction() == Actions.Wait.toString()) {
                                        ants.get(i).get(ant).WavePropagation(food_in_wave_algorithm_id);
                                        ants.get(i).get(ant).setAction(Actions.GoToFood.toString());
                                        works[d].y++;
                                    }
                                    //Мы дали муравью работу, можно дальше не искать
                                        break;
                                }
                                else
                                    //Если воды нет и мы не вышли из поиска работы, значит работа для муравья есть, продолжаем искать
                                    continue;
                            }

                            if (works[d].x == ant_on_water_id) {
                                //Если вода на карте есть, то отправляем муравья добывать воду
                                if(water.size() != 0)
                                {
                                    ants.get(i).get(ant).WavePropagation(water_in_wave_algorithm_id);
                                    ants.get(i).get(ant).setAction(Actions.GoToWater.toString());
                                    works[d].y++;
                                }
                                else
                                    //Иначе смотрим следующую работу
                                    continue;
                                //Если отправили муравья на работу, то завершаем поиск работы
                                break;
                            }

                            if (works[d].x == ant_on_material_id) {
                                //Если на карте есть материалы, то отправляем муравья добывать их
                                if(materials.size() != 0)
                                {
                                    ants.get(i).get(ant).WavePropagation(material_in_wave_algorithm_id);
                                    ants.get(i).get(ant).setAction(Actions.GoToMaterial.toString());
                                    works[d].y++;
                                }
                                else
                                    //Иначе смотрим следующую работу
                                    continue;
                                //Если отправили муравья на работу, то завершаем поиск работы
                                break;
                            }

                            if (works[d].x == ant_on_build_or_repair_id)
                            {
                                //Если муравейник нуждается в мочинке или улучшении, то отправляем муравья работать
                                if(anthills.get(i).getDurability() != anthill_durability)
                                {
                                    ants.get(i).get(ant).setPurpose(anthills.get(i).getCoords());
                                    ants.get(i).get(ant).WavePropagation();
                                    ants.get(i).get(ant).setAction(Actions.Repair.toString());
                                    works[d].y++;
                                }
                                else
                                {
                                    if(anthills.get(i).getBuild_step() != 0)
                                    {
                                        ants.get(i).get(ant).setPurpose(anthills.get(i).getCoords());
                                        ants.get(i).get(ant).WavePropagation();
                                        ants.get(i).get(ant).setAction(Actions.Build.toString());
                                        works[d].y++;
                                    }
                                    else
                                        continue;
                                }
                            }
                            break;
                        }
                    }

                }
                else
                {
                    //Если муравей - воин, то он будет либо охранять яйца, либо патрулировать муравейник, защищая его от вражеских насекомых

                }
                        //Ходим муравьями
            }

        }

    }

    public boolean isIts_night() {
        return is_night;
    }

    public boolean isIts_rain() {
        return is_rain;
    }

    public int ant_grab_object(ant ant)
    {
        return (ant.getStrength() / 2) + (ant.getStrength() % 2);
    }

    public static void bubbleSort(Point[] arr){
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
}
