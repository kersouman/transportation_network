package car;

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
		switch((Integer)((Car)myAgent).getState())
		{
		case 0:
			try 
			{
				this.gpsInitialPathRequest();
			} 
			catch (IOException e) {e.printStackTrace();}

			((Car)myAgent).setState(1);

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
					((Car)myAgent).setPath((List<String>)(
							(Object[])pathReply.getContentObject())[1]);
					((Car)myAgent).setTotalTimeTravel((int)(
							(Object[])pathReply.getContentObject())[2]);
				}
				catch (UnreadableException e) {e.printStackTrace();}

				((Car)myAgent).setState(2);
			}

			break;
		case 2:
			try 
			{
				this.clockRegistration();
			}
			catch (IOException e) {e.printStackTrace();}

			((Car)myAgent).setState(3);

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
				((Car)myAgent).setCurrentJunction(
								((Car)myAgent).getPath().get(0));
				try 
				{
					this.requestCurrentTravelTime(0, 1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Car)myAgent).setState(4);
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
				
				((Car)myAgent).setState(5);
				block((int)(blockTime*1000));
			}
			break;
		case 5:
			int index_currentJunction = ((Car)myAgent).getPath().indexOf(
					((Car)myAgent).getCurrentJunction()) + 1;
			
			if (index_currentJunction == 
					((Car)myAgent).getPath().size() - 1)
			{
				((Car)myAgent).setState(6);
			}
			else
			{
				try 
				{
					((Car)myAgent).setCurrentJunction(
							((Car)myAgent).getPath().get(
									index_currentJunction));
					this.requestCurrentTravelTime(index_currentJunction - 1, 
							index_currentJunction,
							index_currentJunction + 1);
				}
				catch (IOException e) {e.printStackTrace();}

				((Car)myAgent).setState(4);
			}
			break;
		case 6:
			System.out.println(((Car)myAgent).getName() + 
					": I have reached my destination and I wait");
			try 
			{
				this.registerArrival();
				this.getTickArrival();
			}
			catch (IOException e) {e.printStackTrace();}
			
			((Car)myAgent).setState(7);
			break;
		case 7:
			ACLMessage tickArrivalReply = myAgent.receive(
					new MessageTemplate(new TickArrivalReply()));
			
			if (tickArrivalReply == null)
			{
				block();
			}
			else
			{
				try 
				{
					System.out.println(((Object[])tickArrivalReply.getContentObject())[1]);
					((Car)myAgent).setTickArrived((int)
							((Object[])tickArrivalReply.getContentObject())[1]);
				}
				catch (UnreadableException e) {e.printStackTrace();}
				
				try 
				{
					this.sendResults();
				}
				catch (IOException e) {e.printStackTrace();}
				
				((Car)myAgent).setState(8);
			}
			break;
		default:
			break;
		}
	}

	private void gpsInitialPathRequest() throws IOException
	{
		Junction[] j_journey = ((Car)myAgent).getAgenda().get(
				Collections.min(((Car)myAgent).getAgenda().keySet()));
		String[] r_content = {
				"Path request",
				j_journey[0].getJunctionID(),
				j_journey[1].getJunctionID()
		};

		ACLMessage gpsRequest = new ACLMessage(ACLMessage.REQUEST);
		gpsRequest.addReceiver(((Car)myAgent).getGPS());

		gpsRequest.setContentObject(r_content);

		myAgent.send(gpsRequest);
	}
	
	private void clockRegistration() throws IOException
	{
		int tickStart = Collections.min(
				((Car)myAgent).getAgenda().keySet());
		((Car)myAgent).setTickExpected(tickStart);
		tickStart -= ((Car)myAgent).getMargin() + 1;
		tickStart -= ((Car)myAgent).getTotalTimeTravel();
		
		((Car)myAgent).setTickStart(tickStart);

		ACLMessage clockRegister = new ACLMessage(ACLMessage.REQUEST);
		clockRegister.addReceiver(((Car)myAgent).getClock());
				
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
				((Car)myAgent).getPath().get(origin),
				((Car)myAgent).getPath().get(destination)
		};
		
		ACLMessage travelTime = new ACLMessage(ACLMessage.REQUEST);
		travelTime.addReceiver(((Car)myAgent).getGPS());
		
		travelTime.setContentObject(tt_content);

		myAgent.send(travelTime);
	}
	
	private void requestCurrentTravelTime(int last, int origin, int destination) 
			throws IOException
	{
		String[] tt_content = {
				"Travel time request",
				((Car)myAgent).getPath().get(origin),
				((Car)myAgent).getPath().get(destination),
				((Car)myAgent).getPath().get(last)
		};
		
		ACLMessage travelTime = new ACLMessage(ACLMessage.REQUEST);
		travelTime.addReceiver(((Car)myAgent).getGPS());
		
		travelTime.setContentObject(tt_content);

		myAgent.send(travelTime);
	}

	private void registerArrival() throws IOException
	{
		int pathLength = ((Car)myAgent).getPath().size() - 1;

		String[] ra_content = {
				"Register arrival",
				((Car)myAgent).getPath().get(pathLength - 1),
				((Car)myAgent).getPath().get(pathLength)
		};

		ACLMessage registerArrival = new ACLMessage(ACLMessage.INFORM);
		registerArrival.addReceiver(((Car)myAgent).getGPS());
		
		registerArrival.setContentObject(ra_content);
		
		myAgent.send(registerArrival);
	}
	
	private void getTickArrival() throws IOException
	{
		ACLMessage tickArrival = new ACLMessage(ACLMessage.INFORM);
		tickArrival.addReceiver(((Car)myAgent).getClock());
		tickArrival.setContent("Request tick arrival");
		myAgent.send(tickArrival);
	}
	
	private void sendResults() throws IOException
	{
		Object[] results = {
			"Results",
			((Car)myAgent).getTickExpected(),
			((Car)myAgent).getTickStart(),
			((Car)myAgent).getTickArrived(),
			((Car)myAgent).getMargin()
		};
		
		ACLMessage resultsMessage = new ACLMessage(ACLMessage.INFORM);
		resultsMessage.addReceiver(((Car)myAgent).getResultsManager());
		
		resultsMessage.setContentObject(results);
		myAgent.send(resultsMessage);
	}

	@Override
	public boolean done() 
	{
		if (((Car)myAgent).getState() == 8)
		{
			return true;
		}
		return false;
	}
	
}
