package com.karys.jtools.menu;

import com.karys.jtools.enums.CodeGenerateEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

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
