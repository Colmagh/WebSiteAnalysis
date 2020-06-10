//Get the root node of the page
var body = document.getElementsByTagName("BODY")[0];
//Initializing node's name variable
var NEXT_NODE_NAME = 2;
//Initializing types arrays
var childType = [];
var nodeType = [];
var classType = [];
var invisibleType = {
	visibility: 0,
	display: 0,
	widthOrHeight: 0,
	negativePosition: 0,
	outsidePosition: 0
};
//Initializing invisibility counter
var nbInvisible =0;
//Get total number of nodes on the page
var nbNodes = body.getElementsByTagName("*").length + 1;
//Color array for Graphviz
var color = {
	a_Node: "aliceblue",
	abbr_Node: "antiquewhite",
	acronym_Node: "antiquewhite",//same as abbr
	address_Node: "antiquewhite3",
	applet_Node: "antiquewhite4",//same as embed and object
	area_Node: "aquamarine",
	article_Node: "aquamarine3",
	aside_Node: "aquamarine4",
	audio_Node: "azure",
	b_Node: "azure3",
	base_Node: "azure4",
	basefont_Node: "beige",
	bdi_Node: "bisque",
	bdo_Node: "bisque3",
	big_Node: "bisque4",
	blockquote_Node: "blanchedalmond",
	body_Node: "blue",
	br_Node: "blueviolet",
	button_Node: "brown",
	canvas_Node: "brown1",
	caption_Node: "burlywood",
	center_Node: "burlywood1",
	cite_Node: "burlywood4",
	code_Node: "cadetblue",
	col_Node: "cadetblue1",
	colgroup_Node: "cadetblue3",
	data_Node: "chartreuse",
	datalist_Node: "chartreuse3",
	dd_Node: "chartreuse4",
	del_Node: "chocolate",
	details_Node: "chocolate1",
	dfn_Node: "coral",
	dialog_Node: "coral3",
	dir_Node: "cornflowerblue",//same as ul
	div_Node: "cornsilk",
	dl_Node: "cornsilk3",
	dt_Node: "crimson",
	em_Node: "cyan",
	embed_Node: "antiquewhite4",
	fieldset_Node: "cyan3",
	figcaption_Node: "cyan4",
	figure_Node: "darkgoldenrod",
	font_Node: "darkgoldenrod1",
	footer_Node: "darkgreen",
	form_Node: "darkkhaki",
	frame_Node: "darkolivegreen",
	frameset_Node: "darkolivegreen1",
	h1_Node: "darkorange",
	h2_Node: "darkorchid",
	h3_Node: "darksalmon",
	h4_Node: "darkseagreen",
	h5_Node: "darkseagreen1",
	h6_Node: "darkslategray1",
	head_Node: "darkslategray4",
	header_Node: "darkturquoise",
	hr_Node: "darkviolet",
	html_Node: "deeppink",
	i_Node: "deeppink3",
	iframe_Node: "deepskyblue",
	img_Node: "deepskyblue3",
	input_Node: "dodgerblue",
	ins_Node: "dodgerblue3",
	kbd_Node: "firebrick1",
	label_Node: "floralwhite",
	legend_Node: "forestgreen",
	li_Node: "gainsboro",
	link_Node: "gold",
	main_Node: "gold3",
	map_Node: "goldenrod1",
	mark_Node: "gray",
	meta_Node: "greenyellow",
	meter_Node: "hotpink",
	nav_Node: "hotpink3",
	noframes_Node: "indianred1",
	noscript_Node: "indianred3",
	object_Node: "antiquewhite4",
	ol_Node: "khaki",
	optgroup_Node: "khaki3",
	option_Node: "lavender",
	output_Node: "lavenderblush",
	p_Node: "lavenderblush2",
	param_Node: "lavenderblush3",
	picture_Node: "lawngreen",
	pre_Node: "lightblue",
	progress_Node: "lightblue1",
	q_Node: "lightblue3",
	rp_Node: "lightcoral",
	rt_Node: "lightgoldenrod",
	ruby_Node: "lightpink",
	s_Node: "lightpink3",
	samp_Node: "lightsalmon",
	script_Node: "lightsalmon2",
	section_Node: "lightseagreen",
	select_Node: "lightskyblue1",
	small_Node: "lightskyblue3",
	source_Node: "lightslateblue",
	span_Node: "lightsteelblue",
	strike_Node: "chocolate",// same as del and s
	strong_Node: "lightsteelblue2",
	style_Node: "limegreen",
	sub_Node: "magenta",
	summary_Node: "magenta3",
	sup_Node: "maroon1",
	svg_Node: "maroon3",
	table_Node: "mediumaquamarine",
	tbody_Node: "mediumorchid",
	td_Node: "mediumorchid1",
	template_Node: "mediumorchid3",
	textarea_Node: "mediumpurple",
	tfoot_Node: "mediumseagreen",
	th_Node: "mediumspringgreen",
	thead_Node: "mediumturquoise",
	time_Node: "mediumvioletred",
	title_Node: "olivedrab",
	tr_Node: "olivedrab1",
	track_Node: "red",
	tt_Node: "royalblue",
	u_Node: "salmon",
	ul_Node: "cornflowerblue",
	var_Node: "thistle1",
	video_Node: "wheat2",
	wbr_Node: "yellow"
};

