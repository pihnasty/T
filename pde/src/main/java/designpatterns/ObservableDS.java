package designpatterns;

import persistence.loader.DataSet;

import java.util.Observable;

/**
 * Created by Max on 20.06.2016.
 */
public abstract class ObservableDS extends Observable {
    abstract public DataSet getDataSet();
}
