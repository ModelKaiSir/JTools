package com.karys.jtools.menu;

import com.karys.jtools.enums.CodeGenerateEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public class ServiceCodeGenerateMenu extends AbstractCodeGenerateMenu {

    private StringProperty name = new SimpleStringProperty("Service层代码生成");

    @Override
    public String getName() {
        return name.get();
    }

    protected StringProperty nameProperty(){
        return name;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public CodeGenerateEnum getGenerateType() {
        return CodeGenerateEnum.SERVICE;
    }
}
