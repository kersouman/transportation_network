package support;

import java.io.IOException;

import org.jdom2.JDOMException;

public class FileTest {

	public static void main(String[] args) {
		try {
			RoadsInterface.generateRoads("roads");
			JunctionsInterface.generateJunctions("junctions");
		} catch (IOException | JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
