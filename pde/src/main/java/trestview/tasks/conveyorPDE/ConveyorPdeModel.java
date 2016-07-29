package trestview.tasks.conveyorPDE;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

interface IConveyorPdeModel {

    public double initialCondition(double r);

    public double boundaryCondition(double r);

    public boolean comparisonBoundaryCondition();

    public double decision(double r);

    public double h(double r);

    public double r(double _s, double _t);

    public List<Point2D.Double> xi0ForConstT(double _t);

    public List<Point2D.Double> xi0ForConstS(double _s);

}


class Condition1 extends ConveyorPdeModel {
    @Override
    public double initialCondition(double r) {

        return 1 + Math.sin(2 * Math.PI * r);
    }

    @Override
    public double boundaryCondition(double r) {

        return 1 - Math.pow(r, 2);
    }


}




class Condition2 extends ConveyorPdeModel {
    @Override
    public double initialCondition(double r) {

        return r;
    }

    @Override
    public double boundaryCondition(double r) {

        return r*2;
    }


}

public abstract class ConveyorPdeModel implements IConveyorPdeModel {


    public abstract double initialCondition(double r);

    public abstract double boundaryCondition(double r);

    @Override
    public boolean comparisonBoundaryCondition() {

        if (boundaryCondition(0) == initialCondition(0)) return true;
        else return false;


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
        double s0 = 0.0;
        double sD = 10.0;
        double dS = 0.1;
        if (comparisonBoundaryCondition() == true) {
            for (double _s = s0; _s <= sD; _s += dS) {
                Point2D.Double p = new Point2D.Double(_s,
                        decision(r(_s, _t))
                );
                doubleList.add(p);
            }
        }
        return doubleList;
    }

    @Override
    public List<Point2D.Double> xi0ForConstS(double _s) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double t0 = 0.0;
        double tD = 10.0;
        double dT = 0.1;
        if (comparisonBoundaryCondition() == true) {
            for (double _t = t0; _t <= tD; _t += dT) {
                Point2D.Double p1 = new Point2D.Double(_t,
                        decision(r(_s, _t))
                );
                doubleList.add(p1);
            }
        }
        return doubleList;

    }


    public List<Point2D.Double> listM(double dec) {


        return null;

    }


    static public void main(String[] args) {


        IConveyorPdeModel conveyorPdeMode = new Condition2();
        //IConveyorPdeModel conveyorPdeModel = new ConveyorPdeModel();

        for (Point2D.Double p : conveyorPdeMode.xi0ForConstT(0)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());

        }

        for (Point2D.Double p1 : conveyorPdeMode.xi0ForConstS(0.0)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p1.getX(), p1.getY());

        }

    }
}
