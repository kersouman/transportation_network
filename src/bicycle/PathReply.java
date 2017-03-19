package bicycle;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class PathReply implements MatchExpression 
{

	@Override
	public boolean match(ACLMessage pathReply) 
	{
		try 
		{
			if (pathReply.getPerformative() == ACLMessage.AGREE &&
					((Object[])pathReply.getContentObject())[0]
							.equals("Path reply"))
			{
				return true;
			}
		} 
		catch (UnreadableException e) {e.printStackTrace();}
		
		return false;
	}

}