//Function to generate Graphviz tree
function generateGraphvizFileContent(domTree)
{
	var website = window.location.href;
	var content = "graph \"\"\n{\n#   node [fontsize=10,width=\".2\", height=\".2\", margin=0];\n#   graph[fontsize=8];\n   label=\"" + website + "\"\n   subgraph cluster01\n   {\n    label=\"" + website + "\"";
	content += domTree;
	content +="   }\n}"

	return content;
}

//Function to count the number of children of a node
function nbChildCounter(parentNode)
{
	var nbChild = 0;

	Object.keys(parentNode).map(function(childElement){
			nbChild +=  parentNode[childElement] ;
		});
	return nbChild;
}

//Function to generate the JSON statistics file content
function generateStatsFileContent()
{
	//For JSON
	var statObj = {
		nbElements: nbNodes,
		minTreeDepth: n.minDepth,
		maxTreeDepth: n.maxDepth,
		minTreeDegree: n.minDegree,
		maxTreeDegree: n.maxDegree,
		nbInvisibleNodes: nbInvisible
	};

	//Children tag type distribution per tag
	Object.keys(childType).map(function(element){
		var nbChild = nbChildCounter(childType[element]);

		Object.keys(childType[element]).map(function(childElement){
			var tabs = "\t";
			//ugly fix for number of tabs. 5 is the max number of tabs
			for(var i = 0; i < 5-(childElement.length-1) / 4; i++)
			{
				tabs += "\t";
			}

			//JSON version
			statObj[childElement] = childType[element][childElement];
		});
	});

	//Tag type frequency
	Object.keys(nodeType).map(function(element){
		var tabs = "\t";
			//ugly fix for number of tabs. 5 is the max number of tabs
			for(var i = 0; i < 5-(element.length-1) / 4; i++)
			{
				tabs += "\t";
			}

		//JSON version
		statObj[element] = nodeType[element];
	});

	//Class frequencey
	Object.keys(classType).map(function(element){
		//JSON version
		statObj[element] = classType[element];
	});

	// ------ INVISIBLE SECTION --------

	//Type distribution
	Object.keys(invisibleType).map(function(element){
		var tabs = "\t";
			//ugly fix for number of tabs. 5 is the max number of tabs
			for(var i = 0; i < 5-(element.length-1) / 4; i++)
			{
				tabs += "\t";
			}
		//JSON version
		statObj[element] = invisibleType[element];
	});

	//JSON version
	return JSON.stringify(statObj);
}

