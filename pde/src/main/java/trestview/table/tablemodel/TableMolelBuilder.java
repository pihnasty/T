package trestview.table.tablemodel;

import persistence.loader.DataSet;
import trestview.dictionary.DictionaryModel;


/**
 * Created by pom on 05.03.2016.
 */
public class  TableMolelBuilder <cL> {

    public static <cL> TableBaseModel build (DictionaryModel dictionaryModel, Class tClass) {

        DataSet dataSet = dictionaryModel.getTMenuModel().getTrestModel().getDataSet();
        AbstractFactoryTableModel factoryTableModel = new FactoryTableModel(dataSet,tClass);

        return ((FactoryTableModel) factoryTableModel).getTableModel();
    }
}
