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
import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.Formatter;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.macro.MacroMap;

public class PercentageInvisibleMacro extends MacroMap
{
	public PercentageInvisibleMacro(Laboratory lab)
	{
		super(lab);
		add("percentageInvisible", "Percentage of all DOM nodes that are invisible across all sites");
		add("totalNodes", "Total number of DOM nodes across all sites");
	}

	@Override
	public void computeValues(Map<String,JsonElement> map)
	{
		long total_size = 0, invisible = 0;
		for (Experiment e : m_lab.getExperiments())
		{
			total_size += e.readInt("nbElementTotal");
			invisible += e.readInt("nbNoeudsInvisibles");
		}
		int percentage = (int) Formatter.divide(invisible * 100, total_size);
		map.put("percentageInvisible", new JsonNumber(percentage));
		map.put("totalNodes", new JsonNumber(total_size));
	}
}
