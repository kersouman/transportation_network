package car;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class TickArrivalReply implements MatchExpression
{

	@Override
	public boolean match(ACLMessage tickArrivalReply) 
	{
		try 
		{
			if (tickArrivalReply.getPerformative() == ACLMessage.AGREE &&
					((Object[])tickArrivalReply.getContentObject())[0]
							.equals("Tick arrival reply"))
				return true;
		} catch (UnreadableException e) {e.printStackTrace();}
		
		return false;
	}

}
