package trestview.table.tablemodel.abstracttablemodel;

import entityProduction.Modelmachine;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by pom on 19.03.2016.
 */
public class ParametersColumnMap  {
    private static Map<String,ParametersColumn> map;
    private ParametersColumnMap () {
        map = new HashMap<>();
        // Parameters  of column  for RowWork
        map.put("id", new ParametersColumn ("id",      int.class, false, 40) );
        map.put("name", new ParametersColumn ("name", String.class, true, 150) );
        map.put("scheme", new ParametersColumn ("scheme", String.class, true, 200) );
        map.put("overallSize", new ParametersColumn ("overallSize", double.class, true, 90) );
        map.put("scaleEquipment", new ParametersColumn ("scaleEquipment", double.class, true, 90) );
        map.put("description", new ParametersColumn ("description",String.class, true, 300) );
        map.put("image", new ParametersColumn ("image",Image.class, true, 100) );
        // Parameters  of column  for RowMachine
        map.put("locationX", new ParametersColumn ("locationX",double.class, true, 100) );
        map.put("locationY", new ParametersColumn ("locationY",double.class, true, 100) );
        map.put("angle", new ParametersColumn ("angle",double.class, true, 100) );
        map.put("state", new ParametersColumn ("state",double.class, true, 100) );
        // Parameters  of column  for Machine
        map.put("modelmachine", new ParametersColumn ("modelmachine", Modelmachine.class, true, 100) );
        // Parameters  of column  for Functiondist
        map.put("averageValue", new ParametersColumn ("averageValue",double.class, true, 100) );
        map.put("meanSquareDeviation", new ParametersColumn ("meanSquareDeviation",double.class, true, 100) );
        map.put("pathData", new ParametersColumn ("pathData", String.class, true, 200) );
    }

    public static ParametersColumn getParametersColumn(String key) {
        new ParametersColumnMap ();
        return map.get(key);
    }

}