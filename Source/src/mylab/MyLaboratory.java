package mylab;

import java.io.*;
import java.util.HashMap;

import Tables.HistogramTable;
import Tables.PercentageHistogramTable;
import ca.uqac.lif.labpal.Laboratory;
import ca.uqac.lif.labpal.table.ExperimentTable;
import ca.uqac.lif.mtnp.plot.TwoDimensionalPlot.Axis;
import ca.uqac.lif.mtnp.plot.gnuplot.*;
import experiments.WebSiteExperiment;

public class MyLaboratory extends Laboratory
{
	//Créer les constantes pour les strings
	
	@Override
	public void setup()
	{
		setTitle("Composition of a website");
		setAuthor("Xavier Chamberland-Thibeault");
		// Create tables
		ExperimentTable nbElementsVSnbClasses = new ExperimentTable("nbElementTotal","nbClasse");
		add(nbElementsVSnbClasses);
		
		ExperimentTable nbElement = new ExperimentTable("expID", "nbElementTotal");
		nbElement.setTitle("Nombre element total");
		nbElement.setNickname("nbElement");
		add(nbElement);
		
		ExperimentTable profondeurMin = new ExperimentTable("profondeurMinArbre", "expID");
		profondeurMin.setTitle("Profondeur minimale de l'arbre du site");
		profondeurMin.setNickname("profondeurMin");
		add(profondeurMin);		
		
		ExperimentTable profondeurMax = new ExperimentTable("profondeurMaxArbre");
		profondeurMax.setTitle("Profondeur maximale de l'arbre du site");
		profondeurMax.setNickname("profondeurMax");
		add(profondeurMax);		
		
		ExperimentTable degreMin = new ExperimentTable("degreMinArbre");
		degreMin.setTitle("Degre minimal de l'arbre du site");
		degreMin.setNickname("degreMin");
		add(degreMin);
		
		ExperimentTable degreMax = new ExperimentTable("degreMaxArbre");
		degreMax.setTitle("Degre maximal de l'arbre du site");
		degreMax.setNickname("degreMax");
		add(degreMax);
		
		ExperimentTable nbNoeudInvisible = new ExperimentTable("nbNoeudsInvisibles");
		nbNoeudInvisible.setTitle("Nombre de noeuds invisible dans le site");
		nbNoeudInvisible.setNickname("nbNoeudInvisbile");
		add(nbNoeudInvisible);
		
		ExperimentTable nbClass = new ExperimentTable("nbClasse");
		nbClass.setTitle("Nombre de classe dans le site");
		nbClass.setNickname("nbClass");
		add(nbClass);
		
		ExperimentTable moyenneParClasse = new ExperimentTable("moyenneParClasse");
		moyenneParClasse.setTitle("Moyenne n'elements par classe du site");
		moyenneParClasse.setNickname("moyenneParClasse");
		add(moyenneParClasse);
		
		ExperimentTable minParClasse = new ExperimentTable("minParClasse");
		minParClasse.setTitle("Minimum d'elements par classe du site");
		minParClasse.setNickname("minParClasse");
		add(minParClasse);
		
		ExperimentTable maxParClasse = new ExperimentTable("maxParClasse");
		maxParClasse.setTitle("Maximum d'elements par classe du site");
		maxParClasse.setNickname("maxParClasse");
		add(maxParClasse);	
		
		
				
		//Add each web site as an experiment
		File f = new File("C:\\Users\\xavierchamberland\\Documents\\Autre\\Uqac\\Maitrise\\Data\\liste_fichiers_statistiques.txt");
		
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String file;
			int cpt = 1;
			
			while((file = br.readLine()) != null)
			{
				WebSiteExperiment exp = new WebSiteExperiment(file, cpt);
				
				add(exp, new ExperimentTable[0]);
				
				nbElement.add(exp);
				profondeurMin.add(exp);
				profondeurMax.add(exp);
				degreMin.add(exp);
				degreMax.add(exp);
				nbNoeudInvisible.add(exp);
				nbClass.add(exp);
				moyenneParClasse.add(exp);
				minParClasse.add(exp);
				maxParClasse.add(exp);
				nbElementsVSnbClasses.add(exp);
				
				cpt++;
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		

		HistogramTable distributionDegreeMax = new HistogramTable(this, "degreMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180,190,200,210,220,230,240,250,1250});
		distributionDegreeMax.setTitle("Distribution of websites relative to the maximum degree of the DOM tree");
		distributionDegreeMax.setNickname("MaxDegree");
		add(distributionDegreeMax);
		
		HistogramTable distributionDepthMax = new HistogramTable(this, "profondeurMaxArbre", new int[] {1,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,40,50});
		distributionDepthMax.setTitle("Distribution of websites relative to the maximum depth of the DOM tree");
		distributionDepthMax.setNickname("MaxDepth");
		add(distributionDepthMax);
		
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
		
		//Create plots		
		ClusteredHistogram histogramMaxDegree = new ClusteredHistogram(distributionDegreeMax);
		histogramMaxDegree.setTitle("Graphical representation of the distribution of websites relative to the maximum degree of the DOM tree");
		histogramMaxDegree.setNickname("MaxDegreePlot");
		histogramMaxDegree.setCaption(Axis.X, "Greatest degree of the DOM tree");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");		
		add(histogramMaxDegree);
		
		ClusteredHistogram histogramMaxDepth = new ClusteredHistogram(distributionDepthMax);
		histogramMaxDepth.setTitle("Graphical representation of the distribution of websites relative to the maximum depth of the DOM tree");
		histogramMaxDepth.setNickname("MaxDepthPlot");
		histogramMaxDegree.setCaption(Axis.X, "Greatest depth of the DOM tree");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");	
		add(histogramMaxDepth);

		ClusteredHistogram histogramNbClasses = new ClusteredHistogram(distributionNbClasses);
		histogramNbClasses.setTitle("Graphical representation of the distribution of websites relative to the number of classes");
		histogramNbClasses.setNickname("nbClassesPlot");
		histogramMaxDegree.setCaption(Axis.X, "Number of classes");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");
		add(histogramNbClasses);

		ClusteredHistogram histogramMaxClasses = new ClusteredHistogram(distributionMaxClasses);
		histogramMaxClasses.setTitle("Graphical representation of the distribution of websites relative to the size of the biggest class");
		histogramMaxClasses.setNickname("MaxDClassesPlot");
		histogramMaxDegree.setCaption(Axis.X, "Size of the biggest classes");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");
		add(histogramMaxClasses);

		ClusteredHistogram histogramAvgClasses = new ClusteredHistogram(distributionAvgClasses);
		histogramAvgClasses.setTitle("Graphical representation of the distribution of websites relative to the average size of the classes");
		histogramAvgClasses.setNickname("avgClassesPlot");
		histogramMaxDegree.setCaption(Axis.X, "Average size of the classes");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");
		add(histogramAvgClasses);

		ClusteredHistogram histogramNoClass = new ClusteredHistogram(distributionNoClasses);
		histogramNoClass.setTitle("Graphical representation of the distribution of websites relative to the number of nodes without a class");
		histogramNoClass.setNickname("noClassPlot");
		histogramMaxDegree.setCaption(Axis.X, "Number of nodes without a class");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");
		add(histogramNoClass);

		ClusteredHistogram histogramPercentInvisible = new ClusteredHistogram(percentInvisibleElements);
		histogramPercentInvisible.setTitle("Graphical representation of the distribution of websites relative to the percentage of invisible nodes");
		histogramPercentInvisible.setNickname("percInvisiblePlot");
		histogramMaxDegree.setCaption(Axis.X, "Percentage of invisible nodes");
		histogramMaxDegree.setCaption(Axis.Y, "Number of sites");
		add(histogramPercentInvisible);

		Scatterplot classesVSsiteSize = new Scatterplot(nbElementsVSnbClasses);
		classesVSsiteSize.setTitle("Distribution of the number of elements relative to the number of classes");
		classesVSsiteSize.setNickname("classToNbElements");
		classesVSsiteSize.withLines(false);
		histogramMaxDegree.setCaption(Axis.X, "Number of elements");
		histogramMaxDegree.setCaption(Axis.Y, "Number of classes");
		add(classesVSsiteSize);
		
	}
	
	public static void main(String[] args)
	{
		// Nothing else to do here
		MyLaboratory.initialize(args, MyLaboratory.class);
	}
}
