package trestview.linechart;

import designpatterns.ObservableDS;
import persistence.loader.DataSet;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.List;

public class LineChartModel extends ObservableDS implements LineChartInterface {


    private List<Point2D.Double> list;
    private List<List<Point2D.Double>> pullList;
    private String titleX;
    private String titleY;

    public LineChartModel(ObservableDS o, Rule rule){
       super(o,rule);
        list =( (LineChartInterface) observableDS).getList();
        pullList =( (LineChartInterface) observableDS).getPullList();
        titleX=( (LineChartInterface) observableDS).getTitleX();
        titleY=( (LineChartInterface) observableDS).getTitleY();
    }

    @Override
    public List<Point2D.Double> getList() {
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

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return pullList;
    }

}
