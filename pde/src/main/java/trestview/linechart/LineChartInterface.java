package trestview.linechart;

import designpatterns.ObservableDS;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;

 public interface LineChartInterface {
    public List<Point2D.Double> getlist();
    public String getTitleX();
    public String getTitleY();
}
