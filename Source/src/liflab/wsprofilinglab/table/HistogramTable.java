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

public class HistogramTable extends HardTable {
	
	private Laboratory lab;
	private String param;
	private int[] gaps;
	
	public HistogramTable (Laboratory lab, String param, int ...gaps)
	{
		super();
		this.lab = lab;
		this.param = param;
		this.gaps = gaps;
	}
	
	public TempTable getDataTable(boolean b)
	{
		HardTable tmp = new HardTable(param, "nbSites");
		HashMap<String, Integer> values = getExperimentsData();
				
		tmp.addAll(generateTableEntries(values));
				
		return tmp.getDataTable(b);
	}
	
	private Collection<TableEntry> generateTableEntries(HashMap<String, Integer> siteValues)
	{
		Collection<TableEntry> entries = new ArrayList<TableEntry>();
		
		for(int i = 0; i < gaps.length; i++)
		{
			TableEntry te = new TableEntry();
			te.put(param, String.valueOf(gaps[i]));
			te.put("nbSites", siteValues.get(String.valueOf(gaps[i])));
			entries.add(te);
		}
		
		return entries;
	}
	
	private HashMap<String, Integer> getExperimentsData()
	{
		HashMap<String, Integer> values = new HashMap<String, Integer>();
		Collection<Experiment> experiments = lab.getExperiments();
		
		for(Experiment e : experiments)
		{
			for(int i = 0; i < gaps.length; i++)
			{
				if(e.readInt(param) <= gaps[i])
				{
					int temp = values.get(String.valueOf(gaps[i])) != null? (int) values.get(String.valueOf(gaps[i])):0;
					temp++;
					values.put(String.valueOf(gaps[i]), temp);
					break;
				}
			}
		}
		
		return values;
	}
}
