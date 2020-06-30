package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

    private final Integer Scene_blocks = 20;

    private boolean is_building_in_process = false;
    private boolean is_simulation_in_process = false;
    private boolean auto_mod = false;

    private int filling_block = blocks.grass.number;
    private int[][] main_array = new int[Scene_blocks][Scene_blocks];

    private Vector ants = new Vector();
    private Vector anthills = new Vector();
    private Vector eggs = new Vector();
    private Vector culture = new Vector();
    private Vector neutral_food = new Vector();
    private Vector materials = new Vector();
    private Vector water = new Vector();

    private final int neutral = -1;
    private final int first_ally = 0;
    private final int second_ally = 1;

    private final int min_material_durability = 30;
    private final int max_material_durability = 40;

    private final int min_water_durability = 80;
    private final int max_water_durability = 100;

    private final int min_food_durability = 6;
    private final int max_food_durability = 12;

    private final int min_food_usable = 30;
    private final int max_food_usable = 40;

    private final int how_step_to_born = 80;

    private final int anthill_durability = 100;
    private int anthill_count = 0;
    private final int MaxAnthillCount = 2;


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

        if(got_anthill == false)
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

        int anthill_number = 0;
        for(int i = 0; i < Scene_blocks; i++)
            for(int j = 0; j < Scene_blocks; j++)
            {
                if(main_array[i][j] == blocks.anthill.number)
                {
                    anthills.add(new anthill(new Pair<Integer, Integer>(i, j), anthill_durability, main_array[i][j], anthill_number));
                }
                if(main_array[i][j] == blocks.apple.number || main_array[i][j] == blocks.infected_plant.number || main_array[i][j] == blocks.mushrooms.number)
                {
                    neutral_food.add(new food(new Pair<Integer, Integer>(i, j), randomize(min_food_durability, max_food_durability),
                            main_array[i][j], randomize(min_food_usable, max_food_usable)));
                }
                if(main_array[i][j] == blocks.water.number)
                {
                    water.add(new objects(new Pair<Integer, Integer>(i, j), randomize(min_water_durability, max_water_durability), main_array[i][j]));
                }
                if(main_array[i][j] == blocks.plant.number || main_array[i][j] == blocks.stick.number)
                {
                    materials.add(new objects(new Pair<Integer, Integer>(i, j), randomize(min_material_durability, max_material_durability), main_array[i][j]));
                }
            }
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
            System.out.println("aa");
        }
    }

    @FXML
    void StepButtonClick(MouseEvent event) {

    }

    @FXML
    void initialize() {
        night.setChoke(0.0);
        night.setWidth(0);
        night.setHeight(255);
        night.setRadius(100);
        night.setBlurType(BlurType.THREE_PASS_BOX);
        night.setColor(Color.BLACK);
        night.setOffsetX(0.0);
        night.setOffsetY(0.0);

        for (int x = 0; x < Scene_blocks; x++) {
            for (int y = 0; y < Scene_blocks; y++) {
                main_scene.add(new ImageView(filling_picture), x, y);
            }
        }


/* Установить ночь
        ObservableList<Node> observableList = main_scene.getChildren();

        for(int i = 0; i < observableList.size(); i++)
        {
            observableList.get(i).setEffect(night);
        }
 */
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

                            if(filling_block == blocks.anthill.number && (anthill_count + 1) <= MaxAnthillCount)
                                anthill_count++;
                            else
                            {
                                Alert error = new Alert(Alert.AlertType.ERROR, "Нельзя поставить более двух муравейников");
                                error.showAndWait();
                                return;
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
}
