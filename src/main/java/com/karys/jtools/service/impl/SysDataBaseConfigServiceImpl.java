package com.karys.jtools.service.impl;

import com.karys.jtools.entity.SysDataSourceConfig;
import com.karys.jtools.service.SysDataBaseConfigService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class SysDataBaseConfigServiceImpl implements SysDataBaseConfigService {

    ObservableList<String> driverListProperty = FXCollections.observableArrayList("com.mysql.cj.jdbc.Driver");

    @Override
    public SysDataSourceConfig getSysDataBaseConfig() {
        return new SysDataSourceConfig();
    }

    @Override
    public ObservableValue<? extends ObservableList<String>> driverListProperty() {
        return new SimpleObjectProperty<>(driverListProperty);
    }
}
