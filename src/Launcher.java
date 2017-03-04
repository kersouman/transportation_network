import clock.Clock;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Launcher {

	static void method() throws StaleProxyException {
		Runtime rt = Runtime.instance();
		rt.setCloseVM(true);
		
		Profile pMain = new ProfileImpl("localhost", 8888, null);
		AgentContainer mc = rt.createMainContainer(pMain);
		
		AgentController test1 =
				mc.createNewAgent("test1", Clock.class.getName(), new Object[0]);
		test1.start();
	}
	
	public static void main(String[] args) {
		try {
			Launcher.method();
		} catch(StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
}