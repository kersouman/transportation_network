package gps;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import map.Junction;
import support.Dijkstra;

@SuppressWarnings("serial")
public class CarItinerary extends CyclicBehaviour 
{

	@Override
	public void action() 
	{
		ACLMessage req = myAgent.receive(
				new MessageTemplate(
						new CarGPSRequest()));
		
		if (req == null)
		{
			System.out.println("GPS will block");
			block();
		}
		else
		{
			System.out.println("GPS tries to resolve the itinerary");
			try 
			{
				this.computeAndSendPath(req);
			} 
			catch (UnreadableException | IOException e) {e.printStackTrace();}
		}
	}
	
	private void computeAndSendPath(ACLMessage req)
		throws UnreadableException, IOException
	{
		this.sendPathToTraveler(this.computePath(req), req.getSender());
	}
	
	private void sendPathToTraveler(List<Junction> path, AID traveler) 
			throws IOException
	{
		ACLMessage pathReply = new ACLMessage(ACLMessage.AGREE);
		
		pathReply.addReceiver(traveler);
		
		List<Object> content = new ArrayList<Object>();
		content.add(path);
		content.add(this.pathTime(path));
		
		pathReply.setContentObject((Serializable)content);
		
		myAgent.send(pathReply);
	}
	
	private List<Junction> computePath(ACLMessage req) 
			throws UnreadableException
	{
		Junction[] limits = (Junction[])req.getContentObject();
		
		Dijkstra dijkstra = 
				new Dijkstra(((CarGPS)myAgent).getJunctions(),
						((CarGPS)myAgent).getDistances());
		dijkstra.execute(limits[0]);
		
		return dijkstra.getPath(limits[1]);
	}
	
	private int pathTime(List<Junction> path)
	{
		int time = 0;
		
		for (int i = 0; i < path.size() - 1; i++)
		{
			time += (int)((CarGPS)myAgent).getDistance(
					Junction.getCommonSectionId(path.get(i), path.get(i+1)));
		}
		
		return time;
	}
		
}
