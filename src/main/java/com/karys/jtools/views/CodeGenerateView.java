package com.karys.jtools.views;

import com.karys.jtools.controller.CodeGenerateController;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.context.annotation.Scope;

@FXMLView(value = "/views/code_generate.fxml", title = "ไปฃ็ ็ๆ")
@Scope("prototype")
public class CodeGenerateView extends AbstractFxmlView<CodeGenerateController> {

}
