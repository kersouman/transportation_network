package bicyclegps;

import java.io.IOException;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import map.Junction;

@SuppressWarnings("serial")
public class BicycleTransitTime extends CyclicBehaviour
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
		float travelTime = this.getTravelTime(travelTimeRequest);
		this.sendReply(travelTime, travelTimeRequest.getSender());
	}
	
	private float getTravelTime(ACLMessage travelTimeRequest) 
			throws UnreadableException
	{
		String[] id_junctions = {
				(String)((Object[])travelTimeRequest.getContentObject())[1],
				(String)((Object[])travelTimeRequest.getContentObject())[2]
		};
		String id_section = Junction.getCommonSectionId(
				((BicycleGPS)myAgent).getJunctions().get(id_junctions[0]),
				((BicycleGPS)myAgent).getJunctions().get(id_junctions[1]));
		float travelTime = ((BicycleGPS)myAgent).getDistance(id_section);
		
		this.updateTravelTime(travelTime, id_section, 0f);
		
		if (((Object[])travelTimeRequest.getContentObject()).length == 4)
		{
			String id_lastSection = Junction.getCommonSectionId(
					((BicycleGPS)myAgent).getJunctions().get(id_junctions[0]),
					((BicycleGPS)myAgent).getJunctions().get(
							(String)((Object[])travelTimeRequest
									.getContentObject())[3]));
			float lastTravelTime = 
					((BicycleGPS)myAgent).getDistance(id_lastSection);
			
			this.updateTravelTime(lastTravelTime, id_lastSection, 0f);
		}
		
		return (float)Math.ceil(travelTime);
	}
	
	private void updateTravelTime(float travelTime, String id_section, float up)
	{
		((BicycleGPS)myAgent).setDistance(id_section, travelTime + up);
	}
	
	private void sendReply(float travelTime, AID receiver) 
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
