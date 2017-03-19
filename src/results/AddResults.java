package results;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class AddResults extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage resultsMessage = myAgent.receive(
				new MessageTemplate(new ResultsMessage()));
		
		if (resultsMessage == null)
		{
			block();
		}
		else
		{
			try
			{
				this.addResults(resultsMessage);
			}
			catch (UnreadableException e) {e.printStackTrace();}
		}
		
		if (((ResultsManager)myAgent).getNbOfAgents() == 
				((ResultsManager)myAgent).getResults().size())
		{
			try {
				this.printResults();
			} catch (IOException e) {e.printStackTrace();}
		}
	}

	private void addResults(ACLMessage resultsMessage) 
			throws UnreadableException
	{
		Object[] rm_content = (Object[])resultsMessage.getContentObject();
		
		ArrayList<Integer> results = new ArrayList<Integer>();
		
		for (int i = 1; i < rm_content.length; i++)
		{
			results.add((int)rm_content[i]);
		}
		
		((ResultsManager)myAgent).addResult(results);
	}
	
	private void printResults() 
			throws IOException
	{
		String nameFile = "./results/no_margin.csv";
		File ftw = new File(nameFile);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(ftw), "UTF-8"));
		String csv = "t_expected,t_start,t_arrived,margin\n";
		for (ArrayList<Integer> listResults: 
			((ResultsManager)myAgent).getResults()) 
		{
			for (int result: listResults)
			{
				csv += result + ",";
			}
			csv = csv.substring(0, csv.length() - 1);
			csv += "\n";
		}
		bw.write(csv);
		bw.close();
	}
	
}
