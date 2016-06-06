package trestview.hboxpane;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import persistence.loader.XmlRW;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by pom on 07.02.2016.
 */
public class HboxpaneView extends HBox implements Observer {
    private HboxpaneModel hboxpaneModel;
    public HboxpaneView() {}

    public HboxpaneView(HboxpaneModel hboxpaneModel, HboxpaneController hboxpaneController ) {
        this.hboxpaneModel =hboxpaneModel;
        FXMLLoader fxmlLoader =   XmlRW.fxmlLoad(this,hboxpaneController, "trestview\\hboxpane\\hboxpaneView.fxml","ui", "");
    }

    @Override
    public void update(Observable o, Object arg) {   hboxpaneModel = (HboxpaneModel)o;  }
}
