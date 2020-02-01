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
package liflab.wsprofilinglab.macro;

import java.util.Map;

import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.mtnp.table.PrimitiveValue;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;
import liflab.wsprofilinglab.ProfilingLab;
import liflab.wsprofilinglab.table.PieChartTable;

public class FractionInvisibleMacroNegative extends PieChartTableMacro
{
	/**
	 * The name of the macro
	 */
	protected static final transient String NAME = "fractionInvisibleNegative";
	
	public FractionInvisibleMacroNegative(ProfilingLab lab, PieChartTable table)
	{
		super(lab, table);
		add(NAME, "Percentage of all invisible elements using the negative position method");
	}
	
	@Override
	public void computeValues(Map<String, JsonElement> map) 
	{
		TempTable ht = m_table.getDataTable();
		String key_name = ht.getColumnName(1);
		String val_name = ht.getColumnName(0);
		int total = 0, negative = 0;
		for (TableEntry te : ht.getEntries())
		{
			PrimitiveValue key = te.get(key_name);
			PrimitiveValue val = te.get(val_name);
			total += val.numberValue().intValue();
			if (key.stringValue().compareTo("negativePosition") == 0)
			{
				negative = val.numberValue().intValue();
			}
		}
		int percentage = (int) (((float) negative * 100f) / ((float) total));
		map.put(NAME, new JsonNumber(percentage));
	}

}
