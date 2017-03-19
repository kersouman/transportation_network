package clock;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

@SuppressWarnings("serial")
public class TickArrivalRequest implements MatchExpression {

	/*
	 * Here, we use a performative INFORM because if we use REQUEST (which
	 * would be logical), we get an error because StartRegister thinks it's
	 * a message of the type ClockRegister
	 */
	@Override
	public boolean match(ACLMessage tickArrivalRequest) {
		if (tickArrivalRequest.getPerformative() == ACLMessage.INFORM &&
				tickArrivalRequest.getContent().equals("Request tick arrival"))
			return true;
		
		return false;
	}

}
