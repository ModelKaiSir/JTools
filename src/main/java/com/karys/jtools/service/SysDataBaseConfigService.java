package com.karys.jtools.service;

import com.karys.jtools.entity.SysDataSourceConfig;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.List;

public interface SysDataBaseConfigService {

    SysDataSourceConfig getSysDataBaseConfig();

    boolean getConfigFlag();

    void setSysDataBaseConfig(SysDataSourceConfig config);

    void setConfigFlag(Integer i);

    ObservableValue<? extends ObservableList<String>> driverListProperty();

    List<String > getProjectList();

    List<String > getObjectPackageList();
}
