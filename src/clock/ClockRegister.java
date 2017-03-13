package clock;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class ClockRegister implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage clockRegister) 
	{
		try 
		{
			if (clockRegister.getPerformative() == ACLMessage.REQUEST && 
					((Object[])clockRegister.getContentObject())[0]
							.equals("Clock register"))
			{
				return true;
			}
		} 
		catch (UnreadableException e) {e.printStackTrace();}
		
		return false;
	}

}
