package results;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class ResultsMessage implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage resultsMessage) 
	{
		try 
		{
			if (resultsMessage.getPerformative() == ACLMessage.INFORM &&
					((Object[])resultsMessage.getContentObject())[0]
							.equals("Results"))
				return true;
		} 
		catch (UnreadableException e) {e.printStackTrace();}
		
		return false;
	}

}
