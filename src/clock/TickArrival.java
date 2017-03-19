package clock;

import java.io.IOException;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class TickArrival extends CyclicBehaviour 
{

	@Override
	public void action() 
	{
		ACLMessage tickArrivalRequest = myAgent.receive(
				new MessageTemplate(new TickArrivalRequest()));
		
		if (tickArrivalRequest == null)
		{
			block();
		}
		else
		{
			Object[] ta_content = {
					"Tick arrival reply",
					((Clock)myAgent).getTick()
			};
			
			ACLMessage tickArrivalReply = new ACLMessage(ACLMessage.AGREE);
			tickArrivalReply.addReceiver(tickArrivalRequest.getSender());
			
			try
			{
				tickArrivalReply.setContentObject(ta_content);
			}
			catch (IOException e) {e.printStackTrace();}
			
			myAgent.send(tickArrivalReply);
		}
	}

}
