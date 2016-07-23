package trestview.tasks.conveyorPDE;

import entityProduction.Machine;

interface  IConveyorPdeModel {

    public double initialCondition(double R);
    public double boundaryCondition(double R);
    public boolean comparisonBoundaryCondition(double R);
    public boolean compari();
    public double decision(double I,double B);
    public double h(double R);

}
public class    ConveyorPdeModel {


    public boolean isDddd() {
        return dddd;
    }

    public boolean dddd;

    public ConveyorPdeModel() {
        System.out.println("Hello !!!!!!");
    }





    static public void main (String [] args) {
        ConveyorPdeModel conveyorPdeModel = new ConveyorPdeModel();

    }
}
