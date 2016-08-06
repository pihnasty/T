    package trestview.tasks.conveyorPDE;

import old.database.DataSet;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


interface InitialCondition {

    double initialCondition(double _r);

}

interface BoundaryCondition {

    double boundaryCondition(double _r);

}


interface ConveyorPdeModel {


    List<Point2D.Double> xi0ForConstT(double _t, InitialCondition a, BoundaryCondition b);

    List<Point2D.Double> xi0ForConstS(double _s, InitialCondition a, BoundaryCondition b);

}

class SinInitialCondition  implements InitialCondition {

    public double initialCondition(double _r) {
        return 1 + Math.sin(2 * Math.PI * _r);
    }

}

class Pow2BoundaryCondition implements BoundaryCondition {

    public double boundaryCondition(double _r) {
        return 1 - Math.pow(_r, 2);
    }

}

class RInitialCondition implements InitialCondition {

    public double initialCondition(double _r) {
        return _r;
    }

}

class RPow2BoundaryCondition implements BoundaryCondition {

    public double boundaryCondition(double _r) {
        return Math.pow(_r, 2);
    }

}

class VСonConveyorPdeModel extends Observable implements ConveyorPdeModel {

VСonConveyorPdeModel(DataSet dataset){




}

    private void changeInitialboundaryCondition(){
        if (  true  ) {
            setChanged();
            notifyObservers();
        }
    }




    private double decision(double r, InitialCondition a, BoundaryCondition b) {


        return a.initialCondition(r) * h(r) + b.boundaryCondition(r) * h(-r);


    }

    private double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    private double r(double _s, double _t) {
        return _s - _t;
    }

    @Override
    public List<Point2D.Double> xi0ForConstT(double _t, InitialCondition a, BoundaryCondition b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double s0 = 0.0;
        double sD = 10.0;
        double dS = 0.1;
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

    @Override
    public List<Point2D.Double> xi0ForConstS(double _s, InitialCondition a, BoundaryCondition b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double t0 = 0.0;
        double tD = 10.0;
        double dT = 0.1;
        if (_s >= 0 && _s <= 1) {
            for (double _t = t0; _t <= tD; _t += dT) {
                Point2D.Double p1 = new Point2D.Double(_t,
                        decision(r(_s, _t), a, b)
                );
                doubleList.add(p1);
            }
        }
        return doubleList;

    }


    static public void main(String[] args) {

        double s = 0;
        double t = 0;
        ConveyorPdeModel vСonConveyorPdeModel = new VСonConveyorPdeModel(null);

        InitialCondition sinConveyorPdeModel = new SinInitialCondition();
        BoundaryCondition pow2BoundaryCondition = new Pow2BoundaryCondition();

        InitialCondition rConveyorPdeModel = new RInitialCondition();
        BoundaryCondition rPow2BoundaryCondition = new RPow2BoundaryCondition();


        for (Point2D.Double p : vСonConveyorPdeModel.xi0ForConstS(s, sinConveyorPdeModel, pow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());

        }

        for (Point2D.Double p1 : vСonConveyorPdeModel.xi0ForConstT(t, sinConveyorPdeModel, pow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p1.getX(), p1.getY());

        }

        for (Point2D.Double p2 : vСonConveyorPdeModel.xi0ForConstS(s, rConveyorPdeModel, rPow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p2.getX(), p2.getY());

        }

        for (Point2D.Double p3 : vСonConveyorPdeModel.xi0ForConstT(t, rConveyorPdeModel, rPow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p3.getX(), p3.getY());

        }


    }

}