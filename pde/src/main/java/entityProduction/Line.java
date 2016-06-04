package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowLine;

import java.util.ArrayList;
import java.util.Date;

/**
 * �������� �������� ������ �  ������ 
 * @author POM
 */
public class Line extends RowLine	{	
	
	/**
	 * �������������� ������:  "������ ������"
	 * @param id			Id "������ ������"
	 * @param name			��� "������ ������"
	 * @param dateBegin		����������� ����� ������ ������� � ������������ ������� � �������  "������ ������" 
	 * @param dateEnd		����������� ����� ���������	������������ ������� � �������  "������ ������" 
	 * @param description	�������� "������ ������"
	 */		
	public Line(int id, String name, ArrayList<Subject_labour>subject_labours, ArrayList<Unit>units, double quantity, Date dateBegin, Date dateEnd, String description){
			super(id, name, quantity, dateBegin, dateEnd, description);

    		nameSubject_labour 	= subject_labours.get(0).getName();
    		price 				= subject_labours.get(0).getPrice();
    		unit                = units.get(0).getName();


	}	
	public Line()							{ 	super(); 					}	
	public Line ( DataSet dataSet)			{	super( dataSet,  Line.class);		}
	

	
	public String getNameSubject_labour()	{	return nameSubject_labour;	}
	public void setNameSubject_labour(String nameSubject_labour)	
											{	this.nameSubject_labour = nameSubject_labour;	}
	public double getPrice()				{	return price;				}
	public void setPrice(double price)		{	this.price = price;			}
	public String getUnit()					{	return unit;				}
	public void setUnit(String unit)		{	this.unit = unit;			}
	
	
	
	
	/*________________________________________________________________________________*/
	/**������� ����� (���������� �������) � ������ ����������� ������				  */
//	private Subject_labour  	subject_labour;	
	/** �������� ������� ����� (���������� �������) � ������ ����������� ������		  */
	private String nameSubject_labour  ="nameSubject_labour";	
	/** ���� ������� ����� (���������� �������) � ������ ����������� ������			  */	
	private double price = 0.0;
	/** ������� ��������� ������� ����� (���������� �������) � ������ �������. ������ */	
	private String unit = "��.���";	
}
