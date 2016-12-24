package trestview.routeperspective;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import persistence.loader.XmlRW;
import trestview.hboxpane.HboxpaneController;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.HboxpaneView;
import trestview.routeperspective.schemaroute.SchemaRouteController;
import trestview.routeperspective.schemaroute.SchemaRouteModel;
import trestview.routeperspective.schemaroute.SchemaRouteView;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

public class RoutePerspectiveView extends BorderPaneObserverDS {
    public RoutePerspectiveView (ObservableDS observableDS, InitializableDS initializableDS ) {
        super(observableDS,initializableDS);

        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,initializableDS, "trestview/routeperspective/routeperspectiveView.fxml","ui", "trestview/routeperspective/routeperspectiveStyle.css");

        SplitPane splitPane = new SplitPane();
        BorderPane borderPane = new BorderPane();


//----------------------------------------------------------------------------------------------------------------------
        MVC schemaRouteMVC = new MVC(SchemaRouteModel.class, SchemaRouteController.class, SchemaRouteView.class, this.observableDS, Rule.RoutePerspective);
        SchemaRouteView view = (SchemaRouteView) schemaRouteMVC.getView();
        view.addEventHandler(MouseEvent.MOUSE_MOVED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, (SchemaRouteController)schemaRouteMVC.getController());
//----------------------------------------------------------------------------------------------------------------------
        MVC tableWorkMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, this.observableDS, Rule.Work );
        MVC hboxpaneWorkMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,this.observableDS.getDataSet(), Rule.Work);
        hboxpaneWorkMVC.addObserverP( (TableModel)tableWorkMVC.getModel());
        tableWorkMVC.addObserverP((SchemaRouteModel)schemaRouteMVC.getModel());
        VBox vboxWork = new VBox();

        Label labelWork = new Label(fxmlLoader.getResources().getString("ListManufacturing"));
        labelWork.setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\RowWork.png")));

        vboxWork.getChildren().addAll(labelWork,(HboxpaneView)hboxpaneWorkMVC.getView(),(TableViewP)tableWorkMVC.getView());
        vboxWork.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxWork.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        MVC tableSubject_labourMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, this.observableDS, Rule.Subject_labour );
        MVC hboxpaneSubject_labourMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,this.observableDS.getDataSet(), Rule.Subject_labour);
        hboxpaneSubject_labourMVC.addObserverP( (TableModel)tableSubject_labourMVC.getModel());
        tableSubject_labourMVC.addObserverP((SchemaRouteModel)schemaRouteMVC.getModel());
        VBox vboxSubject_labour = new VBox();

        Label labelSubject_labour = new Label(fxmlLoader.getResources().getString("ListManufacturing"));
        labelWork.setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\RowWork.png")));

        tableWorkMVC.addObserverP((TableModel)tableSubject_labourMVC.getModel());


        vboxSubject_labour.getChildren().addAll(labelWork,(HboxpaneView)hboxpaneSubject_labourMVC.getView(),(TableViewP)tableSubject_labourMVC.getView());
        vboxSubject_labour.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSubject_labour.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------



//        MVC tableMachineMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, this.observableDS, Rule.Machine );
//        MVC hboxpaneMachineMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,this.observableDS.getDataSet(), Rule.RowMachine);
//        hboxpaneMachineMVC.addObserverP( (TableModel)tableMachineMVC .getModel());
//
//
//
//        tableWorkMVC.addObserverP((TableModel)tableMachineMVC .getModel());
//
//
//        tableMachineMVC.addObserverP((SchemaRouteModel)schemaRouteMVC.getModel());
//
//        VBox vboxMachine = new VBox();
//        Label labelMachine = new Label(fxmlLoader.getResources().getString("ListEquipment"));
//        labelMachine.setGraphic(new ImageView(new  Image("file:pde\\src\\main\\resources\\images\\icons\\RowMachine.png")));
//
//
//        vboxMachine.getChildren().addAll(labelMachine,(HboxpaneView)hboxpaneMachineMVC.getView(),(TableViewP)tableMachineMVC.getView());
//        vboxMachine.setSpacing(5);   // The amount of vertical space between each child in the vbox.
//        vboxMachine.setPadding(new Insets(30, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        VBox vboxSplitPaneLeft = new VBox();
        vboxSplitPaneLeft.getChildren().addAll(vboxWork,vboxSubject_labour);
        vboxSplitPaneLeft.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSplitPaneLeft.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------

/*
        final StackPane sp3 = new StackPane();
        sp3.setMinHeight(120);
        sp3.getChildren().add(new Button("Button Tree"));
        borderPane.setBottom(sp3);
        splitPaneInner.getItems().addAll((BorderPane)schemaWorkMVC.getView(), sp3);
        splitPaneInner.setDividerPositions(0.5f, 0.1f);
        splitPaneInner.setOrientation(Orientation.VERTICAL);
*/



       borderPane.setCenter((BorderPane)schemaRouteMVC.getView());
        splitPane.getItems().addAll(vboxSplitPaneLeft, borderPane);
        splitPane.setDividerPositions(0.2f, 0.6f);

        setCenter(splitPane);

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
