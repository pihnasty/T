package trestview.hboxpane;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import persistence.loader.XmlRW;
import resources.images.icons.IconT;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by pom on 07.02.2016.
 */
public class HboxpaneController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button delButton;

    private HboxpaneModel hboxpaneModel;

    public HboxpaneController() {

    }

    public HboxpaneController(HboxpaneModel hboxpaneModel) {
        this.hboxpaneModel = hboxpaneModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addButton. setGraphic(new ImageView(new Image(IconT.class.getResource("add.png").toString())));
        editButton.setGraphic(new ImageView(new Image(IconT.class.getResource("edit.png").toString())));
        saveButton.setGraphic(new ImageView(new Image(IconT.class.getResource("save.png").toString())));
        delButton. setGraphic(new ImageView(new Image(IconT.class.getResource("del.png").toString())));
    }


    @FXML
    public void handleAddAsAction(MouseEvent e )  { hboxpaneModel.addRowTable();  }
    @FXML
    public void handleSaveAsAction(MouseEvent e ) { hboxpaneModel.saveRowTable(); }
    @FXML
    public void handleEditAsAction(MouseEvent e ) { hboxpaneModel.editRowTable(); }
    @FXML
    public void handleDelAsAction(MouseEvent e )  { hboxpaneModel.delRowTable(); }

}
