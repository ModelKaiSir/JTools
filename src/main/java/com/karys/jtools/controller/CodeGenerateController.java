package com.karys.jtools.controller;

import com.karys.jtools.core.JavaCodeTemplateGenerateManager;
import com.karys.jtools.core.notify.MessageNotification;
import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.mapstruct.GenerateConfigMapper;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.karys.jtools.utils.TaskUtil;
import com.karys.jtools.utils.ValidateUtil;
import com.karys.jtools.views.CodeGenerateView;
import de.felixroske.jfxsupport.AbstractFxmlController;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.enums.NotificationPos;
import io.github.palexdev.materialfx.notifications.MFXNotificationCenterSystem;
import io.github.palexdev.materialfx.notifications.MFXNotificationSystem;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.SneakyThrows;
import net.synedra.validatorfx.Validator;

import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CodeGenerateController extends AbstractFxmlController implements Initializable {

    @FXML
    public MFXButton action;

    @FXML
    private Text title;

    @FXML
    private MFXLegacyComboBox<String> projectComboBox;

    @FXML
    private MFXLegacyComboBox<String> packageComboBox;

    @FXML
    private MFXTextField author;

    @FXML
    private MFXTextField tableNames;

    private SysDataBaseConfigService sysDataBaseConfigService;

    private JavaCodeTemplateGenerateManager codeTemplateGenerateManager;

    private ObjectProperty<CodeGenerateEnum> generateType = new SimpleObjectProperty<>();

    private GenerateFormProperty formProperty = new GenerateFormProperty();

    private Validator validator = new Validator();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sysDataBaseConfigService = getBean(SysDataBaseConfigService.class);
        codeTemplateGenerateManager = getBean(JavaCodeTemplateGenerateManager.class);
        generateFormNode();

        validator.createCheck().dependsOn("project", this.projectComboBox.valueProperty()).withMethod(c -> ValidateUtil.notNull(c.get("project"), c)).decorates(this.projectComboBox).immediate();
        validator
                .createCheck()
                .dependsOn("packagePath", this.packageComboBox.valueProperty())
                .withMethod(c -> ValidateUtil.notNull(c.get("packagePath"), c))
                .decorates(this.packageComboBox)
                .immediate();
        validator.createCheck().dependsOn("tableNames", this.tableNames.textProperty()).withMethod(c -> ValidateUtil.notNull(c.get("tableNames"), c)).decorates(this.tableNames).immediate();
    }

    private void generateFormNode() {

        // data bind
        projectComboBox.setItems(FXCollections.observableArrayList(sysDataBaseConfigService.getProjectList()));
        packageComboBox.setItems(FXCollections.observableArrayList(sysDataBaseConfigService.getObjectPackageList()));

        formProperty.project.bind(projectComboBox.valueProperty());
        formProperty.packagePath.bind(packageComboBox.valueProperty());
        formProperty.author.bind(author.textProperty());
        formProperty.tableNames.bind(tableNames.textProperty());
    }

    public ObjectProperty<CodeGenerateEnum> generateTypeProperty() {
        return generateType;
    }

    public StringProperty titleProperty() {
        return title.textProperty();
    }

    @FXML
    public void onGenerateAction(ActionEvent event) {

        if (getBean(SettingController.class).isUnReady()) {
            MessageNotification message = new MessageNotification();
            message.setTitleText("警告");
            message.setContentText("请先进行全局设置！");
            MFXNotificationSystem.instance().setPosition(NotificationPos.BOTTOM_RIGHT).publish(message);
            return;
        }

        if (!validator.validate()) {
            MessageNotification message = new MessageNotification();
            message.setTitleText("警告");
            message.setContentText("必填项不能为空！");
            MFXNotificationSystem.instance().setPosition(NotificationPos.BOTTOM_RIGHT).publish(message);
            return;
        }

        MFXProgressSpinner progressSpinner = new MFXProgressSpinner();
        getBean(MainController.class).getRightContainer().getChildren().add(progressSpinner);

        TaskUtil.simple(() -> {
            GenerateConfig config = GenerateConfigMapper.INSTANCE.generateToEntity(formProperty);
            codeTemplateGenerateManager.generate(generateType.get(), sysDataBaseConfigService.getSysDataBaseConfig(), config);
            TimeUnit.SECONDS.sleep(3);
            return 1;
        }, e -> {
            getBean(MainController.class).getRightContainer().getChildren().remove(progressSpinner);

            MessageNotification message = new MessageNotification();
            message.setTitleText("提示");
            message.setContentText("生成完成！");
            MFXNotificationSystem.instance().setPosition(NotificationPos.BOTTOM_RIGHT).publish(message);
        });
    }

    public void clean() {
        projectComboBox.setValue(null);
        packageComboBox.setValue(null);
        author.textProperty().setValue("");
        tableNames.textProperty().setValue("");
    }

    @Data
    public class GenerateFormProperty {

        private ObjectProperty<String> project = new SimpleObjectProperty<>();

        private ObjectProperty<String> packagePath = new SimpleObjectProperty<>();

        @NotBlank
        private StringProperty author = new SimpleStringProperty("");

        @NotBlank
        private StringProperty tableNames = new SimpleStringProperty("");
    }
}
