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
    double g = 9.8;

    public double initialCondition(double r) {
        double sD = 1;
        return 2 + Math.sin((2 * Math.PI) / (sD) * r);
    }

    public double boundaryCondition(double r) {

        return 2 * g - g * Math.pow(r, 2);


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

    public double r(double s, double t) {
        return s - g * t;
    }

    @Override
    public List<Point2D.Double> xi0ForConstT(double t) {
        List<Point2D.Double> doubleList = new ArrayList<>();
        double s0 = 0.0;
        double sD = 10.0;
        double dS =0.1;

        for (double s=s0; s<=sD; s+=dS) {
            Point2D.Double p = new Point2D.Double(s,
                                                  decision(r(s,t))
                                                  );
            doubleList.add(p);
        }
        return doubleList;
    }

    @Override
    public List<Point2D.Double> xi0ForConstS(double s) {


        return null;
    }


    public List<Point2D.Double> listM(double dec) {


        return null;

    }


    static public void main(String[] args) {

        IConveyorPdeModel conveyorPdeModel = new ConveyorPdeModel();

        for (Point2D.Double p : conveyorPdeModel.xi0ForConstT(0)) {
            System.out.printf("p.s= %5.2f              p.hi0=  %5.2f  %n", p.getX(),p.getY());

        }

    }
}
