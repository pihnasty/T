package trestview.tasks.conveyorPDE;

import designpatterns.ObservableDS;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class VConConveyorPdeController implements Initializable
{
    private Label label;

    public VConConveyorPdeController(ObservableDS o) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setLabel(Label label) {
        this.label = label;
    }


}
