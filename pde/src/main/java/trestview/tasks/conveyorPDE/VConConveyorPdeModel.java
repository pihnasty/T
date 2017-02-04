package trestview.tasks.conveyorPDE;

import com.sun.javafx.binding.StringFormatter;
import designpatterns.ObservableDS;
import trestview.linechart.LineChartInterface;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.function.DoubleFunction;


public class VConConveyorPdeModel extends ObservableDS implements LineChartInterface {

    private String titleOx;
    private String titleOy;
    private String titleGraph;

    private double xMin;
    private double xMax;

    private double xTickUnit;
    private double yMin;
    private double yMax;
    private double yTickUnit;


    private List<Point2D.Double> list_qS;
    private List<Point2D.Double> list_qT;
    private List<Point2D.Double> list;

    private List<List<Point2D.Double>> pullList_qS;
    private List<List<Point2D.Double>> pullList_qT;
    private List<List<Point2D.Double>> pullList_qX;
    private List<List<Point2D.Double>> pullList;

    private List<String> list_qS_Legend;
    private List<String> list_qT_Legend;
    private List<String> listLegend;

    private double tMax = 2.0;
    private double tMin = 0.0;
    private double sMax = 1.0;


    public enum Conditions {v1, v2}

    ;

    public List<Point2D.Double> getListConT() {
        return list_qS;
    }

    public void setListConT(List<Point2D.Double> listConT) {
        this.list_qS = listConT;
    }

    public List<Point2D.Double> getListConS() {
        return list_qT;
    }

    public void setListConS(List<Point2D.Double> listConS) {
        this.list_qT = listConS;
    }


    public VConConveyorPdeModel(ObservableDS o, Rule rule) {
        super(o, rule);
        double _s0 = 0.0;
        double _t0 = 0.0;
        double numberOfCurves = 10.0;
        double g1 = 1;


        DoubleFunction<Double> sinConveyorPdeModelS = _r -> (2 + Math.sin(2 * Math.PI * _r)) / 3.0;
        DoubleFunction<Double> sinConveyorPdeModelT = _r -> (2 + Math.sin(2 * Math.PI * (0.5 - _r))) / 3.0;
        DoubleFunction<Double> pow2BoundaryCondition = _r -> {
            double ftMax = 1.0 / 3.0;
            return 0.5;
        };

        DoubleFunction<Double> conditionForS = null;
        DoubleFunction<Double> conditionForT = null;


        Conditions v = Conditions.v2;

        switch (v) {
            case v2:
                _s0 = -0.01;
                _t0 = 0.0;
                tMin = 0;
                tMax = 1.0;
                conditionForS = sinConveyorPdeModelS;
                conditionForT = pow2BoundaryCondition;
                break;
            default:
                break;
        }

        //  (_r*_r)*ftMax;// 0.3;
        //    2) (_r*_r)*ftMax;        1)    (10*Math.pow(_r, 2)/100)*ftMax;}

        pullList_qT = new ArrayList<>();
        pullList_qS = new ArrayList<>();
        pullList_qX = new ArrayList<>();

        list_qT_Legend = new ArrayList<>();
        list_qS_Legend = new ArrayList<>();


        for (double _s = 0; _s < 1.0; _s += 1.0 / numberOfCurves) {
            pullList_qT.add(qT(_s, _s0, _t0, conditionForS, conditionForT, g1));
            list_qT_Legend.add("S/Sd=" + String.format("%3.1f", _s));

        }
        for (double _t = tMin; _t < tMax; _t += (tMax - tMin) / numberOfCurves) {
            pullList_qS.add(qS(_t, _s0, _t0, conditionForS, conditionForT, g1));
            list_qS_Legend.add("t/Td=" + String.format("%3.1f", _t));
        }
        for (double _t = tMin; _t < tMax; _t += (tMax - tMin) / numberOfCurves) {
            pullList_qX.add(qX(_t, _s0, _t0, conditionForS, conditionForT, g1));
            list_qS_Legend.add("t/Td=" + String.format("%3.1f", _t));
        }
    }


    public VConConveyorPdeModel dataBuildVConConveyorPdeModel(String s) {
        VConConveyorPdeModel VConConveyorPdeModel =
                new VConConveyorPdeModel(this, null).setTitleGraph(ResourceBundle.getBundle("ui").getString("productionLine"))
                        .setxMin(0.0).setyMin(0.0).setyMax(1.0).setyTickUnit(0.1);
        if (s == "oX=S") {
            VConConveyorPdeModel.setTitleX(ResourceBundle.getBundle("ui").getString("titleOxS"))
                    .setxMax(sMax).setxTickUnit(sMax / 10.0)
                    .setTitleY(ResourceBundle.getBundle("ui").getString("titleOyS"))
                    .setList(getListConT())
                    .setPullList(pullList_qS).setListLegend(list_qS_Legend);
        }
        if (s == "oX=T") {
            VConConveyorPdeModel.setTitleX(ResourceBundle.getBundle("ui").getString("titleOxT"))
                    .setxMin(tMin).setxMax(tMax).setxTickUnit((tMax - tMin) / 10.0)
                    .setTitleY(ResourceBundle.getBundle("ui").getString("titleOyT"))
                    .setList(getListConS())
                    .setPullList(pullList_qT).setListLegend(list_qT_Legend);
        }
        if (s == "oX=X") {
            VConConveyorPdeModel.setTitleX(ResourceBundle.getBundle("ui").getString("titleOxT"))
                    .setxMin(tMin).setxMax(tMax).setxTickUnit((tMax - tMin) / 10.0)
                    .setTitleY(ResourceBundle.getBundle("ui").getString("titleOyT"))
                    .setList(getListConS())
                    .setPullList(pullList_qX).setListLegend(list_qT_Legend);
        }

        if (s.length() > 4) {
            if (s.substring(0, 9).equals("oX=Salone")) {
                List<List<Point2D.Double>> pullListAlone = new ArrayList<>();
                List<String> listLegendAlone = new ArrayList<>();

                int i = Integer.parseInt(s.substring(10, 11));
                pullListAlone.add(pullList_qS.get(i));
                listLegendAlone.add(list_qS_Legend.get(i));

                VConConveyorPdeModel.setTitleGraph("")
                        .setTitleX((i + 1) + ")   " + list_qS_Legend.get(i))
                        .setxMax(sMax).setxTickUnit(sMax / 10.0)

                        .setPullList(pullListAlone).setListLegend(listLegendAlone);
            }
            if (s.substring(0, 9).equals("oX=Talone")) {
                List<List<Point2D.Double>> pullListAlone = new ArrayList<>();
                List<String> listLegendAlone = new ArrayList<>();

                int i = Integer.parseInt(s.substring(10, 11));
                pullListAlone.add(pullList_qT.get(i));
                listLegendAlone.add(list_qT_Legend.get(i));

                VConConveyorPdeModel.setTitleGraph("")
                        .setTitleX((i + 1) + ")   " + list_qT_Legend.get(i))
                        .setxMin(tMin).setxMax(tMax).setxTickUnit((tMax - tMin) / 10.0)

                        .setPullList(pullListAlone).setListLegend(listLegendAlone);
            }
        }


        return VConConveyorPdeModel;
    }

