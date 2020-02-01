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

public class FractionInBetweenMacro extends CumulativeDistributionMacro
{
	protected int m_lowerBound;
	
	protected int m_higherBound;
	
	public FractionInBetweenMacro(Laboratory lab, String prefix, CumulativeDistributionTable table, String unit, int start, int end)
	{
		super(lab, prefix, table);
		add(m_prefix + "Lower", "Lower bucket " + unit);
		add(m_prefix + "Upper", "Upper bucket for " + unit);
		add(m_prefix + "Fraction", "Fraction of sites with " + unit + " between " + start + " and " + end);
		m_lowerBound = start;
		m_higherBound = end;
	}

	@Override
	public void computeValues(Map<String, JsonElement> map) 
	{
		TempTable ht = m_table.getDataTable();
		String key_name = ht.getColumnName(0);
		String val_name = ht.getColumnName(1);
		float low_val = 0, high_val = 0;
		for (TableEntry te : ht.getEntries())
		{
			PrimitiveValue key = te.get(key_name);
			PrimitiveValue val = te.get(val_name);
			if (key.isNumeric() && val.isNumeric())
			{
				float cur_key = key.numberValue().floatValue();
				if (cur_key == m_lowerBound)
				{
					low_val = val.numberValue().floatValue();
				}
				if (cur_key == m_higherBound)
				{
					high_val = val.numberValue().floatValue();
					break;
				}
			}
		}
		map.put(m_prefix + "Lower", new JsonNumber(m_lowerBound));
		map.put(m_prefix + "Upper", new JsonNumber(m_higherBound));
		map.put(m_prefix + "Fraction", new JsonNumber((int) ((high_val - low_val) * 100)));
	}
}
