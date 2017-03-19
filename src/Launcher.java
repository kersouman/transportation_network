import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jdom2.JDOMException;

import car.Car;
import cargps.CarGPS;
import clock.Clock;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import map.Junction;
import results.ResultsManager;
import support.AgendaInterface;

public class Launcher 
{

	static void method() throws StaleProxyException, JDOMException, IOException
	{
		Runtime rt = Runtime.instance();
		rt.setCloseVM(true);
		
		Profile pMain = new ProfileImpl("localhost", 8888, null);
		AgentContainer mc = rt.createMainContainer(pMain);
		
		map.Map map = new map.Map("roads", "junctions");
		Map<String, HashMap<Integer, Junction[]>> agendas =
				AgendaInterface.generateAgendas("agenda", map.getVertices());

		Object[] clockInit = {
				400
		};
		AgentController clock =
				mc.createNewAgent("clock", Clock.class.getName(), clockInit);
		clock.start();
		
		Object[] carGPSInit = {
				map
		};
		AgentController carGPS = 
				mc.createNewAgent("carGPS", CarGPS.class.getName(), carGPSInit);
		carGPS.start();
		
		Random rand = new Random();
		int travelerByBatch = 50;
		String[] agendaId = {
				"SACO","SACS","SAS","SANI","SASu","SAE","SAX",
				"CCO","CCS","CS","CNI","CSu","CE","CX",
				"NCO","NCS","NS","NNI","NSu","NE","NX",
				"MCO","MCS","MS","MNI","MSu","ME","MX"
		};
		
		Object[] rmInit = {
				travelerByBatch*agendaId.length
		};
		AgentController resultsManager =
				mc.createNewAgent("rm", ResultsManager.class.getName(), rmInit);
		resultsManager.start();
		
		for (String id: agendaId)
		{
			for (int i = 0; i < travelerByBatch; i++)
			{
				Object[] travelerInit = {
						agendas.get(id),
						map,
						0//rand.nextInt(60)
				};
				AgentController car = 
						mc.createNewAgent(id + i, Car.class.getName(), travelerInit);
				car.start();
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			Launcher.method();
		} 
		catch(StaleProxyException e) {e.printStackTrace();}
		catch (JDOMException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}
	}
	
}