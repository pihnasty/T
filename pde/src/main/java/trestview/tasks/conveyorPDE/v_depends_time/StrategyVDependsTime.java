package trestview.tasks.conveyorPDE.v_depends_time;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;

import static trestview.tasks.conveyorPDE.v_depends_time.StrategyVDependsTime.MathP.integralF;

public interface StrategyVDependsTime {

     double decision(double s, double t);

    double decisionCharacteristic(double s, double t);

    AxisParametrs  getAxisParametrs();

    /*Parameters for the graph scale t={tMin,tMax}, S={sMin,sMax}, y={yMin,yMax}
    * numberOfCurves - Number of curves on the graph (number of graphs)
    * yTickUnitNumber - Number of the tick at the axis */
    class AxisParametrs {

        private double tMax ;         private double tMin ;
        private double sMax ;         private double sMin ;
        private double yMax ;         private double yMin ;
        private double numberOfCurves;
        private double yTickUnitNumber;


        public AxisParametrs() {
           this(0.0, 1.0, 0.0, 2.0, 0.0, 1.0, 10.0, 10.0);
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

        public double getYtickUnit() {
            return (yMax - yMin) / yTickUnitNumber;
        }

    }

    /*
    * s0, t0 - The coordinate of the technological position and the time when the subjects of labour began to come in at this position
    * tS= Ts/Td, - Ratio of the duration of the works day to the duration of the production cycle
    * */
    class TaskParameters {

        protected double s0 = 0.0;
        protected double t0 = 0.0;          protected double tK = new AxisParametrs().tMax;
        protected double tS = 0.5;



    }

    class Psi {
        protected DoubleFunction<Double> hm1_S = s -> MathP.h(s)*(1-s);

    }

    class G {
        private TaskParameters taskParameters;
        protected DoubleFunction<Double> g0_g1coswt;
        private double wS;

        public G() {
            this( new TaskParameters () );
        }
        public G(TaskParameters taskParameters) {
            this.taskParameters = taskParameters;
            this.wS = 2.0*Math.PI / taskParameters.tS;
            g0_g1coswt = t -> 2.0 - Math.cos(wS*t);
            // There are .....
        }
    }

    class Gamma {
        protected DoubleFunction<Double> h = t -> MathP.h(t);
    }

    class CashIntegralValue {
        private List<Pair<Double, Double>> integralCashSorted;
        private double C1max = Double.MIN_VALUE;
        private double C1min = Double.MAX_VALUE;


        public CashIntegralValue() {
            integralCashSorted = new ArrayList<>();
        }

        public void addValue(double t, double C1) {
            integralCashSorted.add(new Pair<>(t, C1));
        }

        public void sorted() {
            integralCashSorted = integralCashSorted.stream().sorted((p1, p2) -> (p2.getValue() > p1.getValue()) ? -1 : 1)
                    .collect(Collectors.toList());
            if (!integralCashSorted.isEmpty()) {
                C1max = integralCashSorted.get(integralCashSorted.size()-1).getValue();
                C1min = integralCashSorted.get(0).getValue();
            }
        }

        public double getFromCacheKey(double C1) {
            try {
                if (C1 > C1max) throw new Exception("-------------------- C1 > C1max ---------------------  C1="+C1+"  C1Max="+C1max);
                if (C1 < C1min) throw new Exception("-------------------- C1 < C1min ---------------------  C1="+C1+"  C1Max="+C1min);
            } catch (Exception e) {  e.printStackTrace();    }
            return integralCashSorted.stream().filter(p -> p.getValue() > C1).findFirst().get().getKey();
        }

        public double getKey(int i) {
            return integralCashSorted.get(i).getKey();
        }

        public double getValue(int i) {
            return integralCashSorted.get(i).getValue();
        }

    }

    class MathP {

        static final double NUMBER_AXIS_PARTITION = 100;

        static double h (double x) {
            return ( x==0.0) ? 0.5 : ((x>0) ? 1.0 : 0.0 );
            //return ( x>=0.0) ? 1.0 : 0.0;
        }
        static FourDoubleFunction integralF = (t0, tK, dt, function) -> {

            double result = 0.0;
            for (double t=t0; t<tK; t+=dt ) {
                result = result + function.apply(t)*dt;
            }
            return result;
        };

        static FourDoubleFunction integrationParameter =
                (t0, tK, C1, function) -> {
                    if (C1 <= 0) return t0;

                    double dt = (tK-t0)/NUMBER_AXIS_PARTITION;
                    double result = 0.0;
                    double tP = t0;

                    while (result < C1) {
                        result = integralF.apply(t0, tP, dt, function);
                        tP+=dt;
                    }
                    return tP;
                };


        interface FiveDoubleFunction {
            Double apply(double t0, double tK, DoubleFunction<Double> function);
        }

        interface FourDoubleFunction {
            Double apply(double t0, double tK, double С1, DoubleFunction<Double> function);
        }
    }

}


class StrategyVDependsTime01 implements StrategyVDependsTime {
    private AxisParametrs  axisParametrs;
    private TaskParameters  taskParameters;
    private DoubleFunction<Double> g;
    private DoubleFunction<Double> psi;
    private DoubleFunction<Double> gamma;
    private CashIntegralValue cashIntegralValue;
    double dt;

    public StrategyVDependsTime01() {
        axisParametrs = new AxisParametrs(0.0, 1.0, 0.0, 1.0, 0.0, 2.0, 10.0, 10.0);

        taskParameters = new TaskParameters();
        taskParameters.tK = axisParametrs.gettMax();
        dt= (taskParameters.tK-taskParameters.t0)/MathP.NUMBER_AXIS_PARTITION;

        g = new G().g0_g1coswt;
        psi = new Psi().hm1_S;
        gamma = new Gamma().h;








    }

    @Override
    public double decision(double s, double t) {

//        if (cashIntegralValue==null) {
//
//            MathP.integralF.apply(taskParameters.t0, t, g)
//        }


       double C1 = s - MathP.integralF.apply(taskParameters.t0, t, dt, g) ;

       double tP = MathP.integrationParameter.apply(taskParameters.t0, taskParameters.tK, -C1, g);

       return MathP.h(s)*gamma.apply(tP)/g.apply(tP) - MathP.h(C1)*gamma.apply(tP)/g.apply(tP)
               +MathP.h(C1)*psi.apply(C1);
    }

    @Override
    public double decisionCharacteristic(double s, double t) {

        return  MathP.integralF.apply(taskParameters.t0, t, dt, g)  - s;
    }

    @Override
    public AxisParametrs getAxisParametrs() {
        return axisParametrs;
    }

}