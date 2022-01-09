package com.karys.jtools.controller;

import com.karys.jtools.core.JavaCodeTemplateGenerateManager;
import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.sun.javafx.scene.SceneHelper;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class CodeGenerateController extends AbstractFxmlController implements Initializable {

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    @Resource
    private JavaCodeTemplateGenerateManager codeTemplateGenerateManager;

    private ObjectProperty<CodeGenerateEnum> generateType = new SimpleObjectProperty<>();

    @FXML
    private VBox container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // add Form
        // container.getChildren().add();
    }

    public ObjectProperty<CodeGenerateEnum> generateTypeProperty() {
        return generateType;
    }

    @FXML
    public void onGenerateAction(ActionEvent event){
        // get Form Data
        codeTemplateGenerateManager.generate(sysDataBaseConfigService.getSysDataBaseConfig(), null);
    }

    @FXML
    public void cancel(ActionEvent event){
        // close
    }
}
