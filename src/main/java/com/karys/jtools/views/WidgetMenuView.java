package com.karys.jtools.views;

import com.karys.jtools.controller.MenuController;
import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.context.annotation.Scope;

@FXMLView(value = "/views/widget_menu.fxml", title = "menu")
@Scope("prototype")
public class WidgetMenuView extends AbstractFxmlView<MenuController> {

}
