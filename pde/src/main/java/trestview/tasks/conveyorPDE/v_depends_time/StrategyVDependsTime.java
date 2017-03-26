package trestview.tasks.conveyorPDE.v_depends_time;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public interface StrategyVDependsTime {





    DoubleFunction<Double> sinConveyorPdeModelS = _r -> (2 + Math.sin(2 * Math.PI * _r)) / 3.0;
    DoubleFunction<Double> sinConveyorPdeModelT = _r -> (2 + Math.sin(2 * Math.PI * (0.5 - _r))) / 3.0;
    DoubleFunction<Double> const_1 = _r -> 1.0;
    DoubleFunction<Double> const_x = _r -> _r;

    double decision(double _s, double _t);

    double decisionCharacteristic(double _s, double _t);

    List<DoubleFunction<Double>> getInitialConditions();

    default  List<DoubleFunction<Double>> getBoundaryConditions() {
        return null;
    }

    default double r(double _s, double _t, double _s0, double _t0, double g) {
        return _s - _s0 - g * (_t - _t0);
    }


    /*Parameters for the graph scale t={tMin,tMax}, S={sMin,sMax}, y={yMin,yMax}
    * numberOfCurves - Number of curves on the graph (number of graphs)
    * yTickUnitNumber - Number of the tick at the axis */
    class AxisParametrs {

        double tMax = 1.0;         double tMin = 0.0;
        double sMax = 1.0;         double sMin = 0.0;
        double yMax = 1.0;         double yMin = 0.0;
        double numberOfCurves = 10.0;
        double yTickUnitNumber = 10.0;


        public AxisParametrs() {
           this(0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 10.0, 10.0);
        }

        public AxisParametrs(double numberOfCurves, double yTickUnitNumber) {
            this(0.0, 1.0, 0.0, 1.0, 0.0, 1.0, numberOfCurves,  yTickUnitNumber);
        }

        public AxisParametrs(double tMin, double tMax, double sMin, double sMax, double yMin, double yMax, double numberOfCurves, double yTickUnitNumber) {
            this.tMin = tMin;
            this.tMax = tMax;
            this.sMin = sMin;
            this.sMax = sMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.numberOfCurves = numberOfCurves;
            this.yTickUnitNumber = yTickUnitNumber;
        }


        public double gettMax() {
            return tMax;
        }

        public void settMax(double tMax) {
            this.tMax = tMax;
        }

        public double gettMin() {
            return tMin;
        }

        public void settMin(double tMin) {
            this.tMin = tMin;
        }

        public double getsMax() {
            return sMax;
        }

        public void setsMax(double sMax) {
            this.sMax = sMax;
        }

        public double getsMin() {
            return sMin;
        }

        public void setsMin(double sMin) {
            this.sMin = sMin;
        }

        public double getyMax() {
            return yMax;
        }

        public void setyMax(double yMax) {
            this.yMax = yMax;
        }

        public double getyMin() {
            return yMin;
        }

        public void setyMin(double yMin) {
            this.yMin = yMin;
        }

        public double getNumberOfCurves() {
            return numberOfCurves;
        }

        public void setNumberOfCurves(double numberOfCurves) {
            this.numberOfCurves = numberOfCurves;
        }

        public double getyTickUnitNumber() {
            return yTickUnitNumber;
        }

        public void setyTickUnitNumber(double yTickUnitNumber) {
            this.yTickUnitNumber = yTickUnitNumber;
        }

    }

    /*
    * s0, t0 - The coordinate of the technological position and the time when the subjects of labour began to come in at this position
    * tS= Ts/Td, - Ratio of the duration of the works day to the duration of the production cycle
    * */
    class TaskParameters {
        double s0 = 0.0;
        double t0 = 0.0;
        double tS = 0.0;
    }

    class Psi {

    }

    class G {
        private TaskParameters taskParameters;
        public G() {
            this( new TaskParameters () );
        }
        public G(TaskParameters taskParameters) {

        }
        double g0_g1coswt(double t) {
            return 1.0;
        }
    }

    class Gamma {

    }


    class MethodDefault {



    }


    default double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }



//    default double getNumberOfCurves() {
//        return numberOfCurves[0];
//    }





// //  default double getYtickUnit() {
//        return (yMax[0]-yMin[0])/yTickUnitNumber[0];
//    }
}


class StrategyVDependsTime01 implements StrategyVDependsTime {
    public StrategyVDependsTime01() {
        _s0[0] = 0.0;        _t0[0] = 0.0;               g[0] = 1.0;

        StrategyVDependsTime.AxisParametrs  AxisParametrs = new StrategyVDependsTime.AxisParametrs();

    }

    @Override
    public double decision(double _s, double _t) {
        double _r = r(_s, _t, get_s0(), get_t0(), getG0());
        return getInitialConditions().get(0).apply(_r + get_s0());
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG0());
    }

    @Override
    public List<DoubleFunction<Double>> getInitialConditions() {
        List<DoubleFunction<Double>> initialConditions = new ArrayList<>();
        initialConditions.add(sinConveyorPdeModelS);
        return  initialConditions ;
    }
}