package trestview.table;

import javafx.fxml.Initializable;
import trestview.table.tablemodel.TableModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pom on 28.02.2016.
 */
public class TableController implements Initializable {

    private TableModel tableModel;

    public TableController(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
