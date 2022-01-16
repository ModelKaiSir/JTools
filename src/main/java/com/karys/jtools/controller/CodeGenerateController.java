package com.karys.jtools.controller;

import com.alibaba.fastjson.JSONObject;
import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.karys.jtools.core.JavaCodeTemplateGenerateManager;
import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.mapstruct.GenerateConfigMapper;
import com.karys.jtools.mapstruct.SysDataBaseConfigMapper;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.sun.javafx.scene.SceneHelper;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Data;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

@FXMLController
public class CodeGenerateController extends AbstractFxmlController implements Initializable {

    @FXML
    public Button generate;

    @FXML
    public Button cancel;

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    @Resource
    private JavaCodeTemplateGenerateManager codeTemplateGenerateManager;

    private ObjectProperty<CodeGenerateEnum> generateType = new SimpleObjectProperty<>();

    @FXML
    private VBox container;

    @FXML
    private Pane formContainer;

    private GenerateFormProperty formProperty = new GenerateFormProperty();

    private Form generateForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ListProperty<String> projectList = new SimpleListProperty<>(FXCollections.observableArrayList(sysDataBaseConfigService.getProjectList()));
        ListProperty<String> packageList = new SimpleListProperty<>(FXCollections.observableArrayList(sysDataBaseConfigService.getObjectPackageList()));

        // add Form
        generateForm = Form.of(Group.of(Field.ofSingleSelectionType(projectList, formProperty.project).label("项目").required("请选择项目"),
                Field.ofSingleSelectionType(packageList, formProperty.packagePath).label("包名").required("请选择包路径"),
                Field.ofStringType(formProperty.tableNames).label("表名").required("表名不能为空").tooltip("多个用逗号分割"),
                Field.ofStringType(formProperty.author).label("作者")
        )).title("generate");


        formContainer.getChildren().add(new FormRenderer(generateForm));
    }

    public ObjectProperty<CodeGenerateEnum> generateTypeProperty() {
        return generateType;
    }

    @FXML
    public void onGenerateAction(ActionEvent event) {

        if (generateForm.validProperty().get()) {

            generateForm.persist();
            GenerateConfig config = GenerateConfigMapper.INSTANCE.generateToEntity(formProperty);
            codeTemplateGenerateManager.generate(sysDataBaseConfigService.getSysDataBaseConfig(), config);
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        // close
        ((Stage) container.getScene().getWindow()).close();
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
