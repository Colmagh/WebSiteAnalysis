package mylab;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import Tables.HistogramTable;
import Tables.PercentUsageHistogramTable;
import Tables.PercentageHistogramTable;
import Tables.PieChartTable;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.mtnp.plot.TwoDimensionalPlot.Axis;
import ca.uqac.lif.mtnp.plot.gnuplot.*;
import experiments.WebSiteExperiment;

public class MyLaboratory extends Laboratory
{
	private List<String> htmlTags = Arrays.asList("SCRIPT", 
												"IFRAME", 
												"TR", 
												"text", 
												"A", 
												"BUTTON", 
												"I", 
												"HEADER", 
												"P", 
												"BR", 
												"FOOTER", 
												"SPAN", 
												"DIV", 
												"LI", 
												"INPUT", 
												"UL", 
												"IMG", 
												"title", 
												"FORM", 
												"H1", 
												"H3", 
												"NOSCRIPT", 
												"STRONG", 
												"TBODY", 
												"HR", 
												"TABLE", 
												"FONT", 
												"ABBR", 
												"TD", 
												"B", 
												"DD", 
												"DL", 
												"DT", 
												"H2", 
												"H4", 
												"THEAD", 
												"SELECT", 
												"ADDRESS", 
												"TH", 
												"CAPTION", 
												"LABEL", 
												"SECTION", 
												"OPTION", 
												"path", 
												"SOURCE", 
												"svg", 
												"g", 
												"symbol", 
												"PICTURE", 
												"TIME", 
												"H5", 
												"NAV", 
												"defs", 
												"use", 
												"LINK", 
												"OL", 
												"ARTICLE", 
												"U", 
												"STYLE", 
												"FIGURE", 
												"MAIN", 
												"circle", 
												"META", 
												"image", 
												"INS", 
												"CENTER", 
												"tspan", 
												"CDC-SEARCH", 
												"ellipse", 
												"CDC-NAV", 
												"SLOT", 
												"VIDEO-JS", 
												"animate", 
												"linearGradient", 
												"stop", 
												"clipPath", 
												"rect", 
												"VIDEO", 
												"H6", 
												"mask", 
												"AMP-FIT-TEXT", 
												"I-AMPHTML-SIZER", 
												"AMP-PIXEL", 
												"AMP-IMG", 
												"AMP-ANALYTICS", 
												"AMP-AD-EXIT", 
												"SUP", 
												"SMALL", 
												"OBJECT", 
												"NB", 
												"BIG", 
												"NOBR", 
												"ASIDE", 
												"EM", 
												"FIGARO-VIDEO-LOOP", 
												//" ", 
												"PRE", 
												"line", 
												"INCLUDE", 
												"SLICE-FRONT-PAGE-NEWS", 
												"SLICE-NEWSBLOC", 
												"CB-BREADCRUMB", 
												"APP-DESKTOP-HEADER", 
												"APP-CMS", 
												"CB-SLICES", 
												"APP-DESKTOP-HEADER-SECOND-LINE", 
												"APP-DESKTOP-HEADER-MENU-ITEM", 
												"APP-FOOTER", 
												"CB-FULLWIDTH-BACKGROUND", 
												"APP-MOBILE-HEADER-CLOSED", 
												"APP-ROOT", 
												"APP-HEADER", 
												"ROUTER-OUTLET", 
												"FOOTER-MENU-TOGGLE", 
												"CB-ARTICLE-THUMBNAIL", 
												"UI-FOOTER", 
												"DYNAMIC-PAGE", 
												"NGX-JSON-LD", 
												"CMS-PAGE", 
												"CB-MINI-TITLE", 
												"SLICE-SECTION-TEXT", 
												"NG-PROGRESS", 
												"SLICE-NEWSROOM", 
												"APP-MOBILE-HEADER-OPENED-BOTTOM-MENU", 
												"CB-ARTICLE-INFO-BAR", 
												"CB-RESPONSIVE-IMG", 
												"APP-MOBILE-HEADER", 
												"APP-DESKTOP-HEADER-ACTION-BUTTON", 
												"SLICE-SUPER-HERO", 
												"APP-MOBILE-HEADER-OPENED", 
												"CB-CTA-BTN-LINK", 
												"LEGEND", 
												"AREA", 
												"MAP", 
												"FIELDSET", 
												"BLOCKQUOTE", 
												"TT", 
												"Q", 
												"CANVAS", 
												"VC:CHAT-WITH-AVA", 
												"LI-ICON", 
												"TEMPLATE", 
												"CODE", 
												"desc", 
												"XPR", 
												"TEXTAREA", 
												"DFN", 
												"DATALIST", 
												"KO-TEMPLATE", 
												"KO-FOOTER", 
												"KO-HEADER", 
												"KO-APP", 
												"pattern", 
												"DEL", 
												"CITE", 
												"BASE", 
												"NONE", 
												"polygon", 
												"S", 
												"metadata", 
												"polyline", 
												"FIGCAPTION", 
												"AUDIO", 
												"PROGRESS",
												"MENU", 
												"A<",  
												"radialGradient", 
												"CONTAINER", 
												"feOffset", 
												"feColorMatrix", 
												"feMerge", 
												"feMergeNode", 
												"feGaussianBlur", 
												"animateTransform", 
												"BBBSEAL", 
												"SUB", 
												"switch", 
												"ALB", 
												"HGROUP",
												"EMBED", 
												"ARK-COOKIEBAR", 
												"ARK-ANALYTICS", 
												"FB:LIKE", 
												"AMP-INSTALL-SERVICEWORKER", 
												"AMP-SIDEBAR", 
												"AMP-AD", 
												"AMP-CAROUSEL", 
												"AMP-STICKY-AD", 
												"MULTI-BANNER-BUILDER", 
												"NEWLY-REGISTERED-MODAL", 
												"GEO-PRICING-MODAL", 
												"INCENTIVE-MODAL", 
												"META PROPERTY=FB:PAGES", 
												"IMPROVEDIGITAL_AD_OUTPUT_INFORMATION", 
												"STRIKE", 
												"HP-REPLACE", 
												"DATA", 
												"BLINK", 
												"YTD-GUIDE-SECTION-RENDERER", 
												"YT-GUIDE-MANAGER", 
												"YTD-PLAYLIST-PANEL-RENDERER", 
												"YTD-THUMBNAIL", 
												"YTD-GUIDE-RENDERER", 
												"YT-GFEEDBACK-MANAGER", 
												"YTD-MINIPLAYER-TOAST", 
												"YTD-APP", 
												"YTD-GUIDE-ENTRY-RENDERER", 
												"YTD-BADGE-SUPPORTED-RENDERER", 
												"APP-DRAWER", 
												"PAPER-BUTTON", 
												"YT-ACTIVITY-MANAGER", 
												"YTD-MINI-GUIDE-RENDERER", 
												"YTD-BANNER-PROMO-RENDERER", 
												"YTD-CHANNEL-LEGAL-INFO-RENDERER", 
												"YTD-PAGE-MANAGER", 
												"YT-ICON-BUTTON", 
												"YTD-MINI-GUIDE-ENTRY-RENDERER", 
												"YT-HISTORY-MANAGER", 
												"YTD-SHELF-RENDERER", 
												"YT-VISIBILITY-MONITOR", 
												"YTD-MINIPLAYER", 
												"YT-HORIZONTAL-LIST-RENDERER", 
												"IRON-ICONSET-SVG", 
												"YTD-TOPBAR-MENU-BUTTON-RENDERER", 
												"YT-FORMATTED-STRING", 
												"YTD-CHANNEL-NAME", 
												"YT-NETWORK-MANAGER", 
												"YTD-SETTINGS-SIDEBAR-RENDERER", 
												"YTD-GUIDE-SIGNIN-PROMO-RENDERER", 
												"YTD-SECTION-LIST-RENDERER", 
												"YTD-SEARCHBOX", 
												"IRON-A11Y-ANNOUNCER", 
												"DOM-IF", 
												"YT-IMG-SHADOW", 
												"PAPER-SPINNER-LITE", 
												"YTD-PLAYLIST-SIDEBAR-RENDERER", 
												"YT-NAVIGATION-MANAGER", 
												"IRON-MEDIA-QUERY", 
												"PAPER-ITEM", 
												"YTD-BROWSE", 
												"YT-MDX-MANAGER", 
												"YTD-TOPBAR-LOGO-RENDERER", 
												"YTD-MASTHEAD", 
												"YT-ICON", 
												"YTD-YOODLE-RENDERER", 
												"YTD-MINIPLAYER-ACTION", 
												"PAPER-SPINNER", 
												"YT-PLAYER-MANAGER", 
												"DOM-MODULE", 
												"DOM-REPEAT", 
												"PAPER-TOOLTIP", 
												"YTD-GRID-VIDEO-RENDERER", 
												"YTD-TWO-COLUMN-BROWSE-RESULTS-RENDERER", 
												"YTD-ITEM-SECTION-RENDERER", 
												"YT-NEXT-CONTINUATION", 
												"YTD-BUTTON-RENDERER", 
												"YTD-POPUP-CONTAINER", 
												"YT-PLAYLIST-MANAGER", 
												"YT-HOTKEY-MANAGER", 
												"ASYNC-INCLUDE", 
												"GWD-GPA-DATA-PROVIDER", 
												"GWD-TEXT-HELPER", 
												"GWD-METRIC-CONFIGURATION", 
												"GWD-IMAGE", 
												"GWD-GOOGLE-AD", 
												"GWD-METRIC-EVENT", 
												"GWD-PAGE", 
												"GWD-TAPAREA", 
												"GWD-DATA-BINDER", 
												"GWD-PAGEDECK", 
												"HERO", 
												"GWD-DOUBLECLICK", 
												"GWD-EXIT", 
												"ZIG-INDEX", 
												"feFlood", 
												"feComposite", 
												"feBlend", 
												"PAPER-RIPPLE", 
												"NAVIGATION", 
												"E", 
												"SIV", 
												"PFL", 
												"PARAM", 
												"EDGE-589493848", 
												"feMorphology", 
												"SITE_ROOT", 
												"WIX-IMAGE", 
												"FBS-CAROUSEL", 
												"FBS-AD", 
												"PROGRESSIVE-IMAGE", 
												"OPTGROUP", 
												"OUTBRAIN", 
												"DETAILS", 
												"PASSWORD-STRENGTH", 
												"SUMMARY", 
												"AUTO-CHECK", 
												"DIALOG", 
												"ESI:REMOVE", 
												"ESI:INCLUDE", 
												"DR", 
												"TFOOT", 
												"TRACK", 
												"VF-TRAY-TRIGGER", 
												"TITRE_TOOLTIP", 
												"KBD", 
												"ICON", 
												"REGION", 
												"WP-AD", 
												"WBR", 
												"GCSE:SEARCHBOX-ONLY");

