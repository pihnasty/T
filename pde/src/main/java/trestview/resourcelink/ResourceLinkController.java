package trestview.resourcelink;

import javafx.fxml.Initializable;
import trestview.dictionary.DictionaryModel;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class ResourceLinkController implements Initializable {

    private ResourceLinkModel resourceLinkModel;
    public ResourceLinkController (ResourceLinkModel resourceLinkModel) {
        this.resourceLinkModel = resourceLinkModel;
     }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
