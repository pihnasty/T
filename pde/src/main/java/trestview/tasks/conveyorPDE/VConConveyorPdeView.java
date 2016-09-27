package trestview.tasks.conveyorPDE;
        import designpatterns.InitializableDS;
        import designpatterns.MVC;
        import designpatterns.ObservableDS;
        import designpatterns.observerdsall.BorderPaneObserverDS;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.control.Label;
        import javafx.scene.layout.VBox;
        import persistence.loader.XmlRW;
        import trestview.linechart.LineChartController;
        import trestview.linechart.LineChartModel;
        import trestview.linechart.LineChartP;

        import java.awt.geom.Point2D;
        import java.util.List;
        import java.util.Observable;

public class VConConveyorPdeView extends BorderPaneObserverDS {

    @FXML
    private Label label = new Label();
    private    List<Point2D.Double> listConT =null;
    private    List<Point2D.Double> listConS=null;

    public  VConConveyorPdeView(ObservableDS observebleDS, InitializableDS initializableDS){
        super(observebleDS,initializableDS);
        inizilize(observableDS);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/menu/tasks/conveyorPDE/vConConveyorPdeView.fxml","ui", "trestview/menu/tasks/conveyorPDE/vConConveyorPdeStyle.css");



        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("T"), null );

        MVC LineChart2MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("S"), null );

        MVC LineChart3MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("S"), null );

        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        vBox.getChildren().addAll((LineChartP)LineChart1MVC.getView(),(LineChartP)LineChart2MVC.getView());
        vBox1.getChildren().addAll((LineChartP)LineChart3MVC.getView());
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