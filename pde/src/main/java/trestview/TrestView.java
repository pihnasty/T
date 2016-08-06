package trestview;

import designpatterns.MVC;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import persistence.loader.DataSet;
import persistence.loader.XmlRW;
import trestmodel.TrestModel;
import trestview.machinetest.MachineTestController;
import trestview.machinetest.MachineTestModel;
import trestview.machinetest.MachineTestView;
import trestview.menu.TMenuController;
import trestview.menu.TMenuModel;
import trestview.menu.TMenuView;
import trestview.resourcelink.ResourceLinkController;
import trestview.resourcelink.ResourceLinkModel;
import trestview.resourcelink.ResourceLinkView;
import trestview.tasks.conveyorPDE.VConConveyorPdeView;
import trestview.tasks.conveyorPDE.VСonConveyorPdeModel;


import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TrestView extends BorderPane implements Observer {
    private TrestModel trestModel;
    private DataSet dataSet;
    private List<Node> nodes;
    private MachineTestView machineTestView;
    private VConConveyorPdeView vConConveyorPdeView;
    private MVC resourceLink;

    public TrestView(TrestModel trestModel) {
        this.trestModel =  trestModel;
        this.dataSet = trestModel.getDataSet();
        XmlRW.fxmlLoad(this,this, "trestview/trestview.fxml","","");

        MVC menu = new MVC(TMenuModel.class, TMenuController.class, TMenuView.class, this.trestModel );
        this.setTop((TMenuView)menu.getView());
        ((TMenuModel) menu.getModel()).addObserver(this);    // this: Depending on the keys pressed Menu is changing appearance for TrestView.

        resourceLink = new MVC(ResourceLinkModel.class, ResourceLinkController.class, ResourceLinkView.class, this.trestModel );
        this.setCenter((BorderPane)resourceLink.getView());

        MachineTestModel machineTestModel = new MachineTestModel((TMenuModel)menu.getModel());
        MachineTestController machineTestController = new MachineTestController(machineTestModel);
        machineTestView = new MachineTestView(machineTestModel, machineTestController);
        machineTestModel.addObserver(machineTestView);




        /*


        ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

         */


        VСonConveyorPdeModel vСonConveyorPdeModel = new VСonConveyorPdeModel(dataSet);
        vConConveyorPdeView =new VConConveyorPdeView(vСonConveyorPdeModel);
        vСonConveyorPdeModel.addObserver(vConConveyorPdeView);






    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass()==TMenuModel.class) { updateCenter((TMenuModel) o);   }
    }

    private void updateCenter (TMenuModel o) {
        switch (o.getMenuItemCall()) {
            case testOfMachineItem:             this.setCenter(machineTestView);                        break;
            case resourcesLinksPerspectiveItem: this.setCenter((BorderPane)resourceLink.getView());     break;
            case conveyorSpeedConstantItem:
                                                this.setCenter(vConConveyorPdeView);
                                                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                                                    break;

            default:                                                                                    break;
        }
    }
}
