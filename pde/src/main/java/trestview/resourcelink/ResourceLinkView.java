package trestview.resourcelink;

import designpatterns.MVC;
import entityProduction.Trest;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import trestview.hboxpane.HboxpaneController;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.HboxpaneView;
import trestview.resourcelink.schemawork.SchemaController;
import trestview.resourcelink.schemawork.SchemaModel;
import trestview.resourcelink.schemawork.SchemaView;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by pom on 07.02.2016.
 */
public class  ResourceLinkView extends BorderPane implements Observer {

    private Observable resourceLinkModel;
    private DataSet dataSet;
    private Trest trest;

    public ResourceLinkView (ResourceLinkModel resourceLinkModel, ResourceLinkController resourceLinkController ) {
        this.resourceLinkModel = resourceLinkModel;
        this.dataSet = resourceLinkModel.getDataSet();
        this.trest = resourceLinkModel.getTrest();
        //this.dictionaryModel =dictionaryModel;
        //this.dataSet =dictionaryModel.getTMenuModel().getTrestModel().getDataSet();
        FXMLLoader fxmlLoader = XmlRW.fxmlLoad(this,resourceLinkController, "trestview/resourcelink/resourceLinkView.fxml","ui", "trestview/resourcelink/resourceLinkStyle.css");

        SplitPane  splitPane = new SplitPane();
        BorderPane borderPane = new BorderPane();


//----------------------------------------------------------------------------------------------------------------------

        MVC schemaWorkMVC  = new MVC (SchemaModel.class, SchemaController.class, SchemaView.class, this.resourceLinkModel, Rule.Work );
        SchemaView view = (SchemaView) schemaWorkMVC.getView();
        view.addEventHandler(MouseEvent.MOUSE_MOVED, (SchemaController)schemaWorkMVC.getController());

        view.addEventHandler(MouseEvent.MOUSE_DRAGGED, (SchemaController)schemaWorkMVC.getController());
     // view.addEventHandler(MouseEvent.MOUSE_PRESSED, (SchemaController)schemaWorkMVC.getController());


//        pschemeView.addMouseMotionListener(pschemeController);                          //  Here we add an observer (MouseMotionListener: mouseDragged, mouseMoved) for the view.
//        pschemeView.addMouseListener(pschemeController);                                //  Here we add an observer (MouseListener:  mousePressed) for the view.


//----------------------------------------------------------------------------------------------------------------------
        MVC tableWorkMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, this.resourceLinkModel, Rule.Work );
        MVC hboxpaneWorkMVC = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,dataSet, Rule.Work);
        hboxpaneWorkMVC.addObserverP( (TableModel)tableWorkMVC.getModel());

        VBox vboxWork = new VBox();

        Label labelWork = new Label(fxmlLoader.getResources().getString("ListManufacturing"));
        labelWork.setGraphic(new ImageView(new Image("file:images\\icons\\RowWork.png")));

        vboxWork.getChildren().addAll(labelWork,(HboxpaneView)hboxpaneWorkMVC.getView(),(TableViewP)tableWorkMVC.getView());
        vboxWork.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxWork.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
         MVC tableMacineMVC  = new MVC (TableModel.class, TableController.class, TableViewP.class, this.resourceLinkModel, Rule.Machine );
         MVC hboxpaneMVCmacine = new MVC (HboxpaneModel.class,HboxpaneController.class,HboxpaneView.class,dataSet, Rule.RowMachine);
         hboxpaneMVCmacine.addObserverP( (TableModel)tableMacineMVC .getModel());

        tableWorkMVC.addObserverP((TableModel)tableMacineMVC .getModel());
        tableWorkMVC.addObserverP((SchemaModel)schemaWorkMVC .getModel());


        VBox vboxMacine = new VBox();
        Label labelMacine = new Label(fxmlLoader.getResources().getString("ListEquipment"));
        labelMacine.setGraphic(new ImageView(new  Image("file:images\\icons\\RowWork.png\\RowMachine.png")));

        vboxMacine.getChildren().addAll(labelMacine,(HboxpaneView)hboxpaneMVCmacine.getView(),(TableViewP)tableMacineMVC.getView());
        vboxMacine.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxMacine.setPadding(new Insets(30, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------
        VBox vboxSplitPaneLeft = new VBox();
        vboxSplitPaneLeft.getChildren().addAll(vboxWork,vboxMacine);
        vboxSplitPaneLeft.setSpacing(5);   // The amount of vertical space between each child in the vbox.
        vboxSplitPaneLeft.setPadding(new Insets(10, 0, 0, 10));   // The top,right,bottom,left padding around the region's content. This space will be included in the calculation of the region's minimum and preferred sizes. By default padding is Insets.EMPTY and cannot be set to null.
//----------------------------------------------------------------------------------------------------------------------


        final StackPane sp3 = new StackPane();
        sp3.setMinHeight(120);
        sp3.getChildren().add(new Button("Button Tree"));

        splitPane.getItems().addAll(vboxSplitPaneLeft, borderPane);

        borderPane.setCenter((BorderPane)schemaWorkMVC.getView());
        borderPane.setBottom(sp3);

/*
        splitPaneInner.getItems().addAll((BorderPane)schemaWorkMVC.getView(), sp3);
        splitPaneInner.setDividerPositions(0.5f, 0.1f);
        splitPaneInner.setOrientation(Orientation.VERTICAL);

*/

      splitPane.setDividerPositions(0.2f, 0.6f);

         setCenter(splitPane);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (ResourceLinkModel.class == o.getClass()) resourceLinkModel = o;
    }
}
