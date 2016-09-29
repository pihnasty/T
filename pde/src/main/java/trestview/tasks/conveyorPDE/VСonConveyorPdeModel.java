package trestview.tasks.conveyorPDE;

import designpatterns.ObservableDS;
import trestview.linechart.LineChartInterface;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.awt.geom.Point2D;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.function.DoubleFunction;


public class VСonConveyorPdeModel extends ObservableDS implements LineChartInterface {

    private String titleOx;
    private String titleOy;




    private List<Point2D.Double> listConT;
    private List<Point2D.Double> listConS;
    private List<Point2D.Double> list;

    private List<List<Point2D.Double>> pullListConT;
    private List<List<Point2D.Double>> pullListConS;
    private List<List<Point2D.Double>> pullList;



    private int numberOfDivisionsOx;
    private int numberOfDivisionsOy;
    private int numberOfDivisionsEpisodes;
    private int maxOx,maxOy,minOx,minOy;


    public List<Point2D.Double> getListConT() {
        return listConT;
    }

    public void setListConT(List<Point2D.Double> listConT) {
        this.listConT = listConT;
    }

    public List<Point2D.Double> getListConS() {
        return listConS;
    }

    public void setListConS(List<Point2D.Double> listConS) {
        this.listConS = listConS;
    }


    public VСonConveyorPdeModel(ObservableDS o, Rule rule) {
        super(o,rule);

        double _s = 0.0;
        double _t = 0.0;

        DoubleFunction<Double> sinConveyorPdeModel    = _r -> 2 + Math.sin(2 * Math.PI * _r);
        DoubleFunction<Double> pow2BoundaryCondition  = _r -> 1 - Math.pow(_r, 2);

        pullListConS = new ArrayList<>();
        pullListConT = new ArrayList<>();


        for (_s=0; _s<1.0;  _s+=0.1)   pullListConS.add(xi0ForConstS(_s, sinConveyorPdeModel, pow2BoundaryCondition));
        for (_t=0; _t<10.0; _t+=1.0)   pullListConT.add(xi0ForConstT(_t, sinConveyorPdeModel, pow2BoundaryCondition));


    }


    public VСonConveyorPdeModel dataBuildVСonConveyorPdeModel(String s) {
        VСonConveyorPdeModel vСonConveyorPdeModel = null;
        if(s=="T") {

            numberOfDivisionsOx=3;
            numberOfDivisionsOy=3;
            numberOfDivisionsEpisodes=1;
            maxOx=5;
            maxOy=5;
            minOx=1;
            minOy=1;
            vСonConveyorPdeModel = new VСonConveyorPdeModel(this, null)
                                        .setTitleX(ResourceBundle.getBundle("ui").getString("titleOxS"))
                                        .setTitleY(ResourceBundle.getBundle("ui").getString("titleOxT"))
                                        .setList(getListConT())
                                        .setPullList(pullListConT);
        }
        if (s=="S"){
            vСonConveyorPdeModel = new VСonConveyorPdeModel(this, null)
                                        .setTitleX(ResourceBundle.getBundle("ui").getString("titleOxT"))
                                        .setTitleY(ResourceBundle.getBundle("ui").getString("titleOxT"))
                                        .setList(getListConS())
                                        .setPullList(pullListConS);
        }

        return vСonConveyorPdeModel;
    }

    private void change() {

        setChanged();
        notifyObservers();

    }

    private double decision(double r, DoubleFunction<Double> a, DoubleFunction<Double> b) {
        return a.apply(r) * h(r) + b.apply(r) * h(-r);
    }

    private double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    private double r(double _s, double _t) {
        return _s - _t;
    }

    private List<Point2D.Double> xi0ForConstT(double _t, DoubleFunction<Double> a, DoubleFunction<Double> b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double s0 = 0.0;
        double sD = 1.0;
        double dS = 0.001;
        if (_t >= 0) {
            for (double _s = s0; _s <= sD; _s += dS) {
                Point2D.Double p = new Point2D.Double(_s,
                        decision(r(_s, _t), a, b)
                );
                doubleList.add(p);
            }
        }
        return doubleList;
    }


    private List<Point2D.Double> xi0ForConstS(double _s, DoubleFunction<Double> a,DoubleFunction<Double> b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double t0 = 0.0;
        double tD = 10.0;
        double dT = 0.01;
        if (_s >= 0 && _s <= 1) {
            for (double _t = t0; _t <= tD; _t += dT) {
                Point2D.Double p = new Point2D.Double(_t,
                                                       decision(r(_s, _t), a, b)
                                                     );
                doubleList.add(p);
            }
        }
        return doubleList;

    }







    @Override
    public String getTitleY() {
        return titleOy;
    }

    public VСonConveyorPdeModel setTitleY(String titleOy) {
        this.titleOy = titleOy;
        return this;
    }
    @Override
    public String getTitleX() {
        return titleOx;
    }
    public VСonConveyorPdeModel setTitleX(String titleOx) {
        this.titleOx = titleOx;
        return this;
    }
    @Override
    public List<Point2D.Double> getList() {
        return list;
    }
    public VСonConveyorPdeModel setList(List<Point2D.Double> list) {
        this.list = list;
        return this;
    }

    @Override
    public List<List<Point2D.Double>> getPullList() {
        return pullList;
    }
    public VСonConveyorPdeModel setPullList(List<List<Point2D.Double>> pullList) {
        this.pullList = pullList;
        return this;
    }
}