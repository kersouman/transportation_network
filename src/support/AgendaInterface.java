package support;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import map.Junction;

public class AgendaInterface 
{

	public static HashMap<String, HashMap<Integer, Junction[]>> 
		generateAgendas(String agenda, List<Junction> junctions)
			throws IOException, JDOMException 
	{
		SAXBuilder build = new SAXBuilder();
		File xml = new File("./resources/" + agenda + ".xml");
		Document doc = (Document)build.build(xml);

		Element root = doc.getRootElement();
		return generateAgendas(root.getChildren("agenda"), junctions);
	}

	private static HashMap<String, HashMap<Integer, Junction[]>>
		generateAgendas(List<Element> l_agenda, List<Junction> junctions) 
	{
		HashMap<String, HashMap<Integer, Junction[]>> hm_agendas = 
				new HashMap<String, HashMap<Integer, Junction[]>>();
		for (Element element: l_agenda) 
		{
			HashMap<Integer, Junction[]> hm_agenda =
					generateAgenda(element.getChildren("entry"), 
							junctions);
			String agendaID = element.getAttributeValue("id");

			hm_agendas.put(agendaID, hm_agenda);
		}
		return hm_agendas;
	}

	private static HashMap<Integer, Junction[]>
		generateAgenda(List<Element> l_e, List<Junction> junctions) 
	{
		HashMap<Integer, Junction[]> hm_agenda = 
				new HashMap<Integer, Junction[]>();
		for (Element element: l_e) 
		{
			int tick = Integer.parseInt(element.getChildText("tick"));
			String origin = element.getChildText("origin");
			String destination = element.getChildText("destination");
			
			Junction j_origin = null;
			Junction j_destination = null;
			
			for (Junction junction: junctions) 
			{
				if (junction.getByID(origin))
				{
					j_origin = junction;
				}
				if (junction.getByID(destination))
				{
					j_destination = junction;
				}
			}
			Junction[] a_junctions = {
					j_origin,
					j_destination
			};
			hm_agenda.put(tick, a_junctions);
		}
		return hm_agenda;
	}

}
