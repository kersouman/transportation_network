package gps;

import java.io.IOException;
import java.io.Serializable;
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
			block();
		}
		else
		{
			try 
			{
				this.computeAndSendPath(req);
			} 
			catch (UnreadableException | IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void computeAndSendPath(ACLMessage req)
		throws UnreadableException, IOException
	{
		this.sendPathToTraveler(this.computePath(req), req.getSender());
	}
	
	private List<Junction> computePath(ACLMessage req) 
			throws UnreadableException
	{
		Junction[] limits = (Junction[])req.getContentObject();
		
		Dijkstra dijkstra = 
				new Dijkstra(((CarGPS)myAgent).getJunctions(),
						((CarGPS)myAgent).getDistances());
		dijkstra.execute(limits[0], limits[1]);
		
		return dijkstra.getPath(limits[1]);
	}

	private void sendPathToTraveler(List<Junction> path, AID traveler) 
			throws IOException
	{
		ACLMessage pathReply = new ACLMessage(ACLMessage.AGREE);
		pathReply.setContentObject((Serializable)path);
		pathReply.setContent("Path reply");
		pathReply.addReceiver(traveler);
		
		myAgent.send(pathReply);
	}
		
}
