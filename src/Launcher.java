import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
				510
		};
		Object[] rmInit = {
				1500
		};
		Object[] travelerInit0 = {
				agendas.get("NS"),
				map,
				0
		};
		Object[] travelerInit1 = {
				agendas.get("SAS"),
				map,
				0
		};
		Object[] travelerInit2 = {
				agendas.get("NSA"),
				map,
				0
		};
		Object[] carGPSInit = {
				map
		};
		
		AgentController clock =
				mc.createNewAgent("clock", Clock.class.getName(), clockInit);
		AgentController carGPS = 
				mc.createNewAgent("carGPS", CarGPS.class.getName(), carGPSInit);
		AgentController resultsManager =
				mc.createNewAgent("rm", ResultsManager.class.getName(), rmInit);
		AgentController[] car  = new AgentController[500];
		AgentController[] car1 = new AgentController[500];
		AgentController[] car2 = new AgentController[500];
		for (int i = 0; i < 500; i++)
		{
			car[i]  = mc.createNewAgent("car " + i, Car.class.getName(), travelerInit0);
			car1[i] = mc.createNewAgent("car1 " + i, Car.class.getName(), travelerInit1);
			car2[i] = mc.createNewAgent("car2 " + i, Car.class.getName(), travelerInit2);
		}
		clock.start();
		carGPS.start();
		resultsManager.start();
		for (int i = 0; i < 500; i++)
		{
			car[i].start();
			car1[i].start();
			car2[i].start();
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