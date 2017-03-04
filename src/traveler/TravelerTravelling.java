package traveler;

import jade.core.behaviours.Behaviour;

public class TravelerTravelling extends Behaviour {

	@Override
	public void action() {
		switch((Integer)((Traveler)myAgent).getAttribute("state")) {
		case 0:
			System.out.println(((Traveler)myAgent).getAttribute("id") 
					+ ": Leaving");
			((Traveler)myAgent).getShortestPath(
					((Traveler)myAgent).getAttribute("dijkstra"),
					((Traveler)myAgent).getAttribute("agenda").get, destination);
			((Traveler)myAgent).setAttribute("state", new Integer(1));
			break;
		case 1:
			
			break;
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