	//Créer les constantes pour les strings
	
	@Override
	public void setup()
	{
		setTitle("Composition of a website");
		setAuthor("Xavier Chamberland-Thibeault");
		
		// Create tables		
		ExperimentTable nbElementsVSnbClasses = new ExperimentTable("nbElementTotal","nbClasse");
		nbElementsVSnbClasses.setTitle("Distribution of the number of elements relative to the number of classes");
		nbElementsVSnbClasses.setNickname("elementsVsClasses");
		add(nbElementsVSnbClasses);
		
		//Add each web site as an experiment
		File f = new File("statistics_files_list.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String file;
			int cpt = 1;
			
			while((file = br.readLine()) != null)
			{
				WebSiteExperiment exp = new WebSiteExperiment(file, cpt);
				
				add(exp, new ExperimentTable[0]);
				
				nbElementsVSnbClasses.add(exp);
				
				cpt++;
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		HistogramTable nbElement = new HistogramTable(this,"nbElementTotal",new int[] {100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200,2300,2400,2500,2600,2700,2800,2900,3000,10000});
		nbElement.setTitle("Distribution of websites relative to their number of elements");
		nbElement.setNickname("nbElement");
		add(nbElement);
		
		HistogramTable distributionDegreeMax = new HistogramTable(this, "degreMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,1250});
		distributionDegreeMax.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		distributionDegreeMax.setNickname("MaxDegree");
		add(distributionDegreeMax);
		
		HistogramTable distributionDepthMax = new HistogramTable(this, "profondeurMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50});
		distributionDepthMax.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		distributionDepthMax.setNickname("MaxDepth");
		add(distributionDepthMax);
		
		PieChartTable elementTagUsage = new PieChartTable(this, "TagType", htmlTags.stream().toArray(String[]::new));
		elementTagUsage.setTitle("Distribution of element tag type usage in websites");
		elementTagUsage.setNickname("percentElement");
		add(elementTagUsage);

		PercentUsageHistogramTable frequencyOfTag = new PercentUsageHistogramTable(this, "tagTypeFrequency", htmlTags.stream().toArray(String[]::new));
		frequencyOfTag.setTitle("Distribution of tag frequency in websites");
		frequencyOfTag.setNickname("tagFrequency");
		add(frequencyOfTag);
		
		HistogramTable distributionNbClasses = new HistogramTable(this, "nbClasse", new int[] {0,1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,350,450,750});
		distributionNbClasses.setTitle("Distribution of websites relative to the number of classes");
		distributionNbClasses.setNickname("NbClasses");
		add(distributionNbClasses);
		
		HistogramTable distributionMaxClasses = new HistogramTable(this, "maxParClasse", new int[] {0,1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,350,450,550,650,750,850,950});
		distributionMaxClasses.setTitle("Distribution of websites relative to the size of the biggest class");
		distributionMaxClasses.setNickname("MaxPerClass");
		add(distributionMaxClasses);
		
		HistogramTable distributionAvgClasses = new HistogramTable(this, "moyenneParClasse", new int[] {0,1,2,4,6,8,10,12,14,16,18,20,22,24,26,28});
		distributionAvgClasses.setTitle("Distribution of websites relative to the average size of the classes");
		distributionAvgClasses.setNickname("avgClasses");
		add(distributionAvgClasses);
		
		HistogramTable distributionNoClasses = new HistogramTable(this, "Pas de classe", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,350,450,550,650,750,850,950,1050,1150,1250,2500,3750,5000});
		distributionNoClasses.setTitle("Distribution of websites relative to the number of nodes wihtout a class");
		distributionNoClasses.setNickname("noClass");
		add(distributionNoClasses);
		
		PercentageHistogramTable percentInvisibleElements = new PercentageHistogramTable(this, "nbNoeudsInvisibles", "nbElementTotal");
		percentInvisibleElements.setTitle("Distribution of websites relative to the percentage of invisible nodes");
		percentInvisibleElements.setNickname("percentInvisible");
		add(percentInvisibleElements);
		
		PieChartTable invisibleTypeUsage = new PieChartTable(this, "InvisibleType", new String[] {"visibility", "display", "widthOrHeight", "negativePosition", "outsidePosition"});
		invisibleTypeUsage.setTitle("Distribution of invisible type usage in websites");
		invisibleTypeUsage.setNickname("invisibleType");
		add(invisibleTypeUsage);
		
		
		//Create plots	
		ClusteredHistogram histogramNbElement = new ClusteredHistogram(nbElement);
		histogramNbElement.setTitle("Graphical representation of the distribution of websites relative to their number of elements");
		histogramNbElement.setNickname("nbElementPlot");
		histogramNbElement.setCaption(Axis.X, "Number of elements");
		histogramNbElement.setCaption(Axis.Y, "Number of sites");		
		add(histogramNbElement);
		
		ClusteredHistogram histogramMaxDegree = new ClusteredHistogram(distributionDegreeMax);
		histogramMaxDegree.setTitle("Graphical representation of the distribution of websites relative to the maximum degree of the DOM tree");
		histogramMaxDegree.setNickname("MaxDegreePlot");
		histogramMaxDegree.setCaption(Axis.X, "Greatest degree of the DOM tree");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");		
		add(histogramMaxDegree);
		
		ClusteredHistogram histogramMaxDepth = new ClusteredHistogram(distributionDepthMax);
		histogramMaxDepth.setTitle("Graphical representation of the distribution of websites relative to the maximum depth of the DOM tree");
		histogramMaxDepth.setNickname("MaxDepthPlot");
		histogramMaxDepth.setCaption(Axis.X, "Greatest depth of the DOM tree");
		histogramMaxDepth.setCaption(Axis.Y, "Number of sites");	
		add(histogramMaxDepth);
		
		ca.uqac.lif.mtnp.plot.gral.PieChart elementTagUsagePlot = new ca.uqac.lif.mtnp.plot.gral.PieChart(elementTagUsage);
		elementTagUsagePlot.setTitle("Graphical representation of the distribution of element tag type usage in websites");
		elementTagUsagePlot.setNickname("elementTagUsage");
		add(elementTagUsagePlot);
		
		ClusteredHistogram histogramPercentFrequency = new ClusteredHistogram(frequencyOfTag);
		histogramPercentFrequency.setTitle("Graphical representation of the distribution of tag frequency in websites");
		histogramPercentFrequency.setNickname("percTagPlot");
		histogramPercentFrequency.setCaption(Axis.X, "Tag");
		histogramPercentFrequency.setCaption(Axis.Y, "Percentage of site using the tag");
		add(histogramPercentFrequency);	
		
		Scatterplot classesVSsiteSize = new Scatterplot(nbElementsVSnbClasses);
		classesVSsiteSize.setTitle("Graphical representation of the distribution of the number of elements relative to the number of classes");
		classesVSsiteSize.setNickname("classToNbElements");
		classesVSsiteSize.withLines(false);
		classesVSsiteSize.setCaption(Axis.X, "Number of elements");
		classesVSsiteSize.setCaption(Axis.Y, "Number of classes");
		add(classesVSsiteSize);

		ClusteredHistogram histogramNbClasses = new ClusteredHistogram(distributionNbClasses);
		histogramNbClasses.setTitle("Graphical representation of the distribution of websites relative to the number of classes");
		histogramNbClasses.setNickname("nbClassesPlot");
		histogramNbClasses.setCaption(Axis.X, "Number of classes");
		histogramNbClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramNbClasses);

		ClusteredHistogram histogramMaxClasses = new ClusteredHistogram(distributionMaxClasses);
		histogramMaxClasses.setTitle("Graphical representation of the distribution of websites relative to the size of the biggest class");
		histogramMaxClasses.setNickname("MaxDClassesPlot");
		histogramMaxClasses.setCaption(Axis.X, "Size of the biggest classes");
		histogramMaxClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramMaxClasses);

		ClusteredHistogram histogramAvgClasses = new ClusteredHistogram(distributionAvgClasses);
		histogramAvgClasses.setTitle("Graphical representation of the distribution of websites relative to the average size of the classes");
		histogramAvgClasses.setNickname("avgClassesPlot");
		histogramAvgClasses.setCaption(Axis.X, "Average size of the classes");
		histogramAvgClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramAvgClasses);

		ClusteredHistogram histogramNoClass = new ClusteredHistogram(distributionNoClasses);
		histogramNoClass.setTitle("Graphical representation of the distribution of websites relative to the number of nodes without a class");
		histogramNoClass.setNickname("noClassPlot");
		histogramNoClass.setCaption(Axis.X, "Number of nodes without a class");
		histogramNoClass.setCaption(Axis.Y, "Number of sites");
		add(histogramNoClass);

		ClusteredHistogram histogramPercentInvisible = new ClusteredHistogram(percentInvisibleElements);
		histogramPercentInvisible.setTitle("Graphical representation of the distribution of websites relative to the percentage of invisible nodes");
		histogramPercentInvisible.setNickname("percInvisiblePlot");
		histogramPercentInvisible.setCaption(Axis.X, "Percentage of invisible nodes");
		histogramPercentInvisible.setCaption(Axis.Y, "Number of sites");
		add(histogramPercentInvisible);
		
		ca.uqac.lif.mtnp.plot.gral.PieChart invisibleTypeUsagePlot = new ca.uqac.lif.mtnp.plot.gral.PieChart(invisibleTypeUsage);
		invisibleTypeUsagePlot.setTitle("Graphical representation of the distribution of invisible type usage in websites");
		invisibleTypeUsagePlot.setNickname("invisibleTypeUsage");
		add(invisibleTypeUsagePlot);		
	}
	
	public static void main(String[] args)
	{
		// Nothing else to do here
		MyLaboratory.initialize(args, MyLaboratory.class);
	}
}
