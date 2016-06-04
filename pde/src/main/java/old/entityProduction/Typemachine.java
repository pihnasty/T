/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowTypemachine;

public class Typemachine extends RowTypemachine {

    /**
     * Инициализирует строку "Технологическая операция"
     *
     * @param id	Id для типа машины
     * @param name	Название типа машины
     * @param description	Описание строки "типа машины"
     */
    public Typemachine(int id, String name, String description) {
        super(id, name, description);
    }

    public Typemachine() {
    }

    public Typemachine(DataSet dataSet) {
        super(dataSet, Typemachine.class);
    }
}