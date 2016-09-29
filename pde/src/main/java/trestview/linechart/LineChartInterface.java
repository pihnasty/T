package trestview.linechart;

import designpatterns.ObservableDS;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;

 public interface LineChartInterface {
    List<Point2D.Double> getList();
    String getTitleX();
    String getTitleY();

    List<List<Point2D.Double>> getPullList();

}
