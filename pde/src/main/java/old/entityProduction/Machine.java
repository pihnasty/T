package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowMachine;

/**
 * @author POM Единица производственного оборудования
 */
public class Machine extends RowMachine {
    
    /** The value is used for the storage of data of the machine model. */
    private Modelmachine modelmachine;
        
            
    public Machine() {
        super();
    }

    /**
     * Инициализирует объект: Машина (технлогичекое оборудвание,
     * производственный блок )
     *
     * @param id	Id машины
     * @param name	Имя машины
     * @param locationX	Расположение машины в цеху X-координата
     * @param locationY	Расположение машины в цеху Y-координата
     * @param foto	Фото машины
     * @param state	Состояние машины (Например вероятность поломки)
     * @param description	Описание машины
     */
    public Machine(int id, String name, Modelmachine modelmachine, double locationX, double locationY, double state, String description) {
        super(id, name, locationX, locationY, state, description);
        
        //String img,
        this.modelmachine = modelmachine;
  //      this.setImg(img);
    }

    public Machine(DataSet dataSet) {
        super(dataSet, Machine.class);
    }

    /* Фото машины									*/
    private String img = "Image/Machine/press_04.png";
/*
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    
    */
       public Modelmachine getModelmachine() {
        return modelmachine;
    }

    public void setModelmachine(Modelmachine modelmachine) {
        this.modelmachine = modelmachine;
    }
    
}
