import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.JDOMException;

import clock.Clock;
import gps.CarGPS;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import map.Junction;
import support.AgendaInterface;
import traveler.Traveler;

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
		Object[] travelerInit = {
				agendas.get("NS"),
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
		AgentController car =
				mc.createNewAgent("car", Traveler.class.getName(), travelerInit);
		AgentController car1 =
				mc.createNewAgent("car1", Traveler.class.getName(), travelerInit);
		AgentController car2 =
				mc.createNewAgent("car2", Traveler.class.getName(), travelerInit);
		clock.start();
		carGPS.start();
		car.start();
		car1.start();
		car2.start();
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