package traveler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import map.Junction;

@SuppressWarnings("serial")
public class Travel extends Behaviour 
{

	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		System.out.println(((Traveler)myAgent).getState());
		switch((Integer)((Traveler)myAgent).getState())
		{
		case 0:
			try 
			{
				this.gpsInitialPathRequest();
			} 
			catch (IOException e) {e.printStackTrace();}

			((Traveler)myAgent).setState(1);

			break;
		case 1:
			ACLMessage pathReply = myAgent.receive(
					new MessageTemplate(new PathReply()));

			if (pathReply == null)
			{
				block();
			}
			else
			{
				try 
				{
					List<Object> repContent = 
							(List<Object>)pathReply.getContentObject();
					((Traveler)myAgent).setPath(
							(List<Junction>)repContent.get(0));
					((Traveler)myAgent).setTotalTimeTravel(
							(int)repContent.get(1));
				} 
				catch (UnreadableException e) {e.printStackTrace();}

				((Traveler)myAgent).setState(2);
			}

			break;
		case 2:
			try 
			{
				this.clockRegistration();
			}
			catch (IOException e) {e.printStackTrace();}

			((Traveler)myAgent).setState(3);

			break;
		case 3:
			ACLMessage clockStart = myAgent.receive(
					new MessageTemplate(new ClockReply()));

			if (clockStart == null)
			{
				block();
			}
			else
			{
				((Traveler)myAgent).setCurrentJunction(
						((Traveler)myAgent).getPath().get(0));
				try 
				{
					this.requestCurrentTravelTime(0, 1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Traveler)myAgent).setState(4);
			}
			break;
		case 4:
			ACLMessage travelTimeReply = myAgent.receive(
					new MessageTemplate(new TravelTimeReply()));

			if (travelTimeReply == null)
			{
				block();
			}
			else
			{
				int blockTime = 0;
				try 
				{
					blockTime = (int)travelTimeReply.getContentObject();
				} 
				catch (UnreadableException e) {e.printStackTrace();}

				((Traveler)myAgent).setState(5);
				block(blockTime);
			}
			break;
		case 5:
			int currentJunction = ((Traveler)myAgent).getPath().indexOf(
					(((Traveler)myAgent).getCurrentJunction())) + 1;
			((Traveler)myAgent).setCurrentJunction(
					((Traveler)myAgent).getPath().get(currentJunction));
			
			if (((Traveler)myAgent).getPath().get(
					((Traveler)myAgent).getPath().size() - 1).getJunctionID()
					.equals(((Traveler)myAgent)
							.getCurrentJunction().getJunctionID()))
			{
				((Traveler)myAgent).setState(6);
			}
			else
			{
				try 
				{
					this.requestCurrentTravelTime(currentJunction, 
							currentJunction+1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Traveler)myAgent).setState(4);
			}
			break;
		case 6:
			System.out.println(((Traveler)myAgent).getName() + 
					": I have reached my destination and I wait");
		default:
			((Traveler)myAgent).setState(0);
			break;
		}
	}

	private void gpsInitialPathRequest() 
			throws IOException
	{
		Junction[] j_journey = ((Traveler)myAgent).getAgenda().get(
				Collections.min(((Traveler)myAgent).getAgenda().keySet()));
		String[] r_content = {
				"Path request",
				j_journey[0].getJunctionID(),
				j_journey[1].getJunctionID()
		};
		
		ACLMessage gpsRequest = new ACLMessage(ACLMessage.REQUEST);
		gpsRequest.addReceiver(((Traveler)myAgent).getGPS().getName());
		
		gpsRequest.setContentObject(r_content);
		
		myAgent.send(gpsRequest);		
	}
	
	private void clockRegistration() 
			throws IOException
	{
		int tickStart = Collections.min(
				((Traveler)myAgent).getAgenda().keySet());
		tickStart -= ((Traveler)myAgent).getMargin();
		
		ACLMessage clockRegister = new ACLMessage(ACLMessage.REQUEST);
		clockRegister.addReceiver(((Traveler)myAgent).getClock().getName());
				
		Object[] cr_content = {
				"Clock register",
				tickStart
		};

		clockRegister.setContentObject(cr_content);
		
		myAgent.send(clockRegister);
	}
	
	private void requestCurrentTravelTime(int origin, int destination) 
			throws IOException
	{
		Junction[] j_junctions = {
				((Traveler)myAgent).getPath().get(origin),
				((Traveler)myAgent).getPath().get(destination)
		};
		String[] tt_content = {
				"Travel time request",
				j_junctions[0].getJunctionID(),
				j_junctions[1].getJunctionID()
		};
		
		ACLMessage travelTime = new ACLMessage(ACLMessage.REQUEST);
		travelTime.addReceiver(((Traveler)myAgent).getGPS().getName());
		
		travelTime.setContentObject(tt_content);

		myAgent.send(travelTime);
	}
	
	@Override
	public boolean done() 
	{
		return false;
	}
	
}
