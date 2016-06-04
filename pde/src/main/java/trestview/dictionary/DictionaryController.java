package trestview.dictionary;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class DictionaryController implements Initializable {

    private DictionaryModel dictionaryModel;

    public DictionaryController (DictionaryModel dictionaryModel) {
        this.dictionaryModel = dictionaryModel;
     }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
