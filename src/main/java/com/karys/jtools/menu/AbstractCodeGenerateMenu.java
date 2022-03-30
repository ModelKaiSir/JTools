package com.karys.jtools.menu;

import com.karys.jtools.enums.CodeGenerateEnum;
import com.karys.jtools.service.SysDataBaseConfigService;
import com.karys.jtools.views.CodeGenerateView;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

public abstract class AbstractCodeGenerateMenu implements Menu, InitializingBean {

    @Resource
    private CodeGenerateView codeGenerateView;

    @Resource
    private SysDataBaseConfigService sysDataBaseConfigService;

    @Override
    public void afterPropertiesSet() throws Exception {

        codeGenerateView.getPresenter().titleProperty().bind(nameProperty());

        if(null != sysDataBaseConfigService.getSysDataBaseConfig()){
            codeGenerateView.getPresenter().generateTypeProperty().bind(new SimpleObjectProperty<>(getGenerateType()));
        }
    }

    protected abstract Property<String> nameProperty();

    public abstract CodeGenerateEnum getGenerateType();

    @Override
    public void clean(){
        codeGenerateView.getPresenter().clean();
    }

    @Override
    public Parent getParent() {

        clean();
        return codeGenerateView.getView();
    }

    @Override
    public String toString() {
        return getName();
    }
}
