package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowWork;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Описание сущности Производство
 * @author ПОМ
 */
public class Work extends RowWork {  
        
    /**
     * Список машин(оборудования), находящегося на производстве
     */
    private ArrayList<Machine> machines = new ArrayList<Machine>();
    /**
     * Список персонала, работающего на производстве
     */
    private ArrayList<Employee> employees = new ArrayList<Employee>();
    /**
     * Список наименований выпускаемой продукции
     */
    private ArrayList<Subject_labour> subject_labours = new ArrayList<Subject_labour>();
    /**
     * Список поступившихся заказов
     */
    private ArrayList<Order> orders = new ArrayList<Order>();
    /**
     * Overall size of the production area on the x-axis (according to the orientation of the scheme).
     */
    private double overallSize;
    /**
     * A scale for a equipment.
     */
    private double scaleEquipment;        

    /**
     * нициализация объекта по заданным параметрам
     *
     * @param id	Id производства
     * @param name	Имя производства
     * @param scheme	Схема производства
     * @param overallSize	Overall size of the production area on the x-axis (according to the orientation of the scheme).
     * @param scaleEquipment	A scale for a equipment.
     * @param machines	Список машин(оборудования), находящегося на производстве
     * @param employees	Список персонала, работающего на производстве
     * @param products	Список наименований продукции, которую выпускает
     * производство
     * @param orders	Список заказов, которые поступили на производство
     * @param description	Описание производства
     * @throws Throwable
     * @throws ParserConfigurationException
     */
    public Work(int id, String name, String scheme, double overallSize, double scaleEquipment,
            ArrayList<Machine> machines,
            ArrayList<Employee> employees,
            ArrayList<Subject_labour> subject_labours,
            ArrayList<Order> orders,
            String description) {
        super(id, name, scheme, overallSize, scaleEquipment, description);
        this.machines = machines;
        this.employees = employees;
        this.subject_labours = subject_labours;
        this.orders = orders;
        this.overallSize = overallSize;
        this.scaleEquipment = scaleEquipment;
    }

    public Work() {
    }

    /**
     * Инициализация нового пустого объекта с уникальным iD
     *
     * @param dataSet	текущий объект DataSet
     */
    public Work(DataSet dataSet) {
        super(dataSet, Work.class);
    }

    public void show() {
        try {
            DataSet.showObjTabs(this, new Class[]{Machine.class}, machines);
        } catch (Throwable exp) {
            exp.printStackTrace();
        }
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Subject_labour> getSubject_labours() {
        return subject_labours;
    }

    public void setSubject_labours(ArrayList<Subject_labour> subject_labours) {
        this.subject_labours = subject_labours;
    }
    
}
