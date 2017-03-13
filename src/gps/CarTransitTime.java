package gps;

import java.io.IOException;

import jade.core.AID;
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
			try
			{
				this.computeAndSendReply(travelTimeRequest);
			}
			catch (UnreadableException | IOException e) {e.printStackTrace();}
		}
	}
	
	private void computeAndSendReply(ACLMessage travelTimeRequest) 
			throws UnreadableException, IOException
	{
		int travelTime = this.getTravelTime(travelTimeRequest);
		this.sendReply(travelTime, travelTimeRequest.getSender());
	}
	
	private int getTravelTime(ACLMessage travelTimeRequest) 
			throws UnreadableException
	{
		String[] id_junctions = {
				(String)((Object[])travelTimeRequest.getContentObject())[1],
				(String)((Object[])travelTimeRequest.getContentObject())[2]
		};
		String id_section = Junction.getCommonSectionId(
				((CarGPS)myAgent).getJunctions().get(id_junctions[0]),
				((CarGPS)myAgent).getJunctions().get(id_junctions[1]));
		float travelTime = ((CarGPS)myAgent).getDistance(id_section);
		
		this.updateTravelTime(travelTime, id_section);
		
		return (int)travelTime;
	}
	
	private void updateTravelTime(float travelTime, String id_section)
	{
		((CarGPS)myAgent).setDistance(id_section, travelTime + 2f);
	}
	
	private void sendReply(int travelTime, AID receiver) 
			throws IOException
	{
		Object[] ttr_content = {
				"Travel time reply",
				travelTime
		};
		
		ACLMessage travelTimeReply = new ACLMessage(ACLMessage.INFORM);
		travelTimeReply.addReceiver(receiver);
		
		travelTimeReply.setContentObject(ttr_content);
		
		myAgent.send(travelTimeReply);
	}

}
