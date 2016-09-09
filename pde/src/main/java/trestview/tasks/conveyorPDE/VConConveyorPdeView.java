package trestview.tasks.conveyorPDE;



        import designpatterns.ObservableDS;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.control.Label;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Text;
        import persistence.loader.XmlRW;
        import trestview.linechart.LineChartController;
        import trestview.linechart.LineChartModel;
        import trestview.linechart.LineChartP;

        import java.awt.geom.Point2D;
        import java.util.List;
        import java.util.Observable;
        import java.util.Observer;


public class VConConveyorPdeView extends BorderPane implements Observer {
   private ObservableDS model;

    @FXML
    private Label label = new Label();
    private    List<Point2D.Double> listConT =null;
    private    List<Point2D.Double> listConS=null;

    public  VConConveyorPdeView(ObservableDS observebleDS, VConConveyorPdeController vConConveyorPdeController){

        model = observebleDS;
        inizilize(model);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,vConConveyorPdeController, "trestview/menu/tasks/conveyorPDE/vConConveyorPdeView.fxml","ui", "trestview/menu/tasks/conveyorPDE/vConConveyorPdeStyle.css");


        ((VСonConveyorPdeModel)model).dataBuild("T");
        ObservableDS m1 = model;


        LineChartModel lineChartModel = new LineChartModel(m1, null);
        LineChartController lineChartController = new LineChartController(lineChartModel);
        LineChartP lineChartP = new LineChartP(lineChartModel,lineChartController);
        lineChartModel.addObserver(lineChartP);


        ((VСonConveyorPdeModel)model).dataBuild("S");
        ObservableDS m2 = model;


        LineChartModel lineChartModel2 = new LineChartModel(m2, null);
        LineChartController lineChartController2 = new LineChartController(lineChartModel);
        LineChartP lineChartP2 = new LineChartP(lineChartModel,lineChartController);
        lineChartModel.addObserver(lineChartP2);


        ((VСonConveyorPdeModel)model).dataBuild("S");
        ObservableDS m3 = model;
        LineChartModel lineChartModel3 = new LineChartModel(m3, null);
        LineChartController lineChartController3 = new LineChartController(lineChartModel);
        LineChartP lineChartP3 = new LineChartP(lineChartModel,lineChartController);
        lineChartModel.addObserver(lineChartP2);

        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        vBox.getChildren().addAll(lineChartP,lineChartP2);
        vBox1.getChildren().addAll(lineChartP3);
        setLeft(vBox);
        setRight(vBox1);

    }



    public void update(Observable observeble, Object arg) {
        inizilize(observeble);

    }

    private void inizilize(Observable observeble) {
//        model=observeble;
//        LineChartModel lineChartModel = new LineChartModel(observeble);
//
//        List<Point2D.Double> list = lineChartModel.getlist();
//
//       // ObservableList<String> y
    }


    static public void main(String[] args) {}

}