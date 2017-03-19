package bicyclegps;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class BicycleGPSRequest implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage gpsRequest) 
	{	
		try 
		{
			if (gpsRequest.getPerformative() == ACLMessage.REQUEST &&
					((String[])gpsRequest.getContentObject())[0]
							.equals("Path request"))
				return true;
		}
		catch (UnreadableException e) {e.printStackTrace();}

		return false;
	}

}
