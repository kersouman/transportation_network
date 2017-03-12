package gps;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import map.Junction;

@SuppressWarnings("serial")
public class CarTransitTime extends CyclicBehaviour
{

	@Override
	public void action() 
	{
		ACLMessage travelTimeRequest = myAgent.receive(
				new MessageTemplate(new TravelTimeRequest()));
		
		if (travelTimeRequest == null)
		{
			block();
		}
		else
		{
			Junction[] junctions = new Junction[2];
			try 
			{
				junctions = (Junction[])travelTimeRequest.getContentObject();
			}
			catch (UnreadableException e) {e.printStackTrace();}
			
			String id = Junction.getCommonSectionId(junctions[0], junctions[1]);
			int travelTime = (int)Math.ceil(((CarGPS)myAgent).getDistance(id));
			((CarGPS)myAgent).setDistance(id, 
					((CarGPS)myAgent).getDistance(id) + 2.0f);
			
			ACLMessage travelTimeReply = new ACLMessage(ACLMessage.INFORM);
			travelTimeReply.setContent("Travel time reply");
			try
			{
				travelTimeReply.setContentObject(travelTime);
			}
			catch (IOException e) {e.printStackTrace();}
			travelTimeReply.addReceiver(travelTimeRequest.getSender());
			
			myAgent.send(travelTimeReply);
		}
	}

}
