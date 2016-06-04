/**
 * 
 */
package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowEmployee;

/**
 * @author Oleg
 *
 */
public class Employee  extends RowEmployee
{	
	/**
	 * �������������� ������: ���������
	 * @param id			Id c���������
	 * @param name			��� ����������
	 * @param description	�������� ����������
	 */
	public Employee (int id, String name,  String description )  {	super(id, name, description);   }	
	public Employee()  {  super();  }	
	public Employee ( DataSet dataSet) 	{	super( dataSet,  Employee.class );	}
}
