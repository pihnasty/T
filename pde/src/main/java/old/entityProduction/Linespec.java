package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowLinespec;

import java.util.ArrayList;

public class Linespec extends RowLinespec
{
	/**
	 * Инициализирует объект:  "строка спецификации"
	 * @param id			Id "строка спецификации"
	 * @param name			Имя "строка спецификации".
	 * @param resourceName	Название используемого ресурса
	 * @param m				Среднее значения потребления ресурса в ходе выполнения технологической операции	 (математическое ожидание)			
	 * @param sigma			Среднеквадратичное отклонение потребления ресурса в ходе выполнения технологической операции	 			
	 * @param distFunc		Функция распределения использования технологических ресурсов для  выполнения технологической операции. 	Каждая технологическая операция обрабатывает предмет труда, потребление ресурса которого распределено по заданному закону 		
	 * @param unit			Среднеквадратичное отклонение потребления ресурса в ходе выполнения технологической операции
	 * @param description	Описание "строка заказа"
	 */		
	public Linespec(int id, String name, ArrayList<Resource> resources , double m, double sigma, ArrayList<FunctionOEM> functionOEMs, ArrayList<Unit> units, String description)	{
		super(id, name, m,  sigma, description);
		setUnit(units.get(0).getName());	
		setDistFunc(functionOEMs.get(0).getName());	
		setResourceName(resources.get(0).getName());
	}
	public Linespec()	{	}
	public Linespec(DataSet dataSet)							{	super(dataSet, Linespec.class);			}
	
	public String getUnit()										{	return unit;				}
	public void setUnit(String unit)							{	this.unit = unit;			}	
	public String getDistFunc()									{	return distFunc;			}
	public void setDistFunc(String distFunc)					{	this.distFunc = distFunc;	}
	public String getResourceName()							    {   return resourceName;	    }
	public void setResourceName(String resourceName)		    {	this.resourceName = resourceName;    }

	/** Единица измерения предмет труда (правильнее продукт) в строке получен. заказа */	
	private String unit 		= "ед.изм";		
	/** Функция распределения использования технологических ресурсов для  выполнения технологической операции	 					*/
	/**	Каждая технологическая операция обрабатывает предмет труда, потребление ресурса которого распределено по заданному закону	*/	
	private String distFunc 	= "Закон распределения";
	/** Имя использованног технологических ресурсов для  выполнения технологической операции	 									*/	
	private String resourceName	= "resourceName";
	
	
}
