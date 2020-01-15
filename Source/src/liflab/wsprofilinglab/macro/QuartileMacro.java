package liflab.wsprofilinglab.macro;

import java.util.Map;

import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonNumber;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.mtnp.table.PrimitiveValue;
import ca.uqac.lif.mtnp.table.TableEntry;
import ca.uqac.lif.mtnp.table.TempTable;
import liflab.wsprofilinglab.table.CumulativeDistributionTable;

public class QuartileMacro extends CumulativeDistributionMacro
{
	
	public QuartileMacro(Laboratory lab, String prefix, CumulativeDistributionTable table, String unit)
	{
		super(lab, prefix, table);
		add(m_prefix + "Fifty", "50th centile for " + unit);
		add(m_prefix + "Ninety", "90th centile for " + unit);
	}

	@Override
	public void computeValues(Map<String, JsonElement> map)
	{
		TempTable ht = m_table.getDataTable();
		String key_name = ht.getColumnName(0);
		String val_name = ht.getColumnName(1);
		float last_val = 0, cur_val = 0;
		for (TableEntry te : ht.getEntries())
		{
			PrimitiveValue val = te.get(val_name);
			if (val.isNumeric())
			{
				cur_val = val.numberValue().floatValue();
				if (cur_val >= .5 && last_val < .5)
				{
					map.put(m_prefix + "Fifty", new JsonNumber(te.get(key_name).numberValue()));
				}
				if (cur_val >= .9 && last_val < .9)
				{
					map.put(m_prefix + "Ninety", new JsonNumber(te.get(key_name).numberValue()));
					break;
				}
				last_val = cur_val;
			}
		}
		
	}
}
