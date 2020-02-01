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

import ca.uqac.lif.labpal.macro.MacroMap;
import liflab.wsprofilinglab.ProfilingLab;
import liflab.wsprofilinglab.table.PieChartTable;

public abstract class PieChartTableMacro extends MacroMap
{
	protected PieChartTable m_table;
	
	public PieChartTableMacro(ProfilingLab lab, PieChartTable table)
	{
		super(lab);
		m_table = table;
	}
}
