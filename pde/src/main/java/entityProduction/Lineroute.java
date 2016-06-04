package entityProduction;

import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowLineroute;

import java.util.ArrayList;

public class Lineroute extends RowLineroute
{
	/**
	 * �������������� ������ ���������� �����
	 * @param id			Id ������ ���������� �����
	 * @param name			�������� ������ ���������� ����� 
	 * @param Operation		������ ��������������� ��������, ������������ � ��������������� �������� � ������������ ��� ������������ �������  
	 * @param numberWork	���������� ����� ���������� ��������������� ��������, ������������ � ��������������� �������� 
	 * @param Machine		������ ������������, ������������� ��� ���������� ��������������� ��������
	 * @param Emplousee		������ ����������, ������������� ��� ���������� ��������������� �������� 
	 * @param linespecs		��������� ������������ ���� �������, ������������ ��� ���������� ��������������� �������� 
	 * @param inputBufferMin	����������� ������� �������� ������, ������������� ��� ���������� ��������������� �������� 
	 * @param inputBuffer		������� ������� �������� ������, ������������� ��� ���������� ��������������� ��������  
	 * @param inputBufferMax	������������ ������� �������� ������, ������������� ��� ���������� ��������������� ��������  
	 * @param outputBufferMin	����������� ������� �������� ������, ������������� ��� ���������� ��������������� �������� 
	 * @param outputBuffer		������� ������� �������� ������, ������������� ��� ���������� ��������������� ��������  
	 * @param outputBufferMax	������������ ������� �������� ������, ������������� ��� ���������� ��������������� ��������   
	 * @param description	�������� ������ ���������� �����
	 */	
	public Lineroute(int id, String name,
					 ArrayList<Operation> operations,
					 int numberWork, ArrayList<Machine> machines, ArrayList<Employee> employees, ArrayList<Linespec> linespecs,
					 int inputBufferMin, int inputBuffer, int inputBufferMax, int outputBufferMin, int outputBuffer, int outputBufferMax, String description)		{
		super(id, name, numberWork, inputBufferMin, inputBuffer, inputBufferMax, outputBufferMin,outputBuffer, outputBufferMax, description);

		this.linespecs = linespecs;
		nameOperation 	= operations.get(0).getName();
		nameMachine 	= machines	.get(0).getName();
		nameEmployee 	= employees	.get(0).getName();
	}

	public Lineroute()														{									}
	public Lineroute(DataSet dataSet)										{	super(dataSet, Lineroute.class);}



	public ArrayList<Linespec> getLinespecs()							    {    return linespecs;				}
	public void setLinespecs(ArrayList<Linespec> linespecs)				    {    this.linespecs = linespecs;    }
	public String getNameOperation()									    {    return nameOperation;		    }
	public void setNameOperation(String nameOperation)					    {    this.nameOperation = nameOperation;    }
	public String getNameMachine()										    {    return nameMachine;		    }
	public void setNameMachine(String nameMachine)							{    this.nameMachine = nameMachine;}
	public String getNameEmployee()										    {    return nameEmployee;		    }
	public void setNameEmployee(String nameEmployee)					    {    this.nameEmployee = nameEmployee;}


	/** �������� ��������������� �������� � ��������������� ��������				  */
	private String nameOperation  ="nameOperation";
	/** �������� ������ � ��������������� ��������									  */
	private String nameMachine  ="nameMachine";
	/** ������� ����������, ������������� �� ���������� ��������������� �������� � ��������������� ��������		*/
	private String nameEmployee  ="nameEmployee";


	private ArrayList<Linespec> linespecs = new ArrayList<Linespec>();
	
}
