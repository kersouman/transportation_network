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
import map.Road;
import map.Section;

public class JunctionsInterface {

	public static List<Junction> 
		generateJunctions(String junctions, List<Road> roads)
			throws IOException, JDOMException 
	{
		SAXBuilder build = new SAXBuilder();
		File xml = new File("./resources/" + junctions + ".xml");
		Document doc = (Document)build.build(xml);
		
		Element root = doc.getRootElement();
		return generateJunctions(root.getChildren("junction"), roads);
	}
	
	private static List<Junction> 
		generateJunctions(List<Element> l_junctions, List<Road> roads) 
	{
		List<Junction> al_junction = new ArrayList<Junction>();
		for (Element element: l_junctions) 
		{
			List<Object[]> al_jointSections =
					generateJointSections(element.getChildren("section"), 
							roads);
			String junctionID = element.getAttributeValue("id");
			
			al_junction.add(new Junction(al_jointSections, junctionID));
		}
		return al_junction;
	}
	
	private static List<Object[]> 
		generateJointSections(List<Element> l_js, List<Road> roads) 
	{
		List<Object[]> al_jointSections = new ArrayList<Object[]>();
		for (Element element: l_js) 
		{
			for (Road road: roads) 
			{
				for (Section section: road.getSections())
				{
					if (section.getByID(element.getAttributeValue("id")))
					{
						Object[] jointSection = {section, element.getText()};
						al_jointSections.add(jointSection);
					}
				}
			}
		}
		return al_jointSections;
	}
	
}
