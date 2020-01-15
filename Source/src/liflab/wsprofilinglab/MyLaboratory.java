package liflab.wsprofilinglab;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ca.uqac.lif.labpal.FileHelper;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.mtnp.plot.TwoDimensionalPlot.Axis;
import ca.uqac.lif.mtnp.plot.gnuplot.*;

public class MyLaboratory extends Laboratory
{
	public static final transient String SITES_FOLDER = "data/sites";
	
	private static final transient List<String> htmlTags = new ArrayList<String>();
	
	static
	{
		Scanner s = new Scanner(MyLaboratory.class.getResourceAsStream("data/html-tags.txt"));
		while (s.hasNextLine())
		{
			htmlTags.add(s.nextLine());
		}
		s.close();
	}

	//CrÃ©er les constantes pour les strings

	@Override
	public void setup()
	{
		setTitle("Structural profiling of websites in the wild");
		setAuthor("Sylvain Hallé and Xavier Chamberland-Thibeault");

		// Create tables		
		ExperimentTable nbElementsVSnbClasses = new ExperimentTable("nbElementTotal","nbClasse");
		nbElementsVSnbClasses.setTitle("Distribution of the number of elements relative to the number of classes");
		nbElementsVSnbClasses.setNickname("elementsVsClasses");
		add(nbElementsVSnbClasses);

		//Add each web site as an experiment
		List<String> filenames = FileHelper.getResourceListing(MyLaboratory.class, "liflab/wsprofilinglab/" + SITES_FOLDER, ".*\\.json");
		int cpt = 1;
		for (String file : filenames)
		{
			if(file.contains("(") && file.contains(")"))
			{
				System.out.println(file);
				continue;
			}
			else
			{
				WebSiteExperiment exp = new WebSiteExperiment(file, cpt);
				add(exp, new ExperimentTable[0]);
				nbElementsVSnbClasses.add(exp);
				cpt++;
			}
		}

		HistogramTable nbElement = new HistogramTable(this,"nbElementTotal",new int[] {10,20,30,40,50,60,70,80,90,100,200,300,400,500,600,700,800,900,1000,1100,1200,1300,1400,1500,1600,1700,1800,1900,2000,2100,2200,2300,2400,2500,2600,2700,2800,2900,10000});
		nbElement.setTitle("Distribution of websites relative to their number of elements");
		nbElement.setNickname("nbElement");
		add(nbElement);
		
		CumulativeDistributionTable nbElementRelative = new CumulativeDistributionTable(nbElement, "Number of elements", "% of sites");
		nbElementRelative.setTitle("Distribution of websites relative to their number of elements - Scatter plot version");
		nbElementRelative.setNickname("nbElementRelative");
		add(nbElementRelative);

		HistogramTable distributionDegreeMax = new HistogramTable(this, "degreMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,1250});
		distributionDegreeMax.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		distributionDegreeMax.setNickname("MaxDegree");
		add(distributionDegreeMax);
		
		CumulativeDistributionTable distributionDegreeMaxRelative = new CumulativeDistributionTable(distributionDegreeMax, "Max tree degree", "% of sites");
		distributionDegreeMaxRelative.setTitle("Distribution of websites relative to the maximum degree of the DOM tree - Scatter plot version");
		distributionDegreeMaxRelative.setNickname("MaxDegreeRelative");
		add(distributionDegreeMaxRelative);

		HistogramTable distributionDepthMax = new HistogramTable(this, "profondeurMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50});
		distributionDepthMax.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		distributionDepthMax.setNickname("MaxDepth");
		add(distributionDepthMax);

		CumulativeDistributionTable distributionDepthMaxRelative = new CumulativeDistributionTable(distributionDepthMax, "Max tree depth", "% of sites");
		distributionDepthMaxRelative.setTitle("Distribution of websites relative to the maximum depth of the DOM tree - Scatter plot version");
		distributionDepthMaxRelative.setNickname("MaxDepthRelative");
		add(distributionDepthMaxRelative);
		
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

		CumulativeDistributionTable distributionNbClassesRelative = new CumulativeDistributionTable(distributionNbClasses, "Number of classes", "% of sites");
		distributionNbClassesRelative.setTitle("Distribution of websites relative to the number of classes - Scatter plot version");
		distributionNbClassesRelative.setNickname("NbClassesRelative");
		add(distributionNbClassesRelative);

		HistogramTable distributionMaxClasses = new HistogramTable(this, "maxParClasse", new int[] {0,1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,350,450,550,650,750,850,950});
		distributionMaxClasses.setTitle("Distribution of websites relative to the size of the biggest class");
		distributionMaxClasses.setNickname("MaxPerClass");
		add(distributionMaxClasses);

		CumulativeDistributionTable distributionMaxClassesRelative = new CumulativeDistributionTable(distributionMaxClasses, "Size of classes", "% of sites");
		distributionMaxClassesRelative.setTitle("Distribution of websites relative to the size of the biggest class - Scatter plot version");
		distributionMaxClassesRelative.setNickname("MaxPerClassRelative");
		add(distributionMaxClassesRelative);

		HistogramTable distributionAvgClasses = new HistogramTable(this, "moyenneParClasse", new int[] {0,1,2,4,6,8,10,12,14,16,18,20,22,24,26,28});
		distributionAvgClasses.setTitle("Distribution of websites relative to the average size of the classes");
		distributionAvgClasses.setNickname("avgClasses");
		add(distributionAvgClasses);

		CumulativeDistributionTable distributionAvgClassesRelative = new CumulativeDistributionTable(distributionAvgClasses, "Average size of classes", "% of sites");
		distributionAvgClassesRelative.setTitle("Distribution of websites relative to the average size of the classes - Scatter plot version");
		distributionAvgClassesRelative.setNickname("avgClassesRelative");
		add(distributionAvgClassesRelative);

		HistogramTable distributionNoClasses = new HistogramTable(this, "Pas de classe", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,350,450,550,650,750,850,950,1050,1150,1250,2500,3750,5000});
		distributionNoClasses.setTitle("Distribution of websites relative to the number of nodes wihtout a class");
		distributionNoClasses.setNickname("noClass");
		add(distributionNoClasses);

		CumulativeDistributionTable distributionNoClassesRelative = new CumulativeDistributionTable(distributionNoClasses, "Elements without class", "% of sites");
		distributionNoClassesRelative.setTitle("Distribution of websites relative to the number of nodes wihtout a class - Scatter plot version");
		distributionNoClassesRelative.setNickname("noClassRelative");
		add(distributionNoClassesRelative);

		PercentageHistogramTable percentInvisibleElements = new PercentageHistogramTable(this, "nbNoeudsInvisibles", "nbElementTotal");
		percentInvisibleElements.setTitle("Distribution of websites relative to the percentage of invisible nodes");
		percentInvisibleElements.setNickname("percentInvisible");
		add(percentInvisibleElements);

		PieChartTable invisibleTypeUsage = new PieChartTable(this, "InvisibleType", new String[] {"visibility", "Display", "widthOrHeight", "negativePosition", "outsidePosition"});
		invisibleTypeUsage.setTitle("Distribution of invisible type usage in websites");
		invisibleTypeUsage.setNickname("invisibleType");
		add(invisibleTypeUsage);


		//Create plots	
		ClusteredHistogram histogramNbElement = new ClusteredHistogram(nbElement);
		histogramNbElement.setTitle("Distribution of websites relative to their number of elements");
		histogramNbElement.setNickname("nbElementPlot");
		histogramNbElement.setCaption(Axis.X, "Number of elements");
		histogramNbElement.setCaption(Axis.Y, "Number of sites");		
		add(histogramNbElement);
		
		Scatterplot scatterNbElement = new Scatterplot(nbElementRelative);
		scatterNbElement.setTitle("Distribution of websites relative to their number of elements");
		scatterNbElement.setNickname("relativeNbElementPlot");
		scatterNbElement.setCaption(Axis.X, "Number of elements");
		scatterNbElement.setCaption(Axis.Y, "% of sites");	
		scatterNbElement.withPoints(false);
		add(scatterNbElement);

		ClusteredHistogram histogramMaxDegree = new ClusteredHistogram(distributionDegreeMax);
		histogramMaxDegree.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		histogramMaxDegree.setNickname("MaxDegreePlot");
		histogramMaxDegree.setCaption(Axis.X, "Greatest degree of the DOM tree");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");		
		add(histogramMaxDegree);
		
		Scatterplot scatterMaxDegree = new Scatterplot(distributionDegreeMaxRelative);
		scatterMaxDegree.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		scatterMaxDegree.setNickname("relativeMaxDegreePlot");
		scatterMaxDegree.setCaption(Axis.X, "Max tree degree");
		scatterMaxDegree.setCaption(Axis.Y, "% of sites");	
		scatterMaxDegree.withPoints(false);
		add(scatterMaxDegree);

		ClusteredHistogram histogramMaxDepth = new ClusteredHistogram(distributionDepthMax);
		histogramMaxDepth.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		histogramMaxDepth.setNickname("MaxDepthPlot");
		histogramMaxDepth.setCaption(Axis.X, "Greatest depth of the DOM tree");
		histogramMaxDepth.setCaption(Axis.Y, "Number of sites");	
		add(histogramMaxDepth);

		Scatterplot scatterMaxDepth = new Scatterplot(distributionDepthMaxRelative);
		scatterMaxDepth.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		scatterMaxDepth.setNickname("relativeMaxDepthPlot");
		scatterMaxDepth.setCaption(Axis.X, "Max tree depth");
		scatterMaxDepth.setCaption(Axis.Y, "% of sites");	
		scatterMaxDepth.withPoints(false);
		add(scatterMaxDepth);
		
		ca.uqac.lif.mtnp.plot.gral.PieChart elementTagUsagePlot = new ca.uqac.lif.mtnp.plot.gral.PieChart(elementTagUsage);
		elementTagUsagePlot.setTitle("Distribution of element tag type usage in websites");
		elementTagUsagePlot.setNickname("elementTagUsage");
		add(elementTagUsagePlot);

		ClusteredHistogram histogramPercentFrequency = new ClusteredHistogram(frequencyOfTag);
		histogramPercentFrequency.setTitle("Distribution of tag frequency in websites");
		histogramPercentFrequency.setNickname("percTagPlot");
		histogramPercentFrequency.setCaption(Axis.X, "Tag");
		histogramPercentFrequency.setCaption(Axis.Y, "Percentage of site using the tag");
		add(histogramPercentFrequency);	

		Scatterplot classesVSsiteSize = new Scatterplot(nbElementsVSnbClasses);
		classesVSsiteSize.setTitle("Distribution of the number of elements relative to the number of classes");
		classesVSsiteSize.setNickname("classToNbElements");
		classesVSsiteSize.withLines(false);
		classesVSsiteSize.setCaption(Axis.X, "Number of elements");
		classesVSsiteSize.setCaption(Axis.Y, "Number of classes");
		add(classesVSsiteSize);

		ClusteredHistogram histogramNbClasses = new ClusteredHistogram(distributionNbClasses);
		histogramNbClasses.setTitle("Distribution of websites relative to the number of classes");
		histogramNbClasses.setNickname("nbClassesPlot");
		histogramNbClasses.setCaption(Axis.X, "Number of classes");
		histogramNbClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramNbClasses);
		
		Scatterplot scatterNbClasses = new Scatterplot(distributionNbClassesRelative);
		scatterNbClasses.setTitle("Distribution of websites relative to the number of classes");
		scatterNbClasses.setNickname("relativeNbClassesPlot");
		scatterNbClasses.setCaption(Axis.X, "Number of classes");
		scatterNbClasses.setCaption(Axis.Y, "% of sites");	
		scatterNbClasses.withPoints(false);
		add(scatterNbClasses);

		ClusteredHistogram histogramMaxClasses = new ClusteredHistogram(distributionMaxClasses);
		histogramMaxClasses.setTitle("Distribution of websites relative to the size of the biggest class");
		histogramMaxClasses.setNickname("MaxClassesPlot");
		histogramMaxClasses.setCaption(Axis.X, "Size of the biggest classes");
		histogramMaxClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramMaxClasses);
		
