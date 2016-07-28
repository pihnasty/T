package trestview.tasks.conveyorPDE;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

interface IConveyorPdeModel {

    public double initialCondition(double r);

    public double boundaryCondition(double r);

    public boolean comparisonBoundaryCondition(double r);

    public double decision(double r);

    public double h(double r);

    public double r(double s, double t);

    public List<Point2D.Double> xi0ForConstT(double t);

    public List<Point2D.Double> xi0ForConstS(double s);

}

public class ConveyorPdeModel implements IConveyorPdeModel {


    public double initialCondition(double r) {

        return 1 + Math.sin(2 * Math.PI * r);
    }

    public double boundaryCondition(double r) {

        return 1 - Math.pow(r, 2);


    }

    @Override
    public boolean comparisonBoundaryCondition(double r) {
        return false;
    }


    public double decision(double r) {

        return initialCondition(r) * h(r) + boundaryCondition(r) * h(-r);


    }

    public double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    public double r(double _s, double _t) {
        return _s - _t;
    }

    @Override
    public List<Point2D.Double> xi0ForConstT(double _t) {
        List<Point2D.Double> doubleList = new ArrayList<>();

        for (double _s = 0; _s <= 1.0; _s += 0.01) {
            Point2D.Double p = new Point2D.Double(_s, decision(r(_s, _t)));
            doubleList.add(p);
        }
        return doubleList;
    }

    @Override
    public List<Point2D.Double> xi0ForConstS(double _s) {
        List<Point2D.Double> doubleList = new ArrayList<>();

        for (double _t = 0; _t <= 10.0; _t += 0.1) {
            Point2D.Double p1 = new Point2D.Double(_t, decision(r(_s, _t)));
            doubleList.add(p1);
        }
        return doubleList;

    }


    public List<Point2D.Double> listM(double dec) {


        return null;

    }


    static public void main(String[] args) {

        IConveyorPdeModel conveyorPdeModel = new ConveyorPdeModel();

        /*for (Point2D.Double p : conveyorPdeModel.xi0ForConstT(0)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(),p.getY());

        }*/

        for (Point2D.Double p1 : conveyorPdeModel.xi0ForConstS(0.0)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p1.getX(), p1.getY());

        }

    }
}
