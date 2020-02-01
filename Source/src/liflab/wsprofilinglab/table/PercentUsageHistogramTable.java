/*
  A lab for web site profiling
  Copyright (C) 2019-2020 Xavier Chamberland-Thibeault and Sylvain Hallé

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package liflab.wsprofilinglab.table;

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

