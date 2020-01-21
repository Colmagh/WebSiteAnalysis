package liflab.wsprofilinglab.macro;

import java.util.Map;

import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.mtnp.table.PrimitiveValue;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;
import liflab.wsprofilinglab.ProfilingLab;
import liflab.wsprofilinglab.table.PieChartTable;

public class FractionInvisibleMacroNegative extends PieChartTableMacro
{
	/**
	 * The name of the macro
	 */
	protected static final transient String NAME = "fractionInvisibleNegative";
	
	public FractionInvisibleMacroNegative(ProfilingLab lab, PieChartTable table)
	{
		super(lab, table);
		add(NAME, "Percentage of all invisible elements using the negative position method");
	}
	
	@Override
	public void computeValues(Map<String, JsonElement> map) 
	{
		TempTable ht = m_table.getDataTable();
		String key_name = ht.getColumnName(1);
		String val_name = ht.getColumnName(0);
		int total = 0, negative = 0;
		for (TableEntry te : ht.getEntries())
		{
			PrimitiveValue key = te.get(key_name);
			PrimitiveValue val = te.get(val_name);
			total += val.numberValue().intValue();
			if (key.stringValue().compareTo("negativePosition") == 0)
			{
				negative = val.numberValue().intValue();
			}
		}
		int percentage = (int) (((float) negative * 100f) / ((float) total));
		map.put(NAME, new JsonNumber(percentage));
	}

}
