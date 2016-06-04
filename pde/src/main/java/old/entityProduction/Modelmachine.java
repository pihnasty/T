/**
 *
 */
package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowModelmachine;

/**
 * @author POM Единица производственного оборудования
 */
public class Modelmachine extends RowModelmachine {

    public Modelmachine() {
        super();
    }

    /**
     * Инициализирует объект: Модель машины
     *
     * @param id	Id модели машины
     * @param name	Название модели машины
     * @param foto	Фото модели машины
     * @param type	Тип машины	(например: прессовое оборудование, термопласты,
     * гильятины, пресса)
     * @param overallDimensionX	Габаритный X-размер машины в метрах
     * @param overallDimensionY	Габаритный Y-размер машины в метрах
     * @param workSizeX	X-размер производственной площади для машины,
     * необходимый для работы в метрах
     * @param workSizeY	Y-размер производственной площади для машины,
     * необходимый для работы в метрах
     * @param foto	Фото машины
     * @param description	Описание машины
     */
    public Modelmachine(int id, String name, String img, String typeMachine, double overallDimensionX, double overallDimensionY, double workSizeX, double workSizeY, String description) {
        super(id, name, img, overallDimensionX, overallDimensionY, workSizeX, workSizeY, description);
        this.typeMachine = typeMachine;
    }

    public Modelmachine(DataSet dataSet) {
        super(dataSet, Modelmachine.class);
    }

    /* Тип машины	 (например: прессовое оборудование, термопласты, гильятины, пресса					*/
    private String typeMachine;

    public String getTypeMachine() {
        return typeMachine;
    }

    public void setTypeMachine(String typeMachine) {
        this.typeMachine = typeMachine;
    }
    

}
