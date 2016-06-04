package old.simulation.GUI.PBuilderRoutePanePack;

import old.entityProduction.Lineroute;
import old.entityProduction.Linespec;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;


public class PTableModelLineRoute <cLa> extends AbstractTableModel {
private ArrayList<cLa> tab;
private Class cL;	
private HashMap <Integer, ColumnData >  columnHeaderLineroute;

public PTableModelLineRoute ( ArrayList<cLa> tab, Class cL, HashMap <Integer, ColumnData >  columnHeaderLineroute) {		 
	this.tab = tab;		this.cL = cL; 	this.columnHeaderLineroute= columnHeaderLineroute;
}

@Override
public int getRowCount()    							{    
	if (this.tab==null) return 0; 
	return tab.size();    									
}

@Override
public int getColumnCount()    							{    return columnHeaderLineroute.size();    				}

@Override
public Object getValueAt(int rowIndex, int columnIndex) {
	if (cL== Lineroute.class) {
		if (columnIndex ==0) return  ((Lineroute) tab.get(rowIndex)).getNumberWork();
		if (columnIndex ==1) return  ((Lineroute) tab.get(rowIndex)).getNameOperation();
		if (columnIndex ==2) return  ((Lineroute) tab.get(rowIndex)).getId();
		if (columnIndex ==3) return  ((Lineroute) tab.get(rowIndex)).getNameMachine();
		if (columnIndex ==4) return  ((Lineroute) tab.get(rowIndex)).getNameEmployee();
		if (columnIndex ==5) return  ((Lineroute) tab.get(rowIndex)).getInputBufferMin();
		if (columnIndex ==6) return  ((Lineroute) tab.get(rowIndex)).getInputBuffer();
		if (columnIndex ==7) return  ((Lineroute) tab.get(rowIndex)).getInputBufferMax();
		if (columnIndex ==8) return  ((Lineroute) tab.get(rowIndex)).getOutputBufferMin();
		if (columnIndex ==9) return  ((Lineroute) tab.get(rowIndex)).getOutputBuffer();
		if (columnIndex ==10)return  ((Lineroute) tab.get(rowIndex)).getOutputBufferMax();
		if (columnIndex ==11)return  ((Lineroute) tab.get(rowIndex)).getName();
		if (columnIndex ==12)return  ((Lineroute) tab.get(rowIndex)).getDescription();
	}
	if (cL== Linespec.class) {
		if (columnIndex ==0) return  ((Linespec) tab.get(rowIndex)).getName();
		if (columnIndex ==1) return  ((Linespec) tab.get(rowIndex)).getId();
		if (columnIndex ==2) return  ((Linespec) tab.get(rowIndex)).getUnit();
		if (columnIndex ==3) return  ((Linespec) tab.get(rowIndex)).getM();
		if (columnIndex ==4) return  ((Linespec) tab.get(rowIndex)).getSigma();
		if (columnIndex ==5) return  ((Linespec) tab.get(rowIndex)).getDistFunc();
		if (columnIndex ==6) return  ((Linespec) tab.get(rowIndex)).getName();
		if (columnIndex ==7) return  ((Linespec) tab.get(rowIndex)).getDescription();
}		    
	return null;
}

@Override
public void setValueAt(Object obj, int rowIndex, int columnIndex) {		
	Object o = getValueForClass(obj,columnHeaderLineroute.get(columnIndex).getColumnClass());
	if (cL== Lineroute.class) {
		if (columnIndex ==0) ((Lineroute) tab.get(rowIndex)). setNumberWork		( (int)    o );
		if (columnIndex ==1) ((Lineroute) tab.get(rowIndex)). setNameOperation  ( (String) o );
		if (columnIndex ==2) ((Lineroute) tab.get(rowIndex)). setId				( (int)    o );
		if (columnIndex ==3) ((Lineroute) tab.get(rowIndex)). setNameMachine 	( (String) o );
		if (columnIndex ==4) ((Lineroute) tab.get(rowIndex)). setNameEmployee 	( (String) o );
		if (columnIndex ==5) ((Lineroute) tab.get(rowIndex)). setInputBufferMin	( (int)    o );
		if (columnIndex ==6) ((Lineroute) tab.get(rowIndex)). setInputBuffer 	( (int)    o );
		if (columnIndex ==7) ((Lineroute) tab.get(rowIndex)). setInputBufferMax ( (int)    o );
		if (columnIndex ==8) ((Lineroute) tab.get(rowIndex)). setOutputBufferMin( (int)    o );
		if (columnIndex ==9) ((Lineroute) tab.get(rowIndex)). setOutputBuffer 	( (int)    o );
		if (columnIndex ==10)((Lineroute) tab.get(rowIndex)). setOutputBufferMax( (int)    o );
		if (columnIndex ==11)((Lineroute) tab.get(rowIndex)). setName			( (String) o );
		if (columnIndex ==12)((Lineroute) tab.get(rowIndex)). setDescription	( (String) o );
	}
	if (cL== Linespec.class) {
		if (columnIndex ==0) ((Linespec) tab.get(rowIndex)). setName		( (String) o );
		if (columnIndex ==1) ((Linespec) tab.get(rowIndex)). setId  		( (int)    o );
		if (columnIndex ==2) ((Linespec) tab.get(rowIndex)). setUnit		( (String) o );
		if (columnIndex ==3) ((Linespec) tab.get(rowIndex)). setM			( (Double) o );
		if (columnIndex ==4) ((Linespec) tab.get(rowIndex)). setSigma 		( (Double) o );
		if (columnIndex ==5) ((Linespec) tab.get(rowIndex)). setDistFunc	( (String) o );
		if (columnIndex ==6)((Lineroute) tab.get(rowIndex)). setName	    ( (String) o );
		if (columnIndex ==7)((Lineroute) tab.get(rowIndex)). setDescription	( (String) o );
	}				
}

public <cL> Object getValueForClass (Object obj, Class cL)	{
	if (cL==Integer.class) return Integer.valueOf(obj.toString());
	if (cL==Double.class)  return Double.valueOf(obj.toString());		
	return (cL) obj.toString();
}


@Override
public boolean isCellEditable (int r, int c ) {	if (c==2) return false; return true;}

@Override
public String getColumnName(int c)  					{ 	return  columnHeaderLineroute.get(c).getColumnName();    }	   

@Override
public Class getColumnClass(int c)  					{ 	return  columnHeaderLineroute.get(c).getColumnClass();    }	
} 




