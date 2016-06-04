package old.database.tabDataSet;
import old.database.DataSet;

/**
 * �������� �������� ������ �������: ��������������� ��������
 * @author POM
 */
public class RowOperation extends RowIdNameDescription {
	/**
	 * �������������� ������: ��������������� ��������
	 * @param id				Id ��������������� ��������
	 * @param name				��� ��������������� ��������
	 * @param description		�������� ��������������� ��������
	 */
	public RowOperation(int id, String name, String description) 	{	super(id, name,  description);				}
	public RowOperation() {}
	public RowOperation(DataSet dataSet, Class cL)					{	super(dataSet, cL);							}
	




}
