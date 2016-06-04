/**
 * 
 */
package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

import java.util.Date;

/**
 * �������� �������� ������ �������: "������ ������"  (������ ���� �������)
 * @author POM
 */
public class RowLine extends RowIdNameDescription {
	/**
	 * �������������� ������:  "������ ������"
	 * @param id			Id "������ ������"
	 * @param name			��� "������ ������"
	 * @param dateBegin		����������� ����� ������ ������� � ������������ ������� � �������  "������ ������" 
	 * @param dateEnd		����������� ����� ���������	������������ ������� � �������  "������ ������" 
	 * @param description	�������� "������ ������"
	 */
	public RowLine(int id, String name, double quantity, Date dateBegin, Date dateEnd,	String description)	
	{	super(id, name, description); 	this.quantity= quantity;		this.dateBegin=dateBegin;	this.dateEnd=dateEnd;	}
	
	public RowLine()							{ 	super(); 						}
	public RowLine (DataSet dataSet, Class cL) {	super( dataSet,  cL);			}
	
	
	public	double getQuantity()				{		return quantity;			}
	public void setQuantity(int quantity)		{		this.quantity = quantity;	}
	
	public Date getDateBegin()					{	return dateBegin;				}
	public void setDateBegin(Date dateBegin)	{	this.dateBegin = dateBegin;		}
	public Date getDateEnd()					{	return dateEnd;					}
	public void setDateEnd(Date dateEnd)		{	this.dateEnd = dateEnd;			}
	
	/** ����������� ����� ������ ������� � ������������ */	
	private Date dateBegin 	= 	new Date();
	/** ����������� ����� ��������� ������������ ������ */	
	private Date dateEnd 	=	new Date();	
	/** ���������� �������� � �������  "������ ������" */		
	private double quantity =	5.0;
}
