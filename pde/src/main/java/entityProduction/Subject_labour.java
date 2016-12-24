/**
 * 
 */
package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowSubject_labour;

import java.util.ArrayList;

public class Subject_labour extends RowSubject_labour {

	public Subject_labour(int id, String name, double price, ArrayList<Route> routes, String description)	{	super(id, name, price, description);	this.routes = routes; 	}
	public Subject_labour ( DataSet dataSet) 																{	super( dataSet,  Subject_labour.class );	 					}
	public Subject_labour()	{}
	
	private ArrayList<Route> routes = new ArrayList<Route>();
	public ArrayList<Route> getRoutes()							{		return routes;			}
	public void setRoutes(ArrayList<Route> routes)				{		this.routes = routes;	}
}
