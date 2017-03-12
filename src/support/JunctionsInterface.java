package support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import map.Junction;
import map.Road;
import map.Section;

public class JunctionsInterface {

	public static HashMap<String, Junction> 
		generateJunctions(String junctions, Map<String, Road> roads)
			throws IOException, JDOMException 
	{
		SAXBuilder build = new SAXBuilder();
		File xml = new File("./resources/" + junctions + ".xml");
		Document doc = (Document)build.build(xml);
		
		Element root = doc.getRootElement();
		return generateJunctions(root.getChildren("junction"), roads);
	}
	
	private static HashMap<String, Junction> 
		generateJunctions(List<Element> l_junctions, 
				Map<String, Road> roads) 
	{
		HashMap<String, Junction> al_junction = new HashMap<String, Junction>();
		
		for (Element element: l_junctions) 
		{
			List<Object[]> al_jointSections =
					generateJointSections(element.getChildren("section"), 
							roads);
			String junctionID = element.getAttributeValue("id");
			
			al_junction.put(junctionID, 
					new Junction(al_jointSections, junctionID));
		}
		return al_junction;
	}
	
	private static List<Object[]> 
		generateJointSections(List<Element> l_js, Map<String, Road> roads) 
	{
		List<Object[]> al_jointSections = new ArrayList<Object[]>();
		for (Element element: l_js) 
		{
			for (String k_road: roads.keySet()) 
			{
				Map<String, Section> sections = 
						roads.get(k_road).getSections();
				for (String k_section: sections.keySet())
				{
					if (sections.get(k_section).getByID(
							element.getAttributeValue("id")))
					{
						Object[] jointSection = {
								sections.get(k_section),
								element.getText()
						};
						al_jointSections.add(jointSection);
					}
				}
			}
		}
		return al_jointSections;
	}
	
}
