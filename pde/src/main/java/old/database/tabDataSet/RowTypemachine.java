package old.database.tabDataSet;

import old.database.DataSet;

/**
 * Описание сущности строка таблицы: Машина (оборудование)
 *
 * @author ПОМ
 */
public class RowTypemachine extends RowIdNameDescription {

    /**
     * Инициализирует строку таблицы: Машина
     *
     * @param id	Id Типа машины
     * @param name	Название типа машины
     * @param description	Описание машины
     */
    public RowTypemachine(int id, String name, String description) {
        super(id, name, description);
    }

    public RowTypemachine() {
        super();
        this.setName("Пресса кривошипные");
    }

    public RowTypemachine(DataSet dataSet, Class cL) {
        super(dataSet, cL);
        this.setName("Пресса кривошипные");
    }
}
