package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Controller {

    String img_url = "/sample/images/grass.jpg";
    final Integer Scene_blocks = 20;
    boolean is_building_in_process = false;
    Tooltip hint = new Tooltip();

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
    private ImageView image_ant_worker;

    @FXML
    private ImageView image_ant_fighter;

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
    }

/////////////////
    @FXML
    void ChooseWater(MouseEvent event) {
        img_url = "/sample/images/water.jpg";
    }

    @FXML
    void WaterHintAppear(MouseEvent event) {
        hint.setText("Вода");
        
    }

    @FXML
    void WaterHintDisappear(MouseEvent event) {

    }
///////////////////////////

    @FXML
    void ChooseAntFighter(MouseEvent event) {
        img_url = "/sample/images/ant_fighter.png";
    }

    @FXML
    void ChooseAntWorker(MouseEvent event) {
        img_url = "/sample/images/ant_worker.png";
    }

    @FXML
    void ChooseAnthill(MouseEvent event) {
        img_url = "/sample/images/anthill.png";
    }

    @FXML
    void ChooseFlower(MouseEvent event) {
        img_url = "/sample/images/plant.png";
    }

    @FXML
    void ChooseGrass(MouseEvent event) {
        img_url = "/sample/images/grass.jpg";
    }

    @FXML
    void ChooseInsectFlower(MouseEvent event) {
        img_url = "/sample/images/infected_plant.png";
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

        image_ant_fighter.setVisible(true);
        image_ant_worker.setVisible(true);
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

        image_ant_fighter.setVisible(false);
        image_ant_worker.setVisible(false);
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

        Image filling_picture = new Image(img_url, 45, 45, true, false);
        ObservableList<Node> grid_items = main_scene.getChildren();

        for (int x = 0; x < Scene_blocks; x++) {
            for (int y = 0; y < Scene_blocks; y++) {
                main_scene.add(new ImageView(filling_picture), x, y);
            }
        }

    }

    void change_picture()
    {
            main_scene.getChildren().forEach(element -> {
                element.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(is_building_in_process) {
                            int matrixX = main_scene.getRowIndex(element);
                            int matrixY = main_scene.getColumnIndex(element);
                            main_scene.getChildren().remove(element);

                            Image filling_picture = new Image(img_url, 45, 45, true, false);

                            main_scene.add(new ImageView(filling_picture), matrixY, matrixX);
                        }
                    }
                });
            });
    }
}
