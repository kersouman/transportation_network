package bicyclegps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class RegisterArrivalTemplate implements MatchExpression {

	@Override
	public boolean match(ACLMessage registerArrival) 
	{
		try 
		{
			if (registerArrival.getPerformative() == ACLMessage.INFORM &&
					((String[])registerArrival.getContentObject())[0]
							.equals("Register arrival"))
			{
				return true;
			}
		}
		catch (UnreadableException e) {e.printStackTrace();}
		return false;
	}

}
