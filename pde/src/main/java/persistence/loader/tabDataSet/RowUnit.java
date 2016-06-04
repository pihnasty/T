package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

/**
 * �������� �������� ������ �������: ������� ���������
 * @author ���
 */
public class RowUnit extends RowIdNameDescription {
	/**
	 * �������������� ������ ������� ���������
	 * @param id	Id ������� ���������
	 * @param name	�������� ������� ���������
	 * @param description	�������� ������� ���������
	 */
	public RowUnit(int id, String name, String description) {	super(id, name, description); 	}	
	public RowUnit (DataSet dataSet, Class cL) 			{	super( dataSet,  cL);			}
	public RowUnit() {}	
}
