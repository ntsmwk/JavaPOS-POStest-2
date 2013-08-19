package postest2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import jpos.JposConst;
import jpos.JposException;
import jpos.RemoteOrderDisplay;

public class RemoteOrderDisplayController implements Initializable {

	@FXML
	private ComboBox<String> logicalName;
	private RemoteOrderDisplay remoteOrderDisplay;
	private static String statistics = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		remoteOrderDisplay = new RemoteOrderDisplay();
	}


	/* ************************************************************************
	 * ************************ Action Handler *********************************
	 * ***********************************************************************
	 */
	

	// Shows information of device
	@FXML
	public void handleInfo(ActionEvent e) {
		try {
			String ver = new Integer(remoteOrderDisplay.getDeviceServiceVersion()).toString();
			String msg = "Service Description: " + remoteOrderDisplay.getDeviceServiceDescription();
			msg = msg + "\nService Version: v" + new Integer(ver.substring(0, 1)) + "."
					+ new Integer(ver.substring(1, 4)) + "." + new Integer(ver.substring(4, 7));
			ver = new Integer(remoteOrderDisplay.getDeviceControlVersion()).toString();
			msg += "\n\nControl Description: " + remoteOrderDisplay.getDeviceControlDescription();
			msg += "\nControl Version: v" + new Integer(ver.substring(0, 1)) + "."
					+ new Integer(ver.substring(1, 4)) + "." + new Integer(ver.substring(4, 7));
			msg += "\n\nPhysical Device Name: " + remoteOrderDisplay.getPhysicalDeviceName();
			msg += "\nPhysical Device Description: " + remoteOrderDisplay.getPhysicalDeviceDescription();

			msg += "\n\nProperties:\n------------------------";

			msg += "\nCapStatisticsReporting: " + (remoteOrderDisplay.getCapStatisticsReporting());

			msg += "\nCapUpdateFirmware: " + (remoteOrderDisplay.getCapUpdateFirmware());

			msg += "\nCapCompareFirmwareVersion: " + (remoteOrderDisplay.getCapCompareFirmwareVersion());

			msg += "\nCapPowerReporting: "
					+ (remoteOrderDisplay.getCapPowerReporting() == JposConst.JPOS_PR_ADVANCED ? "Advanced"
							: (remoteOrderDisplay.getCapPowerReporting() == JposConst.JPOS_PR_STANDARD ? "Standard"
									: "None"));

			msg = msg + "\nCapMapCharacterSet: " + remoteOrderDisplay.getCapMapCharacterSet();
			msg = msg + "\nCapSelectCharacterSet: " + remoteOrderDisplay.getCapSelectCharacterSet();
			msg = msg + "\nCapTone: " + remoteOrderDisplay.getCapTone();
			msg = msg + "\nCapTouch: " + remoteOrderDisplay.getCapTouch();

			JOptionPane.showMessageDialog(null, msg, "Info", JOptionPane.INFORMATION_MESSAGE);

		} catch (JposException jpe) {
			JOptionPane.showMessageDialog(null, "Exception in Info\nException: " + jpe.getMessage(),
					"Exception", JOptionPane.ERROR_MESSAGE);
			System.err.println("Jpos exception " + jpe);
		}
	}

	// Shows statistics of device
	@FXML
	public void handleStatistics(ActionEvent e) {
		String[] stats = new String[] { "", "U_", "M_" };
		try {
			remoteOrderDisplay.retrieveStatistics(stats);
		} catch (JposException jpe) {
			jpe.printStackTrace();
		}

		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new java.io.StringReader(stats[1])));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(stats[1].getBytes()));

			printStatistics(doc.getDocumentElement(), "");

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException saxe) {
			saxe.printStackTrace();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, statistics, "Statistics", JOptionPane.INFORMATION_MESSAGE);
		statistics = "";
	}

	// Method to parse the String XML and print the data
	private static void printStatistics(Node e, String tab) {
		if (e.getNodeType() == Node.TEXT_NODE) {
			statistics += tab + e.getNodeValue() + "\n";
			return;
		}

		if (!(e.getNodeName().equals("Name") || e.getNodeName().equals("Value")
				|| e.getNodeName().equals("UPOSStat") || e.getNodeName().equals("Event")
				|| e.getNodeName().equals("Equipment") || e.getNodeName().equals("Parameter")))
			statistics += tab + e.getNodeName();

		if (e.getNodeValue() != null) {
			statistics += tab + " " + e.getNodeValue();
		}

		NodeList childs = e.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			printStatistics(childs.item(i), " ");
		}
	}

	@FXML
	public void handleFirmware(ActionEvent e) {
		try {
			FirmwareUpdateDlg dlg = new FirmwareUpdateDlg(remoteOrderDisplay);
			dlg.setVisible(true);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Exception: " + e2.getMessage(), "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
}