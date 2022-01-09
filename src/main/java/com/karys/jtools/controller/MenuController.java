package com.karys.jtools.controller;

import de.felixroske.jfxsupport.AbstractFxmlController;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends AbstractFxmlController implements Initializable {

    private StringProperty title = new SimpleStringProperty("");

    @FXML
    private Button action;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        action.textProperty().bind(title);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObjectProperty<EventHandler<ActionEvent>> actionProperty(){
        return action.onActionProperty();
    }
}
