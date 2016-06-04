package old.database.tabDataSet;

import old.database.DataSet;

/**
 * Описание сущности строка таблицы: Машина (оборудование)
 *
 * @author ПОМ
 *
 */
public class RowMachine extends RowIdNameDescription {

    /**
     * Инициализирует строку таблицы: Машина
     * @param id	Id машины
     * @param name	Имя машины
     * @param locationX	Расположение машины в цеху X-координата
     * @param locationY	Расположение машины в цеху Y-координата
     * @param foto	Фото машины
     * @param state	Состояние машины (Например вероятность поломки)
     * @param description	Описание машины
     */
    public RowMachine(int id, String name, double locationX, double locationY, double state, String description) {
        super(id, name, description);
        this.locationX = locationX;
        this.locationY = locationY;
        this.setState(state);
    }

    public RowMachine() {
    }

    public RowMachine(DataSet dataSet, Class cL) {
        super(dataSet, cL);
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public Double getState() {
        return state;
    }

    public void setState(Double state) {
        this.state = state;
    }
    
    /* Расположение машины в цеху (координата Х)                                */
    private double locationX = 0;
    /* Расположение машины в цеху (координата Y)                                */
    private double locationY = 0;
    /* Фото машины								*/

    /* Состояние машины до ремонта                                              */
    private double state = 0.0;
}
