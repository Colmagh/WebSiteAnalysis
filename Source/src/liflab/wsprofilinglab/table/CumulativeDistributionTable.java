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

import ca.uqac.lif.mtnp.table.HardTable;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;

public class CumulativeDistributionTable extends HardTable  {

	HistogramTable histTable;
	String xAxis, yAxis;
	
	public CumulativeDistributionTable (HistogramTable histTable, String xAxis, String yAxis)
	{
		super();
		this.histTable = histTable;
		this.xAxis =xAxis;
		this.yAxis = yAxis;
	}
	
	public TempTable getDataTable(boolean b)
	{
		HardTable tmp = new HardTable(xAxis, yAxis);
				
		tmp.addAll(generateTableEntries());		
		return tmp.getDataTable(b);
	}
	
	private Collection<TableEntry> generateTableEntries()
	{
		Collection<TableEntry> entries = new ArrayList<TableEntry>();
		HashMap<String, Double> values = new HashMap<String, Double>();
		TempTable table = histTable.getDataTable(true);
		int nbRows = table.getRowCount();
		double total = 0;
		
		for(int i = 0; i < nbRows; i++)
		{
			
			total += table.get(1, i).numberValue() != null ? Double.parseDouble(table.get(1,i).toString()):0;
			values.put(table.get(0, i).toString(), total);
		}
		
		for(int i = 0; i < values.size(); i ++)
		{
			values.replace(table.get(0,i).toString(),values.get(table.get(0,i).toString())/total);
		}
		
		for(int i = 0; i < values.size(); i++)
		{
			TableEntry te = new TableEntry();
			te.put(yAxis, values.get(table.get(0,i).toString()));
			te.put(xAxis, table.get(0, i));
			entries.add(te);
		}
		
		return entries;
	}
}
