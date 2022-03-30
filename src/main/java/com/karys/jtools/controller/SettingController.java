package com.karys.jtools.controller;

import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.mapstruct.SysDataBaseConfigMapper;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.karys.jtools.service.impl.SysDataBaseConfigServiceImpl;
import com.karys.jtools.utils.ValidateUtil;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.dialogs.MFXDialogs;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import lombok.Data;
import lombok.ToString;
import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.TooltipWrapper;
import net.synedra.validatorfx.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

@FXMLController
public class SettingController extends AbstractFxmlController implements Initializable {

    @FXML
    public MFXButton chooseFile;

    @FXML
    public Label chooseFilePath;

    @FXML
    public MFXButton resetChoose;

    @FXML
    public MFXTextField url;

    @FXML
    public MFXTextField userName;

    @FXML
    public MFXPasswordField password;

    @FXML
    public MFXTextField author;

    @FXML
    private GridPane root;

    @FXML
    public MFXLegacyComboBox<String> driverComboBox;

    @FXML
    private MFXButton save;

    @FXML
    private AnchorPane toolbar;

    @Resource
    private LocalValidatorFactoryBean validatorFactoryBean;

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    private SettingProperty settingProperty = new SettingProperty();

    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SysDataSourceConfig config = Optional.ofNullable(sysDataBaseConfigService.getSysDataBaseConfig()).orElseGet(SysDataSourceConfig::new);

        // tips
        // MFXTooltip.of(driverComboBox, "This combo box allows you to add new items to the list (no duplicates allowed) when pressing Enter. It also allows to restore the previous selected item by pressing Ctrl+Shift+Z. Both key strokes are default for all MFXComboBoxes but the action to perform must be configured by the user. This combo box is also set to scroll to the selected item when opening the popup.").install();

        settingProperty.url.bind(this.url.textProperty());
        settingProperty.driver.bind(driverComboBox.valueProperty());
        settingProperty.userName.bind(this.userName.textProperty());
        settingProperty.password.bind(this.password.textProperty());
        settingProperty.author.bind(this.author.textProperty());
        settingProperty.rootPath.bind(this.chooseFilePath.textProperty());

        driverComboBox.setItems(SysDataBaseConfigServiceImpl.driverList);

        chooseFilePath.visibleProperty().bind(settingProperty.getChooseVisible());
        resetChoose.visibleProperty().bind(settingProperty.getChooseVisible());

        reset(config);

        settingProperty.chooseVisible.set(StringUtils.isNotBlank(config.getRootPath()));

        validator.createCheck().dependsOn("url", this.url.textProperty()).withMethod(c -> ValidateUtil.notNull(c.get("url"), c)).decorates(this.url).immediate();
        validator.createCheck().dependsOn("driver", this.driverComboBox.valueProperty()).withMethod(c -> ValidateUtil.notNull(c.get("driver"), c)).decorates(this.driverComboBox).immediate();
        validator.createCheck().dependsOn("userName", this.userName.textProperty()).withMethod(c -> ValidateUtil.notNull(c.get("userName"), c)).decorates(this.userName).immediate();
        validator.createCheck().dependsOn("password", this.password.textProperty()).withMethod(c -> ValidateUtil.notNull(c.get("password"), c)).decorates(this.password).immediate();
        validator.createCheck().dependsOn("author", this.author.textProperty()).withMethod(c -> ValidateUtil.notNull(c.get("author"), c)).decorates(this.author).immediate();
        validator
                .createCheck()
                .dependsOn("chooseFilePath", this.chooseFilePath.textProperty())
                .withMethod(c -> ValidateUtil.notNull(c.get("chooseFilePath"), c))
                .decorates(this.chooseFilePath)
                .immediate();

        settingProperty.unReady.bind(validator.containsErrorsProperty());

        TooltipWrapper<MFXButton> signUpWrapper = new TooltipWrapper<>(save, validator.containsErrorsProperty(), Bindings.concat("Cannot sign up:\n", validator.createStringBinding()));
        toolbar.getChildren().add(signUpWrapper);
    }

    public void reset(SysDataSourceConfig config) {
        this.url.setText(config.getDataBaseUrl());
        this.driverComboBox.setValue(config.getDriverName());
        this.userName.setText(config.getUserName());
        this.password.setText(config.getPassword());
        this.author.setText(config.getAuthor());
        this.chooseFilePath.setText(config.getRootPath());
    }

    public void reset() {
        SysDataSourceConfig config = Optional.ofNullable(sysDataBaseConfigService.getSysDataBaseConfig()).orElseGet(SysDataSourceConfig::new);
        reset(config);
    }

    @FXML
    public void save(ActionEvent actionEvent) {

        // Set<ConstraintViolation<SettingProperty>> validate = validatorFactoryBean.validate(settingProperty);
        // boolean warn = validator.containsWarnings();

        boolean success = validator.validate();

        if (success) {
            sysDataBaseConfigService.setSysDataBaseConfig(SysDataBaseConfigMapper.INSTANCE.settingToEntity(settingProperty));
            MFXDialogs.info().setHeaderText("系统提示").setContentText("保存成功").toStageDialogBuilder().initModality(Modality.APPLICATION_MODAL).get().showDialog();
        } else {
            MFXDialogs.error().setHeaderText("错误").setContentText("保存失败").toStageDialogBuilder().initModality(Modality.APPLICATION_MODAL).get().showDialog();
        }
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

    public boolean isUnReady() {

        if (sysDataBaseConfigService.getConfigFlag()) {
            return true;
        }
        return settingProperty.unReady.get();
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

        private BooleanProperty unReady = new SimpleBooleanProperty(false);
    }
}
