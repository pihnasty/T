package trestview.tasks.conveyorPDE;

import java.util.function.DoubleFunction;

public interface Strategy {
    double _s0[] = {0.0};
    double _t0[] = {0.0};
    double numberOfCurves[] = {10.0};
    double g1[] = {0.0};

    DoubleFunction<Double> sinConveyorPdeModelS = _r -> (2 + Math.sin(2 * Math.PI * _r)) / 3.0;
    DoubleFunction<Double> sinConveyorPdeModelT = _r -> (2 + Math.sin(2 * Math.PI * (0.5 - _r))) / 3.0;
    DoubleFunction<Double> pow2BoundaryCondition = _r -> 0.5;

    double decision(double _s, double _t);

    double decisionCharacteristic(double _s, double _t);

    DoubleFunction<Double> getInitialCondition();

    default DoubleFunction<Double> getBoundaryCondition() {
        return null;
    }

    default double r(double _s, double _t, double _s0, double _t0, double g) {
        return _s - _s0 - g * (_t - _t0);
    }

    default double h(double r) {
        if (r < 0) return 0;
        if (r == 0) return 0.5;
        else return 1.0;
    }

    default double get_s0() {
        return _s0[0];
    }

    default double get_t0() {
        return _t0[0];
    }

    default double getNumberOfCurves() {
        return numberOfCurves[0];
    }

    default double getG1() {
        return g1[0];
    }

}


class Strategy01 implements Strategy {
    public Strategy01() {
        _s0[0] = 0.5;
        _t0[0] = 0.0;
        numberOfCurves[0] = 10.0;
        g1[0] = 2.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r = r(_s, _t, get_s0(), get_t0(), getG1());
        return getInitialCondition().apply(_r + get_s0());
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG1());
    }

    @Override
    public DoubleFunction<Double> getInitialCondition() {
        return sinConveyorPdeModelS ;
    }
}

class Strategy02 implements Strategy {
    public Strategy02() {
        _s0[0] = 0.5;
        _t0[0] = 0.0;
        numberOfCurves[0] = 10.0;
        g1[0] = 1.0;
    }

    @Override
    public double decision(double _s, double _t) {
        double _r = r(_s, _t, get_s0(), get_t0(), getG1());
        return getInitialCondition().apply(_r + get_s0());
    }

    @Override
    public double decisionCharacteristic(double _s, double _t) {
        return  r(get_s0(), get_t0(), _s, _t, getG1());
    }

    @Override
    public DoubleFunction<Double> getInitialCondition() {
        return  sinConveyorPdeModelS ;
    }
}