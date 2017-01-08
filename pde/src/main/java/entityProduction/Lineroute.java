package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowLineroute;

import java.util.ArrayList;

public class Lineroute extends RowLineroute {

    private String nameOperation = "nameOperation";
    private String nameMachine = "nameMachine";
    private Operation operation;
    private Machine machine;

    public Lineroute() {
    }

    public Lineroute(DataSet dataSet) {
        super(dataSet, Lineroute.class);
    }

    public Lineroute(int id, String name,
                     Operation operation,
                     Machine machine,
                     int numberWork,
                     int inputBufferMin, int inputBuffer, int inputBufferMax, int outputBufferMin, int outputBuffer, int outputBufferMax, String description) {
        super(id, name, numberWork, inputBufferMin, inputBuffer, inputBufferMax, outputBufferMin, outputBuffer, outputBufferMax, description);
        this.operation = operation;
        this.machine = machine;
        if (operation!=null) nameOperation = operation.getName();
        if (machine!=null)   nameMachine = machine.getName();
    }

    public String getNameOperation() {
        return nameOperation;
    }

    public void setNameOperation(String nameOperation) {
        this.nameOperation = nameOperation;
    }

    public String getNameMachine() {
        return nameMachine;
    }

    public void setNameMachine(String nameMachine) {
        this.nameMachine = nameMachine;
    }
}
