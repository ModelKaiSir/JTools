package com.karys.jtools.menu;

import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.views.WidgetMenuView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EntityCodeGenerateMenu extends AbstractCodeGenerateMenu {

    // CodeGenerateView

    private StringProperty name = new SimpleStringProperty("实体代码生成");

    @Override
    public String getName() {
        return name.get();
    }

    protected StringProperty nameProperty() {
        return name;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public CodeGenerateEnum getGenerateType() {
        return CodeGenerateEnum.ENTITY;
    }
}
