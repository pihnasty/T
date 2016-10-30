package trestview.tasks.conveyorPDE;
        import designpatterns.InitializableDS;
        import designpatterns.MVC;
        import designpatterns.ObservableDS;
        import designpatterns.observerdsall.BorderPaneObserverDS;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.control.Label;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import persistence.loader.XmlRW;
        import trestview.linechart.LineChartController;
        import trestview.linechart.LineChartModel;
        import trestview.linechart.LineChartP;

        import java.awt.*;
        import java.awt.geom.Point2D;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Observable;
        import java.util.ResourceBundle;

public class VConConveyorPdeView extends BorderPaneObserverDS {

    @FXML
    private Label label = new Label();
    private    List<Point2D.Double> listConT =null;
    private    List<Point2D.Double> listConS=null;

    public  VConConveyorPdeView(ObservableDS observebleDS, InitializableDS initializableDS){
        super(observebleDS,initializableDS);
        inizilize(observableDS);
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/tasks/conveyorPDE/vConConveyorPdeView.fxml","ui", "trestview/tasks/conveyorPDE/vConConveyorPdeStyle.css");

     //   setStyle("-fx-background-color: red;");

 //        getStyleClass().add("vConConveyorPdeStyle");
   //     getStylesheets().add("barchartsample/Chart.css");

        int widthSection = (int)1.5*Toolkit.getDefaultToolkit ().getScreenSize ().width/3;

        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("oX=S"), null );

        MVC LineChart2MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("oX=T"), null );

        MVC LineChart3MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("oX=S"), null );


        List<MVC> listMVCs = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCs.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("oX=Salone0"+i), null )
        );

        List<MVC> listMVCt = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCt.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VСonConveyorPdeModel) observableDS).dataBuildVСonConveyorPdeModel("oX=Talone0"+i), null )
        );


        VBox vBoxLeft = new VBox();
        vBoxLeft.getChildren().addAll((LineChartP)LineChart1MVC.getView(),(LineChartP)LineChart2MVC.getView(),(LineChartP)LineChart3MVC.getView());
        setLeft(vBoxLeft);




        VBox vBoxCenterLeft = new VBox();
        VBox vBoxCenterRight = new VBox();
        int j=0;
        for (MVC mvc : listMVCs)
            if(j++ < 5) vBoxCenterLeft.getChildren().add((LineChartP)mvc.getView());
                   else vBoxCenterRight.getChildren().add((LineChartP)mvc.getView());
        Label labelT = new Label(ResourceBundle.getBundle("ui").getString("titleSectionCenter"));
        labelT.setMinHeight(40);
        HBox hBoxCenter = new HBox(vBoxCenterLeft,vBoxCenterRight);
        hBoxCenter.setMaxWidth(widthSection);    hBoxCenter.setMinWidth(widthSection-1);
        setCenter(new VBox (labelT,hBoxCenter));



        VBox vBoxRightLeft = new VBox();
        VBox vBoxRightRight = new VBox();
        j=0;
        for (MVC mvc : listMVCt)
            if(j++ < 5) vBoxRightLeft.getChildren().add((LineChartP)mvc.getView());
            else vBoxRightRight.getChildren().add((LineChartP)mvc.getView());


        Label labelS = new Label(ResourceBundle.getBundle("ui").getString("titleSectionRight"));
        labelS.setMinHeight(40);
        HBox hBoxRight = new HBox(vBoxRightLeft,vBoxRightRight);
        hBoxRight.setMaxWidth(widthSection);    hBoxRight.setMinWidth(widthSection-1);
        setRight(new VBox (labelS, hBoxRight) );

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