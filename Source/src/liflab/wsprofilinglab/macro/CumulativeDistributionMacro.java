package liflab.wsprofilinglab.macro;

import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.macro.MacroMap;
import liflab.wsprofilinglab.table.CumulativeDistributionTable;

public abstract class CumulativeDistributionMacro extends MacroMap
{
	protected String m_prefix;

	protected CumulativeDistributionTable m_table;
	
	CumulativeDistributionMacro(Laboratory lab, String prefix, CumulativeDistributionTable table)
	{
		super(lab);
		m_prefix = prefix;
		m_table = table;
	}
}
