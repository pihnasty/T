package trestview.tasks.conveyorPDE;
        import designpatterns.InitializableDS;
        import designpatterns.MVC;
        import designpatterns.ObservableDS;
        import designpatterns.observerdsall.BorderPaneObserverDS;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.Tooltip;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.StackPane;
        import javafx.scene.layout.VBox;
        import persistence.loader.XmlRW;
        import trestview.dictionary.DictionaryController;
        import trestview.dictionary.DictionaryModel;
        import trestview.dictionary.DictionaryView;
        import trestview.linechart.LineChartController;
        import trestview.linechart.LineChartModel;
        import trestview.linechart.LineChartP;
        import trestview.table.tablemodel.abstracttablemodel.Rule;


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



        Button buttonForStrategy01 = new Button();
        buttonForStrategy01.setText("Начальное условие");
        buttonForStrategy01.setTooltip(new Tooltip("sddsdsdsdsd"));

        Button button2 = new Button();
        button2.setText("Начальное условие2");

        HBox hBoxBottom1 = new HBox(buttonForStrategy01, button2);
        setBottom(hBoxBottom1);



        buttonForStrategy01.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((VConConveyorPdeModel) observebleDS).setStrategy(new Strategy01());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((VConConveyorPdeModel) observebleDS).setStrategy(new Strategy02());
            }
        });

    }




    public void update(Observable observeble, Object arg) {
        inizilize((ObservableDS)observeble);

    }

    private void inizilize(ObservableDS observableDS) {
        int widthSection = (int)1.5* Toolkit.getDefaultToolkit ().getScreenSize ().width/3;

        MVC LineChart1MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=S"), null );

        MVC LineChart2MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=T"), null );

        MVC LineChart3MVC  = new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=X"), null );


        List<MVC> listMVCs = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCs.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=Salone0"+i), null )
        );

        List<MVC> listMVCt = new ArrayList<>();
        for (int i=0; i<10; i++)  listMVCt.add( new MVC (LineChartModel.class, LineChartController.class, LineChartP.class,
                ((VConConveyorPdeModel) observableDS).dataBuildVConConveyorPdeModel("oX=Talone0"+i), null )
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
        hBoxCenter.setMaxWidth(widthSection);
        hBoxCenter.setMinWidth(widthSection-1);
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
        hBoxRight.setMaxWidth(widthSection);
        hBoxRight.setMinWidth(widthSection-1);
        setRight(new VBox (labelS, hBoxRight) );
    }

}