package liflab.wsprofilinglab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.mtnp.table.HardTable;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;

public class PercentUsageHistogramTable extends HardTable {
	
	private Laboratory lab;
	private String columnName;
	private String[] desiredGaps;
	HashMap<String, Integer> values;
	
	public PercentUsageHistogramTable (Laboratory lab, String columnName, String ...desiredGaps)
	{
		super();
		this.lab = lab;
		this.columnName = columnName;
		this.desiredGaps = desiredGaps;
	}
	
	public TempTable getDataTable(boolean b)
	{
		HardTable tmp = new HardTable(columnName, "nbSites");
		getExperimentsData();
				
		tmp.addAll(generateTableEntries(values));
				
		return tmp.getDataTable(b);
	}
	
	private Collection<TableEntry> generateTableEntries(HashMap<String, Integer> siteValues)
	{
		Collection<TableEntry> entries = new ArrayList<TableEntry>();
		
		for(int i = 0; i < desiredGaps.length; i++)
		{
			TableEntry te = new TableEntry();
			te.put(columnName, desiredGaps[i]);
			te.put("nbSites", siteValues.get(desiredGaps[i])*100/lab.getExperiments().size());
			entries.add(te);
		}
		
		return entries;
	}
	
	private void getExperimentsData()
	{
		GenerateHashMap();
		Collection<Experiment> experiments = lab.getExperiments();
		
		for(Experiment e : experiments)
		{
			for(int i = 0; i < desiredGaps.length; i++)
			{				
				if(e.hasParameter(desiredGaps[i]))
				{			
					values.put(desiredGaps[i], values.get(desiredGaps[i]) + 1);
				}
			}
		}
	}
	
	private void GenerateHashMap()
	{
		values = new HashMap<String, Integer>();
		for(int i = 0; i < desiredGaps.length; i++)
		{
			values.put(desiredGaps[i], 0);
		}
	}
}

