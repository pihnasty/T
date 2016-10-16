package trestview.routeperspective.schemabase;

import designpatterns.ObservableDS;
import entityProduction.Machine;
import entityProduction.Work;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import persistence.loader.XmlRW;
import persistence.loader.tabDataSet.RowMachine;
import trestview.resourcelink.ResourceLinkModel;
import trestview.resourcelink.schemawork.Q;
import trestview.table.tablemodel.TableModel;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class SchemaBaseModel extends ObservableDS implements Observer {

    protected Work work;



    protected List<Q> qs;

    public SchemaBaseModel(ObservableDS observableDS, Rule rule) {
        super(observableDS, rule);
        this.qs = new ArrayList();
        if (!trest.getWorks().isEmpty())  createDataSchemaModel(trest.getWorks().get(0));
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    protected void createDataSchemaModel(Work work) {
        qs.clear();
        this.work = work;
        qs.addAll(work.getMachines().stream().map(Q::new).collect(Collectors.toList())); //for (Machine machine : work.getMachines()) {  qs.add(new Q(machine)); }
    }

//------------------------------------------------------------------------

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<Q> getQs() {
        return qs;
    }

    public void setQs(List<Q> qs) {
        this.qs = qs;
    }

}
