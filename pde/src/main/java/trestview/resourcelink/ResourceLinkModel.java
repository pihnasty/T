package trestview.resourcelink;

import designpatterns.ObservableDS;
import entityProduction.Trest;
import persistence.loader.DataSet;
import trestmodel.TrestModel;
import trestview.menu.TMenuModel;
import trestview.resourcelink.schemawork.Q;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class ResourceLinkModel extends Observable  {

    private Observable trestModel;
    private DataSet dataSet;

    private Trest trest;

    public ResourceLinkModel(Observable trestModel) {
        this.trestModel = trestModel;
        this.trest =  ((TrestModel)trestModel).getTrest();
        this.dataSet = ((TrestModel)trestModel).getDataSet();

    }

    public void changed() {
        setChanged();
        notifyObservers();
    }

    public Observable getTrestModel()                { changed();   return trestModel;  }
    public void setTrestModel(Observable trestModel) { this.trestModel = trestModel;    }
    public DataSet getDataSet()                      { return dataSet;                  }
    public Trest getTrest()                          { return trest;                    }
    public void setTrest(Trest trest)                { this.trest = trest;              }
}
