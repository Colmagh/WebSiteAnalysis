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

	//Créer les constantes pour les strings

	@Override
	public void setup()
	{
		setTitle("Structural profiling of websites in the wild");
		setAuthor("Xavier Chamberland-Thibeault");

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
			WebSiteExperiment exp = new WebSiteExperiment(file, cpt);
			add(exp, new ExperimentTable[0]);
			nbElementsVSnbClasses.add(exp);
			cpt++;
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

		ClusteredHistogram histogramMaxDegree = new ClusteredHistogram(distributionDegreeMax);
		histogramMaxDegree.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		histogramMaxDegree.setNickname("MaxDegreePlot");
		histogramMaxDegree.setCaption(Axis.X, "Greatest degree of the DOM tree");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");		
		add(histogramMaxDegree);

		ClusteredHistogram histogramMaxDepth = new ClusteredHistogram(distributionDepthMax);
		histogramMaxDepth.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		histogramMaxDepth.setNickname("MaxDepthPlot");
		histogramMaxDepth.setCaption(Axis.X, "Greatest depth of the DOM tree");
		histogramMaxDepth.setCaption(Axis.Y, "Number of sites");	
		add(histogramMaxDepth);

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

		ClusteredHistogram histogramMaxClasses = new ClusteredHistogram(distributionMaxClasses);
		histogramMaxClasses.setTitle("Distribution of websites relative to the size of the biggest class");
		histogramMaxClasses.setNickname("MaxDClassesPlot");
		histogramMaxClasses.setCaption(Axis.X, "Size of the biggest classes");
		histogramMaxClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramMaxClasses);

		ClusteredHistogram histogramAvgClasses = new ClusteredHistogram(distributionAvgClasses);
		histogramAvgClasses.setTitle("Distribution of websites relative to the average size of the classes");
		histogramAvgClasses.setNickname("avgClassesPlot");
		histogramAvgClasses.setCaption(Axis.X, "Average size of the classes");
		histogramAvgClasses.setCaption(Axis.Y, "Number of sites");
		add(histogramAvgClasses);

		ClusteredHistogram histogramNoClass = new ClusteredHistogram(distributionNoClasses);
		histogramNoClass.setTitle("Distribution of websites relative to the number of nodes without a class");
		histogramNoClass.setNickname("noClassPlot");
		histogramNoClass.setCaption(Axis.X, "Number of nodes without a class");
		histogramNoClass.setCaption(Axis.Y, "Number of sites");
		add(histogramNoClass);

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
