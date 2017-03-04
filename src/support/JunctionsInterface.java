package support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import map.Junction;

public class JunctionsInterface 
{

	public static List<Junction> generateJunctions(String junctions)
			throws IOException, JDOMException 
	{
		SAXBuilder build = new SAXBuilder();
		File xml = new File("./resources/" + junctions + ".xml");
		Document doc = (Document)build.build(xml);
		
		Element root = doc.getRootElement();
		return generateJunctions(root.getChildren("junction"));
	}
	
	private static List<Junction> generateJunctions(List<Element> l_junctions) 
	{
		List<Junction> al_junction = new ArrayList<Junction>();
		
		for (Element element: l_junctions) 
		{
			List<String[]> al_jointSections =
					generateJointSections(element.getChildren("section"));
			String junctionID = element.getAttributeValue("id");
			
			al_junction.add(new Junction(al_jointSections, junctionID));
		}
		return al_junction;
	}
	
	private static List<String[]> generateJointSections(List<Element> l_js) 
	{
		List<String[]> al_jointSections = new ArrayList<String[]>();
		
		for (Element element: l_js) 
		{
			String[] jointSection = {element.getAttributeValue("id"),
					element.getText()};
			
			al_jointSections.add(jointSection);
		}
		return al_jointSections;
	}
	
}
