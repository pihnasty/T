package trestview.orderplaninigperspective.tables;

import entityProduction.Line;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.Observable;
import java.util.Observer;

/**
 * Username: dummy_user
 * Date: 5/14/2017
 * Time: 9:05 PM
 */
public class MyView extends StackPane implements Observer {
    private MyModel myModel;
    private MyController myController;

    public MyView(MyModel myModel, MyController myController) {
        this.myModel = myModel;
        this.myController = myController;

        printButton();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void printButton() {
        this.getChildren().clear();
//        myModel.getWork().getOrders().forEach(order -> this.getChildren().add(new Label(order.getName())));

        System.out.println("==============IEHOROV===========");
        myModel.getWork().getOrders().forEach(order -> System.out.println(order.getName()));


        this.setWidth(200);
        this.setHeight(1000);
        GridPane gridPane = new GridPane();


        this.getChildren().add(gridPane);
        myModel.getWork().getOrders().forEach(order -> {
            if (order.getLines().isEmpty()) return;

            TableView<Line> tableView = new TableView<>();
            ObservableList<Line> data =
                    FXCollections.observableArrayList(order.getLines());

            TableColumn<Line, String> firstNameCol = new TableColumn<>(order.getName());
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableView.getColumns().add(firstNameCol);
            tableView.setItems(data);

            tableView.setFixedCellSize(25);
            tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(Bindings.size(tableView.getItems()).add(1.01)));
            tableView.minHeightProperty().bind(tableView.prefHeightProperty());
            tableView.maxHeightProperty().bind(tableView.prefHeightProperty());

            gridPane.addRow(order.getId(), tableView);
        });
    }
}
