package trestview.table.tablemodel;

import designpatterns.ObservableDS;
import entityProduction.Machine;
import entityProduction.Modelmachine;
import entityProduction.Typemachine;
import entityProduction.Work;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.*;
import trestview.hboxpane.HboxpaneModel;
import trestview.hboxpane.MethodCall;
import trestview.resourcelink.ResourceLinkModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static persistence.loader.XmlRW.FieldToField_ifClass;

public class TableModel <cL> extends AbstractTableModel implements Observer {

    private MethodCall methodCall;
    private Observable observableModel;
    private RowIdNameDescription parentselectRow;
    private SectionDataSet sectionDataSet;

    public TableModel(DataSet dataSet ) {  /* -------- */
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataset = dataSet;
    }


    /**
     * @param  rule    The data type for a table row. This is [RowWork.class] for the table = [ArrayList<RowWork>].
     */
    public TableModel(Observable observableModel, Rule rule) {
        this.observableModel = observableModel;
        this.sectionDataSet = ((ResourceLinkModel)observableModel).getSectionDataSet();
        this.rule = rule;
        this.tClass =  rule.getClassTab();
        if(rule.getClassTab()== Work.class)  {
            this.dataset = ((ResourceLinkModel)observableModel).getDataSet();
            this.trest = ((ResourceLinkModel)observableModel).getTrest();
            this.tab = trest.getWorks();
            this.selectRow = tab.get(0);
        }
        if(rule.getClassTab()== Machine.class)  {
            this.dataset = ((ResourceLinkModel)observableModel).getDataSet();
            this.trest = ((ResourceLinkModel)observableModel).getTrest();
            this.tab = trest.getWorks().get(0).getMachines();
            this.selectRow = tab.get(0);
            this.parentselectRow = trest.getWorks().get(0);
        }



        this.parametersOfColumns = buildParametersColumn() ;

    }

    public TableModel(DataSet dataSet, Rule rule) {
        this.rule = rule;
        this.tClass =  rule.getClassTab();
        this.tab = dataSet.getTabIND(tClass);
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataset = dataSet;
    }





    public TableModel(ArrayList<cL> tab, Rule rule) {
        // TODO Найти метод определения базового типа данных [cL] в массиве ArrayList<cL>
        this.rule=rule;
        this.tab = tab;
        this.tClass = rule.getClassTab();
        //  this.tClass = RowFunctiondist.class;
        this.parametersOfColumns = buildParametersColumn() ;
        this.dataset = null;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass() == HboxpaneModel.class)    { updateHBoxpaneModel((HboxpaneModel) o);  }
        if (o.getClass() == TableModel.class )
            if (  ((TableModel)o).getRule() == Rule.Work)  {
                methodCall = MethodCall.selectRowTable;
                parentselectRow = (RowIdNameDescription) ((TableModel) o).selectRow;
                for(Work w: trest.getWorks()) if ( w == ((TableModel) o).selectRow )  this.tab = w.getMachines();
            }
        changed();
    }

    public void selectRowForTwoTableModel() {
        methodCall = MethodCall.selectRowTable;
        if (rule == Rule.Work) changed();
    }

    private void updateHBoxpaneModel(HboxpaneModel o) {
        switch (o.getMethodCall()) {
            case addRowTable:
                methodCall = MethodCall.addRowTable;
                addEntity();

                break;
            case saveRowTable:
                methodCall = MethodCall.saveRowTable;
                for(Object r: tab) FieldToField_ifClass(dataset, r);
                this.dataset.saveDataset();
                if (  rule == Rule.Machine)  {

                    for(Work w: trest.getWorks())
                        if ( w == parentselectRow  )  this.tab = w.getMachines();
                    changed();
                }
                break;
            case editRowTable:
                methodCall = MethodCall.editRowTable;
                break;
            case delRowTable:
                methodCall = MethodCall.delRowTable;
                if (tClass == RowTypemachine.class )    XmlRW.delRow (selectRow, tab, dataset.getTabTypemachines(), dataset.getTabModelmachineTypemachines());
                if (tClass == RowMachine.class )        XmlRW.delRow (selectRow, tab, dataset.getTabMachines(), dataset.getTabWorksMachines());
                if (tClass == RowWork.class )           XmlRW.delRow (selectRow, tab, dataset.getTabWorks(), dataset.getTabTrestsWorks());

                if (tClass == Work.class )              XmlRW.delRow (selectRow, tab, dataset.getTabWorks(), dataset.getTabTrestsWorks());
                if (tClass == Machine.class )           XmlRW.delRow (selectRow, tab, dataset.getTabMachines(), dataset.getTabWorksMachines());

                break;



            default:
                break;
        }
    }

    private void addEntity() {

            switch (rule) {
                case RowFunctiondist:
                    break;
                case Work:
                    createEntityRowentity();
                    dataset.getTabTrestsWorks().add(new RowTrestWork(trest.getId(),r.getId(),""));
                    break;
                case Machine:
                    createEntityRowentity();
//                    dataset.getTabWorksMachines().add(new RowWorkMachine(parentselectRow.getId(),r.getId(),""));
//                    dataset.getTabModelmachineMachines().add( new RowModelmachineMachine(idModelMachine ,r.getId(),"")  );       // пробывал добавить оборудование
           //update(observableModel,null);
                    break;
                default:
                    createRowentity();
            }
    }

    private void createRowentity()  {
        try {
        Constructor constructor;
        constructor = tClass.getConstructor(DataSet.class, Class.class);
        selectRow = constructor.newInstance(this.dataset,tClass);
        tab.add(selectRow);
        }   catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)     { e.printStackTrace(); }
    }

    private void createEntityRowentity()  {
        try {
            Constructor constructor = tClass.getSuperclass().getConstructor(DataSet.class, Class.class);
            r = (RowIdNameDescription) constructor.newInstance(this.dataset, tClass.getSuperclass());

            selectRow = dataset.createObject(r);


            if (tClass ==   Machine.class) {

                dataset.addRowToDataSet(r, parentselectRow, dataset.getTabModelmachines().get(0));
                for (Typemachine typemachine : sectionDataSet.getTypemachines())
                    for (Modelmachine modelmachine : typemachine.getModelmachines())
                        if (dataset.getTabModelmachines().get(0).getId() == modelmachine.getId()) {
                            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                            ((Machine) selectRow).setModelmachine(modelmachine);
                        }

                ((Machine) selectRow).setWork((Work) parentselectRow);
            }


            tab.add(selectRow);
            List<RowIdNameDescription> tabRow = dataset.getTabIND(tClass.getSuperclass());
            tabRow.add(r);

//changed();

        }   catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)     { e.printStackTrace(); }
    }

    public MethodCall getMethodCall() { return methodCall;  }


}