//Function to generate a dom tree readable by Graphviz from a root node
function print(node)
{
	var toPrint = "     "

	if(node.parentNode === null)
	{
		//Node is the root node
		toPrint += node.nodeName + " ;\n";
	}
	else
	{
		toPrint += node.parentNode + " -- " + node.nodeName + " ;\n";
	}

	//Generate entry for the current node
	toPrint += "     " + node.nodeName + " [label=\"" + node.domNode.nodeName + "\", color=" + color[node.domNode.nodeName.toLowerCase()+"_Node"] + ", style=filled] ;\n"
	for(var i = 0; i < node.children.length; i++)
	{
		//Print all the children
		toPrint += "\n" + print(node.children[i])
	}

	return toPrint;
}

//Function to generate the correct node name for Graphviz
function getNodeName()
{
	var name = "n";
	var nbZeros = nbNodes.toString().length - NEXT_NODE_NAME.toString().length;

	//Get the correct number of 0s
	for(var i = 0; i < nbZeros; i++)
	{
		name += "0";
	}

	name += NEXT_NODE_NAME;
	NEXT_NODE_NAME++;

	return name;
}

//Function to fill the childType array
function typeOfChildrenArrayFiller(parentTag, currentTag)
{
	// Array counting number of each type of children
	if(childType[parentTag] === undefined)
	{
		//No parent of that type yet
		var nodeCounter = [];
		nodeCounter[currentTag] = 1;
		childType[parentTag] = nodeCounter;
	}
	else{
		if(childType[parentTag][currentTag] === undefined)
		{
			//No children of that type yet
			childType[parentTag][currentTag] = 1;
		}
		else
		{
			childType[parentTag][currentTag]++;
		}
	}
}

//Function to fill the nodeType array
function numberOfNodesArrayFiller(currentTag, currentClass)
{
	// Array counting the number of nodes of each type on the page
	if(nodeType[currentTag] === undefined)
	{
		//First element of that type
		nodeType[currentTag] = 1;
	}
	else
	{
		nodeType[currentTag]++;
	}

	if(currentClass === "")
	{
		//Node as no class
		currentClass = "Pas de classe";
	}

	if(classType[currentClass] === undefined)
	{
		//Fisrt node with that class
		classType[currentClass] = 1;
	}
	else
	{
		classType[currentClass]++;
	}
}

//Function to fill the ivisibileType array
function invisibleArrayFiller(style, rectangle)
{
	//Array counting the number of invisible elements of each type;
	if(style.visibility.toLowerCase() === "hidden")
	{
		//Visibility hidden
		invisibleType.visibility ++;
		nbInvisible++;
	}
	else if (style.display.toLowerCase() === "none")
	{
		//Display none
		invisibleType.display ++;
		nbInvisible++;
	}
	else if (style.width <= 0 || style.height <= 0)
	{
		//No height ot width
		invisibleType.widthOrHeight ++;
		nbInvisible++;
	}
	else if ((rectangle.x + rectangle.width) <= 0 || (rectangle.y + rectangle.height) < 0)
	{
		//Negative position
		invisibleType.negativePosition ++;
		nbInvisible++;
	}
	//Resolution 1366x768??
	else if(rectangle.x > body.clientWidth || rectangle.y > body.clientHeight)
	{
		//Outside of the page
		invisibleType.outsidePosition ++;
		nbInvisible++;
	}

	/*
		References:
		https://stackoverflow.com/questions/19669786/check-if-element-is-visible-in-dom
		https://stackoverflow.com/questions/8897289/how-to-check-if-an-element-is-off-screen

	*/
}

//Function to generate data for the statistics file
function completeStatsArrays(currentNode)
{

	var parentTag = currentNode.parentNode.tagName;
	var currentTag = currentNode.tagName;
	var currentClass = currentNode.className;

	//Not usable on internet explorer
	var style = window.getComputedStyle(currentNode);
	//Not usable on internet explorer
	var rectangle = currentNode.getBoundingClientRect();

	typeOfChildrenArrayFiller(parentTag, currentTag);
	numberOfNodesArrayFiller(currentTag, currentClass);
	invisibleArrayFiller(style, rectangle);
}

