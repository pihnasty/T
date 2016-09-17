package trestview.routeperspective;

import designpatterns.ObservableDS;
import entityProduction.Trest;
import persistence.loader.DataSet;
import persistence.loader.SectionDataSet;
import trestmodel.TrestModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.Observable;

/**
 * Created by pom on 07.02.2016.
 */
public class RoutePerspectiveModel extends ObservableDS {


    private Observable trestModel;

    private SectionDataSet sectionDataSet;

    private Trest trest;

    public RoutePerspectiveModel (ObservableDS o, Rule rule)  {
        this.trestModel = trestModel;
        this.trest =  ((TrestModel)trestModel).getTrest();
        this.dataSet = ((TrestModel)trestModel).getDataSet();
        this.sectionDataSet = ((TrestModel)trestModel).getSectionDataSet();

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
    public SectionDataSet getSectionDataSet()        { return sectionDataSet;           }
    public void setSectionDataSet(SectionDataSet sectionDataSet) { this.sectionDataSet = sectionDataSet; }
}
