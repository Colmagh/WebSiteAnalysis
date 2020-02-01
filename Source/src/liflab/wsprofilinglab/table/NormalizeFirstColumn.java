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

import ca.uqac.lif.mtnp.table.PrimitiveValue;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TableTransformation;
import ca.uqac.lif.mtnp.table.TempTable;

/**
 * Replaces the content of each entry by its fraction of the
 * sum of all values for the column
 */
public class NormalizeFirstColumn implements TableTransformation
{
	public NormalizeFirstColumn()
	{
		super();
	}

	@Override
	public TempTable transform(TempTable ... tables)
	{
		TempTable in_table = tables[0];
		TempTable out_table = new TempTable(in_table.getId(), in_table.getColumnNames());
		if (in_table.getRowCount() == 0)
		{
			return out_table;
		}
		String col_name = in_table.getColumnName(0);
		float total = 0;
		for (TableEntry te : in_table.getEntries())
		{
			PrimitiveValue o = te.get(col_name);
			if (o.isNumeric())
			{
				total += o.numberValue().floatValue();
			}
		}
		for (TableEntry te : in_table.getEntries())
		{
			TableEntry new_te = new TableEntry(te);
			PrimitiveValue o = te.get(col_name);
			if (o.isNumeric())
			{
				new_te.put(col_name, o.numberValue().floatValue() / total);
			}
			out_table.add(new_te);
		}
		return out_table;
	}
}
