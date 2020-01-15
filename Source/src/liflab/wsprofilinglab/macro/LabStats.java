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