		Scatterplot scatterMaxClasses = new Scatterplot(distributionMaxClassesRelative);
		scatterMaxClasses.setTitle("Distribution of websites relative to the size of the biggest class");
		scatterMaxClasses.setNickname("relativeMaxClassesPlot");
		scatterMaxClasses.setCaption(Axis.X, "Size of classes");
		scatterMaxClasses.setCaption(Axis.Y, "% of sites");	
		scatterMaxClasses.withPoints(false);
		add(scatterMaxClasses);

		ClusteredHistogram histogramAvgClasses = new ClusteredHistogram(distributionAvgClasses);
		histogramAvgClasses.setTitle("Distribution of websites relative to the average size of the classes");
		histogramAvgClasses.setNickname("avgClassesPlot");
		histogramAvgClasses.setCaption(Axis.X, "Average size of the classes");
		histogramAvgClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramAvgClasses);
		
		Scatterplot scatterAvgClasses = new Scatterplot(distributionAvgClassesRelative);
		scatterAvgClasses.setTitle("Distribution of websites relative to the average size of the classes");
		scatterAvgClasses.setNickname("relativeAvgClassesPlot");
		scatterAvgClasses.setCaption(Axis.X, "Average size of classes");
		scatterAvgClasses.setCaption(Axis.Y, "% of sites");	
		scatterAvgClasses.withPoints(false);
		add(scatterAvgClasses);

		ClusteredHistogram histogramNoClass = new ClusteredHistogram(distributionNoClasses);
		histogramNoClass.setTitle("Distribution of websites relative to the number of nodes without a class");
		histogramNoClass.setNickname("noClassPlot");
		histogramNoClass.setCaption(Axis.X, "Number of nodes without a class");
		histogramNoClass.setCaption(Axis.Y, "Number of sites");
		add(histogramNoClass);
		
		Scatterplot scatterNoClass = new Scatterplot(distributionNoClassesRelative);
		scatterNoClass.setTitle("Distribution of websites relative to the number of nodes without a class");
		scatterNoClass.setNickname("relativeNoClassPlot");
		scatterNoClass.setCaption(Axis.X, "Elements without class");
		scatterNoClass.setCaption(Axis.Y, "% of sites");	
		scatterNoClass.withPoints(false);
		add(scatterNoClass);

		ClusteredHistogram histogramPercentInvisible = new ClusteredHistogram(percentInvisibleElements);
		histogramPercentInvisible.setTitle("Distribution of websites relative to the percentage of invisible nodes");
		histogramPercentInvisible.setNickname("percInvisiblePlot");
		histogramPercentInvisible.setCaption(Axis.X, "Percentage of invisible nodes");
		histogramPercentInvisible.setCaption(Axis.Y, "Number of sites");
		add(histogramPercentInvisible);

		ca.uqac.lif.mtnp.plot.gral.PieChart invisibleTypeUsagePlot = new ca.uqac.lif.mtnp.plot.gral.PieChart(invisibleTypeUsage);
		invisibleTypeUsagePlot.setTitle("Distribution of invisible type usage in websites");
		invisibleTypeUsagePlot.setNickname("invisibleTypeUsage");
		add(invisibleTypeUsagePlot);		
	}

	public static void main(String[] args)
	{
		// Nothing else to do here
		MyLaboratory.initialize(args, MyLaboratory.class);
	}
}
