package com.karys.jtools.controller;

import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.mapstruct.SysDataBaseConfigMapper;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.karys.jtools.validator.NotNullProperty;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

@FXMLController
public class SettingController extends AbstractFxmlController implements Initializable {

    @FXML
    public Button chooseFile;

    @FXML
    public Label chooseFilePath;

    @FXML
    public Button resetChoose;

    @FXML
    public TextField url;

    @FXML
    public TextField userName;

    @FXML
    public PasswordField password;

    @FXML
    public TextField author;

    @FXML
    private GridPane root;

    @FXML
    public ComboBox<String> driverComboBox;

    @Resource
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    private SettingProperty settingProperty = new SettingProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SysDataSourceConfig config = Optional.ofNullable(sysDataBaseConfigService.getSysDataBaseConfig()).orElseGet(SysDataSourceConfig::new);

        settingProperty.url.bind(this.url.textProperty());
        settingProperty.driver.bind(driverComboBox.valueProperty());
        settingProperty.userName.bind(this.userName.textProperty());
        settingProperty.password.bind(this.password.textProperty());
        settingProperty.author.bind(this.author.textProperty());
        settingProperty.rootPath.bind(this.chooseFilePath.textProperty());
        driverComboBox.itemsProperty().bind(sysDataBaseConfigService.driverListProperty());

        chooseFilePath.visibleProperty().bind(settingProperty.getChooseVisible());
        resetChoose.visibleProperty().bind(settingProperty.getChooseVisible());

        this.url.setText(config.getDataBaseUrl());
        this.driverComboBox.setValue(config.getDriverName());
        this.userName.setText(config.getUserName());
        this.password.setText(config.getPassword());
        this.author.setText(config.getAuthor());
        this.chooseFilePath.setText(config.getRootPath());
        settingProperty.chooseVisible.set(StringUtils.isNotBlank(config.getRootPath()));
    }

    @FXML
    public void save(ActionEvent actionEvent) {


        Set<ConstraintViolation<SettingProperty>> validate = validatorFactoryBean.validate(settingProperty);

        if (validate.isEmpty()) {
            sysDataBaseConfigService.setSysDataBaseConfig(SysDataBaseConfigMapper.INSTANCE.settingToEntity(settingProperty));
            Dialog l = new Alert(Alert.AlertType.INFORMATION, "保存成功");
            l.show();
        } else {
            new Alert(Alert.AlertType.WARNING, "保存失败").showAndWait();
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    public void openChooseFile(ActionEvent event) {

        DirectoryChooser chooser = new DirectoryChooser();
        File choose = chooser.showDialog(GUIState.getStage());

        if (null != choose) {
            chooseFilePath.setText(choose.getPath());
            settingProperty.getChooseVisible().set(true);
        }
    }

    @FXML
    public void resetChooseFile(ActionEvent event) {
        chooseFilePath.setText("");
        settingProperty.getChooseVisible().set(false);
    }

    @Data
    @ToString
    public class SettingProperty {

        @NotBlank
        private SimpleStringProperty url = new SimpleStringProperty();
        @NotBlank
        private SimpleStringProperty driver = new SimpleStringProperty();
        @NotBlank
        private SimpleStringProperty userName = new SimpleStringProperty();
        @NotBlank
        private SimpleStringProperty password = new SimpleStringProperty();
        @NotBlank
        private SimpleStringProperty author = new SimpleStringProperty();
        @NotBlank
        private SimpleStringProperty rootPath = new SimpleStringProperty();

        private BooleanProperty chooseVisible = new SimpleBooleanProperty(false);
    }
}
