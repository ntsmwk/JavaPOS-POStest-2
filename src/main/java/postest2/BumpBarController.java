package postest2;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import jpos.BumpBar;
import jpos.JposException;


public class BumpBarController extends CommonController implements Initializable {

	@FXML
	@RequiredState(JposState.ENABLED)
	public TabPane functionPane;
	@FXML
	@RequiredState(JposState.ENABLED)
	public CheckBox asyncMode;
	@FXML
	public TextField autoToneDuration;
	@FXML
	public TextField autoToneFrequency;
	@FXML
	public TextField currentUnitID;
	@FXML
	public TextField timeout;
	@FXML
	public TextField bumpBarSound_units;
	@FXML
	public TextField bumpBarSound_frequency;
	@FXML
	public TextField bumpbarSound_duration;
	@FXML
	public TextField bumpBarSound_numberOfCycles;
	@FXML
	public TextField bumpBarSound_interSoundWait;
	@FXML
	public TextField setKeyTranslation_units;
	@FXML
	public TextField setKeyTranslation_scanCode;
	@FXML
	public TextField setKeyTranslation_logicalKey;
	

	@FXML
	public ComboBox<String> checkHealth_level;
	
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		service = new BumpBar();
		setUpLogicalNameComboBox();
		RequiredStateChecker.invokeThis(this, service);
	}

	/* ************************************************************************
	 * ************************ Action Handler *********************************
	 * ***********************************************************************
	 */

	@FXML
	public void handleDeviceEnable(ActionEvent e) {
		System.out.println("DevEnable");
		try {
			((BumpBar) service).setDeviceEnabled(deviceEnabled.isSelected());
		} catch (JposException je) {
			JOptionPane.showMessageDialog(null, je.getMessage());
		}
		setUpCheckHealthLevel();
		RequiredStateChecker.invokeThis(this, service);
	}

	@FXML
	public void handleAsyncMode(ActionEvent e) {
		System.out.println("async");
		try {
			((BumpBar) service).setAsyncMode(asyncMode.isSelected());
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}

	@FXML
	public void handleSetAutoToneDuration(ActionEvent e) {
		System.out.println("setAutoDur");
		if (autoToneDuration.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field is empty!");
		} else {
			try {
				((BumpBar) service).setAutoToneDuration(Integer.parseInt(autoToneDuration.getText()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void handleSetAutoToneFrequency(ActionEvent e) {
		System.out.println("setautoFre");
		if (autoToneFrequency.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field is empty!");
		} else {
			try {
				((BumpBar) service).setAutoToneFrequency(Integer.parseInt(autoToneFrequency.getText()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void handleSetCurrentUnitID(ActionEvent e) {
		System.out.println("setCurrentID");
		if (currentUnitID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field is empty!");
		} else {
			try {
				((BumpBar) service).setCurrentUnitID(Integer.parseInt(currentUnitID.getText()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void handleSetTimeout(ActionEvent e) {
		System.out.println("setTimeout");
		if (timeout.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Field is empty!");
		} else {
			try {
				((BumpBar) service).setTimeout(Integer.parseInt(timeout.getText()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void handleCheckHealth(ActionEvent e) {
		System.out.println("checkhealt");
		
		try {
			((BumpBar) service).checkHealth(CommonConstantMapper.getConstantNumberFromString(checkHealth_level.getSelectionModel().getSelectedItem()));
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
		
	}

	@FXML
	public void handleClearInput(ActionEvent e) {
		System.out.println("clearin");
		try {
			((BumpBar) service).clearInput();
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}

	@FXML
	public void handleClearOutput(ActionEvent e) {
		System.out.println("clearouts");
		try {
			((BumpBar) service).clearOutput();
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
			e1.printStackTrace();
		}
	}

	@FXML
	public void handleBumpBarSound(ActionEvent e) {
		System.out.println("sound");
		if (bumpbarSound_duration.getText().isEmpty() || bumpBarSound_frequency.getText().isEmpty()
				|| bumpBarSound_interSoundWait.getText().isEmpty()
				|| bumpBarSound_numberOfCycles.getText().isEmpty() || bumpBarSound_units.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Every Field should have a value!");
		} else {
			try {
				((BumpBar) service).bumpBarSound(Integer.parseInt(bumpBarSound_units.getText()),
						Integer.parseInt(bumpBarSound_frequency.getText()),
						Integer.parseInt(bumpbarSound_duration.getText()),
						Integer.parseInt(bumpBarSound_numberOfCycles.getText()),
						Integer.parseInt(bumpBarSound_interSoundWait.getText()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void handleSetKeyTranslation(ActionEvent e) {
		System.out.println("setKey");
		if (setKeyTranslation_logicalKey.getText().isEmpty()
				|| setKeyTranslation_scanCode.getText().isEmpty()
				|| setKeyTranslation_units.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Every Field should have a value!");
		} else {
			try {
				((BumpBar) service).setKeyTranslation(Integer.parseInt(setKeyTranslation_units.getText()),
						Integer.parseInt(setKeyTranslation_scanCode.getText()),
						Integer.parseInt(setKeyTranslation_logicalKey.getText()));

			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	private void setUpCheckHealthLevel(){
		checkHealth_level.getItems().clear();
		checkHealth_level.getItems().add(CommonConstantMapper.JPOS_CH_INTERNAL.getConstant());
		checkHealth_level.getItems().add(CommonConstantMapper.JPOS_CH_EXTERNAL.getConstant());
		checkHealth_level.getItems().add(CommonConstantMapper.JPOS_CH_INTERACTIVE.getConstant());
		checkHealth_level.setValue(CommonConstantMapper.JPOS_CH_INTERNAL.getConstant());
	}
	

	private void setUpLogicalNameComboBox() {
		if (!LogicalNameGetter.getLogicalNamesByCategory("BumpBar").isEmpty()) {
			logicalName.setItems(LogicalNameGetter.getLogicalNamesByCategory("BumpBar"));
		}
	}
	

}
