package com.karys.jtools.service;

import com.karys.jtools.entity.SysDataSourceConfig;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public interface SysDataBaseConfigService {

    SysDataSourceConfig getSysDataBaseConfig();

    ObservableValue<? extends ObservableList<String>> driverListProperty();
}
