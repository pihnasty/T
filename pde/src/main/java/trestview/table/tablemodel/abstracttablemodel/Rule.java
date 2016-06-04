package trestview.table.tablemodel.abstracttablemodel;

import entityProduction.*;
import persistence.loader.tabDataSet.*;

public enum Rule {  RowWork (RowWork.class),
                    RowMachine(RowMachine.class),
                    RowTypemachine(RowTypemachine.class),
                    RowFunctiondist(RowFunctiondist.class),
                    Work (Work.class),
                    Machine(Machine.class),
                    Functiondist(Functiondist.class),
                    Functiondist2(Functiondist.class);
    private Rule(Class clazz) {  this.clazz = clazz;  }
    private Class clazz;
    public Class getClassTab () { return this.clazz; }
}

