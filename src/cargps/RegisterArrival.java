package cargps;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import map.Junction;

@SuppressWarnings("serial")
public class RegisterArrival extends CyclicBehaviour 
{

	@Override
	public void action()
	{
		ACLMessage registerArrival = myAgent.receive(
				new MessageTemplate(new RegisterArrivalTemplate()));

		if (registerArrival == null)
		{
			block();
		}
		else
		{
			try
			{
				String[] id_junctions = {
						(String)((Object[])registerArrival.getContentObject())[1],
						(String)((Object[])registerArrival.getContentObject())[2]
				};
				String id_section = Junction.getCommonSectionId(
						((CarGPS)myAgent).getJunctions().get(id_junctions[0]),
						((CarGPS)myAgent).getJunctions().get(id_junctions[1]));
				float travelTime = ((CarGPS)myAgent).getDistance(id_section);

				((CarGPS)myAgent).setDistance(id_section, travelTime - 2f);
			}
			catch (UnreadableException e) {e.printStackTrace();}
		}
	}
	
}
