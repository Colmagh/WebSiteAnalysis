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
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.mtnp.table.PrimitiveValue;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;
import liflab.wsprofilinglab.table.CumulativeDistributionTable;

public class QuartileMacro extends CumulativeDistributionMacro
{
	
	public QuartileMacro(Laboratory lab, String prefix, CumulativeDistributionTable table, String unit)
	{
		super(lab, prefix, table);
		add(m_prefix + "Fifty", "50th centile for " + unit);
		add(m_prefix + "Ninety", "90th centile for " + unit);
	}

	@Override
	public void computeValues(Map<String, JsonElement> map)
	{
		TempTable ht = m_table.getDataTable();
		String key_name = ht.getColumnName(0);
		String val_name = ht.getColumnName(1);
		float last_val = 0, cur_val = 0;
		for (TableEntry te : ht.getEntries())
		{
			PrimitiveValue val = te.get(val_name);
			if (val.isNumeric())
			{
				cur_val = val.numberValue().floatValue();
				if (cur_val >= .5 && last_val < .5)
				{
					map.put(m_prefix + "Fifty", new JsonNumber(te.get(key_name).numberValue()));
				}
				if (cur_val >= .9 && last_val < .9)
				{
					map.put(m_prefix + "Ninety", new JsonNumber(te.get(key_name).numberValue()));
					break;
				}
				last_val = cur_val;
			}
		}
		
	}
}
