package experiments;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ca.uqac.lif.labpal.Experiment;
import ca.uqac.lif.labpal.ExperimentException;

public class WebSiteExperiment extends Experiment {
	private static List<String>WhiteList = Arrays.asList(
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
	
	private static List<String> SVGList = Arrays.asList(
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
	
	public WebSiteExperiment(String filePath, int expID)
	{
		setInput("FilePath", filePath);
		setInput("expID", expID);
	}
	public WebSiteExperiment(String filePath)
	{
		setInput("FilePath", filePath);
	}
	public WebSiteExperiment()
	{
	}
	
	@Override
	public void execute() throws ExperimentException, InterruptedException {
		String filePath = readString("FilePath");
		String siteName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
		
		File f = new File(filePath);
		
		write("siteName", siteName);
		
		try {
			FileReader reader =new FileReader(f);
			JSONParser parser = new JSONParser();
			
			JSONObject data = (JSONObject) parser.parse(reader);
			
		
			@SuppressWarnings("unchecked")
			Iterator<String> keys = (Iterator<String>) data.keySet().iterator();
			int maxParClasse = 0;
			int minParClasse = Integer.MAX_VALUE;
			int moyenneParClasse = 0;
			int nbClasse = 0;
			
			while(keys.hasNext())
			{
				Object key = keys.next();
				String keyName = key.toString();
				int keyValue = Integer.parseInt(data.get(key).toString());
				
				if(containsLowerCase(keyName) && !WhiteList.contains(keyName) && !SVGList.contains(keyName))
				{
					nbClasse++;
					moyenneParClasse += keyValue;
					
					if (minParClasse > keyValue)
					{
						minParClasse = keyValue;
					}
					
					if (maxParClasse < keyValue)
					{
						maxParClasse = keyValue;
					}
				}
				else
				{
					write(keyName,keyValue);
				}
			}

			write("nbClasse", nbClasse);
			write("minParClasse", minParClasse);
			write("maxParClasse", maxParClasse);			
			write("moyenneParClasse", nbClasse > 0 ?moyenneParClasse/nbClasse : 0);
			
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println(filePath + " " +e.getMessage());
		}		
	}
	
	private boolean containsLowerCase(String str)
	{
		//convert String to char array
        char[] charArray = str.toCharArray();
        
        for(int i=0; i < charArray.length; i++){
            
            //if any character is not in lower case, return false
            if( Character.isLowerCase( charArray[i] ))
                return true;
        }
        
        return false;
	}
	
}