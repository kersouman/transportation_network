package clock;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class ClockRegister implements MatchExpression {

	@Override
	public boolean match(ACLMessage register) {
		if (register.getPerformative() == ACLMessage.REQUEST && 
				register.getContent().equals("Clock register"))
		{
			return true;
		}
		
		return false;
	}

}
