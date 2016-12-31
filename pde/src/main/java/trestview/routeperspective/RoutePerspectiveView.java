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
        MVC schemaRouteMVC = new MVC(SchemaRouteModel.class, SchemaRouteController.class, SchemaRouteView.class, observableDS, Rule.RoutePerspective);
        SchemaRouteView view = (SchemaRouteView) schemaRouteMVC.getView();
        view.addEventHandler(MouseEvent.MOUSE_MOVED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, (SchemaRouteController)schemaRouteMVC.getController());
        view.addEventHandler(MouseEvent.MOUSE_RELEASED, (SchemaRouteController)schemaRouteMVC.getController());
//----------------------------------------------------------------------------------------------------------------------
        MVC tableWorkMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, observableDS, Rule.Work );
        MVC hboxpaneWorkMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,observableDS, Rule.Work);
        hboxpaneWorkMVC.addObserverP( (TableModel)tableWorkMVC.getModel());
        tableWorkMVC.addObserverP((SchemaRouteModel)schemaRouteMVC.getModel());
        VBox vboxWork = new VBox();

        Label labelWork = new Label(fxmlLoader.getResources().getString("ListManufacturing"));
        labelWork.setGraphic(new ImageView(new Image("file:pde\\src\\main\\resources\\images\\icons\\RowWork.png")));

        vboxWork.getChildren().addAll(labelWork,(HboxpaneView)hboxpaneWorkMVC.getView(),(TableViewP)tableWorkMVC.getView());
        vboxWork.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxWork.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        MVC tableSubject_labourMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, (ObservableDS) tableWorkMVC.getModel(), Rule.Subject_labour );
        MVC hboxpaneSubject_labourMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,observableDS, Rule.Subject_labour);
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
        MVC tableRouteMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class,(ObservableDS) tableSubject_labourMVC.getModel(), Rule.Route );
        MVC hboxpaneRouteMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,observableDS, Rule.Route);
        hboxpaneRouteMVC.addObserverP( (TableModel)tableRouteMVC.getModel());
        tableRouteMVC.addObserverP((SchemaRouteModel)schemaRouteMVC.getModel());
        VBox vboxRoute = new VBox();

        tableSubject_labourMVC.addObserverP((TableModel)tableRouteMVC.getModel());

        vboxRoute.getChildren().addAll(labelWork,(HboxpaneView)hboxpaneRouteMVC.getView(),(TableViewP)tableRouteMVC.getView());
        vboxRoute.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxRoute.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.

//----------------------------------------------------------------------------------------------------------------------
        VBox vboxSplitPaneLeft = new VBox();
        vboxSplitPaneLeft.getChildren().addAll(vboxWork,vboxSubject_labour ,vboxRoute);
        vboxSplitPaneLeft.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSplitPaneLeft.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------

       borderPane.setCenter((BorderPane)schemaRouteMVC.getView());
        splitPane.getItems().addAll(vboxSplitPaneLeft, borderPane);
        splitPane.setDividerPositions(0.2f, 0.6f);

        setCenter(splitPane);

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
