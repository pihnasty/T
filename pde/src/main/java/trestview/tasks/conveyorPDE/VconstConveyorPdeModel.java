package trestview.tasks.conveyorPDE;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

interface InitialCondition {

    public double initialCondition(double _r);

}

interface BoundaryCondition {

    public double boundaryCondition(double _r);

}


interface ConveyorPdeModel {

    // public boolean comparisonBoundaryCondition(IinitialCondition a,IboundaryCondition b);

    public double decision(double r, InitialCondition a, BoundaryCondition b);

    public double h(double r);

    public double r(double _s, double _t);

    public List<Point2D.Double> xi0ForConstT(double _t, InitialCondition a, BoundaryCondition b);

    public List<Point2D.Double> xi0ForConstS(double _s, InitialCondition a, BoundaryCondition b);

}

class SinInitialCondition implements InitialCondition {

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

public class VconstConveyorPdeModel implements ConveyorPdeModel {


    /*

        @Override
        public boolean comparisonBoundaryCondition(IinitialCondition a,IboundaryCondition b) {

            if (b.boundaryCondition(0) == a.initialCondition(0)) return true;
            else return false;


        }

    */
    public double decision(double r, InitialCondition a, BoundaryCondition b) {


        return a.initialCondition(r) * h(r) + b.boundaryCondition(r) * h(-r);


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
    public List<Point2D.Double> xi0ForConstT(double _t, InitialCondition a, BoundaryCondition b) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double s0 = 0.0;
        double sD = 10.0;
        double dS = 0.1;
         if (_t>=0) {
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
         if (_s>=0 &&_s<=1) {
        for (double _t = t0; _t <= tD; _t += dT) {
            Point2D.Double p1 = new Point2D.Double(_t,
                    decision(r(_s, _t), a, b)
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


        ConveyorPdeModel vconstConveyorPdeModel = new VconstConveyorPdeModel();

        InitialCondition sinConveyorPdeModel = new SinInitialCondition();

        InitialCondition sinLConveyorPdeModel= _r ->  1 + Math.sin(2 * Math.PI * _r);


        double z=5;

        BoundaryCondition pow2BoundaryCondition = new Pow2BoundaryCondition();

        InitialCondition rConveyorPdeModel = new RInitialCondition();
        BoundaryCondition rpow2BoundaryCondition = new RPow2BoundaryCondition();

// 1 ------------------------------------------------------------------
        for (Point2D.Double p : vconstConveyorPdeModel.xi0ForConstS(0.5,sinConveyorPdeModel, pow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());
        }
// 2 ------------------------------------------------------------------
        for (Point2D.Double p : vconstConveyorPdeModel.xi0ForConstS(0.5,sinConveyorPdeModel::initialCondition, pow2BoundaryCondition::boundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());
        }
// 3 ------------------------------------------------------------------
        for (Point2D.Double p : vconstConveyorPdeModel.xi0ForConstS(0.5,sinLConveyorPdeModel, pow2BoundaryCondition::boundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());
        }

// 3 ------------------------------------------------------------------
        for (Point2D.Double p : vconstConveyorPdeModel.xi0ForConstS(0.5,
                                                                    _r ->  1 + Math.sin(2 * Math.PI * _r),
                                                                    pow2BoundaryCondition::boundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(), p.getY());
        }




        for (Point2D.Double p1 : vconstConveyorPdeModel.xi0ForConstT(0,sinConveyorPdeModel,pow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p1.getX(), p1.getY());

        }

        for (Point2D.Double p2 : vconstConveyorPdeModel.xi0ForConstS(0.5,rConveyorPdeModel,rpow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p2.getX(), p2.getY());

        }

        for (Point2D.Double p3 : vconstConveyorPdeModel.xi0ForConstT(0,rConveyorPdeModel,rpow2BoundaryCondition)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p3.getX(), p3.getY());

        }



    }

}