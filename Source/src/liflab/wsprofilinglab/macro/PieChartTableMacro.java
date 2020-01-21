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
