package com.karys.jtools.menu;

import com.karys.jtools.entity.GenerateConfig;
import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.views.CodeGenerateView;
import com.karys.jtools.views.WidgetMenuView;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

public abstract class AbstractCodeGenerateMenu implements Menu, InitializingBean {

    @Resource
    protected WidgetMenuView menuView;

    @Resource
    private CodeGenerateView codeGenerateView;

    @Override
    public void afterPropertiesSet() throws Exception {
        menuView.getPresenter().titleProperty().bindBidirectional(nameProperty());
        menuView.getPresenter().actionProperty().bind(this.onActionProperty());
    }

    protected abstract Property<String> nameProperty();

    protected ObservableValue<? extends EventHandler<ActionEvent>> onActionProperty(){

        return new SimpleObjectProperty<>(e -> {
            codeGenerateView.getPresenter().generateTypeProperty().bind(new SimpleObjectProperty<>(getGenerateType()));
            codeGenerateView.showView(Modality.APPLICATION_MODAL);
        });
    }

    public abstract CodeGenerateEnum getGenerateType();

    @Override
    public Parent getParent() {
        Pane container = new Pane();
        container.getChildren().add(menuView.getView());
        return container;
    }
}
