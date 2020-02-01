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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
	private int topX;
	
	public PieChartTable (Laboratory lab, String firstColumnName, String ... params)
	{
		super();
		this.lab = lab;
		this.params = params;
		generateHashMap();
		this.columnName = firstColumnName;
		this.topX = 0;
	}
	
	public void setTopX(int x)
	{
		if(x > 0)
		{
			this.topX = x;
		}
		else
		{
			this.topX = 0;
		}
	}
	
	@Override
	public TempTable getDataTable(boolean b)
	{
		HardTable tmp = new HardTable(COL_NAME, columnName);
		getExperimentsData();

		if(topX == 0)
			tmp.addAll(generateTableEntries());
		else if(topX > 0)
			tmp.addAll(generateXTableEntries());
				
		return tmp.getDataTable(b);
	}
	
	private Collection<TableEntry> generateXTableEntries()
	{
		Collection<TableEntry> entries = new ArrayList<TableEntry>();
		int others = 0;
		
		List<Entry<String, Integer>> entriesToSort = new ArrayList<Entry<String, Integer>>(nbElement.entrySet());
			
		Collections.sort(entriesToSort, new Comparator<Entry<String, Integer>>() {
		   
			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		});
		
		Collections.reverse(entriesToSort);
		
		for(int i = 0; i < topX; i++)
		{
			TableEntry te = new TableEntry();
			te.put(columnName, entriesToSort.get(i).getKey());
			te.put(COL_NAME, entriesToSort.get(i).getValue());
			entries.add(te);
		}
		
		for(int i = topX; i < entriesToSort.size(); i++)
		{
			others += entriesToSort.get(i).getValue();
		}
		
		TableEntry te = new TableEntry();
		te.put(columnName, "Others");
		te.put(COL_NAME, others);
		entries.add(te);
		
		return entries;
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