    private void change() {

        setChanged();
        notifyObservers();

    }

    private double decision(double _s, double _t, double _s0, double _t0, DoubleFunction<Double> a, DoubleFunction<Double> b, double g) {
        double _r = r(_s, _t, _s0, _t0, g);
        return a.apply(_r + _s0) * (h(_r) + h(_s0 - _s)) + b.apply(_t0 - _r / g) * (h(-_r) - h(_s0 - _s));
    }

    private double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    private double r(double _s, double _t, double _s0, double _t0, double g) {
        return _s - _s0 - g * (_t - _t0);
    }

    private List<Point2D.Double> qS(double _t, double _s0, double _t0, DoubleFunction<Double> a, DoubleFunction<Double> b, double g) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double dS = 0.001;

        for (double _s = 0.0; _s <= 1.0; _s += dS) {
            Point2D.Double p = new Point2D.Double(_s,
                    decision(_s, _t, _s0, _t0, a, b, g)
            );
            doubleList.add(p);
        }

        return doubleList;
    }


    private List<Point2D.Double> qT(double _s, double _s0, double _t0, DoubleFunction<Double> a, DoubleFunction<Double> b, double g) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double tD = 10.0;
        double dT = 0.01;
        for (double _t = tMin; _t <= tD; _t += dT) {
            Point2D.Double p = new Point2D.Double(_t,
                    decision(_s, _t, _s0, _t0, a, b, g)
            );
            doubleList.add(p);
        }
        return doubleList;
    }

    private List<Point2D.Double> qX(double _s, double _s0, double _t0, DoubleFunction<Double> a, DoubleFunction<Double> b, double g) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double tD = 10.0;
        double dT = 0.01;
        for (double _t = tMin; _t <= tD; _t += dT) {
            Point2D.Double p = new Point2D.Double(_t,
                    r(_s0, _t0, _s, _t, g)
            );
            doubleList.add(p);
        }
        return doubleList;
    }

    @Override
    public List<String> getListLegend() {
        return listLegend;
    }

    public VConConveyorPdeModel setListLegend(List<String> listLegend) {
        this.listLegend = listLegend;
        return this;
    }


    @Override
    public String getTitleY() {
        return titleOy;
    }

    public VConConveyorPdeModel setTitleY(String titleOy) {
        this.titleOy = titleOy;
        return this;
    }

    @Override
    public String getTitleX() {
        return titleOx;
    }

    public VConConveyorPdeModel setTitleX(String titleOx) {
        this.titleOx = titleOx;
        return this;
    }

    @Override
    public List<Point2D.Double> getList() {
        return list;
    }

    public VConConveyorPdeModel setList(List<Point2D.Double> list) {
        this.list = list;
        return this;
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return pullList;
    }

    public VConConveyorPdeModel setPullList(List<List<Point2D.Double>> pullList) {
        this.pullList = pullList;
        return this;
    }

    @Override
    public double getxMin() {
        return xMin;
    }

    public VConConveyorPdeModel setxMin(double xMin) {
        this.xMin = xMin;
        return this;
    }

    @Override
    public double getxMax() {
        return xMax;
    }

    public VConConveyorPdeModel setxMax(double xMax) {
        this.xMax = xMax;
        return this;
    }

    @Override
    public double getyMax() {
        return yMax;
    }

    public VConConveyorPdeModel setyMax(double yMax) {
        this.yMax = yMax;
        return this;
    }

    @Override
    public double getyMin() {
        return yMin;
    }

    public VConConveyorPdeModel setyMin(double yMin) {
        this.yMin = yMin;
        return this;
    }

    @Override
    public String getTitleGraph() {
        return titleGraph;
    }

    public VConConveyorPdeModel setTitleGraph(String titleGraph) {
        this.titleGraph = titleGraph;
        return this;
    }

    @Override
    public double getxTickUnit() {
        return xTickUnit;
    }

    public VConConveyorPdeModel setxTickUnit(double xTickUnit) {
        this.xTickUnit = xTickUnit;
        return this;
    }

    @Override
    public double getyTickUnit() {
        return yTickUnit;
    }

    public VConConveyorPdeModel setyTickUnit(double yTickUnit) {
        this.yTickUnit = yTickUnit;
        return this;
    }
}