//Node structure
class Node {
	constructor(domNode, level, parentNode){
		//Get link to the dom node
		this.domNode = domNode;
		//Get parent's nodeName as a Graphviz node
		this.parentNode = parentNode;
		//Get node's Graphviz name
		this.nodeName = getNodeName();
		//Get the level
		this.level = level;
		//Get stats
		this.childCount = 0;
		this.children = [];
		this.maxDepth = -1;
		this.minDepth = 99999999;
		this.degree = domNode.children.length;
		this.maxDegree = this.degree;
		this.minDegree = this.degree;
		for(var i = 0; i < domNode.children.length; i++)
		{
			this.children.push(new Node(domNode.children[i], level+1, this.nodeName));
			this.childCount += this.children[i].childCount;

			if(this.maxDepth < this.children[i].maxDepth)
			{
				this.maxDepth = this.children[i].maxDepth + 1;
			}

			if(this.minDepth > this.children[i].minDepth)
			{
				this.minDepth = this.children[i].minDepth + 1;
			}

			if(this.maxDegree < this.children[i].maxDegree)
			{
				this.maxDegree = this.children[i].maxDegree;
			}

			if(this.minDegree > this.children[i].minDegree && this.children[i].domNode.childElementCount)
			{
				this.minDegree = this.children[i].minDegree;
			}
		}
		this.childCount += this.children.length;

		if(this.childCount > 0 && this.maxDepth === -1)
		{
			this.maxDepth = 0;
		}

		if(this.minDepth === 99999999)
		{
			this.minDepth = 0;
		}
		completeStatsArrays(this.domNode);
	}
}

// Function to download data to a file
function download(fileNameToSaveAs, textToWrite) {
  /* Saves a text string as a blob file*/  
  var ie = navigator.userAgent.match(/MSIE\s([\d.]+)/),
	  ie11 = navigator.userAgent.match(/Trident\/7.0/) && navigator.userAgent.match(/rv:11/),
	  ieEDGE = navigator.userAgent.match(/Edge/g),
	  ieVer=(ie ? ie[1] : (ie11 ? 11 : (ieEDGE ? 12 : -1)));

  if (ie && ieVer<10) {
	console.log("No blobs on IE ver<10");
	return;
  }

  var textFileAsBlob = new Blob([textToWrite], {
	type: 'text/plain'
  });

  if (ieVer>-1) {
	window.navigator.msSaveBlob(textFileAsBlob, fileNameToSaveAs);

  } else {
	var downloadLink = document.createElement("a");
	downloadLink.download = fileNameToSaveAs;
	downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
	downloadLink.onclick = function(e) { document.body.removeChild(e.target); };
	downloadLink.style.display = "none";
	document.body.appendChild(downloadLink);
	downloadLink.click();
  }
  
  //reference https://stackoverflow.com/questions/18755750/saving-text-in-a-local-file-in-internet-explorer-10
}
	  
	  

//Generate dom tree from the root node
var n = new Node(body,0, null);
//Get name for the files
var splittedUrl = document.location.href.split("/");
var hostName = splittedUrl[2].split(".");
var siteName = hostName[0] === "www"?hostName[1]: hostName[0];

//Get current page name if avaible
var pageName = splittedUrl[3].split("/")[0];
pageName = pageName.split("?")[0];
pageName = pageName.split(".")[0];
var fileName = (pageName ==="" || pageName === undefined)?siteName:siteName + "_" + pageName;
var statsFileName = fileName + "_statistics.json";
fileName += ".dot";

//Donwload the files
download(printFileContent(print(n)), fileName, "text/plain");
download(printStatsFileContent(), statsFileName, "text/plain");