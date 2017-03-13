package traveler;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class TravelTimeReply implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage ttRep) 
	{
		try 
		{
			if (ttRep.getPerformative() == ACLMessage.INFORM &&
					((Object[])ttRep.getContentObject())[0]
							.equals("Travel time reply"))
			{
				return true;
			}
		}
		catch (UnreadableException e) {e.printStackTrace();}
		
		return false;
	}

}
