package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowOperation;

public class Operation extends RowOperation {

    /**
     * Инициализирует строку "Технологическая операция"
     *
     * @param id	Id для "Технологическая операция"
     * @param name	Название технологической операции
     * @param description	Описание строки "Технологическая операция"
     */
    public Operation(int id, String name, String description) {
        super(id, name, description);
    }

    public Operation() {
    }

    public Operation(DataSet dataSet) {
        super(dataSet, Operation.class);
    }
}
