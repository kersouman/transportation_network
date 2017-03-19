package bicycle;

import java.io.IOException;
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
		switch((Integer)((Bicycle)myAgent).getState())
		{
		case 0:
			try 
			{
				this.gpsInitialPathRequest();
			} 
			catch (IOException e) {e.printStackTrace();}

			((Bicycle)myAgent).setState(1);

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
					((Bicycle)myAgent).setPath((List<String>)(
							(Object[])pathReply.getContentObject())[1]);
					((Bicycle)myAgent).setTotalTimeTravel((int)(
							(Object[])pathReply.getContentObject())[2]);
				}
				catch (UnreadableException e) {e.printStackTrace();}

				((Bicycle)myAgent).setState(2);
			}

			break;
		case 2:
			try 
			{
				this.clockRegistration();
			}
			catch (IOException e) {e.printStackTrace();}

			((Bicycle)myAgent).setState(3);

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
				((Bicycle)myAgent).setCurrentJunction(
								((Bicycle)myAgent).getPath().get(0));
				try 
				{
					this.requestCurrentTravelTime(0, 1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Bicycle)myAgent).setState(4);
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
				float blockTime = 0f;
				try 
				{
					blockTime = (float)
							((Object[])travelTimeReply.getContentObject())[1];
				} 
				catch (UnreadableException e) {e.printStackTrace();}
				
				((Bicycle)myAgent).setState(5);
				block((int)(blockTime*1000));
			}
			break;
		case 5:
			int index_currentJunction = ((Bicycle)myAgent).getPath().indexOf(
					((Bicycle)myAgent).getCurrentJunction()) + 1;
			
			if (index_currentJunction == 
					((Bicycle)myAgent).getPath().size() - 1)
			{
				((Bicycle)myAgent).setState(6);
			}
			else
			{
				try 
				{
					((Bicycle)myAgent).setCurrentJunction(
							((Bicycle)myAgent).getPath().get(
									index_currentJunction));
					this.requestCurrentTravelTime(index_currentJunction - 1, 
							index_currentJunction,
							index_currentJunction + 1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Bicycle)myAgent).setState(4);
			}
			break;
		case 6:
			System.out.println(((Bicycle)myAgent).getName() + 
					": I have reached my destination and I wait");
			try 
			{
				this.registerArrival();
			}
			catch (IOException e) {e.printStackTrace();}
			
			((Bicycle)myAgent).setState(7);
		default:
			break;
		}
	}

	private void gpsInitialPathRequest() 
			throws IOException
	{
		Junction[] j_journey = ((Bicycle)myAgent).getAgenda().get(
				Collections.min(((Bicycle)myAgent).getAgenda().keySet()));
		String[] r_content = {
				"Path request",
				j_journey[0].getJunctionID(),
				j_journey[1].getJunctionID()
		};

		ACLMessage gpsRequest = new ACLMessage(ACLMessage.REQUEST);
		gpsRequest.addReceiver(((Bicycle)myAgent).getGPS());

		gpsRequest.setContentObject(r_content);

		myAgent.send(gpsRequest);
	}
	
	private void clockRegistration() 
			throws IOException
	{
		int tickStart = Collections.min(
				((Bicycle)myAgent).getAgenda().keySet());
		tickStart -= ((Bicycle)myAgent).getMargin() + 1;
		tickStart -= ((Bicycle)myAgent).getTotalTimeTravel();

		ACLMessage clockRegister = new ACLMessage(ACLMessage.REQUEST);
		clockRegister.addReceiver(((Bicycle)myAgent).getClock());
				
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
		String[] tt_content = {
				"Travel time request",
				((Bicycle)myAgent).getPath().get(origin),
				((Bicycle)myAgent).getPath().get(destination)
		};
		
		ACLMessage travelTime = new ACLMessage(ACLMessage.REQUEST);
		travelTime.addReceiver(((Bicycle)myAgent).getGPS());
		
		travelTime.setContentObject(tt_content);

		myAgent.send(travelTime);
	}
	
	private void requestCurrentTravelTime(int last, int origin, int destination) 
			throws IOException
	{
		String[] tt_content = {
				"Travel time request",
				((Bicycle)myAgent).getPath().get(origin),
				((Bicycle)myAgent).getPath().get(destination),
				((Bicycle)myAgent).getPath().get(last)
		};
		
		ACLMessage travelTime = new ACLMessage(ACLMessage.REQUEST);
		travelTime.addReceiver(((Bicycle)myAgent).getGPS());
		
		travelTime.setContentObject(tt_content);

		myAgent.send(travelTime);
	}

	private void registerArrival() throws IOException
	{
		int pathLength = ((Bicycle)myAgent).getPath().size() - 1;

		String[] ra_content = {
				"Register arrival",
				((Bicycle)myAgent).getPath().get(pathLength - 1),
				((Bicycle)myAgent).getPath().get(pathLength)
		};

		ACLMessage registerArrival = new ACLMessage(ACLMessage.INFORM);
		registerArrival.addReceiver(((Bicycle)myAgent).getGPS());
		
		registerArrival.setContentObject(ra_content);
		
		myAgent.send(registerArrival);
	}

	@Override
	public boolean done() 
	{
		if (((Bicycle)myAgent).getState() == 7)
		{
			return true;
		}
		return false;
	}
	
}
