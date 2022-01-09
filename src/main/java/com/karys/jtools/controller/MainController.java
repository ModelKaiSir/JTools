package com.karys.jtools.controller;

import com.karys.jtools.menu.Menu;
import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FXMLController
public class MainController extends AbstractFxmlController implements Initializable {

    @FXML
    private FlowPane container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenuWidgets(getBeanList(Menu.class));
    }

    private void loadMenuWidgets(Collection<Menu> beanList) {
        container.getChildren().addAll(beanList.stream().map(Menu::getParent).collect(Collectors.toList()));
    }
}
