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
package liflab.wsprofilinglab;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.ExperimentException;
import ca.uqac.lif.mtnp.util.FileHelper;

public class WebSiteExperiment extends Experiment 
{
	/**
	 * Name for parameter "Site"
	 */
	public static final transient String SITE_NAME = "Site";
	
	/**
	 *	Name for parameter containing the file extension
	 */
	public static final transient String FILE_EXTENSION = ".json";
	
	/**
	 * Name for parameter containing the file name modifier
	 */
	public static final transient String FILE_NAME_MODIFIER = "_statistics";
	
	/**
	 * Names for added parameters to the experiment
	 */
	public static final transient String NB_CLASS = "nbClass";
	public static final transient String MIN_PER_CLASS = "minPerClass";
	public static final transient String MAX_PER_CLASS = "maxPerClass";
	public static final transient String AVG_PER_CLASS = "avgPerClass";

	/**
	 * List of experiment parameters that do <em>not</em> correspond to HTML
	 * tag names
	 */
	private static final List<String> s_whiteList = Arrays.asList(
			"visibility",
			"display",
			"widthOrHeight",
			"negativePosition",
			"outsidePosition",
			"nbElementTotal",
			"profondeurMinArbre",
			"profondeurMaxArbre",
			"degreMinArbre",
			"degreMaxArbre",
			"nbNoeudsInvisibles",
			"Pas de classe");

	/**
	 * List of tag names that can be found in the &lt;svg&gt; part of a
	 * page
	 */
	private static final List<String> s_svgList = Arrays.asList(
			"a",
			"animate",
			"animateMotion",
			"animateTransform",
			"circle",
			"clipPath",
			"color-profile",
			"defs",
			"desc",
			"discard",
			"ellipse",
			"feBlend",
			"feColorMatrix",
			"feComponentTransfer",
			"feComposite",
			"feConvolveMatrix",
			"feDiffuseLighting",
			"feDisplacementMap",
			"feDistantLight",
			"feDropShadow",
			"feFlood",
			"feFuncA",
			"feFuncB",
			"feFuncG",
			"feFuncR",
			"feGaussianBlur",
			"feImage",
			"feMerge",
			"feMergeNode",
			"feMorphology",
			"feOffset",
			"fePointLight",
			"feSpecularLighting",
			"feSpotLight",
			"feTile",
			"feTurbulence",
			"filter",
			"foreignObject",
			"g",
			"hatch",
			"hatchpath",
			"image",
			"line",
			"linearGradient",
			"marker",
			"mask",
			"mesh",
			"meshgradient",
			"meshpatch",
			"meshrow",
			"metadata",
			"mpath",
			"path",
			"pattern",
			"polygon",
			"polyline",
			"radialGradient",
			"rect",
			"script",
			"set",
			"solidcolor",
			"stop",
			"style",
			"svg",
			"switch",
			"symbol",
			"text",
			"textPath",
			"title",
			"tspan",
			"unknown",
			"use",
			"view");

	public WebSiteExperiment(String filePath)
	{
		this();
		String siteName = filePath.replace(FILE_NAME_MODIFIER, "");
		siteName = siteName.replace(FILE_EXTENSION, "");
		setInput(SITE_NAME, siteName);
	}

	WebSiteExperiment()
	{
		super();
		describe(SITE_NAME, "The name of the web site");
	}

	@Override
	public void execute() throws ExperimentException, InterruptedException
	{
		String filePath = getFilePath();
		String contents = FileHelper.readToString(WebSiteExperiment.class.getResourceAsStream(ProfilingLab.SITES_FOLDER + "/" + filePath));
		JSONParser parser = new JSONParser();

		JSONObject data;
		
		try 
		{
			//Get the web site's data
			data = (JSONObject) parser.parse(contents);
		}
		catch (ParseException e)
		{
			throw new ExperimentException(e);
		}
		
		@SuppressWarnings("unchecked")
		Iterator<String> keys = (Iterator<String>) data.keySet().iterator();
		int maxPerClass = 0;
		int minPerClass = Integer.MAX_VALUE;
		int avgPerClass = 0;
		int nbClass = 0;

		/**
		 * Go through the JSON to get every attribute and compile them into
		 * desirable statistics
		 */
		while(keys.hasNext())
		{
			Object key = keys.next();
			String keyName = key.toString();
			int keyValue = Integer.parseInt(data.get(key).toString());

			/**
			 * If it has a lower case, isn't in the white list nor in the SVG list,
			 * then it's a class name and must be compiled, otherwise it is added as
			 * such to the experiment
			 */
			if(containsLowerCase(keyName) && !s_whiteList.contains(keyName) && !s_svgList.contains(keyName))
			{
				nbClass++;
				avgPerClass += keyValue;

				if (minPerClass > keyValue)
				{
					minPerClass = keyValue;
				}

				if (maxPerClass < keyValue)
				{
					maxPerClass = keyValue;
				}
			}
			else
			{
				write(keyName,keyValue);
			}
		}

		// Add the compiled data to the experiment
		write(NB_CLASS, nbClass);
		write(MIN_PER_CLASS, minPerClass);
		write(MAX_PER_CLASS, maxPerClass);			
		write(AVG_PER_CLASS, nbClass > 0 ? avgPerClass / nbClass : 0);
	}

	private String getFilePath()
	{
		//Get the name of the internal file containing the harvested data for this site
		String filePath = readString(SITE_NAME);
		if(filePath.contains("Copie"))
		{
			filePath = filePath.replace(" - Copie", FILE_NAME_MODIFIER + " - Copie" + FILE_EXTENSION);
		}
		else
		{
			filePath += FILE_NAME_MODIFIER + FILE_EXTENSION;
		}
		
		return filePath;
	}
	
	private boolean containsLowerCase(String str)
	{
		//convert String to char array
		char[] charArray = str.toCharArray();

		for(int i=0; i < charArray.length; i++)
		{
			//if any character is not in lower case, return false
			if( Character.isLowerCase( charArray[i] ))
			{
				return true;
			}
		}
		return false;
	}
}
