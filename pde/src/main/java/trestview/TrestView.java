package trestview;

import designpatterns.InitializableDS;
import designpatterns.MVC;
import designpatterns.ObservableDS;
import designpatterns.observerdsall.BorderPaneObserverDS;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import trestcontroller.TrestController;
import trestmodel.TrestModel;
import trestview.machinetest.MachineTestController;
import trestview.machinetest.MachineTestModel;
import trestview.machinetest.MachineTestView;
import trestview.menu.TMenuController;
import trestview.menu.TMenuModel;
import trestview.menu.TMenuView;
import trestview.orderplaninigperspective.OrderPlaninigPerspectiveController;
import trestview.orderplaninigperspective.OrderPlaninigPerspectiveModel;
import trestview.orderplaninigperspective.OrderPlaninigPerspectiveView;
import trestview.resourcelink.ResourceLinkController;
import trestview.resourcelink.ResourceLinkModel;
import trestview.resourcelink.ResourceLinkView;
import trestview.routeperspective.RoutePerspectiveController;
import trestview.routeperspective.RoutePerspectiveModel;
import trestview.routeperspective.RoutePerspectiveView;
import trestview.table.tablemodel.abstracttablemodel.Rule;
import trestview.tasks.conveyorPDE.VConConveyorPdeController;
import trestview.tasks.conveyorPDE.VConConveyorPdeView;
import trestview.tasks.conveyorPDE.VСonConveyorPdeModel;


import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TrestView extends BorderPaneObserverDS {
    private TrestModel trestModel;
    private DataSet dataSet;
    private List<Node> nodes;
    private MVC resourceLink;
    private MVC conConveyorPdeModel;
    private MVC orderPlaninigPerspective;
    private MVC routePerspective;
    private MVC machineTest;


    public TrestView(ObservableDS trestModel, InitializableDS trestController) {
        super(trestModel,trestController);
        this.trestModel = (TrestModel) trestModel;
        this.dataSet = trestModel.getDataSet();
        XmlRW.fxmlLoad(this,this, "trestview/trestview.fxml","","");

        MVC menu = new MVC(TMenuModel.class, TMenuController.class, TMenuView.class, this.trestModel,null );
        this.setTop((TMenuView)menu.getView());
        ((TMenuModel) menu.getModel()).addObserver(this);    // this: Depending on the keys pressed Menu is changing appearance for TrestView.

        resourceLink = new MVC(ResourceLinkModel.class, ResourceLinkController.class, ResourceLinkView.class, this.trestModel, null);

        // MVC (Class mClass, Class cClass, Class vClass, ObservableDS o, Rule rule )
//        Constructor vConstructor = vClass.getConstructor( ObservableDS.class, InitializableDS.class);
//        view = vConstructor.newInstance(model,controller);


        this.setCenter((BorderPane)resourceLink.getView());

//        MachineTestModel machineTestModel = new MachineTestModel((TMenuModel)menu.getModel());
//        MachineTestController machineTestController = new MachineTestController(machineTestModel);
//        machineTestView = new MachineTestView(machineTestModel, machineTestController);
//        machineTestModel.addObserver(machineTestView);
         machineTest = new MVC (MachineTestModel.class,  MachineTestController.class, MachineTestView.class, this.trestModel, Rule.Functiondist);


        conConveyorPdeModel = new MVC (VСonConveyorPdeModel.class, VConConveyorPdeController.class, VConConveyorPdeView.class,this.trestModel, null);

        routePerspective = new MVC (RoutePerspectiveModel.class, RoutePerspectiveController.class, RoutePerspectiveView.class,this.trestModel, null);

        orderPlaninigPerspective = new MVC (OrderPlaninigPerspectiveModel.class, OrderPlaninigPerspectiveController.class, OrderPlaninigPerspectiveView.class,this.trestModel, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass()==TMenuModel.class) { updateCenter((TMenuModel) o);   }
    }

    private void updateCenter(TMenuModel o) {
        switch (o.getMenuItemCall()) {
            case testOfMachineItem:
                this.setCenter((BorderPane) machineTest.getView());
                break;
            case resourcesLinksPerspectiveItem:
                this.setCenter((BorderPane) resourceLink.getView());
                break;
            case conveyorSpeedConstantItem:
                this.setCenter((BorderPane) conConveyorPdeModel.getView());
                break;
            case routePerspectiveItem:
                this.setCenter((BorderPane) routePerspective.getView());
                break;
            case orderPlaninigPerspectiveItem:
                this.setCenter((BorderPane) orderPlaninigPerspective.getView());
                break;
            default:
                break;
        }
    }
}
