package gps;

import java.io.IOException;
import java.util.ArrayList;

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
		ACLMessage gpsRequest = myAgent.receive(
				new MessageTemplate(
						new CarGPSRequest()));
		
		if (gpsRequest == null)
		{
			System.out.println("GPS blocks");
			block();
		}
		else
		{
			System.out.println("GPS tries to resolve the itinerary");
			try 
			{
				this.computeAndSendPath(gpsRequest);
			} 
			catch (UnreadableException | IOException e) {e.printStackTrace();}
		}
	}
	
	private void computeAndSendPath(ACLMessage req)
		throws UnreadableException, IOException
	{
		this.sendPathToTraveler(this.computePath(req), req.getSender());
	}
	
	private void sendPathToTraveler(ArrayList<String> path, AID traveler) 
			throws IOException
	{
		Object[] content = {
			"GPS reply",
			path,
			this.pathTime(path)
		};
		
		ACLMessage pathReply = new ACLMessage(ACLMessage.AGREE);	
		pathReply.addReceiver(traveler);
		
		pathReply.setContentObject(content);
		
		myAgent.send(pathReply);
	}
	
	private ArrayList<String> computePath(ACLMessage gpsRequest) 
			throws UnreadableException
	{
		String[] limits = {
				((String[])gpsRequest.getContentObject())[1],
				((String[])gpsRequest.getContentObject())[2]
		};
		
		Dijkstra dijkstra = 
				new Dijkstra(((CarGPS)myAgent).getJunctions(),
						((CarGPS)myAgent).getDistances());
		dijkstra.execute(limits[0]);
		
		return dijkstra.getPath(limits[1]);
	}
	
	private int pathTime(ArrayList<String> path)
	{
		int time = 0;
		
		for (int i = 0; i < path.size() - 1; i++)
		{
			Junction j1 = ((CarGPS)myAgent).getJunctions().get(path.get(i));
			Junction j2 = ((CarGPS)myAgent).getJunctions().get(path.get(i+1));
			time += (int)((CarGPS)myAgent).getDistance(
					Junction.getCommonSectionId(j1, j2));
		}
		
		return time;
	}
		
}
