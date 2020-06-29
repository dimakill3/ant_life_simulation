package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private final Integer Scene_blocks = 20;
    private boolean is_building_in_process = false;
    private Image filling_picture = new Image(img_url, 45, 45, true, false);
    private int filling_block = 0;
    private int[][] main_array = new int[Scene_blocks][Scene_blocks];
    private InnerShadow night = new InnerShadow();
    Pair<String, Integer>[] objects_id = new Pair[10];

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
        img_url = "/sample/images/apple_on_grass.png";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.apple.number;
    }

/////////////////
    @FXML
    void ChooseWater(MouseEvent event) {
        img_url = "/sample/images/water.jpg";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.water.number;
    }

    @FXML
    void WaterHintAppear(MouseEvent event) {

        
    }

    @FXML
    void WaterHintDisappear(MouseEvent event) {

    }
///////////////////////////

    @FXML
    void ChooseAnthill(MouseEvent event) {
        img_url = "/sample/images/anthill.png";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.anthill.number;
    }

    @FXML
    void ChooseFlower(MouseEvent event) {
        img_url = "/sample/images/plant.png";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.plant.number;
    }

    @FXML
    void ChooseGrass(MouseEvent event) {
        img_url = "/sample/images/grass.jpg";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.grass.number;
    }

    @FXML
    void ChooseStick(MouseEvent event) {
        img_url = "/sample/images/stick.png";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.stick.number;
    }

    @FXML
    void ChooseInsectFlower(MouseEvent event) {
        img_url = "/sample/images/infected_plant.png";
        filling_picture = new Image(img_url, 45, 45, true, false);
        filling_block = blocks.infected_plant.number;
    }

    @FXML
    void CellClick(MouseEvent event) {
        if(is_building_in_process) {
            change_picture();
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
                            main_scene.getChildren().remove(element);

                            main_array[matrixX][matrixY] = filling_block;
                            main_scene.add(new ImageView(filling_picture), matrixX, matrixY);
                        }
                    }
                });
            });
    }

    void find_need_object(String name)
    {

    }
}
