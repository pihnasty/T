package designpatterns;

import entityProduction.Trest;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import trestmodel.TrestModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

/**
 * Created by Max on 20.06.2016.
 */
public class ObservableDS extends Observable {
    protected DataSet dataSet;
    protected ObservableDS observableDS;
    protected Rule rule;
    protected Trest trest;

    protected SectionDataSet sectionDataSet;

    public ObservableDS ()  {}

    public ObservableDS (ObservableDS observableDS)  {
        //  this.observableDS = observableDS;
    }

    public ObservableDS (ObservableDS observableDS, Rule rule)  {
        this.observableDS = observableDS;
        this.dataSet = observableDS.getDataSet();
        this.rule=rule;
        this.trest=observableDS.getTrest();
        this.sectionDataSet = observableDS.getSectionDataSet();
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public DataSet getDataSet() {return dataSet;}

    public Trest getTrest() {
        return trest;
    }

    public SectionDataSet getSectionDataSet() {
        return sectionDataSet;
    }

    public void setSectionDataSet(SectionDataSet sectionDataSet) {
        this.sectionDataSet = sectionDataSet;
    }
}
