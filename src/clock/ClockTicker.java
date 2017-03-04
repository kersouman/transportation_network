package clock;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class ClockTicker extends TickerBehaviour {

	public ClockTicker(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		this.updateDate();
		System.out.println(((Clock)myAgent).getAttribute("day") + ":" +
				((Clock)myAgent).getAttribute("hour") + ":" +
				((Clock)myAgent).getAttribute("minute"));
	}
	
	private void updateDate() {
		((Clock)myAgent).setAttribute("tick", 
				((Integer)((Clock)myAgent).getAttribute("tick") + 1)%1440);
		int tick = (Integer)((Clock)myAgent).getAttribute("tick");
		if (tick == 0) {
			if ((Integer)((Clock)myAgent).getAttribute("hour") != 0)
				((Clock)myAgent).setAttribute("day", 
						(Integer)((Clock)myAgent).getAttribute("day") + 1);
			((Clock)myAgent).setAttribute("hour", new Integer(0));
			((Clock)myAgent).setAttribute("minute", new Integer(0));
		} else {
			if (tick%60 == 0) {
				((Clock)myAgent).setAttribute("hour", 
						(Integer)((Clock)myAgent).getAttribute("hour") + 1);
				((Clock)myAgent).setAttribute("minute", new Integer(0));
			} else {
				((Clock)myAgent).setAttribute("minute",
						(Integer)((Clock)myAgent).getAttribute("minute") + 1);
			}
		}
	}
	
}
