package trestview.tasks.conveyorPDE;

import designpatterns.InitializableDS;
import designpatterns.ObservableDS;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;


public class VConConveyorPdeController extends InitializableDS
{
    private Label label;

    public VConConveyorPdeController(ObservableDS observableDS) {
        super(observableDS);
    }


    public void setLabel(Label label) {
        this.label = label;
    }


}
