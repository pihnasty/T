/**
 * 
 */
package old.entityProduction;

import old.database.DataSet;
import old.database.tabDataSet.RowSubject_labour;

import java.util.ArrayList;

/**
 * @author POM
 *	������� �����
 */
public class Subject_labour extends RowSubject_labour {
	/**
	 * �������������� ������: ������� �����
	 * @param id			Id ������� �����
	 * @param name			��� ������� �����
	 * @param description	�������� ������� �����
	 */
	public Subject_labour(int id, String name,  double price,  ArrayList<Route> routes,	String description)	{	super(id, name, price, description);	this.routes = routes; 	}
	public Subject_labour ( DataSet dataSet) 																{	super( dataSet,  Subject_labour.class );	 					}
	public Subject_labour()	{}
	
	private ArrayList<Route> routes = new ArrayList<Route>();
	public ArrayList<Route> getRoutes()							{		return routes;			}
	public void setRoutes(ArrayList<Route> routes)				{		this.routes = routes;	}
}
