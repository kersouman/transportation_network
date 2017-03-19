package results;

import java.util.ArrayList;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

@SuppressWarnings("serial")
public class ResultsManager extends Agent 
{

	private ArrayList<ArrayList<Integer>> results = 
			new ArrayList<ArrayList<Integer>>();
	private int nbOfAgents = 0;
	
	public void setup()
	{
		this.nbOfAgents = (int)getArguments()[0];
		
		this.setDescriptionService();
		this.addBehaviour(new AddResults());
	}
	
	private void setDescriptionService() 
	{
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Results");
		sd.setType("resultsManager");
		
		DFAgentDescription dfad = new DFAgentDescription();
		dfad.setName(this.getAID());
		dfad.addServices(sd);
		try 
		{
			DFService.register(this, dfad);
		} 
		catch (FIPAException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void addResult(ArrayList<Integer> results)
	{
		this.results.add(results);
	}
	
	public ArrayList<ArrayList<Integer>> getResults()
	{
		return this.results;
	}
	
	public int getNbOfAgents()
	{
		return this.nbOfAgents;
	}
}

