package trestview.table.tablemodel.abstracttablemodel;

import entityProduction.Functiondist;
import persistence.loader.tabDataSet.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pom on 19.03.2016.
 */
public class ColumnsOrderMap {
    private static Map<Rule, ColumnsOrder> map;

    private ColumnsOrderMap() {
        map = new HashMap<>();
        map.put(Rule.RowWork, new ColumnsOrder("id", "name", "scheme", "image", "overallSize", "scaleEquipment", "description"));
        map.put(Rule.Work, new ColumnsOrder("id", "name", "scheme", "image", "overallSize", "scaleEquipment", "description"));
        map.put(Rule.RowMachine, new ColumnsOrder("id", "name", "image","locationX","locationY",  "state",  "description" ));
        map.put(Rule.Machine, new ColumnsOrder("id", "name", "modelmachine", "image","locationX","locationY", "angle", "state",  "description" ));

        map.put(Rule.Subject_labour, new ColumnsOrder("id", "name", "price","unitName", "description"));
        map.put(Rule.Route, new ColumnsOrder("id", "name","description" ));
        map.put(Rule.Lineroute, new ColumnsOrder("numberWork","name","nameOperation","nameMachine","inputBufferMin","inputBuffer","inputBufferMax","outputBufferMin","outputBuffer","outputBufferMax","id","description" ));

        map.put(Rule.RowTypemachine, new ColumnsOrder("id", "name", "description" ));
        map.put(Rule.RowFunctiondist, new ColumnsOrder("id", "name", "description" ));
        map.put(Rule.RowUnit, new ColumnsOrder("id", "name", "description" ));
        map.put(Rule.RowOperation, new ColumnsOrder("id", "name", "description" ));
        map.put(Rule.Functiondist, new ColumnsOrder("id", "name", "averageValue", "meanSquareDeviation", "pathData", "description" ));
        map.put(Rule.Functiondist2, new ColumnsOrder("id", "name" ));
    }

    public static ColumnsOrder getColumns(Rule key) {
        new ColumnsOrderMap();
        return map.get(key);
    }
}



