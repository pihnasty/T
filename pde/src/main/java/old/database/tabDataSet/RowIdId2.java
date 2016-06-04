package old.database.tabDataSet;
public class RowIdId2	{
	public RowIdId2(Integer id, int id2, String description) 	{	this.id=id; this.id2=id2;	this.description=description;	}
	public RowIdId2()    {}
	public int getId()								{	return id;						}
	public void setId(int id)						{	this.id = id;					}
	public int getId2()								{	return id2;						}
	public void setId2(int id2)						{	this.id2 = id2;					}
	public String getDescription()					{	return description;				}
	public void setDescription(String description)	{	this.description = description;	}	
	/* Id ������� ��������							*/	
	private int id;
	/* Id ������� ��������							*/	
	private int id2;	
	/* ��������   ������							*/ 
	private String description;			
}
