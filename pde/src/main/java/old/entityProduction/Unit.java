package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowUnit;

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
