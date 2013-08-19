/*
 * Copyright 2013 NTS New Technology Systems GmbH. All Rights reserved.
 * NTS PROPRIETARY/CONFIDENTIAL. Use is subject to NTS License Agreement.
 * Address: Doernbacher Strasse 126, A-4073 Wilhering, Austria
 * Homepage: www.ntswincash.com
 */
package postest2;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.swing.JOptionPane;

import jpos.Belt;
import jpos.JposException;

public class BeltController extends CommonController implements Initializable {


	@FXML
	public ComboBox<Boolean> autoStopBackward;
	@FXML
	public ComboBox<Boolean> autoStopForward;
	@FXML
	public ComboBox<Integer> moveBackward_speed;
	@FXML
	public ComboBox<Integer> moveForward_speed;
	@FXML
	public ComboBox<String> resetitemCount_direction;
	@FXML
	public ComboBox<String> adjustItemCount_direction;

	@FXML
	public TextField autoStopBackwardDelayTime;
	@FXML
	public TextField autoStopForwardDelayTime;
	@FXML
	public TextField adjustItemCount_Count;
	
	@FXML @RequiredState(JposState.ENABLED)
	public Pane functionPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		service = new Belt();
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
			if (deviceEnabled.isSelected()) {
				((Belt)service).setDeviceEnabled(true);
				setUpComboBoxes();
				
			} else {
				((Belt)service).setDeviceEnabled(false);
			}
		} catch (JposException je) {
			JOptionPane.showMessageDialog(null, je.getMessage());
		}
	}

	@FXML
	public void handleAutoStopBackward(ActionEvent e) {
		System.out.println("AutoStopBackward");
		if(autoStopBackward.getSelectionModel().getSelectedItem() != null){
			try {
				((Belt)service).setAutoStopBackward(autoStopBackward.getSelectionModel().getSelectedItem());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleAutoStopBackwardDelayTime(ActionEvent e) {
		System.out.println("AutoStopBWDT");
		if(!autoStopBackwardDelayTime.getText().isEmpty()){
			try {
				((Belt)service).setAutoStopBackwardDelayTime(Integer.parseInt(autoStopBackwardDelayTime.getText()));
			} catch (NumberFormatException e1){
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleAutoStopForward(ActionEvent e) {
		System.out.println("ASW");
		if(autoStopForward.getSelectionModel().getSelectedItem() != null){
			try {
				((Belt)service).setAutoStopForward(autoStopForward.getSelectionModel().getSelectedItem());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleAutoStopForwardDelayTime(ActionEvent e) {
		System.out.println("ASFDT");
		if(!autoStopForwardDelayTime.getText().isEmpty()){
			try {
				((Belt)service).setAutoStopForwardDelayTime(Integer.parseInt(autoStopForwardDelayTime.getText()));
			} catch (NumberFormatException e1){
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} 
		}
	}

	@FXML
	public void handleAdjustItemCount(ActionEvent e) {
		System.out.println("AIC");
		if(adjustItemCount_direction.getSelectionModel().getSelectedItem() != null){
	
			try {
				((Belt)service).adjustItemCount(BeltConstantMapper.getConstantNumberFromString(adjustItemCount_direction
						.getSelectionModel().getSelectedItem()),
						Integer.parseInt(adjustItemCount_Count.getText()));
			} catch (NumberFormatException e1){
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleMoveBackward(ActionEvent e) {
		System.out.println("MB");
		if(moveBackward_speed.getSelectionModel().getSelectedItem() != null){
			try {
				((Belt)service).moveBackward(moveBackward_speed.getSelectionModel().getSelectedItem());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleMoveForward(ActionEvent e) {
		System.out.println("MF");
		if(moveForward_speed.getSelectionModel().getSelectedItem() != null){
			try {
				((Belt)service).moveForward(moveForward_speed.getSelectionModel().getSelectedItem());
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleResetBelt(ActionEvent e) {
		System.out.println("RB");
		try {
			((Belt)service).resetBelt();
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	@FXML
	public void handleResetItemCount(ActionEvent e) {
		System.out.println("RIC");
		if(resetitemCount_direction.getSelectionModel().getSelectedItem() != null){
			try {
				((Belt)service).resetItemCount(BeltConstantMapper.getConstantNumberFromString(resetitemCount_direction
						.getSelectionModel().getSelectedItem()));
			} catch (JposException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}

	@FXML
	public void handleStopBelt(ActionEvent e) {
		System.out.println("SB");
		try {
			((Belt)service).stopBelt();
		} catch (JposException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}

	/*
	 * Initialize ComboBoxes
	 */

	private void setUpAutoStopBackward() {
		autoStopBackward.getItems().clear();
		autoStopBackward.getItems().add(true);
		autoStopBackward.getItems().add(false);
		autoStopBackward.setValue(true);
	}

	private void setUpAutoStopForward() {
		autoStopForward.getItems().clear();
		autoStopForward.getItems().add(true);
		autoStopForward.getItems().add(false);
		autoStopForward.setValue(true);
	}

	private void setUpAdjustItemCountDirection() {
		adjustItemCount_direction.getItems().clear();
		adjustItemCount_direction.getItems().add(BeltConstantMapper.BELT_AIC_BACKWARD.getConstant());
		adjustItemCount_direction.getItems().add(BeltConstantMapper.BELT_AIC_FORWARD.getConstant());
		adjustItemCount_direction.setValue(BeltConstantMapper.BELT_AIC_FORWARD.getConstant());
	}


	// Cannot implement correctly because the Variable CapSpeedStepsBackward of
	// the JPOS Class Belt is not implemented correctly
	// Wrong Datatype (boolean instead of int)
	private void setUpMoveBackwardSpeed() {
		// int speedSteps;
		// for(int i = 0; i < belt.getcaps.getCapSpeedStepsBackward();i++){
		// }
		moveBackward_speed.getItems().clear();
		moveBackward_speed.getItems().add(1);
		moveBackward_speed.getItems().add(2);
		moveBackward_speed.setValue(1);

	}

	// Cannot implement correctly because the Variable CapSpeedStepsBackward of
	// the JPOS Class Belt is not implemented correctly
	// Wrong Datatype (boolean instead of int)
	private void setUpMoveForwardSpeed() {
		// int speedSteps;
		// for(int i = 0; i < belt.getCapSpeedStepsBackward();i++){
		// }
		moveForward_speed.getItems().clear();
		moveForward_speed.getItems().add(1);
		moveForward_speed.getItems().add(2);
		moveForward_speed.setValue(1);
	}

	private void setUpResetItemCount() {
		resetitemCount_direction.getItems().clear();
		resetitemCount_direction.getItems().add(BeltConstantMapper.BELT_RIC_BACKWARD.getConstant());
		resetitemCount_direction.getItems().add(BeltConstantMapper.BELT_RIC_FORWARD.getConstant());
		resetitemCount_direction.setValue(BeltConstantMapper.BELT_RIC_FORWARD.getConstant());
	}

	private void setUpComboBoxes() {
		setUpAutoStopBackward();
		setUpAutoStopForward();
		setUpAdjustItemCountDirection();
		setUpMoveBackwardSpeed();
		setUpMoveForwardSpeed();
		setUpResetItemCount();

	}

	private void setUpLogicalNameComboBox() {
		if(!LogicalNameGetter.getLogicalNamesByCategory("Belt").isEmpty()){
			logicalName.setItems(LogicalNameGetter.getLogicalNamesByCategory("Belt"));
		}
	}

	
}
