package designpatterns;

import persistence.loader.DataSet;

import java.util.Observable;

/**
 * Created by Max on 20.06.2016.
 */
public class ObservableDS extends Observable {
    protected DataSet dataSet;
    private ObservableDS observableDS;
    public DataSet getDataSet() {return dataSet;}
}
