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
import jpos.CashChanger;
import jpos.JposConst;
import jpos.JposException;

public class CashChangerController implements Initializable {

	@FXML
	private ComboBox<String> logicalName;
	private CashChanger cashChanger;
	private static String statistics = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cashChanger = new CashChanger();
	}

	/* ************************************************************************
	 * ************************ Action Handler *********************************
	 * ***********************************************************************
	 */

	// Shows information of device
	@FXML
	public void handleInfo(ActionEvent e) {
		try {
			String ver = new Integer(cashChanger.getDeviceServiceVersion()).toString();
			String msg = "Service Description: " + cashChanger.getDeviceServiceDescription();
			msg = msg + "\nService Version: v" + new Integer(ver.substring(0, 1)) + "."
					+ new Integer(ver.substring(1, 4)) + "." + new Integer(ver.substring(4, 7));
			ver = new Integer(cashChanger.getDeviceControlVersion()).toString();
			msg += "\n\nControl Description: " + cashChanger.getDeviceControlDescription();
			msg += "\nControl Version: v" + new Integer(ver.substring(0, 1)) + "."
					+ new Integer(ver.substring(1, 4)) + "." + new Integer(ver.substring(4, 7));
			msg += "\n\nPhysical Device Name: " + cashChanger.getPhysicalDeviceName();
			msg += "\nPhysical Device Description: " + cashChanger.getPhysicalDeviceDescription();

			msg += "\n\nProperties:\n------------------------";

			msg += "\nCapStatisticsReporting: " + (cashChanger.getCapStatisticsReporting());

			msg += "\nCapUpdateFirmware: " + (cashChanger.getCapUpdateFirmware());

			msg += "\nCapCompareFirmwareVersion: " + (cashChanger.getCapCompareFirmwareVersion());

			msg += "\nCapPowerReporting: "
					+ (cashChanger.getCapPowerReporting() == JposConst.JPOS_PR_ADVANCED ? "Advanced"
							: (cashChanger.getCapPowerReporting() == JposConst.JPOS_PR_STANDARD ? "Standard"
									: "None"));

			msg = msg + "\nCapDeposit: " + cashChanger.getCapDeposit();
			msg = msg + "\nCapDepositDataEvent: " + cashChanger.getCapDepositDataEvent();
			msg = msg + "\nCapDiscrepancy: " + cashChanger.getCapDiscrepancy();
			msg = msg + "\nCapEmptySensor: " + cashChanger.getCapEmptySensor();
			msg = msg + "\nCapFullSensor: " + cashChanger.getCapFullSensor();
			msg = msg + "\nCapJamSensor: " + cashChanger.getCapJamSensor();
			msg = msg + "\nCapNearFullSensor: " + cashChanger.getCapNearFullSensor();
			msg = msg + "\nCapNearEmptySensor: " + cashChanger.getCapNearEmptySensor();
			msg = msg + "\nCapPauseDeposit: " + cashChanger.getCapPauseDeposit();
			msg = msg + "\nCapRealTimeData: " + cashChanger.getCapRealTimeData();
			msg = msg + "\nCapRepayDeposit: " + cashChanger.getCapRepayDeposit();
			

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
			cashChanger.retrieveStatistics(stats);
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
			FirmwareUpdateDlg dlg = new FirmwareUpdateDlg(cashChanger);
			dlg.setVisible(true);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, "Exception: " + e2.getMessage(), "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}