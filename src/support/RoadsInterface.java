package support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import map.Point;
import map.Road;
import map.Section;

public class RoadsInterface 
{
	
	public static List<Road> generateRoads(String roads)
			throws IOException, JDOMException 
	{
		SAXBuilder build = new SAXBuilder();
		File xml = new File("./resources/" + roads + ".xml");
		Document doc = (Document)build.build(xml);
		
		Element root = doc.getRootElement();
		return generateRoads(root.getChildren("road"));
	}
	
	private static List<Road> generateRoads(List<Element> l_roads) 
	{
		List<Road> al_roads = new ArrayList<Road>();
		
		for (Element element: l_roads) 
		{
			List<Section> al_section =
					generateSections(element.getChildren("section"));
			String name = element.getChildText("name");
			
			al_roads.add(new Road(al_section, name));
		}
		return al_roads;
	}
	
	private static List<Section> generateSections(List<Element> l_sections) 
	{
		List<Section> al_section = new ArrayList<Section>();
		
		for (Element element: l_sections) 
		{
			List<Point> al_point =
					generatePoints(element.getChildren("coordinate"));
			String sectionID = element.getAttributeValue("id");

			al_section.add(new Section(al_point, sectionID));
		}
		return al_section;
	}
	
	private static List<Point> generatePoints(List<Element> l_coordinates) 
	{
		List<Point> al_points = new ArrayList<Point>();

		for (Element element: l_coordinates) 
		{
			float lat = Float.parseFloat(element.getChildText("lat"));
			float lng = Float.parseFloat(element.getChildText("lng"));
			
			al_points.add(new Point(lat, lng));
		}
		return al_points;
	}
	
}
