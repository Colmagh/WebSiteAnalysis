package liflab.wsprofilinglab.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.mtnp.table.HardTable;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;

public class PieChartTable extends HardTable
{
	public static final transient String COL_NAME = "nbElement";
	
	private Laboratory lab;
	private String columnName;
	private String[] params;
	private HashMap<String, Integer> nbElement;
	
	public PieChartTable (Laboratory lab, String firstColumnName, String ... params)
	{
		super();
		this.lab = lab;
		this.params = params;
		generateHashMap();
		this.columnName = firstColumnName;
	}
	
	@Override
	public TempTable getDataTable(boolean b)
	{
		HardTable tmp = new HardTable(COL_NAME, columnName);
		getExperimentsData();

		tmp.addAll(generateTableEntries());
				
		return tmp.getDataTable(b);
	}
	
	private Collection<TableEntry> generateTableEntries()
	{
		Collection<TableEntry> entries = new ArrayList<TableEntry>();
		
		for(int i = 0; i < params.length; i++)
		{
			TableEntry te = new TableEntry();
			te.put(columnName, String.valueOf(params[i]));
			te.put(COL_NAME, nbElement.get(params[i]));
			entries.add(te);
		}
		
		return entries;
	}
	
	private void getExperimentsData()
	{
		Collection<Experiment> experiments = lab.getExperiments();
		int size = params.length;
		
		for(Experiment e : experiments)
		{
			for(int i = 0; i < size; i++)
			{
				
				if(e.hasParameter(params[i]))
				{
					nbElement.put(params[i], nbElement.get(params[i]) + e.getAllParameters().getInt(params[i]));
				} 
			}
		}
	}
	
	private void generateHashMap()
	{
		nbElement = new HashMap<String, Integer>();
		for(int i = 0; i < params.length; i++)
		{
			nbElement.put(params[i], 0);
		}
	}
}