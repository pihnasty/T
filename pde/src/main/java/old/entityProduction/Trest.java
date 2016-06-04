/**
 * 
 */
package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowTrest;

import java.util.ArrayList;


/**
 * @author Oleg
 *
 */
public class Trest extends RowTrest
{	
	/**
	 * 
	 * @param id			Id ������. ������������ �������������� ����� �������� ��� ������ �������
	 * @param name			��� ������ 
	 * @param description	�������� ������	
	 * @param works
	 */
	public Trest(int id, String name,  String description,	 ArrayList<Work> works)
	{ 
		super(id, name, description); this.works = works; 	 
		//DataSet.showObjTabs(this, new Class [] {Work.class} , works); 
	}	
	/**
	 * ������������� ������ ������� ������� � ���������� iD 
	 * @param dataSet	������� ������ DataSet	
	 */
	public Trest ( DataSet dataSet) 	{	super( dataSet,  Work.class );	}
	public Trest() {}
	
	public void show () 	{	try { DataSet.showObjTabs(this, new Class [] {Work.class} , works);}	catch (Throwable exp)   {  exp.printStackTrace(); } 	}
	
		
	
	
	
	public ArrayList<Work> getWorks()			{	return works;		}
	public void setWorks(ArrayList<Work> works)	{	this.works = works;	}
	
	/*________________________________________________________________________________*/
	/**������ ���������������� �����������  (�����������), ���������� � ������ ������ */
	private ArrayList<Work>  	works;
}


