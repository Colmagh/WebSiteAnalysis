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
import ca.uqac.lif.labpal.macro.MacroMap;

public class LabStats extends MacroMap
{
	
	public LabStats(Laboratory lab)
	{
		super(lab);
		add("numdatapoints", "Number of data points in the whole lab");
		add("numsites", "Number of websites analyzed");
	}

	@Override
	public void computeValues(Map<String, JsonElement> map)
	{
		map.put("numdatapoints", new JsonNumber(m_lab.countDataPoints()));
		map.put("numsites", new JsonNumber(m_lab.getExperiments().size()));
	}
}
