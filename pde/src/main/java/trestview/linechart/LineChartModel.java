package trestview.linechart;

import designpatterns.ObservableDS;
import persistence.loader.DataSet;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.List;

public class LineChartModel extends ObservableDS implements LineChartInterface {


    private List<Point2D.Double> list;
    private String titleX;
    private String titleY;

    public LineChartModel(ObservableDS o, Rule rule){
       super(o,rule);
        list =( (LineChartInterface) observableDS).getlist();
        titleX=( (LineChartInterface) observableDS).getTitleX();
        titleY=( (LineChartInterface) observableDS).getTitleY();
    }

    @Override
    public List<Point2D.Double> getlist() {
        return list;
    }

    @Override
    public String getTitleX() {
        return titleX;
    }

    @Override
    public String getTitleY() {
        return titleY;
    }

}
