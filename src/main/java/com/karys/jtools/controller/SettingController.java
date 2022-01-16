package com.karys.jtools.controller;

import com.karys.jtools.service.SysDataBaseConfigService;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class SettingController extends AbstractFxmlController implements Initializable {

    @FXML
    public ComboBox<String> driverComboBox;

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverComboBox.itemsProperty().bind(sysDataBaseConfigService.driverListProperty());
    }

    @FXML
    public void save(ActionEvent actionEvent) {

    }

    @FXML
    public void cancel(ActionEvent actionEvent) {

    }
}
