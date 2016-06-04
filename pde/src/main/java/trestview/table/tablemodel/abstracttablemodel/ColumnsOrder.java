package trestview.table.tablemodel.abstracttablemodel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by pom on 19.03.2016.
 */
public class ColumnsOrder extends ArrayList<String> {
    public ColumnsOrder (String ... args) {
        Arrays.asList(args).stream().filter(s->{this.add(s); return true; }).count();   //         for(String s: args) { this.add(s); }
    }
}
