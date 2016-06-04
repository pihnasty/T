package trestview.machinetest;

import designpatterns.MVC;
import entityProduction.Functiondist;
import persistence.loader.DataSet;
import persistence.loader.tabDataSet.*;
import trestview.menu.TMenuModel;
import trestview.table.TableController;
import trestview.table.TableViewP;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Roman Korsun on 22.03.2016.
 */
public class MachineTestModel extends Observable {

    private TMenuModel menuModel;
    private ArrayList<Double> randomValuesList;
    private DataSet dataSet;
    private TableViewP tableView;
//    private Class tClass;



    public MachineTestModel() {
        this.randomValuesList = new ArrayList<>();
    }
    public MachineTestModel(TMenuModel menuModel) {
        this.menuModel = menuModel;
        this.randomValuesList = new ArrayList<Double>();
        populateList(100);
//        this.tClass = tClass;
        this.dataSet =  menuModel.getTrestModel().getDataSet();

//-----------------------------------------------------------------------------------------------
        ArrayList<Functiondist> functiondists = new ArrayList<>();
     //   for (RowFunctiondist rowFun: dataSet.getTabFunctiondists()) functiondists.add(dataSet.createObject( rowFun));   // Это эквивалентно  dataSet.getTabFunctiondists().stream().filter(w->{functiondists.add(dataSet.createObject(w)); return true;}).count();
        dataSet.getTabFunctiondists().stream().filter(w->{functiondists.add(dataSet.createObject(w)); return true;}).count();
        DataSet.showTab(functiondists);
        MVC tableMVC = new MVC(TableModel.class, TableController.class, TableViewP.class, functiondists, Rule.Functiondist);
        tableView = (TableViewP) tableMVC.getView();
//--------------------------------------------------------------------------------------------------


    }

    public TMenuModel getTMenuModel() {
        return menuModel;
    }

    public void setTMenuModel(TMenuModel menuModel) {
        this.menuModel = menuModel;
        changed();
    }

    public ArrayList<Double> getRandomValuesList() {
        return randomValuesList;
    }

    public void setRandomValuesList(ArrayList<Double> randomValuesList) {
        this.randomValuesList = randomValuesList;
    }


    //adds a value to the List
    public void addValue(double value) {
        this.randomValuesList.add(value);
    }

    //removes value from List
    public void removeValue(int index) {
        this.randomValuesList.remove(index);
    }

    //populate the List with a test data
    private void populateList(int limit) {
        for (double i = 0; i < limit; i++) {
            randomValuesList.add(Math.atan(i));
        }
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public TableViewP getTableView() {
        return tableView;
    }

    public void setTableView(TableViewP tableView) {
        this.tableView = tableView;
    }

}
