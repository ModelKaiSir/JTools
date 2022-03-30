package com.karys.jtools.menu;

import com.karys.jtools.enums.CodeGenerateEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class ControllerCodeGenerateMenu extends AbstractCodeGenerateMenu {

    private StringProperty name = new SimpleStringProperty("控制层代码生成");

    @Override
    public String getName() {
        return name.get();
    }

    protected StringProperty nameProperty(){
        return name;
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public CodeGenerateEnum getGenerateType() {
        return CodeGenerateEnum.CONTROLLER;
    }
}
