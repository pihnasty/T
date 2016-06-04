package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowUnit;

/**
 * �������� �������� "������� ���������"
 *
 * @author POM
 */

public class Unit extends RowUnit {
	/**
	 * �������������� ������:  "������� ���������" 
	 * @param id			Id "������� ���������" 
	 * @param name			��� "������� ���������" 
	 * @param description	�������� "������� ���������" 
	 */		
	public Unit(int id, String name, String description)		{	super(id, name, description);	}	
	public Unit()												{ 	super();	 					}	
	public Unit ( DataSet dataSet)								{	super( dataSet,  Unit.class);	}
}
