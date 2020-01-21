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
