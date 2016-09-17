package designpatterns;

import persistence.loader.DataSet;
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

    public ObservableDS ()  {}

    public ObservableDS (ObservableDS observableDS)  {
      //  this.observableDS = observableDS;
    }

    public ObservableDS (ObservableDS observableDS, Rule rule)  {
        this.observableDS = observableDS;
        this.dataSet = observableDS.getDataSet();
        this.rule=rule;
    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public DataSet getDataSet() {return dataSet;}
}
